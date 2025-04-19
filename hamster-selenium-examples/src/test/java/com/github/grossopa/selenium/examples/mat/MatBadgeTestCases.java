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

import com.github.grossopa.hamster.selenium.component.mat.main.MatBadge;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the actual feature of {@link MatBadge}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatBadgeTestCases extends AbstractBrowserSupport {

    public void testBadge() {
        driver.navigate().to("https://material.angular.io/components/badge/examples");

        WebComponent container = driver.findComponent(By.tagName("badge-overview-example"));
        List<MatBadge> badges = container.findComponentsAs(By.className("mat-badge"), c -> c.as(mat()).toBadge());
        assertEquals(5, badges.size());
        badges.stream().peek(badge -> assertTrue(badge.validate())).map(MatBadge::getBadgeContent)
                .forEach(content -> assertTrue(content.validate()));

        assertEquals("4", badges.get(0).getBadgeContent().getText());
        assertEquals("1", badges.get(1).getBadgeContent().getText());
        assertEquals("8", badges.get(2).getBadgeContent().getText());
        assertEquals("7", badges.get(3).getBadgeContent().getText());
        assertEquals("15", badges.get(4).getBadgeContent().getText());
    }

    public static void main(String[] args) {
        MatBadgeTestCases test = new MatBadgeTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testBadge();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
