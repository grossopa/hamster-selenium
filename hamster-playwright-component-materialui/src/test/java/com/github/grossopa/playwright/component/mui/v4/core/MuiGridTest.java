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

class MuiGridTest {
    MuiGrid testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiGrid(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Grid", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void isContainerTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiGrid-container");
        assertTrue(testSubject.isContainer());
    }

    @Test void isContainerFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiGrid-item");
        assertFalse(testSubject.isContainer());
    }

    @Test void isItemTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiGrid-item");
        assertTrue(testSubject.isItem());
    }

    @Test void isItemFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiGrid-container");
        assertFalse(testSubject.isItem());
    }

    @Test void gridItemSpacingValue() {
        assertEquals(0, testSubject.gridItemSpacingValue(0));
        assertEquals(4, testSubject.gridItemSpacingValue(1));
        assertEquals(8, testSubject.gridItemSpacingValue(2));
    }

    @Test void getSpacing() {
        when(locator.getAttribute("class")).thenReturn("spacing-2");
        assertEquals(2, testSubject.getSpacing());
    }

    @Test void getSpacingDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals(0, testSubject.getSpacing());
    }

    @Test void isWrapTrue() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertTrue(testSubject.isWrap());
    }

    @Test void isWrapFalse() {
        when(locator.getAttribute("class")).thenReturn("nowrap");
        assertFalse(testSubject.isWrap());
    }

    @Test void getDirectionRow() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("row", testSubject.getDirection());
    }

    @Test void getDirectionColumn() {
        when(locator.getAttribute("class")).thenReturn("direction-column");
        assertEquals("column", testSubject.getDirection());
    }

    @Test void getDirectionRowReverse() {
        when(locator.getAttribute("class")).thenReturn("direction-row-reverse");
        assertEquals("row-reverse", testSubject.getDirection());
    }

    @Test void getDirectionColumnReverse() {
        when(locator.getAttribute("class")).thenReturn("direction-column-reverse");
        assertEquals("column-reverse", testSubject.getDirection());
    }

    @Test void getJustifyContentDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("flex-start", testSubject.getJustifyContent());
    }

    @Test void getJustifyContentCenter() {
        when(locator.getAttribute("class")).thenReturn("justify-content-center");
        assertEquals("center", testSubject.getJustifyContent());
    }

    @Test void getJustifyContentFlexEnd() {
        when(locator.getAttribute("class")).thenReturn("justify-content-flex-end");
        assertEquals("flex-end", testSubject.getJustifyContent());
    }

    @Test void getJustifyContentSpaceBetween() {
        when(locator.getAttribute("class")).thenReturn("justify-content-space-between");
        assertEquals("space-between", testSubject.getJustifyContent());
    }

    @Test void getJustifyContentSpaceAround() {
        when(locator.getAttribute("class")).thenReturn("justify-content-space-around");
        assertEquals("space-around", testSubject.getJustifyContent());
    }

    @Test void getAlignItemsDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("flex-start", testSubject.getAlignItems());
    }

    @Test void getAlignItemsCenter() {
        when(locator.getAttribute("class")).thenReturn("align-items-center");
        assertEquals("center", testSubject.getAlignItems());
    }

    @Test void getAlignItemsFlexEnd() {
        when(locator.getAttribute("class")).thenReturn("align-items-flex-end");
        assertEquals("flex-end", testSubject.getAlignItems());
    }

    @Test void getAlignItemsStretch() {
        when(locator.getAttribute("class")).thenReturn("align-items-stretch");
        assertEquals("stretch", testSubject.getAlignItems());
    }

    @Test void getAlignItemsBaseline() {
        when(locator.getAttribute("class")).thenReturn("align-items-baseline");
        assertEquals("baseline", testSubject.getAlignItems());
    }

    @Test void getSpacingNullClass() {
        when(locator.getAttribute("class")).thenReturn("some-other-class");
        assertEquals(0, testSubject.getSpacing());
    }

    @Test void isContainerNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isContainer());
    }

    @Test void isItemNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isItem());
    }
}
