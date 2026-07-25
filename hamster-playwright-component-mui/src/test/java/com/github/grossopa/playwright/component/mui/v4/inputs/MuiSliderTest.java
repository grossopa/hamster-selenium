package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;
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

    @Test void getComponentName() { assertEquals("Slider", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    @Test void defaultInverseScaleFunction() {
        assertEquals(5.0, MuiSlider.DEFAULT_INVERSE_SCALE_FUNCTION.apply(5.0));
    }

    @Test void getInverseScaleFunction() {
        assertNotNull(testSubject.getInverseScaleFunction());
    }

    @Test void constructorWithCustomScaleFunction() {
        Function<Double, Double> customFn = x -> x * 2;
        MuiSlider custom = new MuiSlider(locator, driver, config, customFn);
        assertEquals(customFn, custom.getInverseScaleFunction());
    }

    @Test void constructorWithNullScaleFunction() {
        MuiSlider custom = new MuiSlider(locator, driver, config, null);
        assertEquals(MuiSlider.DEFAULT_INVERSE_SCALE_FUNCTION, custom.getInverseScaleFunction());
    }

    @Test void isVerticalTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiSlider-root MuiSlider-vertical");
        assertTrue(testSubject.isVertical());
    }

    @Test void isVerticalFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiSlider-root");
        assertFalse(testSubject.isVertical());
    }

    @Test void isVerticalNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isVertical());
    }

    @Test void isInvertedTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiSlider-root MuiSlider-trackInverted");
        assertTrue(testSubject.isInverted());
    }

    @Test void isInvertedFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiSlider-root");
        assertFalse(testSubject.isInverted());
    }

    @Test void moveThumbInvalidPercentageLow() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.moveThumb(-0.1));
    }

    @Test void moveThumbInvalidPercentageHigh() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.moveThumb(1.1));
    }

    @Test void moveThumbValidPercentage() {
        testSubject.moveThumb(0.5);
        verify(locator).evaluate(contains("mousedown"), eq(0.5));
    }

    /**
     * Mocks the findComponent chain for getFirstThumb():
     * locator.locator(anyString()) → childLocator
     * childLocator.first() → firstLocator
     * firstLocator.getAttribute("aria-valuenow") → value
     * firstLocator.getAttribute("aria-valuemin") → min
     * firstLocator.getAttribute("aria-valuemax") → max
     */
    private Locator mockGetFirstThumb(String value, String min, String max) {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("aria-valuenow")).thenReturn(value);
        when(firstLocator.getAttribute("data-value")).thenReturn(null);
        when(firstLocator.getAttribute("aria-valuemin")).thenReturn(min);
        when(firstLocator.getAttribute("aria-valuemax")).thenReturn(max);
        return firstLocator;
    }

    // getValue → getFirstThumb().getValue()
    @Test void getValue() {
        mockGetFirstThumb("50", "0", "100");
        assertEquals("50", testSubject.getValue());
    }

    @Test void getValueDefault() {
        mockGetFirstThumb(null, "0", "100");
        assertEquals("0", testSubject.getValue());
    }

    // getValueInteger
    @Test void getValueInteger() {
        mockGetFirstThumb("42.7", "0", "100");
        assertEquals(42, testSubject.getValueInteger());
    }

    // getValueLong
    @Test void getValueLong() {
        mockGetFirstThumb("42.7", "0", "100");
        assertEquals(42L, testSubject.getValueLong());
    }

    // getValueDouble
    @Test void getValueDouble() {
        mockGetFirstThumb("42.7", "0", "100");
        assertEquals(42.7, testSubject.getValueDouble());
    }

    // getMinValue → getFirstThumb().getMinValue()
    @Test void getMinValue() {
        mockGetFirstThumb("50", "10", "100");
        assertEquals("10", testSubject.getMinValue());
    }

    @Test void getMinValueDefault() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("aria-valuemin")).thenReturn(null);
        assertEquals("0", testSubject.getMinValue());
    }

    // getMinValueInteger
    @Test void getMinValueInteger() {
        mockGetFirstThumb("50", "10", "100");
        assertEquals(10, testSubject.getMinValueInteger());
    }

    // getMinValueLong
    @Test void getMinValueLong() {
        mockGetFirstThumb("50", "10", "100");
        assertEquals(10L, testSubject.getMinValueLong());
    }

    // getMinValueDouble
    @Test void getMinValueDouble() {
        mockGetFirstThumb("50", "10.5", "100");
        assertEquals(10.5, testSubject.getMinValueDouble());
    }

    // getMaxValue → getFirstThumb().getMaxValue()
    @Test void getMaxValue() {
        mockGetFirstThumb("50", "0", "200");
        assertEquals("200", testSubject.getMaxValue());
    }

    @Test void getMaxValueDefault() {
        Locator childLocator = mock(Locator.class);
        Locator firstLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.first()).thenReturn(firstLocator);
        when(firstLocator.getAttribute("aria-valuemax")).thenReturn(null);
        assertEquals("100", testSubject.getMaxValue());
    }

    // getMaxValueInteger
    @Test void getMaxValueInteger() {
        mockGetFirstThumb("50", "0", "200");
        assertEquals(200, testSubject.getMaxValueInteger());
    }

    // getMaxValueLong
    @Test void getMaxValueLong() {
        mockGetFirstThumb("50", "0", "200");
        assertEquals(200L, testSubject.getMaxValueLong());
    }

    // getMaxValueDouble
    @Test void getMaxValueDouble() {
        mockGetFirstThumb("50", "0", "200.5");
        assertEquals(200.5, testSubject.getMaxValueDouble());
    }

    // getFirstThumb
    @Test void getFirstThumb() {
        mockGetFirstThumb("50", "0", "100");
        MuiSliderThumb thumb = testSubject.getFirstThumb();
        assertNotNull(thumb);
        assertEquals("50", thumb.getValue());
    }

    // getAllThumbs
    @Test void getAllThumbs() {
        Locator thumbLocator1 = mock(Locator.class);
        Locator thumbLocator2 = mock(Locator.class);
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(thumbLocator1, thumbLocator2));

        when(thumbLocator1.getAttribute("aria-valuenow")).thenReturn("10");
        when(thumbLocator2.getAttribute("aria-valuenow")).thenReturn("20");

        List<MuiSliderThumb> thumbs = testSubject.getAllThumbs();
        assertEquals(2, thumbs.size());
        assertEquals("10", thumbs.get(0).getValue());
        assertEquals("20", thumbs.get(1).getValue());
    }

    @Test void getAllThumbsEmpty() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of());

        List<MuiSliderThumb> thumbs = testSubject.getAllThumbs();
        assertTrue(thumbs.isEmpty());
    }

    // setValue(Double)
    @Test void setValueDouble() {
        mockGetFirstThumb("50", "0", "100");
        testSubject.setValue(75.0);
        verify(locator).evaluate(contains("mousedown"), eq(0.75));
    }

    @Test void setValueDoubleOutOfRange() {
        mockGetFirstThumb("50", "0", "100");
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(150.0));
    }

    @Test void setValueDoubleBelowRange() {
        mockGetFirstThumb("50", "0", "100");
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(-10.0));
    }

    // setValue(Integer)
    @Test void setValueInteger() {
        mockGetFirstThumb("50", "0", "100");
        testSubject.setValue(60);
        verify(locator).evaluate(contains("mousedown"), eq(0.6));
    }

    // setValue(Long)
    @Test void setValueLong() {
        mockGetFirstThumb("50", "0", "100");
        testSubject.setValue(80L);
        verify(locator).evaluate(contains("mousedown"), eq(0.8));
    }

    // createSliderThumb
    @Test void createSliderThumb() {
        Locator thumbLocator = mock(Locator.class);
        MuiSliderThumb thumb = testSubject.createSliderThumb(thumbLocator);
        assertNotNull(thumb);
    }
}
