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

package com.github.grossopa.selenium.component.mui.v4.navigation;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.component.util.WebComponentUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;

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
     * The component name
     */
    public static final String COMPONENT_NAME = "Accordion";

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

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5);
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
     * Gets the accordion actions component. could be null if it's not defined.
     *
     * @return the accordion actions component. could be null if it's not defined.
     */
    @Nullable
    public MuiAccordionActions getAccordionActions() {
        List<WebComponent> result = this.findComponents(By.className(config.getRootCss("AccordionActions")));
        return result.isEmpty() ? null : new MuiAccordionActions(result.get(0), driver, config);
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

    /**
     * Try to expand the accordion
     */
    public void expand() {
        MuiAccordionSummary summary = getAccordionSummary();
        if (summary == null) {
            // try to click itself
            this.element.click();
        } else {
            summary.getExpandButton().click();
        }
    }


    @Override
    public boolean isEnabled() {
        return !WebComponentUtils.attributeContains(element, "class", config.getCssPrefix() + "-disabled");
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public String toString() {
        return "MuiAccordion{" + "element=" + element + '}';
    }
}
