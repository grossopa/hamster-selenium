/*
 * Copyright © 2023 the original author or authors.
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
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiDrawerTest {

    MuiDrawer testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        testSubject = new MuiDrawer(element, driver, config);
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("Drawer", testSubject.getComponentName());
    }

    @Test
    void isOpenTrue() {
        when(element.getCssValue("visibility")).thenReturn("visible");
        when(element.getAttribute("aria-hidden")).thenReturn("false");
        assertTrue(testSubject.isOpen());
    }

    @Test
    void isOpenFalseHidden() {
        when(element.getCssValue("visibility")).thenReturn("hidden");
        assertFalse(testSubject.isOpen());
    }

    @Test
    void isOpenFalseAriaHidden() {
        when(element.getCssValue("visibility")).thenReturn("visible");
        when(element.getAttribute("aria-hidden")).thenReturn("true");
        assertFalse(testSubject.isOpen());
    }

    @Test
    void openWhenClosed() {
        when(element.getCssValue("visibility")).thenReturn("hidden");
        testSubject.open();
        verify(element).click();
    }

    @Test
    void openWhenAlreadyOpen() {
        when(element.getCssValue("visibility")).thenReturn("visible");
        when(element.getAttribute("aria-hidden")).thenReturn("false");
        testSubject.open();
        verify(element, never()).click();
    }

    @Test
    void closeWhenOpen() {
        when(element.getCssValue("visibility")).thenReturn("visible");
        when(element.getAttribute("aria-hidden")).thenReturn("false");
        testSubject.close();
        verify(element).click();
    }

    @Test
    void closeWhenAlreadyClosed() {
        when(element.getCssValue("visibility")).thenReturn("hidden");
        testSubject.close();
        verify(element, never()).click();
    }

    @Test
    void toggle() {
        testSubject.toggle();
        verify(element).click();
    }

    @Test
    void getNavigationItems() {
        WebElement listElement = mock(WebElement.class);
        WebElement item1 = mock(WebElement.class);
        WebElement item2 = mock(WebElement.class);
        when(element.findElement(By.className("MuiList-root"))).thenReturn(listElement);
        when(listElement.findElements(By.tagName("li"))).thenReturn(Arrays.asList(item1, item2));
        List<WebComponent> items = testSubject.getNavigationItems();
        assertEquals(2, items.size());
    }

    @Test
    void getNavigationItemsFallback() {
        when(element.findElement(By.className("MuiList-root"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        WebElement item1 = mock(WebElement.class);
        when(element.findElements(By.tagName("li"))).thenReturn(List.of(item1));
        List<WebComponent> items = testSubject.getNavigationItems();
        assertEquals(1, items.size());
    }

    @Test
    void getVariantPermanent() {
        when(element.getAttribute("class")).thenReturn("MuiDrawer-docked");
        assertEquals("permanent", testSubject.getVariant());
    }

    @Test
    void getVariantPersistentLeft() {
        when(element.getAttribute("class")).thenReturn("MuiDrawer-paperAnchorDockedLeft");
        assertEquals("persistent", testSubject.getVariant());
    }

    @Test
    void getVariantPersistentRight() {
        when(element.getAttribute("class")).thenReturn("MuiDrawer-paperAnchorDockedRight");
        assertEquals("persistent", testSubject.getVariant());
    }

    @Test
    void getVariantPersistentTop() {
        when(element.getAttribute("class")).thenReturn("MuiDrawer-paperAnchorDockedTop");
        assertEquals("persistent", testSubject.getVariant());
    }

    @Test
    void getVariantPersistentBottom() {
        when(element.getAttribute("class")).thenReturn("MuiDrawer-paperAnchorDockedBottom");
        assertEquals("persistent", testSubject.getVariant());
    }

    @Test
    void getVariantTemporary() {
        when(element.getAttribute("class")).thenReturn("MuiDrawer-root");
        assertEquals("temporary", testSubject.getVariant());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiDrawer{element=element-toString}", testSubject.toString());
    }
}
