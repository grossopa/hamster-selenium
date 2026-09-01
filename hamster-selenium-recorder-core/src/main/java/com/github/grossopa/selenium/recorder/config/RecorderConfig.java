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
package com.github.grossopa.selenium.recorder.config;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.recorder.page.ContextPathPageStrategy;
import com.github.grossopa.selenium.recorder.page.PageIdentificationStrategy;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The configuration of a recorder session, covering the key attributes to scan, the target component framework,
 * the page identification strategy and the page object generation output.
 *
 * <p>Example usage:
 * <pre>{@code
 * RecorderConfig config = RecorderConfig.builder()
 *         .framework(ComponentFramework.MUI)
 *         .muiVersion(MuiVersion.V5)
 *         .keyAttribute("data-testid")
 *         .outputDir(Path.of("generated-pageobjects"))
 *         .basePackage("com.example.pageobjects")
 *         .build();
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.15
 * @see ComponentFramework
 * @see PageIdentificationStrategy
 */
public class RecorderConfig {

    /**
     * The default key attributes to scan: id and name.
     */
    public static final List<String> DEFAULT_KEY_ATTRIBUTES = List.of("id", "name");

    /**
     * The default base package of the generated page objects.
     */
    public static final String DEFAULT_BASE_PACKAGE = "com.example.pageobjects";

    private final List<String> keyAttributes;
    private final List<String> extraSelectors;
    private final ComponentFramework framework;
    private final MuiVersion muiVersion;
    private final Path outputDir;
    private final String basePackage;
    private PageIdentificationStrategy pageStrategy;

    /**
     * Constructs an instance with all configuration values.
     *
     * @param keyAttributes the key attributes to collect when scanning, must not be null
     * @param extraSelectors the additional CSS selectors for finding interactive elements, must not be null
     * @param framework the target component framework, must not be null
     * @param muiVersion the Material UI version, only relevant when framework is {@link ComponentFramework#MUI}
     * @param outputDir the output directory of the generated page objects, must not be null
     * @param basePackage the base package of the generated page objects, must not be null
     * @param pageStrategy the strategy for identifying which page the current url belongs to, must not be null
     */
    public RecorderConfig(List<String> keyAttributes, List<String> extraSelectors, ComponentFramework framework,
            MuiVersion muiVersion, Path outputDir, String basePackage, PageIdentificationStrategy pageStrategy) {
        this.keyAttributes = new ArrayList<>(requireNonNull(keyAttributes));
        this.extraSelectors = new ArrayList<>(requireNonNull(extraSelectors));
        this.framework = requireNonNull(framework);
        this.muiVersion = requireNonNull(muiVersion);
        this.outputDir = requireNonNull(outputDir);
        this.basePackage = requireNonNull(basePackage);
        this.pageStrategy = requireNonNull(pageStrategy);
    }

    /**
     * Creates a new builder instance with default values.
     *
     * @return the created builder instance
     */
    public static RecorderConfigBuilder builder() {
        return new RecorderConfigBuilder();
    }

    /**
     * Gets the key attributes to collect when scanning elements. The default value is {@code [id, name]} and the user
     * could add customized attributes such as {@code data-testid}.
     *
     * @return the key attributes to collect when scanning elements
     */
    public List<String> getKeyAttributes() {
        return keyAttributes;
    }

    /**
     * Gets the additional CSS selectors for finding interactive elements besides the built-in ones.
     *
     * @return the additional CSS selectors for finding interactive elements
     */
    public List<String> getExtraSelectors() {
        return extraSelectors;
    }

    /**
     * Gets the target component framework that the user selects.
     *
     * @return the target component framework
     */
    public ComponentFramework getFramework() {
        return framework;
    }

    /**
     * Gets the Material UI version, only relevant when framework is {@link ComponentFramework#MUI}.
     *
     * @return the Material UI version
     */
    public MuiVersion getMuiVersion() {
        return muiVersion;
    }

    /**
     * Gets the output directory of the generated page objects.
     *
     * @return the output directory of the generated page objects
     */
    public Path getOutputDir() {
        return outputDir;
    }

    /**
     * Gets the base package of the generated page objects.
     *
     * @return the base package of the generated page objects
     */
    public String getBasePackage() {
        return basePackage;
    }

    /**
     * Gets the strategy for identifying which page the current url belongs to.
     *
     * @return the page identification strategy
     */
    public PageIdentificationStrategy getPageStrategy() {
        return pageStrategy;
    }

    /**
     * Sets the strategy for identifying which page the current url belongs to, allowing the user to replace the
     * default {@link ContextPathPageStrategy} at runtime.
     *
     * @param pageStrategy the new page identification strategy to set
     */
    public void setPageStrategy(PageIdentificationStrategy pageStrategy) {
        this.pageStrategy = requireNonNull(pageStrategy);
    }

    /**
     * The builder of {@link RecorderConfig}.
     *
     * @author Jack Yin
     * @since 1.15
     */
    public static class RecorderConfigBuilder {

        private final List<String> keyAttributes = new ArrayList<>(DEFAULT_KEY_ATTRIBUTES);
        private final List<String> extraSelectors = new ArrayList<>();
        private ComponentFramework framework = ComponentFramework.MUI;
        private MuiVersion muiVersion = MuiVersion.V4;
        private Path outputDir = Path.of("generated-pageobjects");
        private String basePackage = DEFAULT_BASE_PACKAGE;
        private PageIdentificationStrategy pageStrategy = new ContextPathPageStrategy();

        /**
         * Adds a customized key attribute to collect when scanning, e.g. {@code data-testid}.
         *
         * @param keyAttribute the key attribute name to add
         * @return this builder instance
         */
        public RecorderConfigBuilder keyAttribute(String keyAttribute) {
            if (!this.keyAttributes.contains(keyAttribute)) {
                this.keyAttributes.add(keyAttribute);
            }
            return this;
        }

        /**
         * Adds an additional CSS selector for finding interactive elements.
         *
         * @param extraSelector the CSS selector to add
         * @return this builder instance
         */
        public RecorderConfigBuilder extraSelector(String extraSelector) {
            this.extraSelectors.add(extraSelector);
            return this;
        }

        /**
         * Sets the target component framework.
         *
         * @param framework the component framework to set
         * @return this builder instance
         */
        public RecorderConfigBuilder framework(ComponentFramework framework) {
            this.framework = framework;
            return this;
        }

        /**
         * Sets the Material UI version.
         *
         * @param muiVersion the Material UI version to set
         * @return this builder instance
         */
        public RecorderConfigBuilder muiVersion(MuiVersion muiVersion) {
            this.muiVersion = muiVersion;
            return this;
        }

        /**
         * Sets the output directory of the generated page objects.
         *
         * @param outputDir the output directory to set
         * @return this builder instance
         */
        public RecorderConfigBuilder outputDir(Path outputDir) {
            this.outputDir = outputDir;
            return this;
        }

        /**
         * Sets the base package of the generated page objects.
         *
         * @param basePackage the base package to set
         * @return this builder instance
         */
        public RecorderConfigBuilder basePackage(String basePackage) {
            this.basePackage = basePackage;
            return this;
        }

        /**
         * Sets the page identification strategy.
         *
         * @param pageStrategy the page identification strategy to set
         * @return this builder instance
         */
        public RecorderConfigBuilder pageStrategy(PageIdentificationStrategy pageStrategy) {
            this.pageStrategy = pageStrategy;
            return this;
        }

        /**
         * Builds the {@link RecorderConfig} instance.
         *
         * @return the built {@link RecorderConfig} instance
         */
        public RecorderConfig build() {
            return new RecorderConfig(keyAttributes, extraSelectors, framework, muiVersion, outputDir, basePackage,
                    pageStrategy);
        }
    }
}
