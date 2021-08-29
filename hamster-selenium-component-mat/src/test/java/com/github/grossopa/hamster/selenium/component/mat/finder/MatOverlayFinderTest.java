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
import com.github.grossopa.hamster.selenium.component.mat.main.MatOverlayContainer;
import com.github.grossopa.hamster.selenium.core.util.SimpleEqualsTester;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MatOverlayFinder}
 *
 * @author Jack Yin
 * @since 1.6
 */
class MatOverlayFinderTest {

    MatOverlayFinder testSubject;
    ComponentWebDriver driver;
    MatConfig config;

    @BeforeEach
    void setUp() {
        driver = mock(ComponentWebDriver.class);
        config = mock(MatConfig.class);
        when(config.getCdkPrefix()).thenReturn("cdk-");
        when(config.getOverlayAbsolutePath()).thenReturn("/html/body");

        testSubject = new MatOverlayFinder(driver, config);
    }

    @Test
    void getConfig() {
        assertEquals(config, testSubject.getConfig());
    }

    @Test
    void findContainers() {
        int[] index = new int[]{0};

        WebElement element1 = mock(WebElement.class);
        WebComponent overlayContainer1 = mock(WebComponent.class);
        when(overlayContainer1.getWrappedElement()).thenReturn(element1);

        WebElement element2 = mock(WebElement.class);
        WebComponent overlayContainer2 = mock(WebComponent.class);
        when(overlayContainer2.getWrappedElement()).thenReturn(element2);

        WebComponent[] overlayContainers = new WebComponent[]{overlayContainer1, overlayContainer2};
        List<MatOverlayContainer> containerResults = newArrayList();

        when(driver.findComponentsAs(eq(By.xpath("/html/body/div[contains(@class,'cdk-overlay-container')]")), any()))
                .then(answer -> {
                    Function<WebComponent, MatOverlayContainer> function = answer.getArgument(1);
                    containerResults.add(function.apply(overlayContainers[index[0]++]));
                    containerResults.add(function.apply(overlayContainers[index[0]++]));
                    return containerResults;
                });
        List<MatOverlayContainer> containers = testSubject.findContainers();

        verify(driver, times(1))
                .findComponentsAs(eq(By.xpath("/html/body/div[contains(@class,'cdk-overlay-container')]")), any());
        assertEquals(2, containers.size());
        assertEquals(containers.get(0).getWrappedElement(), element1);
        assertEquals(containers.get(1).getWrappedElement(), element2);
    }

    @Test
    @SuppressWarnings("unchecked")
    void findVisibleContainers() {
        WebElement element1 = mock(WebElement.class);
        MatOverlayContainer overlayContainer1 = mock(MatOverlayContainer.class);
        when(overlayContainer1.getWrappedElement()).thenReturn(element1);
        when(overlayContainer1.isDisplayed()).thenReturn(true);

        WebElement element2 = mock(WebElement.class);
        MatOverlayContainer overlayContainer2 = mock(MatOverlayContainer.class);
        when(overlayContainer2.getWrappedElement()).thenReturn(element2);

        List<MatOverlayContainer> containers = newArrayList(overlayContainer1, overlayContainer2);
        when(driver.findComponentsAs(any(), any(Function.class))).thenReturn(containers);

        List<MatOverlayContainer> result = testSubject.findVisibleContainers();
        assertEquals(1, result.size());
        assertEquals(overlayContainer1, result.get(0));
    }

    @Test
    @SuppressWarnings("unchecked")
    void findTopVisibleContainer() {
        MatOverlayContainer overlayContainer1 = mock(MatOverlayContainer.class);
        when(overlayContainer1.isDisplayed()).thenReturn(true);

        MatOverlayContainer overlayContainer2 = mock(MatOverlayContainer.class);
        when(overlayContainer2.isDisplayed()).thenReturn(true);

        MatOverlayContainer overlayContainer3 = mock(MatOverlayContainer.class);

        List<MatOverlayContainer> containers = newArrayList(overlayContainer1, overlayContainer2, overlayContainer3);
        when(driver.findComponentsAs(any(), any(Function.class))).thenReturn(containers);

        MatOverlayContainer result = testSubject.findTopVisibleContainer();
        assertEquals(overlayContainer2, result);
    }

    @Test
    @SuppressWarnings("unchecked")
    void findTopVisibleContainerNull() {
        MatOverlayContainer overlayContainer1 = mock(MatOverlayContainer.class);
        MatOverlayContainer overlayContainer2 = mock(MatOverlayContainer.class);
        MatOverlayContainer overlayContainer3 = mock(MatOverlayContainer.class);

        List<MatOverlayContainer> containers = newArrayList(overlayContainer1, overlayContainer2, overlayContainer3);
        when(driver.findComponentsAs(any(), any(Function.class))).thenReturn(containers);

        MatOverlayContainer result = testSubject.findTopVisibleContainer();
        assertNull(result);
    }

    @Test
    void testEquals() {
        SimpleEqualsTester tester = new SimpleEqualsTester();

        ComponentWebDriver driver1 = mock(ComponentWebDriver.class);
        ComponentWebDriver driver2 = mock(ComponentWebDriver.class);
        MatConfig config1 = mock(MatConfig.class);
        MatConfig config2 = mock(MatConfig.class);
        tester.addEqualityGroup(new MatOverlayFinder(driver1, config1), new MatOverlayFinder(driver1, config1));
        tester.addEqualityGroup(new MatOverlayFinder(driver1, config2));
        tester.addEqualityGroup(new MatOverlayFinder(driver2, config1));
        tester.addEqualityGroup(new MatOverlayFinder(driver2, config2));
        tester.testEquals();
    }

    @Test
    void testToString() {
        when(driver.toString()).thenReturn("driver");
        when(config.toString()).thenReturn("config");
        assertEquals("MatOverlayFinder{driver=driver, config=config}", testSubject.toString());
    }
}
