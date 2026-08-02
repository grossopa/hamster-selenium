package com.github.grossopa.playwright.component.mui.v4.surfaces;

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

class MuiToolbarTest {
    MuiToolbar testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiToolbar(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Toolbar", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindButtons(Locator... buttonLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(buttonLocators));
    }

    @Test void getButtonCount() {
        mockFindButtons(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getButtonCount());
    }

    @Test void clickButtonByIndex() {
        Locator btnLocator = mock(Locator.class);
        mockFindButtons(btnLocator);
        testSubject.clickButton(0);
        verify(btnLocator).click();
    }

    @Test void clickButtonByIndexOutOfBounds() {
        mockFindButtons();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.clickButton(0));
    }

    @Test void clickButtonByText() {
        Locator btnLocator = mock(Locator.class);
        when(btnLocator.innerText()).thenReturn("Save");
        mockFindButtons(btnLocator);
        testSubject.clickButton("Save");
        verify(btnLocator).click();
    }

    @Test void clickButtonByTextNotFound() {
        mockFindButtons();
        assertThrows(IllegalArgumentException.class, () -> testSubject.clickButton("Save"));
    }

    @Test void isDenseTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiToolbar-gutters");
        assertTrue(testSubject.isDense());
    }

    @Test void isDenseFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiToolbar-root");
        assertFalse(testSubject.isDense());
    }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Toolbar content");
        assertEquals("Toolbar content", testSubject.getText());
    }
}
