package com.github.grossopa.playwright.component.mui.v4.navigation;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiDrawerTest {
    MuiDrawer testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiDrawer(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Drawer", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void isOpenTrue() {
        when(locator.isVisible()).thenReturn(true);
        assertTrue(testSubject.isOpen());
    }

    @Test void isOpenFalse() {
        when(locator.isVisible()).thenReturn(false);
        assertFalse(testSubject.isOpen());
    }

    @Test void close() {
        testSubject.close();
        verify(locator).press("Escape");
    }

    @Test void getAnchorLeft() {
        when(locator.getAttribute("class")).thenReturn("MuiDrawer-paper anchorLeft");
        assertEquals("left", testSubject.getAnchor());
    }

    @Test void getAnchorRight() {
        when(locator.getAttribute("class")).thenReturn("anchorRight");
        assertEquals("right", testSubject.getAnchor());
    }

    @Test void getAnchorTop() {
        when(locator.getAttribute("class")).thenReturn("anchorTop");
        assertEquals("top", testSubject.getAnchor());
    }

    @Test void getAnchorBottom() {
        when(locator.getAttribute("class")).thenReturn("anchorBottom");
        assertEquals("bottom", testSubject.getAnchor());
    }

    @Test void getAnchorDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("left", testSubject.getAnchor());
    }

    @Test void isModalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiDrawer-modal");
        assertTrue(testSubject.isModal());
    }

    @Test void isModalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiDrawer-paper");
        assertFalse(testSubject.isModal());
    }

    @Test void getContentText() {
        when(locator.innerText()).thenReturn("Drawer content");
        assertEquals("Drawer content", testSubject.getContentText());
    }
}
