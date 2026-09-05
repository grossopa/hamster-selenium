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
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * The default implementation of {@link ElementScanner} that evaluates one JavaScript snippet to walk through the DOM
 * and collect all key interactive elements in one round trip.
 *
 * <p>It locates interactive elements by built-in tag names and ARIA roles plus the user defined extra selectors,
 * collects the configured key attributes (id, name and customized ones) and marks each found element with a temporary
 * attribute {@value #MARKER_ATTRIBUTE} so that the element could be re-located by its scan index afterwards.</p>
 *
 * @author Jack Yin
 * @since 1.15
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

    private static final String SCAN_SCRIPT = """
            var selector = arguments[0];
            var attrs = arguments[1];
            var markerAttr = arguments[2];
            var results = [];
            var elems = document.querySelectorAll(selector);
            for (var i = 0; i < elems.length; i++) {
                var el = elems[i];
                var rect = el.getBoundingClientRect();
                var style = window.getComputedStyle(el);
                if (style.display === 'none' || style.visibility === 'hidden'
                        || (rect.width === 0 && rect.height === 0)) {
                    continue;
                }
                var obj = {tagName: el.tagName.toLowerCase(),
                    text: (el.innerText || '').trim().substring(0, arguments[3]), attributes: {}};
                for (var j = 0; j < attrs.length; j++) {
                    var value = el.getAttribute(attrs[j]);
                    if (value) {
                        obj.attributes[attrs[j]] = value;
                    }
                }
                el.setAttribute(markerAttr, String(results.length));
                results.push(obj);
            }
            return results;
            """;

    private final RecorderConfig config;

    /**
     * Constructs an instance with the recorder configuration.
     *
     * @param config the recorder configuration, must not be null
     */
    public DefaultElementScanner(RecorderConfig config) {
        this.config = requireNonNull(config);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScannedElement> scan(ComponentWebDriver driver) {
        Object result = driver.executeScript(SCAN_SCRIPT, buildSelector(), config.getKeyAttributes(),
                MARKER_ATTRIBUTE, MAX_TEXT_LENGTH);
        List<Map<String, Object>> rawElements = result instanceof List<?> list ? (List<Map<String, Object>>) list
                : List.of();
        List<ScannedElement> scannedElements = new ArrayList<>();
        for (int i = 0; i < rawElements.size(); i++) {
            scannedElements.add(toScannedElement(i, rawElements.get(i)));
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
        return By.cssSelector("[" + MARKER_ATTRIBUTE + "=\"" + index + "\"]");
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

    private ScannedElement toScannedElement(int index, Map<String, Object> rawElement) {
        String tagName = String.valueOf(rawElement.getOrDefault("tagName", ""));
        String text = String.valueOf(rawElement.getOrDefault("text", ""));
        Map<String, String> attributes = toStringAttributes(
                (Map<String, Object>) rawElement.getOrDefault("attributes", Map.of()));
        List<LocatorCandidate> candidates = buildLocatorCandidates(index, tagName, text, attributes);
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

    private List<LocatorCandidate> buildLocatorCandidates(int index, String tagName, String text,
            Map<String, String> attributes) {
        List<LocatorCandidate> candidates = new ArrayList<>();
        String id = attributes.get("id");
        if (id != null && !id.isBlank()) {
            candidates.add(
                    new LocatorCandidate(LocatorType.ID, id, LocatorCandidate.PRIORITY_ID, "by id \"" + id + "\""));
        }
        String name = attributes.get("name");
        if (name != null && !name.isBlank()) {
            candidates.add(new LocatorCandidate(LocatorType.NAME, name, LocatorCandidate.PRIORITY_NAME,
                    "by name \"" + name + "\""));
        }
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            if (!"id".equals(entry.getKey()) && !"name".equals(entry.getKey()) && !entry.getValue().isBlank()
                    && !entry.getValue().contains("\"")) {
                String cssValue = "[" + entry.getKey() + "=\"" + entry.getValue() + "\"]";
                candidates.add(new LocatorCandidate(LocatorType.CSS_SELECTOR, cssValue,
                        LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE,
                        "by attribute " + entry.getKey() + "=\"" + entry.getValue() + "\""));
            }
        }
        if (!text.isBlank() && !text.contains("'") && text.length() <= MAX_TEXT_LENGTH) {
            String xpathValue = "//" + tagName + "[normalize-space()='" + text + "']";
            candidates.add(new LocatorCandidate(LocatorType.XPATH, xpathValue, LocatorCandidate.PRIORITY_TEXT,
                    "by text \"" + text + "\""));
        }
        candidates.add(new LocatorCandidate(LocatorType.CSS_SELECTOR,
                "[" + MARKER_ATTRIBUTE + "=\"" + index + "\"]", LocatorCandidate.PRIORITY_MARKER,
                "by scan index " + index));
        candidates.sort(Comparator.comparingInt(LocatorCandidate::getPriority));
        return candidates;
    }
}
