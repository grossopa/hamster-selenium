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

package com.github.grossopa.selenium.examples.locator;

import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.NoSuchElementException;

import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testing locator related functions.
 *
 * @author Jack Yin
 * @since 1.5
 */
public class LocatorTestCases extends AbstractBrowserSupport {

    @SuppressWarnings("all")
    public void testBy2Builder() {
        driver.navigate().to("https://www.google.com/ncr");

        WebComponent googleSearch;

        googleSearch = driver
                .findComponent(xpathBuilder().anywhere("input").attr("value").contains("oogle Se").build());
        assertEquals("Google Search", googleSearch.getAttribute("value"));
        googleSearch = driver.findComponent(xpathBuilder().anywhere().attr("value").contains("oogle Se").build());
        assertEquals("Google Search", googleSearch.getAttribute("value"));
        googleSearch = driver.findComponent(xpathBuilder().anywhere().attr("value").startsWith("Google Se").build());
        assertEquals("Google Search", googleSearch.getAttribute("value"));
        googleSearch = driver.findComponent(xpathBuilder().anywhere().attr("value").exact("Google Search").build());
        assertEquals("Google Search", googleSearch.getAttribute("value"));

        assertThrows(NoSuchElementException.class, () -> driver
                .findComponent(xpathBuilder().relative("input").attr("value").contains("oogle Se").build()));
        assertThrows(NoSuchElementException.class, () -> driver
                .findComponent(xpathBuilder().anywhere("input").attr("value").startsWith("oogle Se").build()));
        assertThrows(NoSuchElementException.class,
                () -> driver.findComponent(xpathBuilder().anywhere("input").attr("value").exact("oogle Se").build()));
        assertThrows(NoSuchElementException.class,
                () -> driver.findComponent(xpathBuilder().anywhere().attr("value").startsWith("oogle Se").build()));

        WebComponent lucky = driver.findComponent(xpathBuilder().anywhere().attr("value").startsWith("I'm").build());
        assertEquals("I'm Feeling Lucky", lucky.getAttribute("value"));
    }

    public static void main(String[] args) {
        LocatorTestCases test = new LocatorTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testBy2Builder();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
