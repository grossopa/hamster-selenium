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

class MuiBreadcrumbsTest {
    MuiBreadcrumbs testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiBreadcrumbs(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Breadcrumbs", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindItems(Locator... itemLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(itemLocators));
    }

    @Test void getItemCount() {
        mockFindItems(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getItemCount());
    }

    @Test void getItemTexts() {
        Locator item1 = mock(Locator.class);
        Locator item2 = mock(Locator.class);
        when(item1.innerText()).thenReturn("Home");
        when(item2.innerText()).thenReturn("Products");
        mockFindItems(item1, item2);
        assertEquals(List.of("Home", "Products"), testSubject.getItemTexts());
    }

    @Test void clickItem() {
        Locator item1 = mock(Locator.class);
        when(item1.innerText()).thenReturn("Home");
        mockFindItems(item1);
        testSubject.clickItem("Home");
        verify(item1).click();
    }

    @Test void clickItemNotFound() {
        mockFindItems();
        assertThrows(IllegalArgumentException.class, () -> testSubject.clickItem("Home"));
    }
}
