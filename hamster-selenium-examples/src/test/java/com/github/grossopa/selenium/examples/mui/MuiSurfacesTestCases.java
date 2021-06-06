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

package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.feedback.MuiDialog;
import com.github.grossopa.selenium.component.mui.feedback.MuiSnackbar;
import com.github.grossopa.selenium.component.mui.inputs.MuiButton;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for Surfaces components.
 *
 * @author Jack Yin
 * @since 1.1
 */
public class MuiSurfacesTestCases extends AbstractBrowserSupport {

    @SuppressWarnings("java:S2925")
    public void testBackdrop() {
        driver.navigate().to("https://material-ui.com/components/backdrop/");
        MuiButton button = driver.findComponent(By2.textContains("Show backdrop")).findComponent(By.xpath("parent::*"))
                .as(mui()).toButton();
        assertFalse(driver.findComponent(By.className("MuiBackdrop-root")).as(mui()).toBackdrop().isDisplayed());
        button.click();
        driver.threadSleep(200L);
        assertTrue(driver.findComponent(By.className("MuiBackdrop-root")).as(mui()).toBackdrop().isDisplayed());
    }

    public void testDialog() {
        driver.navigate().to("https://material-ui.com/components/dialogs/");

        Optional<MuiDialog> dialogFirstOpt = driver.findComponents(By.className("MuiDialog-root")).stream()
                .map(webComponent -> webComponent.as(mui()).toDialog()).filter(WebElement::isDisplayed).findFirst();
        assertTrue(dialogFirstOpt.isEmpty());

        MuiButton button = driver.findComponent(By2.textContains("Open simple dialog")).findComponent(By2.parent())
                .as(mui()).toButton();
        button.click();

        Optional<MuiDialog> dialogOpt = driver.findComponents(By.className("MuiDialog-root")).stream()
                .map(webComponent -> webComponent.as(mui()).toDialog()).filter(WebElement::isDisplayed).findFirst();
        assertTrue(dialogOpt.isPresent());
        MuiDialog dialog = dialogOpt.get();
        assertTrue(dialog.validate());
        assertEquals("Set backup account", dialog.getDialogTitle().getText());
        dialog.close(800L);

        MuiButton openAlertButton = driver.findComponent(By2.textContains("Open alert dialog"))
                .findComponent(By2.parent()).as(mui()).toButton();
        driver.moveTo(openAlertButton);
        openAlertButton.click();
        Optional<MuiDialog> alertDialogOpt = driver.findComponents(By.className("MuiDialog-root")).stream()
                .map(webComponent -> webComponent.as(mui()).toDialog()).filter(WebElement::isDisplayed).findFirst();
        assertTrue(alertDialogOpt.isPresent());
        MuiDialog alertDialog = alertDialogOpt.get();
        assertTrue(alertDialog.validate());

        assertEquals("Use Google's location service?", alertDialog.getDialogTitle().getText());
        assertEquals("Let Google help apps determine location. This means sending anonymous "
                + "location data to Google, even when no apps are running.", alertDialog.getDialogContent().getText());
        assertEquals(2L, alertDialog.getDialogActions().findComponents(By.className("MuiButton-root")).stream()
                .map(webComponent -> webComponent.as(mui()).toButton()).filter(AbstractMuiComponent::validate).count());
        dialog.close(800L);
    }

    public void testSnackbar() {
        driver.navigate().to("https://material-ui.com/components/snackbars/");

        MuiButton simpleButton = driver.findComponent(By2.textContains("Open simple snackbar"))
                .findComponent(By2.parent()).as(mui()).toButton();
        simpleButton.click();
        MuiSnackbar simpleSnackbar = simpleButton.findComponent(By2.parent())
                .findComponent(By.className("MuiSnackbar-root")).as(mui()).toSnackbar(6500);

        assertEquals("Note archived", simpleSnackbar.getContent().getMessage().getText());
        assertEquals(2,
                simpleSnackbar.getContent().getAction().findComponents(By.className("MuiButtonBase-root")).size());
        simpleSnackbar.startAutoHideCheck();
    }

    public static void main(String[] args) {
        MuiSurfacesTestCases test = new MuiSurfacesTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testBackdrop();
            test.testDialog();
            test.testSnackbar();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}
