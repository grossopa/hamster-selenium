package com.github.grossopa.playwright.component.mui.v4.datadisplay;

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

class MuiAvatarTest {
    MuiAvatar testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAvatar(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Avatar", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getText() {
        when(locator.innerText()).thenReturn("AB");
        assertEquals("AB", testSubject.getText());
    }

    // findComponent("img") always returns non-null DefaultWebComponent
    @Test void isImageAvatarAlwaysTrue() {
        Locator imgChildLocator = mock(Locator.class);
        when(locator.locator("img")).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(imgChildLocator);
        assertTrue(testSubject.isImageAvatar());
    }

    // getImg - findComponent("img") returns non-null wrapper
    @Test void getImg() {
        Locator imgChildLocator = mock(Locator.class);
        when(locator.locator("img")).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(imgChildLocator);
        WebComponent img = testSubject.getImg();
        assertNotNull(img);
    }

    // getAlt - getImg() returns non-null, then getAttribute("alt")
    @Test void getAlt() {
        Locator imgChildLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator("img")).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("alt")).thenReturn("User avatar");
        assertEquals("User avatar", testSubject.getAlt());
    }

    // getSrc - getImg() returns non-null, then getAttribute("src")
    @Test void getSrc() {
        Locator imgChildLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator("img")).thenReturn(imgChildLocator);
        when(imgChildLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("src")).thenReturn("https://example.com/avatar.png");
        assertEquals("https://example.com/avatar.png", testSubject.getSrc());
    }
}
