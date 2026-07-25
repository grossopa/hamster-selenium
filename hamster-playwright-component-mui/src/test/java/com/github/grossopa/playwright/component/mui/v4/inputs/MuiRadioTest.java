package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiRadioTest {
    MuiRadio testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiRadio(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Radio", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void isCheckedTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiRadio-root Mui-checked");
        assertTrue(testSubject.isChecked());
    }

    @Test
    void isCheckedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiRadio-root");
        assertFalse(testSubject.isChecked());
    }

    @Test
    void selectWhenNotChecked() {
        when(locator.getAttribute("class")).thenReturn("MuiRadio-root");
        testSubject.select();
        verify(locator).click();
    }

    @Test
    void selectWhenAlreadyChecked() {
        when(locator.getAttribute("class")).thenReturn("MuiRadio-root Mui-checked");
        testSubject.select();
        verify(locator, never()).click();
    }
}
