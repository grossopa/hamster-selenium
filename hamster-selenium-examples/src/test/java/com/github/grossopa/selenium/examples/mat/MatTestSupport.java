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
package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.hamster.selenium.component.mat.MatComponents;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.config.MatVersion;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;

/**
 * Version-aware base class for Angular Material E2E tests.
 *
 * <p>Provides {@link #baseUrl()} and {@link #matComponents()} as overridable hooks so that
 * legacy test subclasses can point at the v12 documentation site with a legacy {@link MatConfig}
 * without duplicating any test logic.</p>
 *
 * @author Jack Yin
 * @since 1.16
 */
public class MatTestSupport extends AbstractBrowserSupport {

    /**
     * Returns the base URL for the Angular Material documentation site.
     *
     * <p>Default is the latest MDC-based site. Legacy subclasses override this to return
     * the v12 URL.</p>
     *
     * @return the base URL ending with {@code /components/}
     */
    protected String baseUrl() {
        return "https://material.angular.dev/components/";
    }

    /**
     * Returns the {@link MatComponents} factory configured for the current Angular Material version.
     *
     * <p>Default is MDC (latest). Legacy subclasses override this to return a legacy-configured
     * instance.</p>
     *
     * @return the mat components factory
     */
    protected MatComponents matComponents() {
        return MatComponents.mat();
    }

    /**
     * Returns the {@link MatConfig} from the current {@link #matComponents()} factory.
     *
     * @return the mat config instance
     */
    protected MatConfig matConfig() {
        return matComponents().getConfig();
    }

    /**
     * Returns the version-correct CSS class name for the given component.
     *
     * <p>When the current version is {@link MatVersion#MDC}, returns
     * {@code getComponentCssPrefix(componentName) + mdcSuffix}. Otherwise returns
     * {@code getComponentCssPrefix(componentName) + legacySuffix}.</p>
     *
     * @param mdcSuffix     the CSS class suffix for MDC mode, e.g. "button-base"
     * @param legacySuffix  the CSS class suffix for legacy mode, e.g. "button"
     * @return the full CSS class name
     */
    protected String cssClass( String mdcSuffix, String legacySuffix) {
        MatConfig config = matConfig();
        return config.getComponentCssPrefix()
                + (config.getVersion() == MatVersion.MDC ? mdcSuffix : legacySuffix);
    }
}
