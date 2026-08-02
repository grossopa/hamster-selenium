package com.github.grossopa.playwright.component.mui.v4.datadisplay;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MuiBadgeTest {
    MuiBadge testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiBadge(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Badge", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private Locator mockFindBadge() {
        Locator badgeLocator = mock(Locator.class);
        when(locator.locator(".MuiBadge-badge")).thenReturn(badgeLocator);
        when(badgeLocator.first()).thenReturn(badgeLocator);
        return badgeLocator;
    }

    // getBadgeContent
    @Test void getBadgeContent() {
        Locator badgeLocator = mockFindBadge();
        when(badgeLocator.innerText()).thenReturn("5");
        assertEquals("5", testSubject.getBadgeContent());
    }

    // isVisible - findComponent returns non-null, then check class for "MuiBadge-invisible"
    @Test void isVisibleTrueWhenNoInvisibleClass() {
        Locator badgeLocator = mockFindBadge();
        when(badgeLocator.getAttribute("class")).thenReturn("MuiBadge-badge");
        assertTrue(testSubject.isVisible());
    }

    @Test void isVisibleFalseWhenInvisible() {
        Locator badgeLocator = mockFindBadge();
        when(badgeLocator.getAttribute("class")).thenReturn("MuiBadge-badge MuiBadge-invisible");
        assertFalse(testSubject.isVisible());
    }

    @Test void isVisibleTrueWhenNullClass() {
        Locator badgeLocator = mockFindBadge();
        when(badgeLocator.getAttribute("class")).thenReturn(null);
        assertTrue(testSubject.isVisible());
    }

    // isDotVariant
    @Test void isDotVariantTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiBadge-root MuiBadge-dot");
        assertTrue(testSubject.isDotVariant());
    }

    @Test void isDotVariantFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiBadge-root");
        assertFalse(testSubject.isDotVariant());
    }

    @Test void isDotVariantNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isDotVariant());
    }
}
