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

package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiRating}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiRatingTest {

    MuiRating testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiRating(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Rating", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertTrue(testSubject.versions().contains(V4));
        assertTrue(testSubject.versions().contains(V5));
        assertTrue(testSubject.versions().contains(V6));
    }

    @Test
    void getValue() {
        WebComponent star1 = mock(WebComponent.class);
        WebComponent star2 = mock(WebComponent.class);
        WebComponent star3 = mock(WebComponent.class);
        
        when(config.isSelected(star1)).thenReturn(false);
        when(config.isChecked(star1)).thenReturn(false);
        when(config.isSelected(star2)).thenReturn(false);
        when(config.isChecked(star2)).thenReturn(false);
        when(config.isSelected(star3)).thenReturn(true);
        when(config.isChecked(star3)).thenReturn(true);
        
        List<WebComponent> stars = asList(star1, star2, star3);
        doReturn(stars).when(testSubject).getStars();
        
        assertEquals(3.0, testSubject.getValue());
    }

    @Test
    void setValue() {
        WebComponent star1 = mock(WebComponent.class);
        WebComponent star2 = mock(WebComponent.class);
        WebComponent star3 = mock(WebComponent.class);
        
        List<WebComponent> stars = asList(star1, star2, star3);
        doReturn(stars).when(testSubject).getStars();
        
        testSubject.setValue(2);
        
        verify(star2).click();
    }

    @Test
    void setValue_invalid() {
        doReturn(asList(mock(WebComponent.class))).when(testSubject).getStars();
        
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(5));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(-1));
    }

    @Test
    void isReadOnly() {
        when(element.getAttribute("aria-readonly")).thenReturn("true");
        assertTrue(testSubject.isReadOnly());
        
        when(element.getAttribute("aria-readonly")).thenReturn("false");
        assertFalse(testSubject.isReadOnly());
        
        when(element.getAttribute("aria-readonly")).thenReturn(null);
        assertFalse(testSubject.isReadOnly());
    }
}