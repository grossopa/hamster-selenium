package com.github.grossopa.playwright.component.mui.v4.lab;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiAutocompleteTest {
    MuiAutocomplete testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAutocomplete(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Autocomplete", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    // getOptions() calls driver.findComponents() directly
    @Test void getOptionCount() {
        WebComponent opt1 = mock(WebComponent.class);
        WebComponent opt2 = mock(WebComponent.class);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt1, opt2));
        assertEquals(2, testSubject.getOptionCount());
    }

    @Test void isLoadingTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiAutocomplete-loading");
        assertTrue(testSubject.isLoading());
    }

    @Test void isLoadingFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiAutocomplete-root");
        assertFalse(testSubject.isLoading());
    }

    // isOpen() calls getOptions() which calls driver.findComponents() directly
    @Test void isOpenTrue() {
        WebComponent opt1 = mock(WebComponent.class);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt1));
        assertTrue(testSubject.isOpen());
    }

    @Test void isOpenFalse() {
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of());
        assertFalse(testSubject.isOpen());
    }

    @Test void close() {
        testSubject.close();
        verify(locator).press("Escape");
    }
}
