package com.github.grossopa.playwright.component.mui.v4.surfaces;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiPaperTest {
    MuiPaper testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiPaper(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Paper", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void getElevation() {
        when(locator.getAttribute("class")).thenReturn("Muielevation4");
        assertEquals(4, testSubject.getElevation());
    }

    @Test void getElevationDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals(0, testSubject.getElevation());
    }

    @Test void getVariantOutlined() {
        when(locator.getAttribute("class")).thenReturn("MuiPaper-outlined");
        assertEquals("outlined", testSubject.getVariant());
    }

    @Test void getVariantElevation() {
        when(locator.getAttribute("class")).thenReturn("MuiPaper-elevation");
        assertEquals("elevation", testSubject.getVariant());
    }

    @Test void getVariantDefault() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertEquals("default", testSubject.getVariant());
    }

    @Test void isRoundedTrue() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertTrue(testSubject.isRounded());
    }

    @Test void isRoundedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiPaper-rounded");
        assertFalse(testSubject.isRounded());
    }

    @Test void isSquaredTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiPaper-rounded");
        assertTrue(testSubject.isSquared());
    }

    @Test void isSquaredFalse() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isSquared());
    }

    @Test void getText() {
        when(locator.innerText()).thenReturn("Paper content");
        assertEquals("Paper content", testSubject.getText());
    }
}
