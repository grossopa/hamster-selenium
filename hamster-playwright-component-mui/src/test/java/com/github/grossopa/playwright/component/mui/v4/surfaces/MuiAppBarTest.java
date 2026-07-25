package com.github.grossopa.playwright.component.mui.v4.surfaces;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiAppBarTest {
    MuiAppBar testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAppBar(locator, driver, config); }

    @Test void getComponentName() { assertEquals("AppBar", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getPositionFixed() {
        when(locator.getAttribute("class")).thenReturn("MuiAppBar-positionFixed");
        assertEquals("fixed", testSubject.getPosition());
    }

    @Test void getPositionAbsolute() {
        when(locator.getAttribute("class")).thenReturn("MuiAppBar-positionAbsolute");
        assertEquals("absolute", testSubject.getPosition());
    }

    @Test void getPositionSticky() {
        when(locator.getAttribute("class")).thenReturn("MuiAppBar-positionSticky");
        assertEquals("sticky", testSubject.getPosition());
    }

    @Test void getPositionDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("static", testSubject.getPosition());
    }

    @Test void getColorPrimary() {
        when(locator.getAttribute("class")).thenReturn("MuiAppBar-colorPrimary");
        assertEquals("primary", testSubject.getColor());
    }

    @Test void getColorSecondary() {
        when(locator.getAttribute("class")).thenReturn("MuiAppBar-colorSecondary");
        assertEquals("secondary", testSubject.getColor());
    }

    @Test void getColorDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("default", testSubject.getColor());
    }

    @Test void hasElevationTrue() {
        when(locator.getAttribute("class")).thenReturn("Muielevation4");
        assertTrue(testSubject.hasElevation());
    }

    @Test void hasElevationFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiAppBar-root");
        assertFalse(testSubject.hasElevation());
    }
}
