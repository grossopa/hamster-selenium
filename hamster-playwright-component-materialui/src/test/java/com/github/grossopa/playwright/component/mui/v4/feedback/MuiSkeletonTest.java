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
