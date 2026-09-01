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
package com.github.grossopa.selenium.recorder.component;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Detects Material UI components by matching the MUI root css classes (e.g. {@code MuiButton-root}) against the css
 * classes of the element itself and its ancestors. The css prefix is taken from {@link MuiConfig} so customized
 * prefixes are also supported.
 *
 * <p>The ancestor traversal covers at most {@value #MAX_ANCESTOR_DEPTH} levels so that e.g. an {@code input} element
 * inside a {@code MuiTextField-root} container is detected as {@code MuiTextField}.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see MuiComponentDefinitions
 */
public class MuiComponentDetector implements ComponentDetector {

    /**
     * The maximum depth of ancestors to look up for component root css classes.
     */
    public static final int MAX_ANCESTOR_DEPTH = 5;

    private static final String ANCESTOR_CLASSES_SCRIPT = """
            var el = arguments[0];
            var depth = arguments[1];
            var classes = [];
            var current = el;
            var level = 0;
            while (current && level < depth) {
                classes.push(typeof current.className === 'string' ? current.className : '');
                current = current.parentElement;
                level++;
            }
            return classes;
            """;

    private final MuiConfig config;
    private final List<MuiComponentDefinition> definitions;

    /**
     * Constructs an instance with default {@link MuiConfig} and default definitions.
     */
    public MuiComponentDetector() {
        this(new MuiConfig(), MuiComponentDefinitions.defaults());
    }

    /**
     * Constructs an instance with given {@link MuiConfig} and component definitions.
     *
     * @param config the MUI configuration providing the css prefix, must not be null
     * @param definitions the component definitions to match against, must not be null
     */
    public MuiComponentDetector(MuiConfig config, List<MuiComponentDefinition> definitions) {
        this.config = requireNonNull(config);
        this.definitions = List.copyOf(requireNonNull(definitions));
    }

    @Override
    public Optional<DetectedComponent> detect(WebElement element, ComponentWebDriver driver) {
        Object result = driver.executeScript(ANCESTOR_CLASSES_SCRIPT, element, MAX_ANCESTOR_DEPTH + 1);
        if (!(result instanceof List<?> ancestorClasses)) {
            return Optional.empty();
        }
        for (Object classValue : ancestorClasses) {
            Optional<DetectedComponent> matched = matchClass(String.valueOf(classValue));
            if (matched.isPresent()) {
                return matched;
            }
        }
        return Optional.empty();
    }

    private Optional<DetectedComponent> matchClass(String classAttributeValue) {
        if (classAttributeValue == null || classAttributeValue.isBlank()) {
            return Optional.empty();
        }
        List<String> classTokens = Arrays.asList(classAttributeValue.split("\\s+"));
        for (MuiComponentDefinition definition : definitions) {
            if (classTokens.contains(config.getRootCss(definition.getComponentName()))) {
                return Optional.of(definition.toDetectedComponent());
            }
        }
        return Optional.empty();
    }
}
