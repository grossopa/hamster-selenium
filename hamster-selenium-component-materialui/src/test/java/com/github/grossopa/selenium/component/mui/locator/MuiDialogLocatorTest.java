/*
 * Copyright © 2020 the original author or authors.
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

package com.github.grossopa.selenium.component.mui.locator;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.feedback.MuiDialog;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiDialogLocator}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiDialogLocatorTest {

    MuiDialogLocator testSubject;
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getOverlayAbsolutePath()).thenReturn("/html/body");
        when(config.getCssPrefix()).thenReturn("Mui");
        List<WebComponent> dialogs = asList(createMockDialog(false), createMockDialog(false), createMockDialog(true));
        when(driver.findComponents(By.xpath("/html/body/div[contains(@class, 'MuiDialog-root')]"))).thenReturn(dialogs);

        testSubject = new MuiDialogLocator(driver, config);
    }

    private WebComponent createMockDialog(boolean visible) {
        WebComponent component = mock(WebComponent.class);
        WebElement element = mock(WebElement.class);
        when(component.getWrappedElement()).thenReturn(element);
        when(element.isDisplayed()).thenReturn(visible);
        return component;
    }

    @Test
    void findAllDialogs() {
        List<MuiDialog> dialogList = testSubject.findAllDialogs();
        assertEquals(3, dialogList.size());
    }

    @Test
    void findVisibleDialogs() {
        List<MuiDialog> dialogList = testSubject.findVisibleDialogs();
        assertEquals(1, dialogList.size());
    }
}
