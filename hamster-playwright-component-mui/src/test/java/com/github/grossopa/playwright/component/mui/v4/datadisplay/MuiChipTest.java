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

class MuiChipTest {
    MuiChip testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiChip(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Chip", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void getLabel() {
        when(locator.innerText()).thenReturn("Chip Label");
        assertEquals("Chip Label", testSubject.getLabel());
    }

    @Test
    void hasDeleteButtonTrue() {
        Locator deleteLocator = mock(Locator.class);
        when(locator.locator(contains("Chip-deleteIcon"))).thenReturn(deleteLocator);
        when(deleteLocator.first()).thenReturn(deleteLocator);
        assertTrue(testSubject.hasDeleteButton());
    }

    @Test
    void isClickableTrueWithRoleButton() {
        when(locator.getAttribute("role")).thenReturn("button");
        assertTrue(testSubject.isClickable());
    }

    @Test
    void isClickableTrueWithTabIndex() {
        when(locator.getAttribute("role")).thenReturn(null);
        when(locator.getAttribute("tabindex")).thenReturn("0");
        assertTrue(testSubject.isClickable());
    }

    @Test
    void isClickableFalse() {
        when(locator.getAttribute("role")).thenReturn(null);
        when(locator.getAttribute("tabindex")).thenReturn(null);
        assertFalse(testSubject.isClickable());
    }
}
