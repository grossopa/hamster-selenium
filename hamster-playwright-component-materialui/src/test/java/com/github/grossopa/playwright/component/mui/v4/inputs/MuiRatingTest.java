package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiRatingTest {
    MuiRating testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() {
        testSubject = new MuiRating(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Rating", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions());
    }

    @Test
    void isReadOnlyTrue() {
        when(locator.getAttribute("class")).thenReturn("MuiRating-root Mui-readOnly");
        assertTrue(testSubject.isReadOnly());
    }

    @Test
    void isReadOnlyFalse() {
        when(locator.getAttribute("class")).thenReturn("MuiRating-root");
        assertFalse(testSubject.isReadOnly());
    }

    @Test
    void isReadOnlyNull() {
        when(locator.getAttribute("class")).thenReturn(null);
        assertFalse(testSubject.isReadOnly());
    }

    private void mockStars(Locator... starLocators) {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(anyString())).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(List.of(starLocators));
    }

    @Test
    void getStars() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        mockStars(star1, star2);
        List<WebComponent> stars = testSubject.getStars();
        assertEquals(2, stars.size());
    }

    @Test
    void getMaxValue() {
        mockStars(mock(Locator.class), mock(Locator.class), mock(Locator.class));
        assertEquals(3, testSubject.getMaxValue());
    }

    @Test
    void getValueReturnsThree() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        Locator star3 = mock(Locator.class);
        when(star1.getAttribute("class")).thenReturn("MuiRating-icon MuiRating-iconFilled");
        when(star2.getAttribute("class")).thenReturn("MuiRating-icon MuiRating-iconFilled");
        when(star3.getAttribute("class")).thenReturn("MuiRating-icon MuiRating-iconFilled");
        mockStars(star1, star2, star3);
        assertEquals(3.0, testSubject.getValue());
    }

    @Test
    void getValueReturnsZeroWhenNoFilled() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        when(star1.getAttribute("class")).thenReturn("MuiRating-icon");
        when(star2.getAttribute("class")).thenReturn("MuiRating-icon");
        mockStars(star1, star2);
        assertEquals(0.0, testSubject.getValue());
    }

    @Test
    void getValueReturnsZeroWhenNoStars() {
        mockStars();
        assertEquals(0.0, testSubject.getValue());
    }

    @Test
    void setValueClicksStar() {
        Locator star1 = mock(Locator.class);
        Locator star2 = mock(Locator.class);
        when(star1.getAttribute("class")).thenReturn("MuiRating-icon");
        when(star2.getAttribute("class")).thenReturn("MuiRating-icon");
        mockStars(star1, star2);
        testSubject.setValue(2);
        verify(star2).click();
    }

    @Test
    void setValueZeroDoesNotClick() {
        Locator star1 = mock(Locator.class);
        mockStars(star1);
        testSubject.setValue(0);
        verify(star1, never()).click();
    }

    @Test
    void setValueTooLargeThrows() {
        mockStars(mock(Locator.class));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(5));
    }

    @Test
    void setValueNegativeThrows() {
        mockStars(mock(Locator.class));
        assertThrows(IllegalArgumentException.class, () -> testSubject.setValue(-1));
    }
}
