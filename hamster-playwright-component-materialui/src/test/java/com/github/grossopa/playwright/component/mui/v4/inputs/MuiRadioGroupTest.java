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

class MuiRadioGroupTest {
    MuiRadioGroup testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiRadioGroup(locator, driver, config); }

    @Test void getComponentName() { assertEquals("RadioGroup", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindRadios(Locator... radioLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator("[type=\"radio\"]")).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(radioLocators));
    }

    // getRadios
    @Test void getRadiosEmpty() {
        mockFindRadios();
        assertTrue(testSubject.getRadios().isEmpty());
    }

    @Test void getRadiosTwo() {
        mockFindRadios(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getRadios().size());
    }

    // getRadioCount
    @Test void getRadioCount() {
        mockFindRadios(mock(Locator.class), mock(Locator.class), mock(Locator.class));
        assertEquals(3, testSubject.getRadioCount());
    }

    // getSelectedValue - isChecked checks class contains "Mui-checked"
    @Test void getSelectedValue() {
        Locator radio1 = mock(Locator.class);
        Locator radio2 = mock(Locator.class);
        when(radio1.getAttribute("class")).thenReturn("MuiRadio-root");
        when(radio2.getAttribute("class")).thenReturn("MuiRadio-root Mui-checked");
        when(radio2.getAttribute("value")).thenReturn("option2");
        mockFindRadios(radio1, radio2);
        assertEquals("option2", testSubject.getSelectedValue());
    }

    @Test void getSelectedValueNull() {
        Locator radio1 = mock(Locator.class);
        when(radio1.getAttribute("class")).thenReturn("MuiRadio-root");
        mockFindRadios(radio1);
        assertNull(testSubject.getSelectedValue());
    }

    // selectByValue
    @Test void selectByValue() {
        Locator radio1 = mock(Locator.class);
        when(radio1.getAttribute("class")).thenReturn("MuiRadio-root");
        when(radio1.getAttribute("value")).thenReturn("opt1");
        mockFindRadios(radio1);
        testSubject.selectByValue("opt1");
        verify(radio1).click();
    }

    @Test void selectByValueNotFound() {
        mockFindRadios();
        assertThrows(IllegalArgumentException.class, () -> testSubject.selectByValue("opt1"));
    }

    // selectByIndex
    @Test void selectByIndex() {
        Locator radio1 = mock(Locator.class);
        Locator radio2 = mock(Locator.class);
        when(radio1.getAttribute("class")).thenReturn("MuiRadio-root");
        when(radio2.getAttribute("class")).thenReturn("MuiRadio-root");
        mockFindRadios(radio1, radio2);
        testSubject.selectByIndex(1);
        verify(radio2).click();
    }

    @Test void selectByIndexOutOfBounds() {
        mockFindRadios(mock(Locator.class));
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.selectByIndex(5));
    }

    @Test void selectByIndexNegative() {
        mockFindRadios(mock(Locator.class));
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.selectByIndex(-1));
    }

    // hasSelection
    @Test void hasSelectionTrue() {
        Locator radio1 = mock(Locator.class);
        when(radio1.getAttribute("class")).thenReturn("MuiRadio-root Mui-checked");
        when(radio1.getAttribute("value")).thenReturn("opt1");
        mockFindRadios(radio1);
        assertTrue(testSubject.hasSelection());
    }

    @Test void hasSelectionFalse() {
        Locator radio1 = mock(Locator.class);
        when(radio1.getAttribute("class")).thenReturn("MuiRadio-root");
        mockFindRadios(radio1);
        assertFalse(testSubject.hasSelection());
    }
}
