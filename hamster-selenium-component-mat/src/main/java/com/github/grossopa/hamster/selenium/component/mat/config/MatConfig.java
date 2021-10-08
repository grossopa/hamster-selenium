/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.hamster.selenium.component.mat.config;

import com.github.grossopa.selenium.core.component.ComponentConfig;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * The root configuration for Material UI Angular components
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatConfig implements ComponentConfig {

    private String tagPrefix = "mat-";

    private String cssPrefix = "mat-";

    private String cdkPrefix = "cdk-";

    @SuppressWarnings("java:S1075")
    private String overlayAbsolutePath = "/html/body";

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
     * Gets the prefix of the css classes, e.g. &lt;mat-option class="<b>mat-selected</b>" &gt;....default value is
     * "mat-".
     *
     * @return the prefix of the css classes
     */
    public String getCssPrefix() {
        return cssPrefix;
    }

    /**
     * Sets the prefix of the css classes.
     *
     * @param cssPrefix the new prefix of css classes to set
     */
    public void setCssPrefix(String cssPrefix) {
        requireNonNull(cssPrefix);
        this.cssPrefix = cssPrefix;
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
     * Gets the isChecked CSS. default value is "Mui-checked"
     *
     * @return the isChecked CSS
     */
    @Override
    public String getIsCheckedCss() {
        return getCssPrefix() + "checked";
    }

    /**
     * Gets the isSelected CSS. default value is "Mui-selected"
     *
     * @return the isSelected CSS
     */
    @Override
    public String getIsSelectedCss() {
        return getCssPrefix() + "selected";
    }

    /**
     * Gets the isDisabled CSS. default value is "Mui-disabled"
     *
     * @return the isDisabled CSS
     */
    @Override
    public String getIsDisabledCss() {
        return getCssPrefix() + "disabled";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatConfig)) {
            return false;
        }
        MatConfig matConfig = (MatConfig) o;
        return tagPrefix.equals(matConfig.tagPrefix) && cssPrefix.equals(matConfig.cssPrefix) && cdkPrefix
                .equals(matConfig.cdkPrefix) && overlayAbsolutePath.equals(matConfig.overlayAbsolutePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagPrefix, cssPrefix, cdkPrefix, overlayAbsolutePath);
    }

    @Override
    public String toString() {
        return "MatConfig{" + "tagPrefix='" + tagPrefix + '\'' + ", cssPrefix='" + cssPrefix + '\'' + ", cdkPrefix='"
                + cdkPrefix + '\'' + ", overlayAbsolutePath='" + overlayAbsolutePath + '\'' + '}';
    }

    /**
     * Creates an instance with all values.
     *
     * @param tagPrefix the prefix of the html tag.
     * @param cssPrefix the prefix of the css tag.
     * @param cdkPrefix the prefix of the cdk css tag.
     * @param overlayAbsolutePath the prefix of the CDK (Component Dev Kit) classes,
     * @return the created instance
     */
    public static MatConfig create(String tagPrefix, String cssPrefix, String cdkPrefix, String overlayAbsolutePath) {
        MatConfig config = new MatConfig();
        config.setTagPrefix(tagPrefix);
        config.setCssPrefix(cssPrefix);
        config.setCdkPrefix(cdkPrefix);
        config.setOverlayAbsolutePath(overlayAbsolutePath);
        return config;
    }
}
