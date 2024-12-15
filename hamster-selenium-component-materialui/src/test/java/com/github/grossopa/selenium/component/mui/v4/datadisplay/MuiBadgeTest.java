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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiBadge}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiBadgeTest {

    MuiBadge testSubject;
    WebElement element = mock(WebElement.class);
    WebElement badgeElement = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(element.findElement(By.className("MuiBadge-badge"))).thenReturn(badgeElement);
        when(badgeElement.getDomAttribute("class")).thenReturn(
                "MuiBadge-badge MuiBadge-anchorOriginTopRightRectangle MuiBadge-colorSecondary");
        testSubject = new MuiBadge(element, driver, config);
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5}, testSubject.versions().toArray());
    }

    @Test
    void getBadge() {
        assertEquals(badgeElement, testSubject.getBadge().getWrappedElement());
    }

    @Test
    void getBadgeNumberZero() {
        when(badgeElement.getText()).thenReturn("0");
        assertEquals(0, testSubject.getBadgeNumber());
    }

    @Test
    void getBadgeNumberEmpty() {
        when(badgeElement.getText()).thenReturn("");
        assertEquals(0, testSubject.getBadgeNumber());
    }

    @Test
    void getBadgeNumber() {
        when(badgeElement.getText()).thenReturn("35");
        assertEquals(35, testSubject.getBadgeNumber());
    }

    @Test
    void getBadgeNumberExceedMax() {
        when(badgeElement.getText()).thenReturn("99+");
        assertEquals(99, testSubject.getBadgeNumber());

        when(badgeElement.getText()).thenReturn("999+");
        assertEquals(999, testSubject.getBadgeNumber());
    }

    @Test
    void isDotDisplayed() {
        when(badgeElement.getDomAttribute("class")).thenReturn(
                "MuiBadge-badge MuiBadge-anchorOriginTopRightRectangle MuiBadge-colorSecondary MuiBadge-dot");
        assertTrue(testSubject.isDotDisplayed());
    }

    @Test
    void isDotDisplayedFalse() {
        when(badgeElement.getDomAttribute("class")).thenReturn(
                "MuiBadge-badge MuiBadge-anchorOriginTopRightRectangle MuiBadge-colorSecondary");
        assertFalse(testSubject.isDotDisplayed());
    }

    @Test
    void getComponentName() {
        assertEquals("Badge", testSubject.getComponentName());
    }

    @Test
    void isBadgeDisplayed() {
        when(badgeElement.getDomAttribute("class")).thenReturn(
                "MuiBadge-badge MuiBadge-anchorOriginTopRightRectangle MuiBadge-colorSecondary");
        assertTrue(testSubject.isBadgeDisplayed());
    }

    @Test
    void isBadgeDisplayedInvisible() {
        when(badgeElement.getDomAttribute("class")).thenReturn(
                "MuiBadge-badge MuiBadge-anchorOriginTopRightRectangle MuiBadge-colorSecondary MuiBadge-invisible");
        assertFalse(testSubject.isBadgeDisplayed());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiBadge{element=element-toString}", testSubject.toString());
    }
}
