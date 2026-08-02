package com.github.grossopa.playwright.component.mui.v4.datadisplay;

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

class MuiListTest {
    MuiList testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiList(locator, driver, config); }

    @Test void getComponentName() { assertEquals("List", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindListItems(Locator... itemLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(itemLocators));
    }

    // getListItems
    @Test void getListItemsEmpty() {
        mockFindListItems();
        assertTrue(testSubject.getListItems().isEmpty());
    }

    @Test void getListItemsTwo() {
        Locator item1 = mock(Locator.class);
        Locator item2 = mock(Locator.class);
        mockFindListItems(item1, item2);
        assertEquals(2, testSubject.getListItems().size());
        assertInstanceOf(MuiListItem.class, testSubject.getListItems().get(0));
    }

    // getItemCount
    @Test void getItemCountZero() {
        mockFindListItems();
        assertEquals(0, testSubject.getItemCount());
    }

    @Test void getItemCountThree() {
        mockFindListItems(mock(Locator.class), mock(Locator.class), mock(Locator.class));
        assertEquals(3, testSubject.getItemCount());
    }
}
