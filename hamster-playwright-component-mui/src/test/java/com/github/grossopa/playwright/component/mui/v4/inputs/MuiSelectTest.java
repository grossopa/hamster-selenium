package com.github.grossopa.playwright.component.mui.v4.inputs;

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

class MuiSelectTest {
    MuiSelect testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiSelect(locator, driver, config);
    }

    @Test void getComponentName() { assertEquals("Select", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getSelectedValue() {
        when(locator.innerText()).thenReturn("Option 1");
        assertEquals("Option 1", testSubject.getSelectedValue());
    }

    @Test void selectByValue() {
        testSubject.selectByValue("val1");
        verify(locator).selectOption("val1");
    }

    // getOptions: first tries findComponents("[role=\"option\"]") via locator chain
    private void mockFindOptions(Locator... optionLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(optionLocators));
    }

    @Test void getOptionsFromLocator() {
        Locator opt1 = mock(Locator.class);
        Locator opt2 = mock(Locator.class);
        mockFindOptions(opt1, opt2);
        List<WebComponent> options = testSubject.getOptions();
        assertEquals(2, options.size());
    }

    @Test void getOptionsFallbackToDriver() {
        // locator chain returns empty
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of());

        // fallback to driver
        WebComponent driverOpt = mock(WebComponent.class);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(driverOpt));

        List<WebComponent> options = testSubject.getOptions();
        assertEquals(1, options.size());
    }

    // selectByVisibleText
    @Test void selectByVisibleText() {
        Locator opt1 = mock(Locator.class);
        Locator opt2 = mock(Locator.class);
        when(opt1.innerText()).thenReturn("Apple");
        when(opt2.innerText()).thenReturn("Banana");
        mockFindOptions(opt1, opt2);

        testSubject.selectByVisibleText("Banana");
        verify(opt2).click();
    }

    @Test void selectByVisibleTextNotFound() {
        Locator opt1 = mock(Locator.class);
        when(opt1.innerText()).thenReturn("Apple");
        mockFindOptions(opt1);

        assertThrows(IllegalArgumentException.class, () -> testSubject.selectByVisibleText("Cherry"));
    }

    // selectByIndex
    @Test void selectByIndex() {
        Locator opt1 = mock(Locator.class);
        Locator opt2 = mock(Locator.class);
        mockFindOptions(opt1, opt2);

        testSubject.selectByIndex(1);
        verify(opt2).click();
    }

    @Test void selectByIndexOutOfBounds() {
        mockFindOptions();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.selectByIndex(0));
    }

    @Test void selectByIndexNegative() {
        Locator opt1 = mock(Locator.class);
        mockFindOptions(opt1);
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.selectByIndex(-1));
    }

    // isOpen
    @Test void isOpenTrue() {
        WebComponent opt = mock(WebComponent.class);
        when(opt.isVisible()).thenReturn(true);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt));
        assertTrue(testSubject.isOpen());
    }

    @Test void isOpenFalseEmpty() {
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of());
        assertFalse(testSubject.isOpen());
    }

    @Test void isOpenFalseNotVisible() {
        WebComponent opt = mock(WebComponent.class);
        when(opt.isVisible()).thenReturn(false);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt));
        assertFalse(testSubject.isOpen());
    }

    // open
    @Test void openWhenClosed() {
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of());
        testSubject.open();
        verify(locator).click();
    }

    @Test void openWhenAlreadyOpen() {
        WebComponent opt = mock(WebComponent.class);
        when(opt.isVisible()).thenReturn(true);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt));
        testSubject.open();
        verify(locator, never()).click();
    }

    // close
    @Test void closeWhenOpen() {
        WebComponent opt = mock(WebComponent.class);
        when(opt.isVisible()).thenReturn(true);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt));
        testSubject.close();
        verify(locator).press("Escape");
    }

    @Test void closeWhenAlreadyClosed() {
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of());
        testSubject.close();
        verify(locator, never()).press("Escape");
    }
}
