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
package com.github.grossopa.selenium.examples.recorder;

import com.github.grossopa.selenium.component.mui.MuiComponent;
import com.github.grossopa.selenium.component.mui.MuiComponents;
import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.recorder.component.MuiComponentDefinition;
import com.github.grossopa.selenium.recorder.component.MuiComponentDefinitions;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.model.PageElementModel;
import com.github.grossopa.selenium.recorder.model.PageModel;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Tests the recorder component discovery with a stable 5-step locator resolution strategy against the official
 * Material UI documentation pages. For each MUI component type, this example finds ALL instances on the page and
 * resolves a stable locator for each using the following priority:
 *
 * <ol>
 * <li><b>id</b> — if the element has an {@code id}, use {@code By.id()} directly</li>
 * <li><b>anchor attribute</b> — if no id, check for a custom anchor selector (default: {@code data-testid})</li>
 * <li><b>ancestor anchor</b> — if neither, traverse ancestors to find one with id or anchor, generate
 * parent→child CSS selector</li>
 * <li><b>sibling list</b> — if no ancestor anchor but multiple siblings of the same type exist under the same
 * parent, return as a list</li>
 * <li><b>discard</b> — if none of the above, the element is discarded (no stable locator possible)</li>
 * </ol>
 *
 * <p>Requires internet access and the Edge Driver Service running on port 38383 (start via
 * {@code StartDriverServiceEdge}). Run this class as a normal Java application.</p>
 *
 * @author Jack Yin
 * @since 1.16
 * @see RecorderSession
 * @see MuiComponentDefinitions
 */
public class RecorderMuiComponentDiscoveryShowCase {

    /**
     * The default anchor attribute for stable locator resolution.
     */
    private static final String DEFAULT_ANCHOR_ATTR = "data-testid";

    private static final Map<String, String> COMPONENT_URLS = new LinkedHashMap<>();
    private static final Set<String> JAVA_RESERVED_WORDS = Set.of("switch", "default", "case", "break", "continue",
            "return", "void", "class", "new", "this", "super", "import", "package", "int", "char", "boolean", "long",
            "double", "float", "short", "byte");
    private static final Pattern NON_ALPHANUMERIC = Pattern.compile("[^a-zA-Z0-9]+");

    static {
        // Inputs
        COMPONENT_URLS.put("Button", "react-button");
        COMPONENT_URLS.put("ButtonGroup", "react-button");
        COMPONENT_URLS.put("Checkbox", "react-checkbox");
        COMPONENT_URLS.put("Fab", "react-button");
        COMPONENT_URLS.put("Radio", "react-radio-button");
        COMPONENT_URLS.put("RadioGroup", "react-radio-button");
        COMPONENT_URLS.put("Slider", "react-slider");
        COMPONENT_URLS.put("Switch", "react-switch");
        COMPONENT_URLS.put("TextField", "react-text-field");
        COMPONENT_URLS.put("Rating", "react-rating");
        // Data Display
        COMPONENT_URLS.put("Avatar", "react-avatar");
        COMPONENT_URLS.put("Badge", "react-badge");
        COMPONENT_URLS.put("Chip", "react-chip");
        COMPONENT_URLS.put("Divider", "react-divider");
        COMPONENT_URLS.put("List", "react-list");
        COMPONENT_URLS.put("ListItem", "react-list");
        // Feedback
        COMPONENT_URLS.put("Backdrop", "react-backdrop");
        COMPONENT_URLS.put("Dialog", "react-dialog");
        COMPONENT_URLS.put("Snackbar", "react-snackbar");
        COMPONENT_URLS.put("CircularProgress", "react-progress");
        COMPONENT_URLS.put("LinearProgress", "react-progress");
        // Navigation
        COMPONENT_URLS.put("Accordion", "react-accordion");
        COMPONENT_URLS.put("BottomNavigation", "react-bottom-navigation");
        COMPONENT_URLS.put("Breadcrumbs", "react-breadcrumbs");
        COMPONENT_URLS.put("Link", "react-link");
        COMPONENT_URLS.put("Menu", "react-menu");
        COMPONENT_URLS.put("Tabs", "react-tabs");
        COMPONENT_URLS.put("Stepper", "react-stepper");
        // Surfaces
        COMPONENT_URLS.put("AppBar", "react-app-bar");
        COMPONENT_URLS.put("Pager", "react-pagination");
        // Core
        COMPONENT_URLS.put("Grid", "react-grid");
        // Lab
        COMPONENT_URLS.put("Autocomplete", "react-autocomplete");
        COMPONENT_URLS.put("Pagination", "react-pagination");
    }

    /**
     * JavaScript that resolves stable locators for all elements matching a root CSS class. Returns a list of
     * resolution results, one per element.
     */
    private static final String RESOLVE_LOCATORS_SCRIPT = """
            var rootCss = arguments[0];
            var anchorAttr = arguments[1];
            var elements = document.querySelectorAll('.' + rootCss);
            var results = [];

            for (var i = 0; i < elements.length; i++) {
                var el = elements[i];
                var result = { index: i };

                // Step 1: element has id
                if (el.id) {
                    result.status = 'single';
                    result.strategy = 'id';
                    result.value = el.id;
                    results.push(result);
                    continue;
                }

                // Step 2: element has anchor attribute
                var anchorValue = el.getAttribute(anchorAttr);
                if (anchorValue) {
                    result.status = 'single';
                    result.strategy = 'anchor';
                    result.value = anchorValue;
                    results.push(result);
                    continue;
                }

                // Step 3: traverse ancestors for id or anchor
                var current = el.parentElement;
                var childPath = '.' + rootCss;
                var depth = 0;
                var found = false;
                while (current && depth < 10) {
                    if (current.id) {
                        result.status = 'single';
                        result.strategy = 'ancestor-id';
                        result.ancestorId = current.id;
                        result.childPath = childPath;
                        found = true;
                        break;
                    }
                    var ancestorAnchor = current.getAttribute(anchorAttr);
                    if (ancestorAnchor) {
                        result.status = 'single';
                        result.strategy = 'ancestor-anchor';
                        result.ancestorAnchor = ancestorAnchor;
                        result.childPath = childPath;
                        found = true;
                        break;
                    }
                    // build path segment from current element
                    var seg = current.tagName.toLowerCase();
                    if (current.className && typeof current.className === 'string') {
                        var classes = current.className.trim().split(/\\s+/);
                        for (var j = 0; j < classes.length; j++) {
                            if (classes[j].length > 2 && !classes[j].startsWith('css-')) {
                                seg = '.' + classes[j];
                                break;
                            }
                        }
                    }
                    childPath = seg + ' > ' + childPath;
                    current = current.parentElement;
                    depth++;
                }
                if (found) {
                    results.push(result);
                    continue;
                }

                // Step 4: check siblings
                var parent = el.parentElement;
                var siblings = parent.querySelectorAll(':scope > .' + rootCss);
                if (siblings.length > 1) {
                    // build parent path
                    var parentPath = '';
                    var p = parent;
                    var pDepth = 0;
                    while (p && pDepth < 10) {
                        if (p.id) {
                            parentPath = '#' + p.id + (parentPath ? ' > ' + parentPath : '');
                            break;
                        }
                        var pAnchor = p.getAttribute(anchorAttr);
                        if (pAnchor) {
                            parentPath = '[' + anchorAttr + '="' + pAnchor + '"]' + (parentPath ? ' > ' + parentPath : '');
                            break;
                        }
                        var pSeg = p.tagName.toLowerCase();
                        if (p.className && typeof p.className === 'string') {
                            var pClasses = p.className.trim().split(/\\s+/);
                            for (var k = 0; k < pClasses.length; k++) {
                                if (pClasses[k].length > 2 && !pClasses[k].startsWith('css-')) {
                                    pSeg = '.' + pClasses[k];
                                    break;
                                }
                            }
                        }
                        parentPath = pSeg + (parentPath ? ' > ' + parentPath : '');
                        p = p.parentElement;
                        pDepth++;
                    }
                    result.status = 'list';
                    result.strategy = 'siblings';
                    result.parentPath = parentPath;
                    result.count = siblings.length;
                    results.push(result);
                    continue;
                }

                // Step 5: discard
                result.status = 'discard';
                results.push(result);
            }
            return results;
            """;

    /**
     * Runs the example.
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        MuiConfig muiConfig = new MuiConfig();
        muiConfig.setVersion(MuiVersion.V5);

        // group components by page URL
        Map<String, List<String>> pagesToVisit = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : COMPONENT_URLS.entrySet()) {
            String url = "https://mui.com/material-ui/" + entry.getValue() + "/";
            pagesToVisit.computeIfAbsent(url, k -> new ArrayList<>()).add(entry.getKey());
        }

        // build definition lookup
        Map<String, MuiComponentDefinition> definitionLookup = new LinkedHashMap<>();
        for (MuiComponentDefinition def : MuiComponentDefinitions.defaults()) {
            definitionLookup.put(def.getComponentName(), def);
        }

        int totalSingle = 0;
        int totalList = 0;
        int totalDiscarded = 0;
        TreeSet<String> cssNotFound = new TreeSet<>();

        RecorderConfig config = RecorderConfig.builder()
                .framework(ComponentFramework.MUI)
                .muiVersion(MuiVersion.V5)
                .outputDir(Path.of("hamster-selenium-examples", "target", "recorder-generated"))
                .basePackage("com.example.pageobjects")
                .build();

        WebDriver rawDriver = RecorderExampleSupport.createDriver();
        try (RecorderSession session = new RecorderSession(rawDriver, config)) {
            int pageCount = 0;
            for (Map.Entry<String, List<String>> pageEntry : pagesToVisit.entrySet()) {
                String url = pageEntry.getKey();
                List<String> componentNames = pageEntry.getValue();
                pageCount++;

                rawDriver.get(url);
                ((JavascriptExecutor) rawDriver).executeScript("window.scrollTo(0, 0)");

                String pageName = componentNames.get(0) + "Page";
                PageModel page = session.newPage(pageName);

                System.out.printf(Locale.ROOT, "%n--- [%d/%d] %s ---%n", pageCount, pagesToVisit.size(), url);

                for (String componentName : componentNames) {
                    String rootCss = muiConfig.getRootCss(componentName);

                    // quick check if any elements exist (use rawDriver to avoid intercepting overhead)
                    List<WebElement> elements = rawDriver.findElements(By.className(rootCss));
                    if (elements.isEmpty()) {
                        cssNotFound.add(componentName);
                        System.out.printf(Locale.ROOT, "  %-22s NOT FOUND on page%n", componentName);
                        continue;
                    }

                    // resolve locators via JavaScript
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> resolutions = (List<Map<String, Object>>) ((JavascriptExecutor) rawDriver)
                            .executeScript(RESOLVE_LOCATORS_SCRIPT, rootCss, DEFAULT_ANCHOR_ATTR);

                    MuiComponentDefinition definition = definitionLookup.get(componentName);
                    if (definition == null) {
                        continue;
                    }
                    DetectedComponent detectedComponent = definition.toDetectedComponent();

                    // track which list groups we've already added (by parentPath)
                    Map<String, String> addedListGroups = new LinkedHashMap<>();

                    int singleCount = 0;
                    int listCount = 0;
                    int discardCount = 0;

                    for (Map<String, Object> resolution : resolutions) {
                        String status = String.valueOf(resolution.get("status"));
                        String strategy = String.valueOf(resolution.get("strategy"));

                        switch (status) {
                            case "single" -> {
                                LocatorCandidate locator = buildSingleLocator(resolution, strategy);
                                String fieldName = buildSingleFieldName(resolution, componentName, strategy);
                                if (fieldName != null && locator != null) {
                                    PageElementModel model = new PageElementModel(fieldName, locator,
                                            detectedComponent);
                                    if (page.addElement(model)) {
                                        singleCount++;
                                    }
                                }
                            }
                            case "list" -> {
                                String parentPath = String.valueOf(resolution.get("parentPath"));
                                if (!addedListGroups.containsKey(parentPath)) {
                                    String cssSelector = parentPath.isEmpty()
                                            ? "." + rootCss
                                            : parentPath + " > ." + rootCss;
                                    LocatorCandidate locator = new LocatorCandidate(LocatorType.CSS_SELECTOR,
                                            cssSelector, LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE,
                                            "by css \"" + cssSelector + "\"");
                                    String fieldName = toCamelCase(componentName) + "s";
                                    fieldName = ensureValidJavaName(fieldName);
                                    PageElementModel model = new PageElementModel(fieldName, locator,
                                            detectedComponent, true);
                                    if (page.addElement(model)) {
                                        addedListGroups.put(parentPath, fieldName);
                                        listCount++;
                                    }
                                }
                            }
                            default -> discardCount++;
                        }
                    }

                    totalSingle += singleCount;
                    totalList += listCount;
                    totalDiscarded += discardCount;

                    System.out.printf(Locale.ROOT,
                            "  %-22s %2d elements → %d single, %d list groups, %d discarded%n",
                            componentName, resolutions.size(), singleCount, listCount, discardCount);
                }
                System.out.flush();
            }

            // generate page objects
            System.out.println("\n==================== CODE GENERATION ====================");
            List<Path> generatedFiles = session.generate();
            RecorderExampleSupport.printGeneratedFiles(generatedFiles);

            // summary
            System.out.println("\n==================== DISCOVERY SUMMARY ====================");
            System.out.println("Pages visited: " + pageCount);
            System.out.println("Stable locators (single): " + totalSingle);
            System.out.println("Stable locators (list):   " + totalList);
            System.out.println("Discarded (no locator):   " + totalDiscarded);
            System.out.println("Generated page objects:   " + generatedFiles.size());

            if (!cssNotFound.isEmpty()) {
                System.out.println("\nComponent types not found (" + cssNotFound.size() + "):");
                cssNotFound.forEach(name -> System.out.println("  - " + name));
            }

            // reflection verification
            System.out.println("\n--- Reflection verification ---");
            int noArgFactoryCount = 0;
            for (Method method : MuiComponents.class.getMethods()) {
                if (method.getName().startsWith("to") && method.getParameterCount() == 0
                        && MuiComponent.class.isAssignableFrom(method.getReturnType())) {
                    noArgFactoryCount++;
                }
            }
            System.out.println("No-arg factory methods on MuiComponents: " + noArgFactoryCount);
            System.out.println("===========================================================");
        }
    }

    private static LocatorCandidate buildSingleLocator(Map<String, Object> resolution, String strategy) {
        return switch (strategy) {
            case "id" -> {
                String id = String.valueOf(resolution.get("value"));
                yield new LocatorCandidate(LocatorType.ID, id, LocatorCandidate.PRIORITY_ID, "by id \"" + id + "\"");
            }
            case "anchor" -> {
                String value = String.valueOf(resolution.get("value"));
                String css = "[" + DEFAULT_ANCHOR_ATTR + "=\"" + value + "\"]";
                yield new LocatorCandidate(LocatorType.CSS_SELECTOR, css, LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE,
                        "by " + DEFAULT_ANCHOR_ATTR + " \"" + value + "\"");
            }
            case "ancestor-id" -> {
                String ancestorId = String.valueOf(resolution.get("ancestorId"));
                String childPath = String.valueOf(resolution.get("childPath"));
                String css = "#" + ancestorId + " " + childPath;
                yield new LocatorCandidate(LocatorType.CSS_SELECTOR, css, LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE,
                        "by ancestor #" + ancestorId + " → " + childPath);
            }
            case "ancestor-anchor" -> {
                String ancestorAnchor = String.valueOf(resolution.get("ancestorAnchor"));
                String childPath = String.valueOf(resolution.get("childPath"));
                String css = "[" + DEFAULT_ANCHOR_ATTR + "=\"" + ancestorAnchor + "\"] " + childPath;
                yield new LocatorCandidate(LocatorType.CSS_SELECTOR, css, LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE,
                        "by ancestor " + DEFAULT_ANCHOR_ATTR + " \"" + ancestorAnchor + "\" → " + childPath);
            }
            default -> null;
        };
    }

    private static String buildSingleFieldName(Map<String, Object> resolution, String componentName,
            String strategy) {
        String baseName = toCamelCase(componentName);
        return switch (strategy) {
            case "id" -> {
                String id = String.valueOf(resolution.get("value"));
                yield ensureValidJavaName(toCamelCase(id));
            }
            case "anchor" -> {
                String value = String.valueOf(resolution.get("value"));
                yield ensureValidJavaName(baseName + capitalize(toCamelCase(value)));
            }
            case "ancestor-id" -> {
                String ancestorId = String.valueOf(resolution.get("ancestorId"));
                yield ensureValidJavaName(toCamelCase(ancestorId) + capitalize(baseName));
            }
            case "ancestor-anchor" -> {
                String ancestorAnchor = String.valueOf(resolution.get("ancestorAnchor"));
                yield ensureValidJavaName(toCamelCase(ancestorAnchor) + capitalize(baseName));
            }
            default -> null;
        };
    }

    private static String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String cleaned = NON_ALPHANUMERIC.matcher(input).replaceAll(" ").trim();
        if (cleaned.isEmpty()) {
            return "";
        }
        String[] words = cleaned.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (word.isEmpty()) {
                continue;
            }
            if (i == 0) {
                result.append(Character.toLowerCase(word.charAt(0)));
            } else {
                result.append(Character.toUpperCase(word.charAt(0)));
            }
            if (word.length() > 1) {
                result.append(word.substring(1));
            }
        }
        return result.toString();
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private static String ensureValidJavaName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        // ensure starts with letter
        if (!Character.isLetter(name.charAt(0))) {
            name = "element" + capitalize(name);
        }
        // handle reserved words
        if (JAVA_RESERVED_WORDS.contains(name)) {
            return name + "Component";
        }
        return name;
    }
}
