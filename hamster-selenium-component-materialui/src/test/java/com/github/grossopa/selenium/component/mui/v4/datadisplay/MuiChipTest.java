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

package com.github.grossopa.selenium.component.mui.v4.datadisplay;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiChip}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiChipTest {

    MuiChip testSubject;
    WebElement avatarElement = mock(WebElement.class);
    WebElement iconElement = mock(WebElement.class);
    WebElement labelElement = mock(WebElement.class);
    WebElement deleteIconElement = mock(WebElement.class);

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(element.findElement(By.className("MuiChip-label"))).thenReturn(labelElement);
        testSubject = new MuiChip(element, driver, config);
    }

    private void mockAvatar() {
        when(element.findElement(By.className("MuiChip-avatar"))).thenReturn(avatarElement);
        when(element.findElements(By.className("MuiChip-avatar"))).thenReturn(singletonList(avatarElement));
    }

    private void mockIcon() {
        when(element.findElement(By.className("MuiChip-icon"))).thenReturn(iconElement);
        when(element.findElements(By.className("MuiChip-icon"))).thenReturn(singletonList(iconElement));
    }

    private void mockDeleteIcon() {
        when(element.findElement(By.className("MuiChip-deleteIcon"))).thenReturn(deleteIconElement);
        when(element.findElements(By.className("MuiChip-deleteIcon"))).thenReturn(singletonList(deleteIconElement));
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("Chip", testSubject.getComponentName());
    }

    @Test
    void hasAvatar() {
        mockAvatar();
        assertTrue(testSubject.hasAvatar());
    }

    @Test
    void hasAvatarNegative() {
        assertFalse(testSubject.hasAvatar());
    }

    @Test
    void getAvatar() {
        mockAvatar();
        assertEquals(avatarElement, testSubject.getAvatar().getWrappedElement());
    }

    @Test
    void hasIcon() {
        mockIcon();
        assertTrue(testSubject.hasIcon());
    }

    @Test
    void hasIconNegative() {
        assertFalse(testSubject.hasIcon());
    }

    @Test
    void getIcon() {
        mockIcon();
        assertEquals(iconElement, testSubject.getIcon().getWrappedElement());
    }

    @Test
    void getLabel() {
        assertEquals(labelElement, testSubject.getLabel().getWrappedElement());
    }

    @Test
    void hasDeleteIcon() {
        mockDeleteIcon();
        assertTrue(testSubject.hasDeleteIcon());
    }

    @Test
    void hasDeleteIconNegative() {
        assertFalse(testSubject.hasDeleteIcon());
    }

    @Test
    void getDeleteIcon() {
        mockDeleteIcon();
        assertEquals(deleteIconElement, testSubject.getDeleteIcon().getWrappedElement());
    }

    @Test
    void avatarLocator() {
        assertEquals(By.className("MuiChip-avatar"), testSubject.avatarLocator());
    }

    @Test
    void iconLocator() {
        assertEquals(By.className("MuiChip-icon"), testSubject.iconLocator());
    }

    @Test
    void deleteIconLocator() {
        assertEquals(By.className("MuiChip-deleteIcon"), testSubject.deleteIconLocator());
    }

    @Test
    void isClickable() {
        when(element.getDomAttribute("class")).thenReturn(
                "MuiButtonBase-root MuiChip-root MuiChip-colorPrimary MuiChip-clickableColorPrimary "
                        + "MuiChip-deletableColorPrimary MuiChip-clickable");
        assertTrue(testSubject.isClickable());
    }

    @Test
    void isClickableNegative() {
        when(element.getDomAttribute("class")).thenReturn(
                "MuiButtonBase-root MuiChip-root MuiChip-colorPrimary MuiChip-clickableColorPrimary "
                        + "MuiChip-deletableColorPrimary");
        assertFalse(testSubject.isClickable());
    }

    @Test
    void isDeletable() {
        mockDeleteIcon();
        assertTrue(testSubject.isDeletable());
    }

    @Test
    void isDeletableNegative() {
        assertFalse(testSubject.isDeletable());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiChip{element=element-toString}", testSubject.toString());
    }
}
