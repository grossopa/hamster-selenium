package com.github.grossopa.playwright.component.mui.v4.lab;

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

class MuiAutocompleteTest {
    MuiAutocomplete testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAutocomplete(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Autocomplete", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    /**
     * Mocks findComponent chain: locator.locator(any).first() → firstLocator
     */
    private Locator mockFindComponent() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        return firstLocator;
    }

    // getInput → findComponent("input")
    @Test void getInput() {
        mockFindComponent();
        WebComponent result = testSubject.getInput();
        assertNotNull(result);
    }

    // typeInput → getInput().fill(text)
    @Test void typeInput() {
        Locator firstLocator = mockFindComponent();
        testSubject.typeInput("hello");
        verify(firstLocator).fill("hello");
    }

    // getOptions → driver.findComponents("[role=\"option\"]")
    @Test void getOptions() {
        WebComponent opt1 = mock(WebComponent.class);
        WebComponent opt2 = mock(WebComponent.class);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt1, opt2));
        assertEquals(2, testSubject.getOptions().size());
    }

    // getOptionCount
    @Test void getOptionCount() {
        WebComponent opt1 = mock(WebComponent.class);
        WebComponent opt2 = mock(WebComponent.class);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt1, opt2));
        assertEquals(2, testSubject.getOptionCount());
    }

    @Test void getOptionCountZero() {
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of());
        assertEquals(0, testSubject.getOptionCount());
    }

    // selectOption
    @Test void selectOption() {
        WebComponent opt1 = mock(WebComponent.class);
        WebComponent opt2 = mock(WebComponent.class);
        when(opt1.innerText()).thenReturn("Apple");
        when(opt1.textContent()).thenReturn("Apple text");
        when(opt2.innerText()).thenReturn("Banana");
        when(opt2.textContent()).thenReturn("Banana text");
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt1, opt2));

        List<String> result = testSubject.selectOption("Banana");
        verify(opt2).click();
        assertEquals(1, result.size());
        assertEquals("Banana text", result.get(0));
    }

    @Test void selectOptionNotFound() {
        WebComponent opt1 = mock(WebComponent.class);
        when(opt1.innerText()).thenReturn("Apple");
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt1));

        assertThrows(IllegalArgumentException.class, () -> testSubject.selectOption("Cherry"));
    }

    // getSelectedValues - single select with value
    @Test void getSelectedValuesSingleWithValue() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("value")).thenReturn("Selected Value");

        List<String> result = testSubject.getSelectedValues();
        assertEquals(1, result.size());
        assertEquals("Selected Value", result.get(0));
    }

    // getSelectedValues - single select with null value, falls through to chips
    @Test void getSelectedValuesNullValueFallsToChips() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("value")).thenReturn(null);
        when(childLocator.all()).thenReturn(List.of());

        List<String> result = testSubject.getSelectedValues();
        assertTrue(result.isEmpty());
    }

    // getSelectedValues - multiple select with chips
    @Test void getSelectedValuesMultipleChips() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("value")).thenReturn(null);

        Locator chip1 = mock(Locator.class);
        Locator chip2 = mock(Locator.class);
        when(chip1.innerText()).thenReturn("Tag1");
        when(chip2.innerText()).thenReturn("Tag2");
        when(childLocator.all()).thenReturn(List.of(chip1, chip2));

        List<String> result = testSubject.getSelectedValues();
        assertEquals(2, result.size());
        assertEquals("Tag1", result.get(0));
        assertEquals("Tag2", result.get(1));
    }

    // isMultiple - with aria-multiselectable
    @Test void isMultipleWithAria() {
        when(locator.getAttribute("aria-multiselectable")).thenReturn("true");
        assertTrue(testSubject.isMultiple());
    }

    // isMultiple - with chips
    @Test void isMultipleWithChips() {
        when(locator.getAttribute("aria-multiselectable")).thenReturn(null);
        Locator chipLocator = mock(Locator.class);
        Locator chipChildLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(chipChildLocator);
        when(chipChildLocator.all()).thenReturn(List.of(chipLocator));
        assertTrue(testSubject.isMultiple());
    }

    // isMultiple - false
    @Test void isMultipleFalse() {
        when(locator.getAttribute("aria-multiselectable")).thenReturn(null);
        Locator chipChildLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(chipChildLocator);
        when(chipChildLocator.all()).thenReturn(List.of());
        assertFalse(testSubject.isMultiple());
    }
    // isLoading
    @Test void isLoadingTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiAutocomplete-loading");
        assertTrue(testSubject.isLoading());
    }

    @Test void isLoadingFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiAutocomplete-root");
        assertFalse(testSubject.isLoading());
    }

    @Test void isLoadingNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isLoading());
    }

    // isReadOnly
    @Test void isReadOnlyTrue() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.getAttribute("readonly")).thenReturn("true");
        assertTrue(testSubject.isReadOnly());
    }

    @Test void isReadOnlyFalse() {
        Locator firstLocator = mockFindComponent();
        when(firstLocator.getAttribute("readonly")).thenReturn(null);
        assertFalse(testSubject.isReadOnly());
    }

    // clear
    @Test void clear() {
        Locator firstLocator = mockFindComponent();
        testSubject.clear();
        verify(firstLocator).fill("");
    }

    // open
    @Test void open() {
        Locator firstLocator = mockFindComponent();
        testSubject.open();
        verify(firstLocator).click();
    }

    // close
    @Test void close() {
        testSubject.close();
        verify(locator).press("Escape");
    }

    // isOpen
    @Test void isOpenTrue() {
        WebComponent opt1 = mock(WebComponent.class);
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of(opt1));
        assertTrue(testSubject.isOpen());
    }

    @Test void isOpenFalse() {
        when(driver.findComponents("[role=\"option\"]")).thenReturn(List.of());
        assertFalse(testSubject.isOpen());
    }
}
