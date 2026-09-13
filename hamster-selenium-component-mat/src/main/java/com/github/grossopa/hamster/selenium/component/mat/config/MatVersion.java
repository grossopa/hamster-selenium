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

/**
 * The Angular Material version enum for CSS selector resolution.
 *
 * <ul>
 *     <li>{@link #LEGACY} — Angular Material v12 and earlier, using {@code mat-*} CSS prefix for all components.</li>
 *     <li>{@link #MDC} — Angular Material latest version (v15+), using {@code mat-mdc-*} CSS prefix for MDC-based
 *         components. Some components (e.g. Accordion, Badge, Stepper) still use the legacy {@code mat-*} prefix.</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.17
 * @see MatConfig#getComponentCssPrefix()
 */
public enum MatVersion {

    /**
     * Angular Material v12 and earlier. All components use the {@code mat-} CSS prefix.
     */
    LEGACY,

    /**
     * Angular Material latest version (v15+). Most components use the {@code mat-mdc-} CSS prefix, except for a
     * predefined set of non-MDC components that continue to use {@code mat-}.
     */
    MDC
}
