/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.grossopa.selenium.recorder.scan;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorContext;
import com.github.grossopa.selenium.recorder.model.LocatorContext.AncestorInfo;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.scan.strategy.*;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static java.util.Objects.requireNonNull;

/**
 * The default implementation of {@link ElementScanner} that evaluates one JavaScript snippet to walk through the DOM
 * and collect all key interactive elements in one round trip.
 *
 * <p>It locates interactive elements by built-in tag names and ARIA roles plus the user defined extra selectors,
 * collects the configured key attributes (id, name and customized ones) and marks each found element with a temporary
 * attribute {@value #MARKER_ATTRIBUTE} so that the element could be re-located by its scan index afterwards.</p>
 *
 * <p>The locator candidates are built by a list of {@link LocatorCandidateStrategy} instances returned from
 * {@link #createDefaultLocatorCandidateStrategies(RecorderConfig)}. If none of the strategies produces a candidate
 * for an element, that element is excluded from the scan result.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see LocatorCandidateStrategy
 */
public class DefaultElementScanner implements ElementScanner {

    /**
     * The temporary attribute written to each scanned element for re-locating by scan index.
     */
    public static final String MARKER_ATTRIBUTE = "data-hamster-rec-idx";

    /**
     * The built-in CSS selector for finding key interactive elements.
     */
    public static final String DEFAULT_INTERACTIVE_SELECTOR = "button, a, input, select, textarea, "
            + "[role=button], [role=link], [role=textbox], [role=checkbox], [role=radio], [role=tab], "
            + "[role=menuitem], [role=combobox], [role=switch], [role=slider]";

    /**
     * The maximum length of the visible text to be kept in the scan result and used as text locator.
     */
    public static final int MAX_TEXT_LENGTH = 80;

    /**
     * The maximum depth to traverse when looking for an ancestor anchor.
     *
     * @since 1.16
     */
    public static final int MAX_ANCESTOR_DEPTH = 10;

    private static final String CLEAR_AND_SCAN_SCRIPT = """
            var selector = arguments[0];
            var attrs = arguments[1];
            var markerAttr = arguments[2];
            var maxTextLen = arguments[3];
            var anchorAttrs = arguments[4];
            // Clear markers from any previous scan to ensure indices start from 0
            document.querySelectorAll('[' + markerAttr + ']').forEach(function(e) {
                e.removeAttribute(markerAttr);
            });
            var results = [];
            var elems = document.querySelectorAll(selector);
            for (var i = 0; i < elems.length; i++) {
                var el = elems[i];
                var rect = el.getBoundingClientRect();
                var style = window.getComputedStyle(el);
                var obj = {tagName: el.tagName.toLowerCase(),
                    text: (el.innerText || '').trim().substring(0, maxTextLen), attributes: {},
                    ancestor: null, siblingCount: 1, nameSiblingCounts: {}};
                for (var j = 0; j < attrs.length; j++) {
                    var value = el.getAttribute(attrs[j]);
                    if (value) {
                        obj.attributes[attrs[j]] = value;
                    }
                }
                // ancestor traversal
                var current = el.parentElement;
                var childPath = '';
                var firstClass = '';
                if (el.className && typeof el.className === 'string') {
                    var cls = el.className.trim().split(/\\s+/);
                    for (var c = 0; c < cls.length; c++) {
                        if (cls[c].length > 2 && !cls[c].startsWith('css-')) {
                            firstClass = '.' + cls[c];
                            break;
                        }
                    }
                }
                childPath = firstClass || el.tagName.toLowerCase();
                var depth = 0;
                while (current && depth < 10) {
                    if (current.id) {
                        obj.ancestor = {id: current.id, childPath: childPath};
                        break;
                    }
                    var foundAnchor = false;
                    for (var a = 0; a < anchorAttrs.length; a++) {
                        if (anchorAttrs[a] !== 'id' && anchorAttrs[a] !== 'name') {
                            var av = current.getAttribute(anchorAttrs[a]);
                            if (av) {
                                obj.ancestor = {testId: av, anchorAttr: anchorAttrs[a], childPath: childPath};
                                foundAnchor = true;
                                break;
                            }
                        }
                    }
                    if (foundAnchor) break;
                    var seg = current.tagName.toLowerCase();
                    if (current.className && typeof current.className === 'string') {
                        var classes = current.className.trim().split(/\\s+/);
                        for (var k = 0; k < classes.length; k++) {
                            if (classes[k].length > 2 && !classes[k].startsWith('css-')) {
                                seg = '.' + classes[k];
                                break;
                            }
                        }
                    }
                    childPath = seg + ' > ' + childPath;
                    current = current.parentElement;
                    depth++;
                }
                // sibling count and name counts
                var parent = el.parentElement;
                if (parent) {
                    var siblings = parent.querySelectorAll(':scope > ' + el.tagName.toLowerCase());
                    obj.siblingCount = siblings.length;
                    var nameCounts = {};
                    for (var s = 0; s < siblings.length; s++) {
                        var n = siblings[s].getAttribute('name');
                        if (n) { nameCounts[n] = (nameCounts[n] || 0) + 1; }
                    }
                    obj.nameSiblingCounts = nameCounts;
                }
                el.setAttribute(markerAttr, String(results.length));
                results.push(obj);
            }
            return results;
            """;

    private final RecorderConfig config;
    private final List<LocatorCandidateStrategy> strategies;

    /**
     * Constructs an instance with the recorder configuration and the default locator candidate strategies created by
     * {@link #createDefaultLocatorCandidateStrategies(RecorderConfig)}.
     *
     * @param config the recorder configuration, must not be null
     */
    public DefaultElementScanner(RecorderConfig config) {
        this(config, createDefaultLocatorCandidateStrategies(config));
    }

    /**
     * Constructs an instance with the recorder configuration and a customized list of locator candidate strategies.
     *
     * @param config the recorder configuration, must not be null
     * @param strategies the locator candidate strategies, must not be null
     */
    public DefaultElementScanner(RecorderConfig config, List<LocatorCandidateStrategy> strategies) {
        this.config = requireNonNull(config);
        this.strategies = List.copyOf(requireNonNull(strategies));
    }

    /**
     * Gets the locator candidate strategies used by this scanner.
     *
     * @return the unmodifiable list of locator candidate strategies
     */
    public List<LocatorCandidateStrategy> getStrategies() {
        return strategies;
    }

    /**
     * Creates the default list of locator candidate strategies: {@link IdLocatorCandidateStrategy},
     * {@link NameLocatorCandidateStrategy}, {@link CustomAttributeLocatorCandidateStrategy},
     * {@link AncestorLocatorCandidateStrategy} and {@link SiblingListLocatorCandidateStrategy}.
     *
     * <p>The {@link TextLocatorCandidateStrategy} and {@link MarkerLocatorCandidateStrategy} are intentionally excluded
     * from the default list. The text strategy is opt-in because text-based locators are often fragile. The marker
     * strategy always produces a candidate and would prevent the scanner from filtering out elements that do not match
     * any meaningful strategy. The marker candidate is appended automatically by the scanner after at least one
     * strategy has matched.</p>
     *
     * @param config the recorder configuration providing the key attributes
     * @return the default list of locator candidate strategies
     */
    public static List<LocatorCandidateStrategy> createDefaultLocatorCandidateStrategies(RecorderConfig config) {
        return List.of(new IdLocatorCandidateStrategy(), new NameLocatorCandidateStrategy(),
                new CustomAttributeLocatorCandidateStrategy(config.getKeyAttributes()),
                new AncestorLocatorCandidateStrategy(), new SiblingListLocatorCandidateStrategy());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScannedElement> scan(ComponentWebDriver driver) {
        Object result = driver.executeScript(CLEAR_AND_SCAN_SCRIPT, buildSelector(), config.getKeyAttributes(),
                MARKER_ATTRIBUTE, MAX_TEXT_LENGTH, config.getKeyAttributes());
        List<Map<String, Object>> rawElements = result instanceof List<?> list ? (List<Map<String, Object>>) list
                : List.of();
        List<ScannedElement> scannedElements = new ArrayList<>();
        for (int i = 0; i < rawElements.size(); i++) {
            ScannedElement element = toScannedElement(i, rawElements.get(i));
            if (!element.getLocatorCandidates().isEmpty()) {
                scannedElements.add(element);
            }
        }
        return scannedElements;
    }

    /**
     * Builds the {@link By} locator for re-locating a scanned element by its scan index via the marker attribute.
     *
     * @param index the scan index of the element
     * @return the {@link By} locator of the marked element
     */
    public static By markerLocator(int index) {
        return xpathBuilder().anywhere().attr(MARKER_ATTRIBUTE).exact(String.valueOf(index)).build();
    }

    /**
     * Removes the marker attributes from all scanned elements to clean up the page.
     *
     * @param driver the driver pointing to the scanned page
     */
    public static void clearMarkers(ComponentWebDriver driver) {
        driver.executeScript(
                "document.querySelectorAll('[" + MARKER_ATTRIBUTE + "]').forEach(function(e) { e.removeAttribute('"
                        + MARKER_ATTRIBUTE + "'); });");
    }

    private String buildSelector() {
        StringBuilder builder = new StringBuilder(DEFAULT_INTERACTIVE_SELECTOR);
        for (String extraSelector : config.getExtraSelectors()) {
            builder.append(", ").append(extraSelector);
        }
        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    private ScannedElement toScannedElement(int index, Map<String, Object> rawElement) {
        String tagName = String.valueOf(rawElement.getOrDefault("tagName", ""));
        String text = String.valueOf(rawElement.getOrDefault("text", ""));
        Map<String, String> attributes = toStringAttributes(
                (Map<String, Object>) rawElement.getOrDefault("attributes", Map.of()));
        LocatorContext context = buildLocatorContext(rawElement);
        List<LocatorCandidate> candidates = buildLocatorCandidates(index, tagName, attributes, text, context);
        return new ScannedElement(index, tagName, attributes, text, candidates);
    }

    private Map<String, String> toStringAttributes(Map<String, Object> rawAttributes) {
        Map<String, String> attributes = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : rawAttributes.entrySet()) {
            if (entry.getValue() != null) {
                attributes.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return attributes;
    }

    private List<LocatorCandidate> buildLocatorCandidates(int index, String tagName, Map<String, String> attributes,
            String text, LocatorContext context) {
        List<LocatorCandidate> candidates = new ArrayList<>();
        for (LocatorCandidateStrategy strategy : strategies) {
            candidates.addAll(strategy.toCandidates(index, tagName, attributes, text, context));
        }
        // append the marker candidate only when at least one strategy has matched
        if (!candidates.isEmpty()) {
            candidates.addAll(new MarkerLocatorCandidateStrategy().toCandidates(index, tagName, attributes, text));
        }
        candidates.sort(Comparator.comparingInt(LocatorCandidate::getPriority));
        return candidates;
    }

    private LocatorContext buildLocatorContext(Map<String, Object> rawElement) {
        AncestorInfo ancestor = null;
        Object rawAncestor = rawElement.get("ancestor");
        if (rawAncestor instanceof Map<?, ?> ancestorMap) {
            String id = ancestorMap.get("id") != null ? String.valueOf(ancestorMap.get("id")) : null;
            String testId = ancestorMap.get("testId") != null ? String.valueOf(ancestorMap.get("testId")) : null;
            String childPath = ancestorMap.containsKey("childPath") ? String.valueOf(ancestorMap.get("childPath"))
                    : "";
            ancestor = new AncestorInfo(id, testId, childPath);
        }
        int siblingCount = rawElement.get("siblingCount") instanceof Number num ? num.intValue() : 1;
        Map<String, Integer> nameSiblingCounts = new LinkedHashMap<>();
        Object rawNameCounts = rawElement.get("nameSiblingCounts");
        if (rawNameCounts instanceof Map<?, ?> nameCountsMap) {
            for (Map.Entry<?, ?> entry : nameCountsMap.entrySet()) {
                if (entry.getKey() != null && entry.getValue() instanceof Number num) {
                    nameSiblingCounts.put(String.valueOf(entry.getKey()), num.intValue());
                }
            }
        }
        return new LocatorContext(ancestor, siblingCount, nameSiblingCounts);
    }
}
