package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiSliderTest {
    MuiSlider testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiSlider(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Slider", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void defaultInverseScaleFunction() {
        assertEquals(5.0, MuiSlider.DEFAULT_INVERSE_SCALE_FUNCTION.apply(5.0));
    }

    @Test
    void getInverseScaleFunction() {
        assertNotNull(testSubject.getInverseScaleFunction());
    }

    @Test
    void constructorWithCustomScaleFunction() {
        Function<Double, Double> customFn = x -> x * 2;
        MuiSlider custom = new MuiSlider(locator, driver, config, customFn);
        assertEquals(customFn, custom.getInverseScaleFunction());
    }

    @Test
    void constructorWithNullScaleFunction() {
        MuiSlider custom = new MuiSlider(locator, driver, config, null);
        assertEquals(MuiSlider.DEFAULT_INVERSE_SCALE_FUNCTION, custom.getInverseScaleFunction());
    }

    @Test
    void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiSlider-root MuiSlider-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test
    void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiSlider-root");
        assertFalse(testSubject.isVertical());
    }

    @Test
    void isVerticalNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isVertical());
    }

    @Test
    void isInvertedTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiSlider-root MuiSlider-trackInverted");
        assertTrue(testSubject.isInverted());
    }

    @Test
    void isInvertedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiSlider-root");
        assertFalse(testSubject.isInverted());
    }

    @Test
    void moveThumbInvalidPercentageLow() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.moveThumb(-0.1));
    }

    @Test
    void moveThumbInvalidPercentageHigh() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.moveThumb(1.1));
    }

    @Test
    void moveThumbValidPercentage() {
        testSubject.moveThumb(0.5);
        verify(locator).evaluate(contains("mousedown"), eq(0.5));
    }
}
