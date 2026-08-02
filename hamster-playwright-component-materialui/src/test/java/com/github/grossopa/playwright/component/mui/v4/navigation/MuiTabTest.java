package com.github.grossopa.playwright.component.mui.v4.navigation;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiTabTest {
    MuiTab testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiTab(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Tab", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getLabel() {
        when(locator.innerText()).thenReturn("Tab 1");
        assertEquals("Tab 1", testSubject.getLabel());
    }

    @Test void isSelectedTrue() {
        when(locator.getAttribute("aria-selected")).thenReturn("true");
        assertTrue(testSubject.isSelected());
    }

    @Test void isSelectedFalse() {
        when(locator.getAttribute("aria-selected")).thenReturn("false");
        assertFalse(testSubject.isSelected());
    }

    @Test void isDisabledTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiTab-disabled");
        assertTrue(testSubject.isDisabled());
    }

    @Test void isDisabledFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiTab-root");
        assertFalse(testSubject.isDisabled());
    }

    @Test void isEnabledTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiTab-root");
        assertTrue(testSubject.isEnabled());
    }

    @Test void isEnabledFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiTab-disabled");
        assertFalse(testSubject.isEnabled());
    }

    @Test void click() {
        when(locator.getAttribute("class")).thenReturn("MuiTab-root");
        testSubject.click();
        verify(locator).click();
    }

    @Test void clickDisabledThrowsException() {
        when(locator.getAttribute("class")).thenReturn("MuiTab-disabled");
        assertThrows(IllegalStateException.class, () -> testSubject.click());
    }
}
