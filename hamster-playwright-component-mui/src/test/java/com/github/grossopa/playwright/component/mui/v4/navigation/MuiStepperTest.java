package com.github.grossopa.playwright.component.mui.v4.navigation;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiStepperTest {
    MuiStepper testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiStepper(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Stepper", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    private void mockFindSteps(Locator... stepLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(stepLocators));
    }

    @Test void getStepCount() {
        mockFindSteps(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getStepCount());
    }

    @Test void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiStepper-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiStepper-horizontal");
        assertFalse(testSubject.isVertical());
    }

    @Test void getActiveStepReturnsNegativeOneWhenNoActiveStep() {
        Locator step1 = mock(Locator.class);
        when(step1.getAttribute("class")).thenReturn("MuiStep-root");
        // findComponents for StepIcon-active returns empty
        Locator iconLocator = mock(Locator.class);
        when(step1.locator(anyString())).thenReturn(iconLocator);
        when(iconLocator.all()).thenReturn(List.of());

        mockFindSteps(step1);
        assertEquals(-1, testSubject.getActiveStep());
    }

    @Test void getActiveStepFindsActiveByClassName() {
        Locator step1 = mock(Locator.class);
        Locator step2 = mock(Locator.class);
        when(step1.getAttribute("class")).thenReturn("MuiStep-root");
        when(step2.getAttribute("class")).thenReturn("MuiStep-root MuiStep-active");
        Locator iconLocator = mock(Locator.class);
        when(step1.locator(anyString())).thenReturn(iconLocator);
        when(step2.locator(anyString())).thenReturn(iconLocator);
        when(iconLocator.all()).thenReturn(List.of());

        mockFindSteps(step1, step2);
        assertEquals(1, testSubject.getActiveStep());
    }
}
