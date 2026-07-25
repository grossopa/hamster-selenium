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

class MuiBadgeTest {
    MuiBadge testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiBadge(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Badge", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void getBadgeContent() {
        Locator badgeLocator = mock(Locator.class);
        when(locator.locator(".MuiBadge-badge")).thenReturn(badgeLocator);
        when(badgeLocator.first()).thenReturn(badgeLocator);
        when(badgeLocator.innerText()).thenReturn("5");
        assertEquals("5", testSubject.getBadgeContent());
    }
}
