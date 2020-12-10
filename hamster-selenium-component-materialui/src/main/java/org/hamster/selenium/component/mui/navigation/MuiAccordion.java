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

package org.hamster.selenium.component.mui.navigation;

import org.hamster.selenium.component.mui.AbstractMuiComponent;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Accordions contain creation flows and allow lightweight editing of an element.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/accordion/">
 * https://material-ui.com/components/accordion/</a>
 * @since 1.0
 */
public class MuiAccordion extends AbstractMuiComponent {
    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiAccordion(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    /**
     * Gets the accordion summary component. could be null if it's not defined.
     *
     * @return the accordion summary component. could be null if it's not defined.
     */
    @Nullable
    public MuiAccordionSummary getAccordionSummary() {
        List<WebComponent> result = this.findComponents(By.className(config.getRootCss("AccordionSummary")));
        return result.isEmpty() ? null : new MuiAccordionSummary(result.get(0), driver, config);
    }

    /**
     * Gets the accordion details component. could be null if it's not defined.
     *
     * @return the accordion details component. could be null if it's not defined.
     */
    @Nullable
    public MuiAccordionDetails getAccordionDetails() {
        List<WebComponent> result = this.findComponents(By.className(config.getRootCss("AccordionDetails")));
        return result.isEmpty() ? null : new MuiAccordionDetails(result.get(0), driver, config);
    }

    /**
     * Determines whether the Accordion Summary part is expanded.
     *
     * @return whether the Accordion Summary part is expanded.
     * @see MuiAccordionSummary#isExpand()
     */
    public boolean isExpand() {
        MuiAccordionSummary summary = getAccordionSummary();
        return summary != null && summary.isExpand();
    }

    @Override
    public String getComponentName() {
        return "Accordion";
    }


}
