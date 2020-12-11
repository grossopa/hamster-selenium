/*
 * Copyright © 2020 the original author or authors.
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

package com.github.grossopa.selenium.core.driver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariOptions;

/**
 * Abstract parent class for enrich the {@link org.openqa.selenium.WebDriver.Options}
 *
 * @author Jack Yin
 * @since 1.0
 */
public abstract class AbstractEnrichOptionsAction implements EnrichOptionsAction {

    @Override
    public Capabilities applyChrome(Capabilities input) {
        return doApplyChrome((ChromeOptions) input);
    }

    @Override
    public Capabilities applyEdge(Capabilities input) {
        return doApplyEdge((EdgeOptions) input);
    }

    @Override
    public Capabilities applyFirefox(Capabilities input) {
        return doApplyFirefox((FirefoxOptions) input);
    }

    @Override
    public Capabilities applyIE(Capabilities input) {
        return doApplyIE((InternetExplorerOptions) input);
    }

    @Override
    public Capabilities applyOpera(Capabilities input) {
        return doApplyOpera((OperaOptions) input);
    }

    @Override
    public Capabilities applySafari(Capabilities input) {
        return doApplySafari((SafariOptions) input);
    }

    /**
     * Enriches the {@link ChromeOptions} instance.
     *
     * @param options
     *         the {@link ChromeOptions}
     * @return the {@link ChromeOptions} instance
     */
    protected abstract Capabilities doApplyChrome(ChromeOptions options);

    /**
     * Enriches the {@link EdgeOptions} instance.
     *
     * @param options
     *         the {@link EdgeOptions}
     * @return the {@link EdgeOptions} instance
     */
    protected abstract Capabilities doApplyEdge(EdgeOptions options);

    /**
     * Enriches the {@link FirefoxOptions} instance.
     *
     * @param options
     *         the {@link FirefoxOptions}
     * @return the {@link FirefoxOptions} instance
     */
    protected abstract Capabilities doApplyFirefox(FirefoxOptions options);

    /**
     * Enriches the {@link InternetExplorerOptions} instance.
     *
     * @param options
     *         the {@link InternetExplorerOptions}
     * @return the {@link InternetExplorerOptions} instance
     */
    protected abstract Capabilities doApplyIE(InternetExplorerOptions options);

    /**
     * Enriches the {@link OperaOptions} instance.
     *
     * @param options
     *         the {@link OperaOptions}
     * @return the {@link OperaOptions} instance
     */
    protected abstract Capabilities doApplyOpera(OperaOptions options);

    /**
     * Enriches the {@link SafariOptions} instance.
     *
     * @param options
     *         the {@link SafariOptions}
     * @return the {@link SafariOptions} instance
     */
    protected abstract Capabilities doApplySafari(SafariOptions options);
}
