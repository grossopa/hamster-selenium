package com.github.grossopa.playwright.component.mui.v4.navigation;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiAccordionTest {
    MuiAccordion testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAccordion(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Accordion", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void isEnabledTrueWhenNoClassName() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertTrue(testSubject.isEnabled());
    }

    @Test void isEnabledTrueWhenNoDisabledClass() {
        when(locator.getAttribute("class")).thenReturn("MuiAccordion-root");
        assertTrue(testSubject.isEnabled());
    }

    @Test void isEnabledFalseWhenDisabled() {
        when(locator.getAttribute("class")).thenReturn("Mui-disabled");
        assertFalse(testSubject.isEnabled());
    }

    @Test void getAccordionSummaryReturnsNullWhenEmpty() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        assertNull(testSubject.getAccordionSummary());
    }

    @Test void getAccordionDetailsReturnsNullWhenEmpty() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        assertNull(testSubject.getAccordionDetails());
    }

    @Test void getAccordionActionsReturnsNullWhenEmpty() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        assertNull(testSubject.getAccordionActions());
    }

    @Test void isExpandedReturnsFalseWhenSummaryNull() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        assertFalse(testSubject.isExpanded());
    }

    @Test void expandClicksLocatorWhenNoSummary() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        testSubject.expand();
        verify(locator).click();
    }

    @Test void collapseDoesNothingWhenNotExpanded() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        testSubject.collapse();
    }
}
