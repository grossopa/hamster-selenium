package com.github.grossopa.playwright.component.mui.v4.inputs;

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

class MuiButtonGroupTest {
    MuiButtonGroup testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiButtonGroup(locator, driver, config); }

    @Test void getComponentName() { assertEquals("ButtonGroup", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindButtons(Locator... btnLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator("button")).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(btnLocators));
    }

    // getButtons
    @Test void getButtonsEmpty() {
        mockFindButtons();
        assertTrue(testSubject.getButtons().isEmpty());
    }

    @Test void getButtonsTwo() {
        mockFindButtons(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getButtons().size());
    }

    // getButtonCount
    @Test void getButtonCount() {
        mockFindButtons(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getButtonCount());
    }

    // clickButton(int)
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

    @Test void clickButtonByIndexNegative() {
        mockFindButtons(mock(Locator.class));
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.clickButton(-1));
    }

    // clickButton(String)
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

    // isVertical
    @Test void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiButtonGroup-root MuiButtonGroup-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiButtonGroup-root");
        assertFalse(testSubject.isVertical());
    }

    @Test void isVerticalNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isVertical());
    }
}
