package com.github.grossopa.playwright.component.mui.v4.inputs;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
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
}
