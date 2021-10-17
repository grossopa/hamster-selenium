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

package com.github.grossopa.hamster.selenium.component.mat.config;

import com.github.grossopa.hamster.selenium.core.util.SimpleEqualsTester;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MatConfig}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatConfigTest {

    MatConfig testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MatConfig();
    }


    @Test
    void getTagPrefix() {
        assertEquals("mat-", testSubject.getTagPrefix());
    }

    @Test
    void setTagPrefix() {
        testSubject.setTagPrefix("abc-");
        assertEquals("abc-", testSubject.getTagPrefix());
    }

    @Test
    void getCssPrefix() {
        assertEquals("mat-", testSubject.getCssPrefix());
    }

    @Test
    void setCssPrefix() {
        testSubject.setCssPrefix("abc-");
        assertEquals("abc-", testSubject.getCssPrefix());
    }

    @Test
    void getOverlayAbsolutePath() {
        assertEquals("/html/body", testSubject.getOverlayAbsolutePath());
    }

    @Test
    void setOverlayAbsolutePath() {
        testSubject.setOverlayAbsolutePath("/html/body/div/div/div");
        assertEquals("/html/body/div/div/div", testSubject.getOverlayAbsolutePath());
    }

    @Test
    void getCdkPrefix() {
        assertEquals("cdk-", testSubject.getCdkPrefix());
    }

    @Test
    void setCdkPrefix() {
        testSubject.setCdkPrefix("abc-");
        assertEquals("abc-", testSubject.getCdkPrefix());
    }

    @Test
    void testEquals() {
        SimpleEqualsTester tester = new SimpleEqualsTester();

        tester.addEqualityGroup(MatConfig.create("tag-", "css-", "cdk-", "/html/body"),
                MatConfig.create("tag-", "css-", "cdk-", "/html/body"));

        tester.addEqualityGroup(MatConfig.create("tag-1", "css-", "cdk-", "/html/body"));
        tester.addEqualityGroup(MatConfig.create("tag-", "css-1", "cdk-", "/html/body"));
        tester.addEqualityGroup(MatConfig.create("tag-", "css-", "cdk-1", "/html/body"));
        tester.addEqualityGroup(MatConfig.create("tag-", "css-", "cdk-", "/html/body1"));

        tester.testEquals();
    }

    @Test
    void testToString() {
        assertEquals("MatConfig{tagPrefix='mat-', cssPrefix='mat-', "
                + "cdkPrefix='cdk-', overlayAbsolutePath='/html/body'}", testSubject.toString());
    }

    @Test
    void getIsCheckedCss() {
        assertEquals("mat-checked", testSubject.getIsCheckedCss());
    }

    @Test
    void getIsSelectedCss() {
        assertEquals("mat-selected", testSubject.getIsSelectedCss());
    }

    @Test
    void getIsDisabledCss() {
        assertEquals("mat-disabled", testSubject.getIsDisabledCss());
    }

    @Test
    void isDisabled1() {
        WebComponent component = mock(WebComponent.class);
        WebElement element = mock(WebElement.class);
        when(component.getWrappedElement()).thenReturn(element);
        when(element.isEnabled()).thenReturn(false);
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabled2() {
        WebComponent component = mock(WebComponent.class);
        WebElement element = mock(WebElement.class);
        when(component.getWrappedElement()).thenReturn(element);
        when(element.isEnabled()).thenReturn(true);
        when(component.getAttribute("class")).thenReturn("mat-disabled");
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabled3() {
        WebComponent component = mock(WebComponent.class);
        WebElement element = mock(WebElement.class);
        when(component.getWrappedElement()).thenReturn(element);
        when(element.isEnabled()).thenReturn(true);
        when(component.getAttribute("class")).thenReturn("");
        when(component.getAttribute("aria-disabled")).thenReturn("true");
        assertTrue(testSubject.isDisabled(component));
    }

    @Test
    void isDisabled4() {
        WebComponent component = mock(WebComponent.class);
        WebElement element = mock(WebElement.class);
        when(component.getWrappedElement()).thenReturn(element);
        when(element.isEnabled()).thenReturn(true);
        when(component.getAttribute("class")).thenReturn("");
        assertFalse(testSubject.isDisabled(component));
    }
}
