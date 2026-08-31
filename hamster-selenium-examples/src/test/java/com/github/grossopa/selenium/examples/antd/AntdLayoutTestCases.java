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
package com.github.grossopa.selenium.examples.antd;

import com.github.grossopa.selenium.component.antd.layout.*;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.github.grossopa.selenium.component.antd.AntdComponents.antd;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for layout components based on the official Ant Design documentation page.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class AntdLayoutTestCases extends AbstractBrowserSupport {

    /**
     * Waits for the lazy-loaded demo container to be rendered and returns it.
     *
     * @param id the demo container element id
     * @return the demo container component
     */
    private WebComponent demo(String id) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
        return driver.findComponent(By.id(id));
    }

    public void testDivider() {
        driver.navigate().to("https://ant.design/components/divider/");

        AntdDivider divider = demo("divider-demo-horizontal")
                .findComponent(By.className("ant-divider")).as(antd()).toDivider();

        assertTrue(divider.validate());
        assertFalse(divider.isVertical());
    }

    public void testSpace() {
        driver.navigate().to("https://ant.design/components/space/");

        AntdSpace space = demo("space-demo-base")
                .findComponent(By.className("ant-space")).as(antd()).toSpace();

        assertTrue(space.validate());
        assertFalse(space.getItems().isEmpty());
    }

    public static void main(String[] args) {
        AntdLayoutTestCases test = new AntdLayoutTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testDivider();
            test.testSpace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
