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

import com.github.grossopa.hamster.selenium.component.mat.main.MatProgressBar;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.hamster.selenium.component.mat.MatComponents.mat;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for {@link MatProgressBar}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MatProgressBarTestCases extends AbstractBrowserSupport {

    public void testBufferProgressBar() {
        MatProgressBar progressBar = driver.findComponent(By.tagName("progress-bar-buffer-example"))
                .findComponent(By.tagName("mat-progress-bar")).as(mat()).toProgressBar();
        assertTrue(progressBar.validate());
        assertEquals(MatProgressBar.Mode.BUFFER, progressBar.getMode());
    }

    public void testConfigurableProgressBar() {
        MatProgressBar progressBar = driver.findComponent(By.tagName("progress-bar-configurable-example"))
                .findComponent(By.tagName("mat-progress-bar")).as(mat()).toProgressBar();

        assertTrue(progressBar.validate());
        assertEquals("0", progressBar.getMinValue());
        assertEquals("100", progressBar.getMaxValue());
        assertEquals("50", progressBar.getValue());
        assertEquals(MatProgressBar.Mode.DETERMINATE, progressBar.getMode());
    }

    public void testIndeterminateProgressBar() {
        MatProgressBar progressBar = driver.findComponent(By.tagName("progress-bar-indeterminate-example"))
                .findComponent(By.tagName("mat-progress-bar")).as(mat()).toProgressBar();
        assertEquals(MatProgressBar.Mode.INDETERMINATE, progressBar.getMode());
    }

    public void testQueryProgressBar() {
        MatProgressBar progressBar = driver.findComponent(By.tagName("progress-bar-query-example"))
                .findComponent(By.tagName("mat-progress-bar")).as(mat()).toProgressBar();
        assertEquals(MatProgressBar.Mode.QUERY, progressBar.getMode());
    }

    public static void main(String[] args) {
        MatProgressBarTestCases test = new MatProgressBarTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://material.angular.io/components/progress-bar/examples");

        test.testBufferProgressBar();
        test.testConfigurableProgressBar();
        test.testIndeterminateProgressBar();
        test.testQueryProgressBar();
    }
}
