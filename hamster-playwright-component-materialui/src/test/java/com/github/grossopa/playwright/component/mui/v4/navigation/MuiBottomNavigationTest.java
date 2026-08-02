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

class MuiBottomNavigationTest {
    MuiBottomNavigation testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiBottomNavigation(locator, driver, config); }

    @Test void getComponentName() { assertEquals("BottomNavigation", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindActions(Locator... actionLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(actionLocators));
    }

    @Test void getActionCount() {
        mockFindActions(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getActionCount());
    }

    @Test void clickActionByIndex() {
        Locator actionLocator = mock(Locator.class);
        mockFindActions(actionLocator);
        testSubject.clickAction(0);
        verify(actionLocator).click();
    }

    @Test void clickActionByIndexOutOfBounds() {
        mockFindActions();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.clickAction(0));
    }

    @Test void clickActionByLabel() {
        Locator actionLocator = mock(Locator.class);
        when(actionLocator.innerText()).thenReturn("Home");
        mockFindActions(actionLocator);
        testSubject.clickAction("Home");
        verify(actionLocator).click();
    }

    @Test void clickActionByLabelNotFound() {
        mockFindActions();
        assertThrows(IllegalArgumentException.class, () -> testSubject.clickAction("Home"));
    }

    @Test void getSelectedIndex() {
        Locator action1 = mock(Locator.class);
        Locator action2 = mock(Locator.class);
        when(action1.getAttribute("aria-selected")).thenReturn("false");
        when(action2.getAttribute("aria-selected")).thenReturn("true");
        mockFindActions(action1, action2);
        assertEquals(1, testSubject.getSelectedIndex());
    }

    @Test void getSelectedIndexReturnsNegativeOneWhenNoneSelected() {
        Locator action1 = mock(Locator.class);
        when(action1.getAttribute("aria-selected")).thenReturn("false");
        mockFindActions(action1);
        assertEquals(-1, testSubject.getSelectedIndex());
    }
}
