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

    // getSteps
    @Test void getStepsEmpty() {
        mockFindSteps();
        assertTrue(testSubject.getSteps().isEmpty());
    }

    @Test void getStepsTwo() {
        mockFindSteps(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getSteps().size());
    }

    // getStepCount
    @Test void getStepCount() {
        mockFindSteps(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getStepCount());
    }

    @Test void getStepCountZero() {
        mockFindSteps();
        assertEquals(0, testSubject.getStepCount());
    }

    // isVertical
    @Test void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiStepper-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiStepper-horizontal");
        assertFalse(testSubject.isVertical());
    }

    @Test void isVerticalNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isVertical());
    }

    // getActiveStep
    @Test void getActiveStepReturnsNegativeOneWhenNoActiveStep() {
        Locator step1 = mock(Locator.class);
        when(step1.getAttribute("class")).thenReturn("MuiStep-root");
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

    // getStepLabels
    @Test void getStepLabels() {
        Locator step1 = mock(Locator.class);
        Locator step2 = mock(Locator.class);

        // step1.findComponent(".MuiStepLabel-label") → step1.locator(any).first()
        Locator labelChild1 = mock(Locator.class);
        Locator labelFirst1 = mock(Locator.class);
        when(step1.locator(anyString())).thenReturn(labelChild1);
        when(labelChild1.first()).thenReturn(labelFirst1);
        when(labelFirst1.innerText()).thenReturn("Step 1");

        // step2.findComponent(".MuiStepLabel-label") → step2.locator(any).first()
        Locator labelChild2 = mock(Locator.class);
        Locator labelFirst2 = mock(Locator.class);
        when(step2.locator(anyString())).thenReturn(labelChild2);
        when(labelChild2.first()).thenReturn(labelFirst2);
        when(labelFirst2.innerText()).thenReturn("Step 2");

        mockFindSteps(step1, step2);
        assertEquals(List.of("Step 1", "Step 2"), testSubject.getStepLabels());
    }

    @Test void getStepLabelsEmpty() {
        mockFindSteps();
        assertTrue(testSubject.getStepLabels().isEmpty());
    }
}
