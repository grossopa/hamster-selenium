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

package com.github.grossopa.hamster.selenium.component.mat.main;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.openqa.selenium.By.className;

/**
 * Tests for {@link MatMenu}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatMenuTest {

    MatMenu testSubject;

    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);

    List<WebElement> menuItems = newArrayList();
    WebElement menuItem1 = mock(WebElement.class);
    WebElement menuItem2 = mock(WebElement.class);
    WebElement menuItem3 = mock(WebElement.class);

    WebComponent menuPanel = mock(WebComponent.class);
    WebElement menuPanelElement = mock(WebElement.class);

    @BeforeEach
    @SuppressWarnings({"unchecked", "rawtypes"})
    void setUp() {
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);
        menuItems.add(menuItem3);

        when(menuItem1.getAttribute("class")).thenReturn("mat-menu-item-submenu-trigger");
        when(menuItem2.getAttribute("class")).thenReturn("mat-menu-item-submenu-trigger");
        when(menuItem3.getAttribute("class")).thenReturn("mat-menu-item-submenu-trigger");

        when(menuItem1.getText()).thenReturn("Item 1");
        when(menuItem2.getText()).thenReturn("Item 2");
        when(menuItem3.getText()).thenReturn("Item 3");

        when(element.findElements(By.className("mat-menu-item"))).thenReturn(menuItems);

        WebDriverWait wait = mock(WebDriverWait.class);
        MatOverlayContainer overlayContainer = mock(MatOverlayContainer.class);

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

        when(config.getOverlayAbsolutePath()).thenReturn("/html/body");
        when(config.getCssPrefix()).thenReturn("mat-");
        when(config.getCdkPrefix()).thenReturn("cdk-");
        when(config.getTagPrefix()).thenReturn("mat-");
        testSubject = new MatMenu(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("Menu", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getAttribute("class")).thenReturn("mat-menu-panel");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getAttribute("class")).thenReturn("mat-menu-panel-23");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element");
        assertEquals("MatMenu{element=element}", testSubject.toString());
    }

    @Test
    void getMenuItems() {
        assertEquals(3, testSubject.getMenuItems().size());
    }

    @Test
    void expandItemByIndex() {
        MatMenu menu = testSubject.expandItemByIndex(1, 100L, 100L);
        assertEquals(menuPanelElement, menu.getWrappedElement());
        verify(driver, times(1)).moveTo(argThat(arg -> {
            MatMenuItem interactedMenuItem = (MatMenuItem) arg;
            assertEquals(menuItem2, interactedMenuItem.getWrappedElement());
            return true;
        }));
    }

    @Test
    void selectItemByIndex() {
        testSubject.selectItemByIndex(1);
        verify(menuItem2, times(1)).click();
    }

    @Test
    void expandItemByText() {
        MatMenu menu = testSubject.expandItemByText("Item 2", 100L, 100L);
        assertEquals(menuPanelElement, menu.getWrappedElement());
        verify(driver, times(1)).moveTo(argThat(arg -> {
            MatMenuItem interactedMenuItem = (MatMenuItem) arg;
            assertEquals(menuItem2, interactedMenuItem.getWrappedElement());
            return true;
        }));
    }

    @Test
    void selectItemByText() {
        testSubject.selectItemByText("Item 3");
        verify(menuItem3, times(1)).click();
    }

    @Test
    void close() {
        testSubject.close();
        verify(element, times(1)).sendKeys(Keys.ESCAPE);
    }

}
