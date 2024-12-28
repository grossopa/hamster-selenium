/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.selenium.component.mui.v4.locator;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.finder.MuiModalFinder;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiModalFinder}
 *
 * @author Jack Yin
 * @since 1.1
 */
class MuiModalFinderTest {

    MuiModalFinder testSubject;
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        doCallRealMethod().when(config).setCssPrefix(any());
        config.setCssPrefix("Mui");
        when(config.getRootCss(any())).thenCallRealMethod();
        when(config.getOverlayAbsolutePath()).thenReturn("/html/body");
        when(config.getModalClasses()).thenCallRealMethod();
        testSubject = new MuiModalFinder(driver, config);
        List<WebComponent> components = newArrayList(createMockOverlay("MuiPopover-root", true),
                createMockOverlay("MuiMenu-root", false), createMockOverlay("MuiMenu-root", false),
                createMockOverlay("MuiMenu-root", true), createMockOverlay("MuiMenu-root", false),
                createMockOverlay("MuiMenu-root", true), createMockOverlay("MuiDialog-root", true),
                createMockOverlay("MuiDialog-root", false), createMockOverlay("MuiDrawer-root", true),
                createMockOverlay("MuiDrawer-root", true), createMockOverlay("MuiSomeCustomized-root", true));
        when(driver.findComponents(By.xpath("/html/body/div"))).thenReturn(components);
    }

    WebComponent createMockOverlay(String classes, boolean isDisplayed) {
        WebComponent overlay = mock(WebComponent.class);
        when(overlay.isDisplayed()).thenReturn(isDisplayed);
        when(overlay.getDomAttribute("class")).thenReturn(classes);
        return overlay;
    }

    @Test
    void findOverlays() {
        assertEquals(10, testSubject.findOverlays().size());
    }

    @Test
    void findOverlaysByComponentName() {
        assertEquals(5, testSubject.findOverlays("Menu").size());
    }

    @Test
    void findVisibleOverlays() {
        assertEquals(6, testSubject.findVisibleOverlays().size());
    }

    @Test
    void findVisibleOverlaysByComponentName() {
        assertEquals(1, testSubject.findVisibleOverlays("Popover").size());
    }

    @Test
    void findTopVisibleOverlay() {
        WebComponent component = testSubject.findTopVisibleOverlay();
        assertNotNull(component);
        assertEquals("MuiDrawer-root", component.getDomAttribute("class"));
    }

    @Test
    void findTopVisibleOverlayNotExists() {
        List<WebComponent> components = singletonList(createMockOverlay("MuiMenu-root", false));
        when(driver.findComponents(By.xpath("/html/body/div"))).thenReturn(components);
        assertNull(testSubject.findTopVisibleOverlay());
    }

    @Test
    void findTopVisibleOverlayByComponentName() {
        WebComponent component = testSubject.findTopVisibleOverlay("Popover");
        assertNotNull(component);
        assertEquals("MuiPopover-root", component.getDomAttribute("class"));
    }

    @Test
    void findTopVisibleOverlayByComponentNameNotExists() {
        assertNull(testSubject.findTopVisibleOverlay("SomeComponentNotExists"));
    }
}
