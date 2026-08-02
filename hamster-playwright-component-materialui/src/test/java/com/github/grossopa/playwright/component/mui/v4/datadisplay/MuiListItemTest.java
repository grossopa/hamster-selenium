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

class MuiListItemTest {
    MuiListItem testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiListItem(locator, driver, config); }

    @Test void getComponentName() { assertEquals("ListItem", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Item Text");
        assertEquals("Item Text", testSubject.getText());
    }

    @Test void isSelectedTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiListItem-root MuiListItem-selected");
        assertTrue(testSubject.isSelected());
    }

    @Test void isSelectedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiListItem-root");
        assertFalse(testSubject.isSelected());
    }

    @Test void isSelectedFalseWhenNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isSelected());
    }

    // click
    @Test void click() {
        testSubject.click();
        verify(locator).click();
    }
}
