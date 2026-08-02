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

class MuiLinkTest {
    MuiLink testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiLink(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Link", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Click here");
        assertEquals("Click here", testSubject.getText());
    }

    @Test void getHref() {
        when(locator.getAttribute("href")).thenReturn("https://example.com");
        assertEquals("https://example.com", testSubject.getHref());
    }

    @Test void isVisitedTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiLink-visited");
        assertTrue(testSubject.isVisited());
    }

    @Test void isVisitedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiLink-root");
        assertFalse(testSubject.isVisited());
    }

    @Test void isUnderlinedTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiLink-underline");
        assertTrue(testSubject.isUnderlined());
    }

    @Test void isUnderlinedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiLink-root");
        assertFalse(testSubject.isUnderlined());
    }

    @Test void click() {
        testSubject.click();
        verify(locator).click();
    }
}
