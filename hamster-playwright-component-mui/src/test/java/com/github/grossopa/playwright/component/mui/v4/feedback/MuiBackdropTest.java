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

class MuiBackdropTest {
    MuiBackdrop testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiBackdrop(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Backdrop", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    // isVisible - true when className does not contain "MuiBackdrop-invisible"
    @Test void isVisibleTrueWithClass() {
        when(locator.getAttribute("class")).thenReturn("MuiBackdrop-root");
        assertTrue(testSubject.isVisible());
    }

    @Test void isVisibleFalseWhenInvisible() {
        when(locator.getAttribute("class")).thenReturn("MuiBackdrop-root MuiBackdrop-invisible");
        assertFalse(testSubject.isVisible());
    }

    @Test void isVisibleFalseWhenNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isVisible());
    }

    // click
    @Test void click() {
        testSubject.click();
        verify(locator).click();
    }

    // isInvisible - true when className contains "MuiBackdrop-invisible"
    @Test void isInvisibleTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiBackdrop-root MuiBackdrop-invisible");
        assertTrue(testSubject.isInvisible());
    }

    @Test void isInvisibleFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiBackdrop-root");
        assertFalse(testSubject.isInvisible());
    }

    @Test void isInvisibleFalseWhenNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isInvisible());
    }
}
