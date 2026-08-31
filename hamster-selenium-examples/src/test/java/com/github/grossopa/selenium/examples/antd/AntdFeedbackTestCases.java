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

import com.github.grossopa.selenium.component.antd.feedback.*;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
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
 * Test cases for feedback components based on the official Ant Design documentation page.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class AntdFeedbackTestCases extends AbstractBrowserSupport {

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

    public void testAlert() {
        driver.navigate().to("https://ant.design/components/alert/");

        AntdAlert alert = demo("alert-demo-basic")
                .findComponent(By.className("ant-alert")).as(antd()).toAlert();

        assertTrue(alert.validate());
        assertEquals("success", alert.getType());
        assertFalse(alert.isClosable());

        AntdAlert closableAlert = demo("alert-demo-closable")
                .findComponent(By.className("ant-alert")).as(antd()).toAlert();
        assertTrue(closableAlert.isClosable());
    }

    public void testModal() {
        driver.navigate().to("https://ant.design/components/modal/");

        WebComponent openButton = demo("modal-demo-basic").findComponent(By2.textExact("Open Modal"))
                .findComponent(By2.parent());
        scrollIntoView(openButton);
        openButton.click();
        sleep();

        // the page also renders a hidden semantic modal, so filter by the visible title text
        AntdModal modal = driver.findComponents(By.className("ant-modal")).stream()
                .filter(component -> component.getText().contains("Basic Modal")).findFirst().orElseThrow()
                .as(antd()).toModal();
        assertTrue(modal.validate());
        assertEquals("Basic Modal", modal.getTitle().getText());
        assertFalse(modal.getBody().getText().isEmpty());

        modal.close();
    }

    public void testProgress() {
        driver.navigate().to("https://ant.design/components/progress/");

        AntdProgress progress = demo("progress-demo-line")
                .findComponent(By.className("ant-progress")).as(antd()).toProgress();

        assertTrue(progress.validate());
        assertEquals(30, progress.getPercent());
    }

    public void testSkeleton() {
        driver.navigate().to("https://ant.design/components/skeleton/");

        AntdSkeleton skeleton = demo("skeleton-demo-active")
                .findComponent(By.className("ant-skeleton")).as(antd()).toSkeleton();

        assertTrue(skeleton.validate());
        assertTrue(skeleton.isActive());
    }

    public void testSpin() {
        driver.navigate().to("https://ant.design/components/spin/");

        AntdSpin spin = demo("spin-demo-basic")
                .findComponent(By.className("ant-spin")).as(antd()).toSpin();

        assertTrue(spin.validate());
        assertTrue(spin.isSpinning());
    }

    public void testDrawer() {
        driver.navigate().to("https://ant.design/components/drawer/");

        WebComponent openButton = demo("components-drawer-demo-basic-right").findComponent(By2.textExact("Open"))
                .findComponent(By2.parent());
        scrollIntoView(openButton);
        openButton.click();
        sleep();

        // the page also renders a hidden semantic drawer, so filter by the visible title text
        AntdDrawer drawer = driver.findComponents(By.className("ant-drawer")).stream()
                .filter(component -> component.getText().contains("Basic Drawer")).findFirst().orElseThrow()
                .as(antd()).toDrawer();
        assertTrue(drawer.validate());
        assertTrue(drawer.isOpen());
        assertEquals("Basic Drawer", drawer.getTitle().getText());
        assertFalse(drawer.getBody().getText().isEmpty());

        drawer.close();
    }

    /**
     * Waits for a short period for the opening animation to complete.
     */
    private static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Scrolls the element into the viewport center to avoid the click being intercepted by the floating site
     * toolbar.
     *
     * @param element the element to scroll into view
     */
    private void scrollIntoView(WebComponent element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public static void main(String[] args) {
        AntdFeedbackTestCases test = new AntdFeedbackTestCases();
        try {
            test.setUpDriver(EDGE);
            test.testAlert();
            test.testModal();
            test.testProgress();
            test.testSkeleton();
            test.testSpin();
            test.testDrawer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
