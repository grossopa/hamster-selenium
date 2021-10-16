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

package com.github.grossopa.selenium.examples.mui.v5.surfaces;

import com.github.grossopa.selenium.component.mui.v4.navigation.MuiAccordion;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for {@link MuiAccordion}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiAccordionTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic features
     *
     * @see <a href="https://mui.com/components/accordion/#basic-accordion">
     * https://mui.com/components/accordion/#basic-accordion</a>
     */
    public void testBasicAccordion() {
        List<MuiAccordion> accordionList = driver.findComponent(By.id("BasicAccordion.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiAccordion-root"), c -> c.as(muiV5()).toAccordion());
        assertEquals(3, accordionList.size());
        accordionList.forEach(accordion -> {
            assertTrue(accordion.validate());
            assertFalse(accordion.isExpand());
        });

        assertEquals("Accordion 1", requireNonNull(accordionList.get(0).getAccordionSummary()).getText());
        assertEquals("Accordion 2", requireNonNull(accordionList.get(1).getAccordionSummary()).getText());
        assertEquals("Disabled Accordion", requireNonNull(accordionList.get(2).getAccordionSummary()).getText());

        assertTrue(requireNonNull(accordionList.get(0)).isEnabled());
        assertTrue(requireNonNull(accordionList.get(1)).isEnabled());
        assertFalse(requireNonNull(accordionList.get(2)).isEnabled());

        requireNonNull(accordionList.get(1).getAccordionSummary()).click();
        assertTrue(accordionList.get(1).isExpand());
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                        + "Suspendisse malesuada lacus ex, sit amet blandit leo lobortis eget.",
                requireNonNull(accordionList.get(1).getAccordionDetails()).getText());

        requireNonNull(accordionList.get(1).getAccordionSummary()).click();
        driver.threadSleep(1000L);
        assertFalse(accordionList.get(1).isExpand());
    }


    public static void main(String[] args) {
        MuiAccordionTestCases test = new MuiAccordionTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/accordion/");

        test.testBasicAccordion();
    }
}
