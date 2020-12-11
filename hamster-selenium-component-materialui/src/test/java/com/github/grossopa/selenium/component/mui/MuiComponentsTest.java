/*
 * Copyright © 2020 the original author or authors.
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

package com.github.grossopa.selenium.component.mui;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiComponents}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiComponentsTest {

    MuiComponents testSubject;
    WebElement element = mock(WebElement.class);
    WebComponent component = mock(WebComponent.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(component.getWrappedElement()).thenReturn(element);
        testSubject = new MuiComponents(config);
        testSubject.setContext(component, driver);
    }


    @Test
    void mui() {
        assertNotNull(MuiComponents.mui().getConfig());
    }

    @Test
    void muiWithConfig() {
        MuiConfig config = mock(MuiConfig.class);
        assertEquals(config, MuiComponents.mui(config).getConfig());
    }

    @Test
    void toButton() {
        assertEquals(element, testSubject.toButton().getWrappedElement());
    }

    @Test
    void toButtonGroup() {
        assertEquals(element, testSubject.toButtonGroup().getWrappedElement());
    }

    @Test
    void toCheckbox() {
        assertEquals(element, testSubject.toCheckbox().getWrappedElement());
    }

    @Test
    void toSelect() {
        assertEquals(element, testSubject.toSelect(By.id("abc")).getWrappedElement());
    }

    @Test
    void testToSelect() {
        assertEquals(element, testSubject.toSelect(By.id("abc"), "attribute-value-name").getWrappedElement());
    }

    @Test
    void toSlider() {
        assertEquals(element, testSubject.toSlider().getWrappedElement());
    }

    @Test
    void toSliderWithInverseScaleFunction() {
        UnaryOperator<Double> inverseScaleFunction = x -> x * 3d;
        assertEquals(inverseScaleFunction, testSubject.toSlider(inverseScaleFunction).getInverseScaleFunction());
    }

    @Test
    void toSwitch() {
        assertEquals(element, testSubject.toSwitch().getWrappedElement());
    }

    @Test
    void toTextField() {
        assertEquals(element, testSubject.toTextField().getWrappedElement());
    }

    @Test
    void toRadio() {
        assertEquals(element, testSubject.toRadio().getWrappedElement());
    }

    @Test
    void toRadioGroup() {
        assertEquals(element, testSubject.toRadioGroup().getWrappedElement());
    }

    @Test
    void toLink() {
        assertEquals(element, testSubject.toLink().getWrappedElement());
    }

    @Test
    void toBreadcrumbs() {
        assertEquals(element, testSubject.toBreadcrumbs().getWrappedElement());
    }

    @Test
    void toBottomNavigation() {
        assertEquals(element, testSubject.toBottomNavigation().getWrappedElement());
    }

    @Test
    void toTabs() {
        assertEquals(element, testSubject.toTabs().getWrappedElement());
    }

    @Test
    void toAvatar() {
        assertEquals(element, testSubject.toAvatar().getWrappedElement());
    }

    @Test
    void toBadge() {
        assertEquals(element, testSubject.toBadge().getWrappedElement());
    }

    @Test
    void toChip() {
        assertEquals(element, testSubject.toChip().getWrappedElement());
    }

    @Test
    void toDivider() {
        assertEquals(element, testSubject.toDivider().getWrappedElement());
    }

    @Test
    void toList() {
        assertEquals(element, testSubject.toList().getWrappedElement());
    }

    @Test
    void toListItem() {
        assertEquals(element, testSubject.toListItem().getWrappedElement());
    }
}
