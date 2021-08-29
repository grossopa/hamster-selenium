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

package com.github.grossopa.hamster.selenium.component.mat.main.sub;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.exception.MenuItemNotExpandableException;
import com.github.grossopa.hamster.selenium.component.mat.main.MatOverlayContainer;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.openqa.selenium.By.className;

/**
 * Tests for {@link MatMenuItem}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatMenuItemTest {

    MatMenuItem testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getOverlayAbsolutePath()).thenReturn("/html/body");
        when(config.getCssPrefix()).thenReturn("mat-");
        when(config.getCdkPrefix()).thenReturn("cdk-");
        when(config.getTagPrefix()).thenReturn("mat-");
        testSubject = new MatMenuItem(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("MenuItem", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getAttribute("class")).thenReturn("mat-menu-item");
        assertTrue(testSubject.validate());
    }


    @Test
    void validateNegative() {
        when(element.getAttribute("class")).thenReturn("mat-menu-item-23");
        assertFalse(testSubject.validate());
    }

    @Test
    void isExpanded() {
        when(element.getAttribute("aria-expanded")).thenReturn("true");
        assertTrue(testSubject.isExpanded());
    }

    @Test
    void isExpandedNegative() {
        when(element.getAttribute("aria-expanded")).thenReturn(null);
        assertFalse(testSubject.isExpanded());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element");
        assertEquals("MatMenuItem{element=element}", testSubject.toString());
    }

    @Test
    void isExpandable() {
        when(element.getAttribute("class")).thenReturn("mat-menu-item-submenu-trigger");
        assertTrue(testSubject.isExpandable());
    }

    @Test
    void isExpandableNegative() {
        when(element.getAttribute("class")).thenReturn("mat-menu-item-submenu-trigger-123");
        assertFalse(testSubject.isExpandable());
    }

    @Test
    void expandNotExpandable() {
        // set to not expandable
        this.isExpandedNegative();
        assertThrows(MenuItemNotExpandableException.class, () -> testSubject.expand(100L, 100L));
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void expand() {
        // set to expandable
        this.isExpandable();

        WebDriverWait wait = mock(WebDriverWait.class);
        MatOverlayContainer overlayContainer = mock(MatOverlayContainer.class);
        WebComponent menuPanel = mock(WebComponent.class);
        WebElement menuPanelElement = mock(WebElement.class);
        WebComponent box = mock(WebComponent.class);

        when(driver.createWait(anyLong())).thenReturn(wait);
        when(wait.until(any())).then(a -> {
            Function func = a.getArgument(0);
            return func.apply(driver);
        });


        when(driver.findComponentsAs(eq(By.xpath("/html/body/div[contains(@class,'cdk-overlay-container')]")),
                any())).thenReturn(newArrayList(overlayContainer));
        when(overlayContainer.isDisplayed()).thenReturn(true);
        when(menuPanel.getWrappedElement()).thenReturn(menuPanelElement);

        when(box.findComponent(By.className("mat-menu-panel"))).thenReturn(menuPanel);
        List<WebComponent> boxes = newArrayList(box);
        when(overlayContainer.findComponents(className("cdk-overlay-connected-position-bounding-box"))).thenReturn(
                boxes);

        assertEquals(menuPanelElement, testSubject.expand(100L, 100L).getWrappedElement());
        assertEquals(menuPanelElement, testSubject.expand(0L, 100L).getWrappedElement());

        verify(driver, times(1)).threadSleep(100L);
    }

}
