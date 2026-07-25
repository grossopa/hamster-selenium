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

    // getAccordionSummary - empty returns null
    @Test void getAccordionSummaryReturnsNullWhenEmpty() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        assertNull(testSubject.getAccordionSummary());
    }

    // getAccordionSummary - non-empty returns MuiAccordionSummary
    @Test void getAccordionSummaryReturnsInstance() {
        Locator summaryLocator = mock(Locator.class);
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(summaryLocator));
        assertNotNull(testSubject.getAccordionSummary());
        assertInstanceOf(MuiAccordionSummary.class, testSubject.getAccordionSummary());
    }

    // getAccordionDetails - empty returns null
    @Test void getAccordionDetailsReturnsNullWhenEmpty() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        assertNull(testSubject.getAccordionDetails());
    }

    // getAccordionDetails - non-empty returns MuiAccordionDetails
    @Test void getAccordionDetailsReturnsInstance() {
        Locator detailsLocator = mock(Locator.class);
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(detailsLocator));
        assertNotNull(testSubject.getAccordionDetails());
        assertInstanceOf(MuiAccordionDetails.class, testSubject.getAccordionDetails());
    }

    // getAccordionActions - empty returns null
    @Test void getAccordionActionsReturnsNullWhenEmpty() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        assertNull(testSubject.getAccordionActions());
    }

    // getAccordionActions - non-empty returns MuiAccordionActions
    @Test void getAccordionActionsReturnsInstance() {
        Locator actionsLocator = mock(Locator.class);
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(actionsLocator));
        assertNotNull(testSubject.getAccordionActions());
        assertInstanceOf(MuiAccordionActions.class, testSubject.getAccordionActions());
    }

    // isExpanded - summary is non-null, check summary.isExpanded()
    @Test void isExpandedReturnsFalseWhenSummaryNull() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        assertFalse(testSubject.isExpanded());
    }

    @Test void isExpandedReturnsTrueWhenSummaryExpanded() {
        Locator summaryLocator = mock(Locator.class);
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(summaryLocator));
        // summary.isExpanded() checks getAttribute("aria-expanded") == "true"
        when(summaryLocator.getAttribute("aria-expanded")).thenReturn("true");
        assertTrue(testSubject.isExpanded());
    }

    @Test void isExpandedReturnsFalseWhenSummaryNotExpanded() {
        Locator summaryLocator = mock(Locator.class);
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(summaryLocator));
        when(summaryLocator.getAttribute("aria-expanded")).thenReturn("false");
        assertFalse(testSubject.isExpanded());
    }

    // expand - when summary exists, clicks summary
    @Test void expandClicksSummaryWhenPresent() {
        Locator summaryLocator = mock(Locator.class);
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(summaryLocator));
        testSubject.expand();
        verify(summaryLocator).click();
    }

    // expand - when no summary, clicks accordion itself
    @Test void expandClicksLocatorWhenNoSummary() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        testSubject.expand();
        verify(locator).click();
    }

    // collapse - when expanded, calls expand() to toggle
    @Test void collapseCallsExpandWhenExpanded() {
        Locator summaryLocator = mock(Locator.class);
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(summaryLocator));
        when(summaryLocator.getAttribute("aria-expanded")).thenReturn("true");
        testSubject.collapse();
        verify(summaryLocator).click();
    }

    // collapse - when not expanded, does nothing
    @Test void collapseDoesNothingWhenNotExpanded() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(Collections.emptyList());
        testSubject.collapse();
    }
}
