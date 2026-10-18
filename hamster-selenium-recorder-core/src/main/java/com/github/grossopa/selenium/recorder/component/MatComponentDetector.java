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

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Detects Angular Material components by matching the root CSS classes (e.g. {@code mat-mdc-button-base},
 * {@code mat-accordion}) against the CSS classes of the element itself and its ancestors.
 *
 * <p>The ancestor traversal covers at most {@value #MAX_ANCESTOR_DEPTH} levels so that e.g. an {@code input} element
 * inside a {@code mat-mdc-form-field} container is detected as {@code MatFormField}.</p>
 *
 * <p>Angular Material uses two CSS prefix groups: MDC-based components ({@code mat-mdc-*}) and non-MDC components
 * ({@code mat-*}). The root CSS classes are pre-defined in {@link MatComponentDefinitions}.</p>
 *
 * @author Jack Yin
 * @since 1.17
 * @see MatComponentDefinitions
 */
public class MatComponentDetector implements ComponentDetector {

    /**
     * The maximum depth of ancestors to look up for component root CSS classes.
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

    private final MatConfig config;
    private final List<MatComponentDefinition> definitions;

    /**
     * Constructs an instance with default {@link MatConfig} and default definitions.
     */
    public MatComponentDetector() {
        this(new MatConfig(), MatComponentDefinitions.defaults());
    }

    /**
     * Constructs an instance with given {@link MatConfig} and component definitions.
     *
     * @param config the Angular Material configuration, must not be null
     * @param definitions the component definitions to match against, must not be null
     */
    public MatComponentDetector(MatConfig config, List<MatComponentDefinition> definitions) {
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
        for (MatComponentDefinition definition : definitions) {
            if (classTokens.contains(definition.getRootCssClass())) {
                return Optional.of(definition.toDetectedComponent());
            }
        }
        return Optional.empty();
    }
}
