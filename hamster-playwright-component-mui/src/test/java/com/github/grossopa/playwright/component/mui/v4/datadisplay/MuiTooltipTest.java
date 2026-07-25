package com.github.grossopa.playwright.component.mui.v4.datadisplay;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiTooltipTest {
    MuiTooltip testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiTooltip(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Tooltip", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    // getTooltipText - driver.findComponent("[role=\"tooltip\"]") returns non-null from driver
    @Test void getTooltipText() {
        WebComponent tooltipComponent = mock(WebComponent.class);
        when(driver.findComponent("[role=\"tooltip\"]")).thenReturn(tooltipComponent);
        when(tooltipComponent.innerText()).thenReturn("Tooltip text");
        assertEquals("Tooltip text", testSubject.getTooltipText());
    }

    @Test void getTooltipTextNull() {
        when(driver.findComponent("[role=\"tooltip\"]")).thenReturn(null);
        assertNull(testSubject.getTooltipText());
    }

    // show - hover
    @Test void show() {
        testSubject.show();
        verify(locator).hover();
    }

    // hide - blur
    @Test void hide() {
        testSubject.hide();
        verify(locator).blur();
    }

    // isVisible - driver.findComponent("[role=\"tooltip\"]:visible") returns non-null
    @Test void isVisibleTrue() {
        WebComponent tooltipComponent = mock(WebComponent.class);
        when(driver.findComponent("[role=\"tooltip\"]:visible")).thenReturn(tooltipComponent);
        assertTrue(testSubject.isVisible());
    }

    @Test void isVisibleFalse() {
        when(driver.findComponent("[role=\"tooltip\"]:visible")).thenReturn(null);
        assertFalse(testSubject.isVisible());
    }

    // getPlacement
    @Test void getPlacement() {
        when(locator.getAttribute("data-placement")).thenReturn("top");
        assertEquals("top", testSubject.getPlacement());
    }

    @Test void getPlacementNull() {
        when(locator.getAttribute("data-placement")).thenReturn(null);
        assertNull(testSubject.getPlacement());
    }
}
