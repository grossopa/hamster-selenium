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
package com.github.grossopa.selenium.component.antd.datadisplay;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link AntdCollapse}
 *
 * @author Jack Yin
 * @since 1.15
 */
class AntdCollapseTest {

    AntdCollapse testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    AntdConfig config = mock(AntdConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getPrefixCls()).thenReturn("sss");
        testSubject = new AntdCollapse(element, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("collapse", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(element.getDomAttribute("class")).thenReturn("sss-collapse");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateNegative() {
        when(element.getDomAttribute("class")).thenReturn("sss-ddd");
        assertFalse(testSubject.validate());
    }

    @Test
    void getPanels() {
        when(element.findElements(By.className("sss-collapse-item"))).thenReturn(List.of(mock(WebElement.class)));
        List<AntdCollapsePanel> panels = testSubject.getPanels();
        assertEquals(1, panels.size());
        assertInstanceOf(AntdCollapsePanel.class, panels.get(0));
    }

    @Test
    void getExpandedPanels() {
        WebElement expandedPanel = mock(WebElement.class);
        WebElement collapsedPanel = mock(WebElement.class);
        when(expandedPanel.getDomAttribute("class")).thenReturn("sss-collapse-item sss-collapse-item-active");
        when(collapsedPanel.getDomAttribute("class")).thenReturn("sss-collapse-item");
        when(element.findElements(By.className("sss-collapse-item"))).thenReturn(
                List.of(expandedPanel, collapsedPanel));
        List<AntdCollapsePanel> panels = testSubject.getExpandedPanels();
        assertEquals(1, panels.size());
        assertTrue(panels.get(0).isExpanded());
    }
}
