package com.github.grossopa.playwright.component.mui.v4.feedback;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiSnackbarTest {
    MuiSnackbar testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiSnackbar(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Snackbar", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindComponentText(String text) {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.innerText()).thenReturn(text);
    }

    // getMessage
    @Test void getMessage() {
        mockFindComponentText("Snackbar message");
        assertEquals("Snackbar message", testSubject.getMessage());
    }

    // getAction
    @Test void getAction() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        WebComponent result = testSubject.getAction();
        assertNotNull(result);
    }

    // clickAction - getAction() returns non-null wrapper, so action != null
    @Test void clickAction() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        testSubject.clickAction();
        verify(firstLocator).click();
    }

    // hasAction - getAction() returns non-null wrapper
    @Test void hasAction() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        assertTrue(testSubject.hasAction());
    }

    // isOpen
    @Test void isOpenTrue() {
        when(locator.isVisible()).thenReturn(true);
        assertTrue(testSubject.isOpen());
    }

    @Test void isOpenFalse() {
        when(locator.isVisible()).thenReturn(false);
        assertFalse(testSubject.isOpen());
    }

    // close - findComponent returns non-null wrapper
    @Test void close() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        testSubject.close();
        verify(firstLocator).click();
    }

    // getAnchorOrigin tests
    @Test void getAnchorOriginBottomLeft() {
        when(locator.getAttribute("class")).thenReturn("MuiSnackbar-anchorOrigin-bottom-left");
        assertEquals("bottom-left", testSubject.getAnchorOrigin());
    }

    @Test void getAnchorOriginBottomRight() {
        when(locator.getAttribute("class")).thenReturn("MuiSnackbar-anchorOrigin-bottom-right");
        assertEquals("bottom-right", testSubject.getAnchorOrigin());
    }

    @Test void getAnchorOriginBottomCenter() {
        when(locator.getAttribute("class")).thenReturn("MuiSnackbar-anchorOrigin-bottom-center");
        assertEquals("bottom-center", testSubject.getAnchorOrigin());
    }

    @Test void getAnchorOriginTopLeft() {
        when(locator.getAttribute("class")).thenReturn("MuiSnackbar-anchorOrigin-top-left");
        assertEquals("top-left", testSubject.getAnchorOrigin());
    }

    @Test void getAnchorOriginTopRight() {
        when(locator.getAttribute("class")).thenReturn("MuiSnackbar-anchorOrigin-top-right");
        assertEquals("top-right", testSubject.getAnchorOrigin());
    }

    @Test void getAnchorOriginTopCenter() {
        when(locator.getAttribute("class")).thenReturn("MuiSnackbar-anchorOrigin-top-center");
        assertEquals("top-center", testSubject.getAnchorOrigin());
    }

    @Test void getAnchorOriginDefault() {
        when(locator.getAttribute("class")).thenReturn("MuiSnackbar-root");
        assertEquals("bottom-left", testSubject.getAnchorOrigin());
    }

    @Test void getAnchorOriginNullClass() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> testSubject.getAnchorOrigin());
    }
}
