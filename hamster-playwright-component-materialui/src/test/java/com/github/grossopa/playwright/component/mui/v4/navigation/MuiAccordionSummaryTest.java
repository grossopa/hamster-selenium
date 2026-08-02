package com.github.grossopa.playwright.component.mui.v4.navigation;

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

class MuiAccordionSummaryTest {
    MuiAccordionSummary testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAccordionSummary(locator, driver, config); }

    @Test void getComponentName() { assertEquals("AccordionSummary", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void isExpandedTrue() {
        when(locator.getAttribute("aria-expanded")).thenReturn("true");
        assertTrue(testSubject.isExpanded());
    }

    @Test void isExpandedFalse() {
        when(locator.getAttribute("aria-expanded")).thenReturn("false");
        assertFalse(testSubject.isExpanded());
    }

    @Test void isExpandedFalseWhenNull() {
        when(locator.getAttribute("aria-expanded")).thenReturn(null);
        assertFalse(testSubject.isExpanded());
    }

    @Test void click() {
        testSubject.click();
        verify(locator).click();
    }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Section 1");
        assertEquals("Section 1", testSubject.getText());
    }

    // getExpandIcon - findComponent returns non-null wrapper
    @Test void getExpandIcon() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        WebComponent icon = testSubject.getExpandIcon();
        assertNotNull(icon);
    }
}
