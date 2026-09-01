/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.grossopa.playwright.component.mat.finder;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.main.MatOverlayContainer;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatOverlayFinder}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MatOverlayFinderTest {

    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = new MatConfig();

    Locator locator1 = mock(Locator.class);
    Locator locator2 = mock(Locator.class);

    MatOverlayContainer container1;
    MatOverlayContainer container2;

    MatOverlayFinder testSubject;

    @BeforeEach
    void setUp() {
        container1 = new MatOverlayContainer(locator1, driver, config);
        container2 = new MatOverlayContainer(locator2, driver, config);
        testSubject = new MatOverlayFinder(driver, config);
    }

    @Test
    void driverNotNull() {
        assertThrows(NullPointerException.class, () -> new MatOverlayFinder(null, config));
    }

    @Test
    void configNotNull() {
        assertThrows(NullPointerException.class, () -> new MatOverlayFinder(driver, null));
    }

    @Test
    void getConfig() {
        assertSame(config, testSubject.getConfig());
    }

    @Test
    void findContainers() {
        doReturn(List.of(container1, container2)).when(driver).findComponentsAs(anyString(), any());
        assertEquals(List.of(container1, container2), testSubject.findContainers());
    }

    @Test
    void findContainersSelector() {
        doReturn(List.of()).when(driver).findComponentsAs(anyString(), any());
        testSubject.findContainers();
        verify(driver).findComponentsAs(eq("xpath=/html/body/div[contains(@class,'cdk-overlay-container')]"),
                any());
    }

    @Test
    void findVisibleContainers() {
        doReturn(List.of(container1, container2)).when(driver).findComponentsAs(anyString(), any());
        when(locator1.isVisible()).thenReturn(true);
        when(locator2.isVisible()).thenReturn(false);
        assertEquals(List.of(container1), testSubject.findVisibleContainers());
    }

    @Test
    void findTopVisibleContainer() {
        doReturn(List.of(container1, container2)).when(driver).findComponentsAs(anyString(), any());
        when(locator1.isVisible()).thenReturn(true);
        when(locator2.isVisible()).thenReturn(true);
        assertSame(container2, testSubject.findTopVisibleContainer());
    }

    @Test
    void findTopVisibleContainerEmpty() {
        doReturn(List.of()).when(driver).findComponentsAs(anyString(), any());
        assertThrows(NoSuchElementException.class, () -> testSubject.findTopVisibleContainer());
    }

    @Test
    void equalsSameInstance() {
        assertEquals(testSubject, testSubject);
    }

    @Test
    void equalsSameValues() {
        assertEquals(testSubject, new MatOverlayFinder(driver, config));
    }

    @Test
    void equalsSameValuesHashCode() {
        assertEquals(testSubject.hashCode(), new MatOverlayFinder(driver, config).hashCode());
    }

    @Test
    void equalsDifferentDriver() {
        assertNotEquals(testSubject, new MatOverlayFinder(mock(ComponentDriver.class), config));
    }

    @Test
    void equalsDifferentConfig() {
        assertNotEquals(testSubject, new MatOverlayFinder(driver, MatConfig.create("a-", "b-", "c-", "/d")));
    }

    @Test
    void equalsNull() {
        assertNotEquals(null, testSubject);
    }

    @Test
    void equalsDifferentType() {
        assertNotEquals("string", testSubject);
    }

    @Test
    void testToString() {
        assertTrue(testSubject.toString().contains("MatOverlayFinder"));
    }
}
