package com.github.grossopa.playwright.component.mui.v4.core;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiBoxTest {
    MuiBox testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiBox(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Box", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Box content");
        assertEquals("Box content", testSubject.getText());
    }

    @Test void getDisplay() {
        when(locator.evaluate("el => getComputedStyle(el).display")).thenReturn("flex");
        assertEquals("flex", testSubject.getDisplay());
    }

    @Test void isFlexTrue() {
        when(locator.evaluate("el => getComputedStyle(el).display")).thenReturn("flex");
        assertTrue(testSubject.isFlex());
    }

    @Test void isFlexTrueForInlineFlex() {
        when(locator.evaluate("el => getComputedStyle(el).display")).thenReturn("inline-flex");
        assertTrue(testSubject.isFlex());
    }

    @Test void isFlexFalse() {
        when(locator.evaluate("el => getComputedStyle(el).display")).thenReturn("block");
        assertFalse(testSubject.isFlex());
    }
}
