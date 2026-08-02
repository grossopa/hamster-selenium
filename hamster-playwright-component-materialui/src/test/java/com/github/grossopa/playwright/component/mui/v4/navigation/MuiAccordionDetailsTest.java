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

class MuiAccordionDetailsTest {
    MuiAccordionDetails testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiAccordionDetails(locator, driver, config); }

    @Test void getComponentName() { assertEquals("AccordionDetails", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Details content");
        assertEquals("Details content", testSubject.getText());
    }

    @Test void isVisibleTrue() {
        when(locator.isVisible()).thenReturn(true);
        assertTrue(testSubject.isVisible());
    }

    @Test void isVisibleFalse() {
        when(locator.isVisible()).thenReturn(false);
        assertFalse(testSubject.isVisible());
    }
}
