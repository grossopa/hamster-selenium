package com.github.grossopa.playwright.component.mui.v4.navigation;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiMenuTest {
    MuiMenu testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiMenu(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Menu", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindMenuItems(Locator... itemLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(itemLocators));
    }

    @Test void getItemCount() {
        mockFindMenuItems(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getItemCount());
    }

    @Test void isOpenTrue() {
        when(locator.isVisible()).thenReturn(true);
        assertTrue(testSubject.isOpen());
    }

    @Test void isOpenFalse() {
        when(locator.isVisible()).thenReturn(false);
        assertFalse(testSubject.isOpen());
    }

    @Test void close() {
        testSubject.close();
        verify(locator).press("Escape");
    }

    @Test void clickItemByIndex() {
        Locator itemLocator = mock(Locator.class);
        mockFindMenuItems(itemLocator);
        testSubject.clickItem(0);
        verify(itemLocator).click();
    }

    @Test void clickItemByIndexOutOfBounds() {
        mockFindMenuItems();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.clickItem(0));
    }

    @Test void clickItemByText() {
        Locator itemLocator = mock(Locator.class);
        when(itemLocator.innerText()).thenReturn("Edit");
        mockFindMenuItems(itemLocator);
        testSubject.clickItem("Edit");
        verify(itemLocator).click();
    }

    @Test void clickItemByTextNotFound() {
        mockFindMenuItems();
        assertThrows(IllegalArgumentException.class, () -> testSubject.clickItem("Edit"));
    }
}
