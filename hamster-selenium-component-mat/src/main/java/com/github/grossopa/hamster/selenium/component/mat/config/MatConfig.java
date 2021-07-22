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

/**
 * The root configuration for Material UI Angular components
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatConfig {

    /**
     * Attribute class
     */
    public static final String ATTR_CLASS = "class";

    private String tagPrefix = "mat-";

    private String cssPrefix = "mat-";

    private String cdkPrefix = "cdk-";

    @SuppressWarnings("java:S1075")
    private String overlayAbsolutePath = "/html/body";

    public String getTagPrefix() {
        return tagPrefix;
    }

    public void setTagPrefix(String tagPrefix) {
        this.tagPrefix = tagPrefix;
    }

    public String getCssPrefix() {
        return cssPrefix;
    }

    public void setCssPrefix(String cssPrefix) {
        this.cssPrefix = cssPrefix;
    }

    public String getOverlayAbsolutePath() {
        return overlayAbsolutePath;
    }

    public void setOverlayAbsolutePath(String overlayAbsolutePath) {
        this.overlayAbsolutePath = overlayAbsolutePath;
    }

    public String getCdkPrefix() {
        return cdkPrefix;
    }

    public void setCdkPrefix(String cdkPrefix) {
        this.cdkPrefix = cdkPrefix;
    }
}
