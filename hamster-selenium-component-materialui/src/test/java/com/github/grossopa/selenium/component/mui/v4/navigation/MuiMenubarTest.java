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
package com.github.grossopa.selenium.component.mui.v4.navigation;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiMenubar}
 *
 * @author Jack Yin
 * @since 1.18
 */
class MuiMenubarTest {

    MuiMenubar testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement menuItemElement1 = mock(WebElement.class);
    WebElement menuItemElement2 = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.getRootCss("MenuItem")).thenReturn("MuiMenuItem-root");
        testSubject = new MuiMenubar(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Menubar", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V6}, testSubject.versions().toArray());
    }

    @Test
    void getMenuItemsEmpty() {
        when(element.findElements(By.className("MuiMenuItem-root"))).thenReturn(emptyList());
        assertTrue(testSubject.getMenuItems().isEmpty());
    }

    @Test
    void getMenuItems() {
        when(element.findElements(By.className("MuiMenuItem-root"))).thenReturn(List.of(menuItemElement1,
                menuItemElement2));

        List<MuiMenuItem> result = testSubject.getMenuItems();
        assertEquals(2, result.size());
        assertEquals(MuiMenuItem.class, result.get(0).getClass());
    }

    @Test
    void openMenuItemByIndex() {
        when(element.findElements(By.className("MuiMenuItem-root"))).thenReturn(List.of(menuItemElement1,
                menuItemElement2));

        testSubject.openMenuItem(0);
        verify(menuItemElement1).click();
    }

    @Test
    void openMenuItemByLabel() {
        when(element.findElements(By.className("MuiMenuItem-root"))).thenReturn(List.of(menuItemElement1,
                menuItemElement2));
        when(menuItemElement1.getText()).thenReturn("File");
        when(menuItemElement2.getText()).thenReturn("Edit");

        testSubject.openMenuItem("Edit");
        verify(menuItemElement2).click();
    }

    @Test
    void openMenuItemByLabelNotFound() {
        when(element.findElements(By.className("MuiMenuItem-root"))).thenReturn(List.of(menuItemElement1));
        when(menuItemElement1.getText()).thenReturn("File");

        assertThrows(IllegalArgumentException.class, () -> testSubject.openMenuItem("NonExistent"));
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiMenubar{element=element-toString}", testSubject.toString());
    }
}
