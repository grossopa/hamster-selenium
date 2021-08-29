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

package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.hamster.selenium.component.mat.main.MatButton;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the actual features of {@link MatButton}
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatButtonTestCases extends AbstractBrowserSupport {

    public void testButtons() {
        driver.navigate().to("https://material.angular.io/components/button/examples");

        WebComponent raisedButtonContainer = driver.findComponent(By.id("button-overview"))
                .findComponent(By.tagName("button-overview-example"))
                .findComponents(By2.xpathBuilder().relative("section").build()).get(1);

        List<MatButton> buttons = raisedButtonContainer.findComponentsAs(By.className("mat-button-base"),
                c -> c.as(mat()).toButton());
        assertEquals(6, buttons.size());
        assertEquals(6L, buttons.stream().filter(MatButton::validate).count());
        assertTrue(buttons.get(0).isEnabled());
        assertFalse(buttons.get(4).isEnabled());
        assertEquals("Basic", buttons.get(0).getText());
        assertEquals("Primary", buttons.get(1).getText());
        assertEquals("Accent", buttons.get(2).getText());
        assertEquals("Warn", buttons.get(3).getText());
        assertEquals("Disabled", buttons.get(4).getText());
        assertEquals("Link", buttons.get(5).getText());
        assertDoesNotThrow(() -> buttons.get(2).click());
    }

    public static void main(String[] args) {
        MatButtonTestCases test = new MatButtonTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testButtons();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
