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
package com.github.grossopa.playwright.component.mui.v4.feedback;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiSkeletonTest {
    MuiSkeleton testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiSkeleton(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Skeleton", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    // getVariant
    @Test void getVariantText() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root MuiSkeleton-text");
        assertEquals("text", testSubject.getVariant());
    }

    @Test void getVariantRectangular() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root MuiSkeleton-rectangular");
        assertEquals("rectangular", testSubject.getVariant());
    }

    @Test void getVariantCircular() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root MuiSkeleton-circular");
        assertEquals("circular", testSubject.getVariant());
    }

    @Test void getVariantRounded() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root MuiSkeleton-rounded");
        assertEquals("rounded", testSubject.getVariant());
    }

    @Test void getVariantDefault() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root");
        assertEquals("text", testSubject.getVariant());
    }

    // isAnimated - true when no pulse/wave class
    @Test void isAnimatedTrueWhenNoAnimation() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root");
        assertTrue(testSubject.isAnimated());
    }

    @Test void isAnimatedFalseWhenPulse() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root MuiSkeleton-pulse");
        assertFalse(testSubject.isAnimated());
    }

    @Test void isAnimatedFalseWhenWave() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root MuiSkeleton-wave");
        assertFalse(testSubject.isAnimated());
    }

    // getAnimation
    @Test void getAnimationPulse() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root MuiSkeleton-pulse");
        assertEquals("pulse", testSubject.getAnimation());
    }

    @Test void getAnimationWave() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root MuiSkeleton-wave");
        assertEquals("wave", testSubject.getAnimation());
    }

    @Test void getAnimationNone() {
        when(locator.getAttribute("class")).thenReturn("MuiSkeleton-root");
        assertEquals("none", testSubject.getAnimation());
    }

    // isLoading - delegates to isVisible()
    @Test void isLoadingTrue() {
        when(locator.isVisible()).thenReturn(true);
        assertTrue(testSubject.isLoading());
    }

    @Test void isLoadingFalse() {
        when(locator.isVisible()).thenReturn(false);
        assertFalse(testSubject.isLoading());
    }
}
