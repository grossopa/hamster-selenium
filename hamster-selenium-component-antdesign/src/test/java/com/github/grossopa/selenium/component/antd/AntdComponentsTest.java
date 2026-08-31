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
package com.github.grossopa.selenium.component.antd;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.component.antd.dataentry.*;
import com.github.grossopa.selenium.component.antd.datadisplay.*;
import com.github.grossopa.selenium.component.antd.feedback.*;
import com.github.grossopa.selenium.component.antd.general.AntdButton;
import com.github.grossopa.selenium.component.antd.layout.*;
import com.github.grossopa.selenium.component.antd.navigation.*;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AntdComponents}
 *
 * @author Jack Yin
 * @since 1.4
 */
class AntdComponentsTest {

    AntdComponents testSubject;
    AntdConfig config = mock(AntdConfig.class);
    WebComponent component = mock(WebComponent.class);
    RemoteWebElement element = mock(RemoteWebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);

    @BeforeEach
    void setUp() {
        when(component.getWrappedElement()).thenReturn(element);
        when(element.getWrappedDriver()).thenReturn(driver);

        testSubject = new AntdComponents();
        testSubject.setContext(component, driver);
    }

    @Test
    void antd() {
        assertNotNull(AntdComponents.antd());
    }

    @Test
    void antdWithConfig() {
        assertSame(config, AntdComponents.antd(config).config);
    }

    @Test
    void toButton() {
        AntdButton button = testSubject.toButton();
        assertEquals(element, button.getWrappedElement());
        assertEquals(driver, button.getWrappedDriver());
    }

    @Test
    void toLayoutComponents() {
        assertNotNull(testSubject.toDivider());
        assertInstanceOf(AntdDivider.class, testSubject.toDivider());
        assertNotNull(testSubject.toSpace());
        assertInstanceOf(AntdSpace.class, testSubject.toSpace());
    }

    @Test
    void toNavigationComponents() {
        assertNotNull(testSubject.toBreadcrumb());
        assertInstanceOf(AntdBreadcrumb.class, testSubject.toBreadcrumb());
        assertNotNull(testSubject.toMenu());
        assertInstanceOf(AntdMenu.class, testSubject.toMenu());
        assertNotNull(testSubject.toPagination());
        assertInstanceOf(AntdPagination.class, testSubject.toPagination());
        assertNotNull(testSubject.toSteps());
        assertInstanceOf(AntdSteps.class, testSubject.toSteps());
    }

    @Test
    void toDataEntryComponents() {
        assertNotNull(testSubject.toCheckbox());
        assertInstanceOf(AntdCheckbox.class, testSubject.toCheckbox());
        assertNotNull(testSubject.toRadio());
        assertInstanceOf(AntdRadio.class, testSubject.toRadio());
        assertNotNull(testSubject.toSwitch());
        assertInstanceOf(AntdSwitch.class, testSubject.toSwitch());
        assertNotNull(testSubject.toInput());
        assertInstanceOf(AntdInput.class, testSubject.toInput());
        assertNotNull(testSubject.toSelect());
        assertInstanceOf(AntdSelect.class, testSubject.toSelect());
    }

    @Test
    void toDataDisplayComponents() {
        assertNotNull(testSubject.toAvatar());
        assertInstanceOf(AntdAvatar.class, testSubject.toAvatar());
        assertNotNull(testSubject.toBadge());
        assertInstanceOf(AntdBadge.class, testSubject.toBadge());
        assertNotNull(testSubject.toCard());
        assertInstanceOf(AntdCard.class, testSubject.toCard());
        assertNotNull(testSubject.toCollapse());
        assertInstanceOf(AntdCollapse.class, testSubject.toCollapse());
        assertNotNull(testSubject.toList());
        assertInstanceOf(AntdList.class, testSubject.toList());
        assertNotNull(testSubject.toTabs());
        assertInstanceOf(AntdTabs.class, testSubject.toTabs());
        assertNotNull(testSubject.toTag());
        assertInstanceOf(AntdTag.class, testSubject.toTag());
        assertNotNull(testSubject.toEmpty());
        assertInstanceOf(AntdEmpty.class, testSubject.toEmpty());
    }

    @Test
    void toFeedbackComponents() {
        assertNotNull(testSubject.toAlert());
        assertInstanceOf(AntdAlert.class, testSubject.toAlert());
        assertNotNull(testSubject.toModal());
        assertInstanceOf(AntdModal.class, testSubject.toModal());
        assertNotNull(testSubject.toProgress());
        assertInstanceOf(AntdProgress.class, testSubject.toProgress());
        assertNotNull(testSubject.toSkeleton());
        assertInstanceOf(AntdSkeleton.class, testSubject.toSkeleton());
        assertNotNull(testSubject.toSpin());
        assertInstanceOf(AntdSpin.class, testSubject.toSpin());
        assertNotNull(testSubject.toDrawer());
        assertInstanceOf(AntdDrawer.class, testSubject.toDrawer());
    }
}
