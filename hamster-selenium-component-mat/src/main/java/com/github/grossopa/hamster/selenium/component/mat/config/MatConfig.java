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
package com.github.grossopa.hamster.selenium.component.mat.config;

import com.github.grossopa.selenium.core.component.ComponentConfig;
import com.github.grossopa.selenium.core.component.WebComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Strings.CS;

/**
 * The root configuration for Material UI Angular components
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatConfig implements ComponentConfig {

    private String tagPrefix = "mat-";

    private final String componentCssPrefix;

    private String cdkPrefix = "cdk-";

    @SuppressWarnings("java:S1075")
    private String overlayAbsolutePath = "/html/body";

    private MatVersion version = MatVersion.MDC;

    private final Map<String, String> internalSelectors = new HashMap<>(Map.of(
            "checkbox-input", "mdc-checkbox__native-control",
            "radio-input", "mdc-radio__native-control",
            "radio-label", "mdc-form-field"
    ));

    /**
     * Default constructor creating an MDC config with {@code "mat-mdc-"} component CSS prefix.
     */
    public MatConfig() {
        this("mat-mdc-");
    }

    /**
     * Constructs a config with the given immutable component CSS prefix.
     *
     * @param componentCssPrefix the CSS prefix for all components, must not be {@code null}
     */
    private MatConfig(String componentCssPrefix) {
        this.componentCssPrefix = requireNonNull(componentCssPrefix);
    }

    /**
     * Gets the prefix of the html tag, e.g. &lt;mat-option&gt;....default value is "mat-".
     *
     * @return the prefix of the html tag
     */
    public String getTagPrefix() {
        return tagPrefix;
    }

    /**
     * Sets the prefix of the html tag.
     *
     * @param tagPrefix the new tag prefix to set
     */
    public void setTagPrefix(String tagPrefix) {
        requireNonNull(tagPrefix);
        this.tagPrefix = tagPrefix;
    }

    /**
     * Gets the prefix of the css classes. Returns the same value as {@link #getComponentCssPrefix()}.
     *
     * @return the immutable component CSS prefix
     */
    @Override
    public String getCssPrefix() {
        return componentCssPrefix;
    }

    /**
     * Gets the prefix of the CDK (Component Dev Kit) classes, e.g. &lt;dev class="<b>cdk-overlay-container</b>"
     * &gt;....default value is "cdk-".
     *
     * @return the prefix of the cdk classes
     */
    public String getCdkPrefix() {
        return cdkPrefix;
    }

    /**
     * Sets the prefix of the CDK (Component Dev Kit) classes.
     *
     * @param cdkPrefix the new prefix of the cdk prefix to set
     */
    public void setCdkPrefix(String cdkPrefix) {
        requireNonNull(cdkPrefix);
        this.cdkPrefix = cdkPrefix;
    }

    /**
     * Gets the overlay absolute path., default value is "/html/body".
     *
     * @return the overlay absolute path.
     */
    public String getOverlayAbsolutePath() {
        return overlayAbsolutePath;
    }

    /**
     * Sets the overlay absolute path.
     *
     * @param overlayAbsolutePath the overlay absolute path to set
     */
    public void setOverlayAbsolutePath(String overlayAbsolutePath) {
        requireNonNull(overlayAbsolutePath);
        this.overlayAbsolutePath = overlayAbsolutePath;
    }

    /**
     * Gets the Angular Material version used for CSS selector resolution.
     *
     * @return the current version, default is {@link MatVersion#MDC}
     */
    public MatVersion getVersion() {
        return version;
    }

    /**
     * Sets the Angular Material version.
     *
     * @param version the version to set
     */
    public void setVersion(MatVersion version) {
        requireNonNull(version);
        this.version = version;
    }

    /**
     * Returns the immutable CSS prefix for all components, e.g. {@code "mat-mdc-"} in MDC mode or {@code "mat-"}
     * in legacy mode. The prefix is determined at construction time and does not change based on component name.
     *
     * @return the immutable CSS prefix
     */
    public String getComponentCssPrefix() {
        return componentCssPrefix;
    }

    // =====================================================================
    // Internal selector overrides
    // =====================================================================

    /**
     * Gets the internal CSS selector for the given key.
     *
     * @param key the internal selector key, e.g. "checkbox-input", "radio-input", "radio-label"
     * @return the CSS selector string
     */
    public String getInternalSelector(String key) {
        return internalSelectors.getOrDefault(key, key);
    }

    /**
     * Sets an internal CSS selector override.
     *
     * @param key the internal selector key
     * @param selector the CSS selector to use
     */
    public void setInternalSelector(String key, String selector) {
        requireNonNull(key);
        requireNonNull(selector);
        internalSelectors.put(key, selector);
    }

    /**
     * Gets the internal selector map.
     *
     * @return the mutable internal selector map
     */
    public Map<String, String> getInternalSelectors() {
        return internalSelectors;
    }

    // =====================================================================
    // State CSS methods
    // =====================================================================

    /**
     * Gets the isChecked CSS for the given component, default value is "mat-mdc-checkbox-checked".
     *
     * @return the isChecked CSS
     */
    @Override
    public String getIsCheckedCss() {
        return componentCssPrefix + "checkbox-checked";
    }

    /**
     * Gets the isSelected CSS, default value is "mat-mdc-selected".
     *
     * @return the isSelected CSS
     */
    @Override
    public String getIsSelectedCss() {
        return componentCssPrefix + "selected";
    }

    /**
     * Gets the isDisabled CSS. default value is "mat-mdc-disabled"
     *
     * @return the isDisabled CSS
     */
    @Override
    public String getIsDisabledCss() {
        return componentCssPrefix + "disabled";
    }

    /**
     * Checks whether the given component is checked by its class attribute.
     *
     * @param component the component to check
     * @return true if the component is checked
     */
    public boolean isChecked(WebComponent component) {
        return attributeContains(component, getIsCheckedCss());
    }

    /**
     * Checks whether the given component is selected by its class attribute.
     *
     * @param component the component to check
     * @return true if the component is selected
     */
    public boolean isSelected(WebComponent component) {
        return attributeContains(component, getIsSelectedCss());
    }

    /**
     * Checks whether the given component is disabled by its native disabled state, class attribute
     * or aria-disabled attribute.
     *
     * @param component the component to check
     * @return true if the component is disabled
     */
    public boolean isDisabled(WebComponent component) {
        if (component == null) {
            return true;
        }
        try {
            if (!component.getWrappedElement().isEnabled()) {
                return true;
            }
        } catch (Exception e) {
            // Ignore exceptions from getWrappedElement()
        }
        return attributeContains(component, getIsDisabledCss())
                || "true".equalsIgnoreCase(component.getDomAttribute("aria-disabled"));
    }

    private boolean attributeContains(WebComponent component, String value) {
        return CS.contains(component.getDomAttribute(CLASS), value);
    }

    // =====================================================================
    // Factory methods
    // =====================================================================

    /**
     * Creates a config for the latest Angular Material (MDC-based) version. This is the default.
     *
     * @return a new MDC config instance
     */
    public static MatConfig mdc() {
        return new MatConfig();
    }

    /**
     * Creates a config for the legacy Angular Material v12 version where all components use the {@code mat-} prefix.
     *
     * @return a new legacy config instance
     */
    public static MatConfig legacy() {
        MatConfig config = new MatConfig("mat-");
        config.version = MatVersion.LEGACY;
        config.setInternalSelector("checkbox-input", "mat-checkbox-input");
        config.setInternalSelector("radio-input", "mat-radio-input");
        config.setInternalSelector("radio-label", "mat-radio-label");
        return config;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatConfig matConfig)) {
            return false;
        }
        return tagPrefix.equals(matConfig.tagPrefix) && componentCssPrefix.equals(matConfig.componentCssPrefix)
                && cdkPrefix.equals(matConfig.cdkPrefix) && overlayAbsolutePath.equals(
                matConfig.overlayAbsolutePath) && version == matConfig.version && internalSelectors.equals(
                matConfig.internalSelectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagPrefix, componentCssPrefix, cdkPrefix, overlayAbsolutePath, version,
                internalSelectors);
    }

    @Override
    public String toString() {
        return "MatConfig{" + "tagPrefix='" + tagPrefix + '\''
                + ", componentCssPrefix='" + componentCssPrefix + '\''
                + ", cdkPrefix='" + cdkPrefix + '\''
                + ", overlayAbsolutePath='" + overlayAbsolutePath + '\''
                + ", version=" + version
                + ", internalSelectors=" + internalSelectors
                + '}';
    }

    /**
     * Creates an instance with custom tag prefix, CDK prefix, overlay path and the default MDC component CSS prefix.
     *
     * @param tagPrefix the prefix of the html tag.
     * @param cdkPrefix the prefix of the cdk tag.
     * @param overlayAbsolutePath the prefix of the CDK (Component Dev Kit) classes,
     * @return the created instance
     */
    public static MatConfig create(String tagPrefix, String cdkPrefix, String overlayAbsolutePath) {
        MatConfig config = new MatConfig();
        config.setTagPrefix(tagPrefix);
        config.setCdkPrefix(cdkPrefix);
        config.setOverlayAbsolutePath(overlayAbsolutePath);
        return config;
    }

    /**
     * Creates an instance with all values including a custom component CSS prefix.
     *
     * @param tagPrefix the prefix of the html tag.
     * @param componentCssPrefix the immutable component CSS prefix
     * @param cdkPrefix the prefix of the cdk tag.
     * @param overlayAbsolutePath the prefix of the CDK (Component Dev Kit) classes,
     * @return the created instance
     */
    public static MatConfig create(String tagPrefix, String componentCssPrefix, String cdkPrefix,
                                   String overlayAbsolutePath) {
        MatConfig config = new MatConfig(componentCssPrefix);
        config.setTagPrefix(tagPrefix);
        config.setCdkPrefix(cdkPrefix);
        config.setOverlayAbsolutePath(overlayAbsolutePath);
        return config;
    }
}
