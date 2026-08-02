package com.github.grossopa.playwright.component.mui.v4.datadisplay;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiDividerTest {
    MuiDivider testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiDivider(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Divider", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiDivider-root MuiDivider-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test
    void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiDivider-root");
        assertFalse(testSubject.isVertical());
    }

    @Test
    void isLightVariantTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiDivider-root MuiDivider-light");
        assertTrue(testSubject.isLightVariant());
    }

    @Test
    void isLightVariantFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiDivider-root");
        assertFalse(testSubject.isLightVariant());
    }

    @Test
    void hasMiddleInsetTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiDivider-root MuiDivider-middle");
        assertTrue(testSubject.hasMiddleInset());
    }

    @Test
    void hasMiddleInsetFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiDivider-root");
        assertFalse(testSubject.hasMiddleInset());
    }
}
