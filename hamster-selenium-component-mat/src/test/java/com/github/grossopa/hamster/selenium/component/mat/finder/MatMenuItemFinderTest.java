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

package com.github.grossopa.hamster.selenium.component.mat.finder;

import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.main.MatMenu;
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
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.openqa.selenium.By.className;

/**
 * Tests for {@link MatMenuItemFinder}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatMenuItemFinderTest {

    MatMenuItemFinder testSubject;
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MatConfig config = mock(MatConfig.class);
    MatOverlayContainer overlayContainer = mock(MatOverlayContainer.class);
    WebComponent menuPanel = mock(WebComponent.class);
    WebElement menuPanelElement = mock(WebElement.class);
    WebDriverWait wait = mock(WebDriverWait.class);

    @BeforeEach
    @SuppressWarnings({"unchecked", "rawtypes"})
    void setUp() {
        when(driver.createWait(anyLong())).thenReturn(wait);
        when(wait.until(any())).then(a -> {
            Function func = a.getArgument(0);
            return func.apply(driver);
        });

        when(config.getOverlayAbsolutePath()).thenReturn("/html/body");
        when(config.getCssPrefix()).thenReturn("mat-");
        when(config.getCdkPrefix()).thenReturn("cdk-");
        when(config.getTagPrefix()).thenReturn("mat-");
        when(driver.findComponentsAs(eq(By.xpath("/html/body/div[contains(@class,'cdk-overlay-container')]")),
                any())).thenReturn(newArrayList(overlayContainer));
        when(overlayContainer.isDisplayed()).thenReturn(true);
        when(menuPanel.getWrappedElement()).thenReturn(menuPanelElement);

        testSubject = new MatMenuItemFinder(driver, config);
    }

    @Test
    void findTopMenu() {
        WebComponent box = mock(WebComponent.class);
        when(box.findComponent(By.className("mat-menu-panel"))).thenReturn(menuPanel);
        List<WebComponent> boxes = newArrayList(box);
        when(overlayContainer.findComponents(className("cdk-overlay-connected-position-bounding-box"))).thenReturn(
                boxes);
        assertEquals(menuPanelElement, requireNonNull(testSubject.findTopMenu()).getWrappedElement());
    }

    @Test
    void findTopMenuWithDelays() {
        WebComponent box = mock(WebComponent.class);
        when(box.findComponent(By.className("mat-menu-panel"))).thenReturn(menuPanel);
        List<WebComponent> boxes = newArrayList(box);
        when(overlayContainer.findComponents(className("cdk-overlay-connected-position-bounding-box"))).thenReturn(
                boxes);
        assertEquals(menuPanelElement, requireNonNull(testSubject.findTopMenu(1000L)).getWrappedElement());
    }

    @Test
    void findTopMenuNoContainer() {
        when(driver.findComponentsAs(eq(By.xpath("/html/body/div[contains(@class,'cdk-overlay-container')]")),
                any())).thenReturn(newArrayList());
        assertNull(testSubject.findTopMenu());
    }

    @Test
    void findTopMenuNoContainerEmptyBoxes() {
        assertNull(testSubject.findTopMenu());
    }

    @Test
    void findMenus() {
        List<WebComponent> menus = newArrayList(mock(MatMenu.class));
        when(overlayContainer.findComponentsAs(eq(By.xpath(
                        ".//*[contains(@class,\"cdk-overlay-connected-position-bounding-box\")]/descendant::*[contains(@class,\"mat-menu-panel\")]")),
                any())).then(a -> {
            Function<WebComponent, MatMenu> arg1 = a.getArgument(1);
            WebElement element = mock(WebElement.class);
            WebComponent component = mock(WebComponent.class);
            when(component.getWrappedElement()).thenReturn(element);
            assertEquals(element, arg1.apply(component).getWrappedElement());
            return menus;
        });
        assertEquals(1, testSubject.findMenus().size());
    }

    @Test
    void findMenusEmpty() {
        when(driver.findComponentsAs(eq(By.xpath("/html/body/div[contains(@class,'cdk-overlay-container')]")),
                any())).thenReturn(newArrayList());
        assertTrue(testSubject.findMenus().isEmpty());
    }

    @Test
    void findMenusWithDelays() {
        List<WebComponent> menus = newArrayList(mock(MatMenu.class));
        when(overlayContainer.findComponentsAs(eq(By.xpath(
                        ".//*[contains(@class,\"cdk-overlay-connected-position-bounding-box\")]/descendant::*[contains(@class,\"mat-menu-panel\")]")),
                any())).thenReturn(menus);
        assertEquals(1, testSubject.findMenus(1000L).size());
    }

    @Test
    void testToString() {
        when(driver.toString()).thenReturn("driver");
        when(config.toString()).thenReturn("config");
        assertEquals("MatMenuItemFinder{driver=driver, config=config}", testSubject.toString());
    }
}
