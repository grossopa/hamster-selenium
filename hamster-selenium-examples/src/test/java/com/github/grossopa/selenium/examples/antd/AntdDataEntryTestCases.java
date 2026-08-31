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

import com.github.grossopa.selenium.component.antd.dataentry.*;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.github.grossopa.selenium.component.antd.AntdComponents.antd;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for data entry components based on the official Ant Design documentation page.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class AntdDataEntryTestCases extends AbstractBrowserSupport {

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

    public void testCheckbox() {
        driver.navigate().to("https://ant.design/components/checkbox/");

        AntdCheckbox checkbox = demo("checkbox-demo-basic")
                .findComponent(By.className("ant-checkbox-wrapper")).as(antd()).toCheckbox();

        assertTrue(checkbox.validate());
        assertFalse(checkbox.isSelected());
        assertFalse(checkbox.isIndeterminate());
        assertTrue(checkbox.isEnabled());
    }

    public void testRadio() {
        driver.navigate().to("https://ant.design/components/radio/");

        AntdRadio radio = demo("radio-demo-basic")
                .findComponent(By.className("ant-radio-wrapper")).as(antd()).toRadio();

        assertTrue(radio.validate());
        assertFalse(radio.isSelected());
        assertTrue(radio.isEnabled());
    }

    public void testSwitch() {
        driver.navigate().to("https://ant.design/components/switch/");

        AntdSwitch antdSwitch = demo("switch-demo-basic")
                .findComponent(By.className("ant-switch")).as(antd()).toSwitch();

        assertTrue(antdSwitch.validate());
        assertTrue(antdSwitch.isSelected());
    }

    public void testInput() {
        driver.navigate().to("https://ant.design/components/input/");

        AntdInput input = demo("input-demo-basic")
                .findComponent(By.className("ant-input")).as(antd()).toInput();

        assertTrue(input.validate());
        // the demo input is a controlled component, set the native value and dispatch the input event
        setValueByScript(input, "Hello Hamster");
        assertEquals("Hello Hamster", input.getInput().getDomProperty("value"));
        setValueByScript(input, "");
        assertEquals("", input.getInput().getDomProperty("value"));
    }

    /**
     * Sets the value of a controlled input via its native setter and dispatches the input event.
     *
     * @param input the input component
     * @param value the value to set
     */
    private void setValueByScript(AntdInput input, String value) {
        ((JavascriptExecutor) driver).executeScript(
                "const setter = Object.getOwnPropertyDescriptor(HTMLInputElement.prototype, 'value').set;"
                        + "setter.call(arguments[0], arguments[1]);"
                        + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", input, value);
    }

    public void testSelect() {
        driver.navigate().to("https://ant.design/components/select/");

        AntdSelect select = demo("select-demo-basic")
                .findComponent(By.className("ant-select")).as(antd()).toSelect();

        assertTrue(select.validate());
        assertFalse(select.getOptions().isEmpty());

        select.selectOption("Lucy");
        assertEquals("Lucy", select.getSelectText());
    }

    public static void main(String[] args) {
        AntdDataEntryTestCases test = new AntdDataEntryTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testCheckbox();
            test.testRadio();
            test.testSwitch();
            test.testInput();
            test.testSelect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
