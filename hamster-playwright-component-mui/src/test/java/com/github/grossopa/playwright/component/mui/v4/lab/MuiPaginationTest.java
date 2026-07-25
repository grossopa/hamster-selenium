package com.github.grossopa.playwright.component.mui.v4.lab;

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

class MuiPaginationTest {
    MuiPagination testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiPagination(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Pagination", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindPages(Locator... pageLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(pageLocators));
    }

    @Test void getPageCount() {
        mockFindPages(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getPageCount());
    }

    @Test void getCurrentPage() {
        Locator page1 = mock(Locator.class);
        Locator page2 = mock(Locator.class);
        when(page1.getAttribute("aria-current")).thenReturn(null);
        when(page2.getAttribute("aria-current")).thenReturn("true");
        mockFindPages(page1, page2);
        assertEquals(2, testSubject.getCurrentPage());
    }

    @Test void getCurrentPageDefault() {
        Locator page1 = mock(Locator.class);
        when(page1.getAttribute("aria-current")).thenReturn(null);
        mockFindPages(page1);
        assertEquals(1, testSubject.getCurrentPage());
    }

    @Test void goToPage() {
        Locator page1 = mock(Locator.class);
        mockFindPages(page1);
        testSubject.goToPage(1);
        verify(page1).click();
    }

    @Test void goToPageOutOfBounds() {
        mockFindPages();
        assertThrows(IndexOutOfBoundsException.class, () -> testSubject.goToPage(1));
    }

    @Test void isCircularTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-circular");
        assertTrue(testSubject.isCircular());
    }

    @Test void isCircularFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-root");
        assertFalse(testSubject.isCircular());
    }

    @Test void getVariantOutlined() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-outlined");
        assertEquals("outlined", testSubject.getVariant());
    }

    @Test void getVariantText() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-text");
        assertEquals("text", testSubject.getVariant());
    }

    @Test void getVariantDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("default", testSubject.getVariant());
    }

    @Test void getSizeSmall() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-sizeSmall");
        assertEquals("small", testSubject.getSize());
    }

    @Test void getSizeLarge() {
        when(locator.getAttribute("class")).thenReturn("MuiPagination-sizeLarge");
        assertEquals("large", testSubject.getSize());
    }

    @Test void getSizeDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("medium", testSubject.getSize());
    }
}
