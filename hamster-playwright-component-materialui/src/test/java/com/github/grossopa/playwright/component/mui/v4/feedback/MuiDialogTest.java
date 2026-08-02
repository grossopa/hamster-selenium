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

class MuiDialogTest {
    MuiDialog testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiDialog(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Dialog", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindComponent(String text) {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.innerText()).thenReturn(text);
    }

    @Test
    void getDialogTitle() {
        mockFindComponent("Test Title");
        WebComponent result = testSubject.getDialogTitle();
        assertNotNull(result);
        assertEquals("Test Title", result.innerText());
    }

    @Test
    void getTitleText() {
        mockFindComponent("My Dialog Title");
        assertEquals("My Dialog Title", testSubject.getTitleText());
    }

    @Test
    void getDialogContent() {
        mockFindComponent("Dialog content here");
        WebComponent result = testSubject.getDialogContent();
        assertNotNull(result);
        assertEquals("Dialog content here", result.innerText());
    }

    @Test
    void getContentText() {
        mockFindComponent("Some content text");
        assertEquals("Some content text", testSubject.getContentText());
    }

    @Test
    void getDialogActions() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        WebComponent result = testSubject.getDialogActions();
        assertNotNull(result);
    }

    @Test
    void isOpenTrue() {
        when(locator.getAttribute("role")).thenReturn("dialog");
        when(locator.isVisible()).thenReturn(true);
        assertTrue(testSubject.isOpen());
    }

    @Test
    void isOpenFalseWrongRole() {
        when(locator.getAttribute("role")).thenReturn("button");
        when(locator.isVisible()).thenReturn(true);
        assertFalse(testSubject.isOpen());
    }

    @Test
    void isOpenFalseNotVisible() {
        when(locator.getAttribute("role")).thenReturn("dialog");
        when(locator.isVisible()).thenReturn(false);
        assertFalse(testSubject.isOpen());
    }

    @Test
    void isOpenFalseNullRole() {
        when(locator.getAttribute("role")).thenReturn(null);
        assertFalse(testSubject.isOpen());
    }

    @Test
    void close() {
        testSubject.close();
        verify(locator).press("Escape");
    }

    @Test
    void clickActionButton() {
        // getDialogActions() → findComponent → locator.locator(any).first() → non-null wrapper
        Locator actionsChildLocator = mock(Locator.class);
        Locator actionsFirstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(actionsChildLocator);
        when(actionsChildLocator.first()).thenReturn(actionsFirstLocator);

        // actions.findComponent("button:text('OK')") → actionsFirstLocator.locator(any).first()
        Locator buttonInnerLocator = mock(Locator.class);
        Locator buttonFirstLocator = mock(Locator.class);
        when(actionsFirstLocator.locator(anyString())).thenReturn(buttonInnerLocator);
        when(buttonInnerLocator.first()).thenReturn(buttonFirstLocator);

        testSubject.clickActionButton("OK");
        verify(buttonFirstLocator).click();
    }
}
