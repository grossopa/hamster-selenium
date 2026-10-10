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
package com.github.grossopa.playwright.component.mat.main;

import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatTreeNodeTest {

    MatTreeNode testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MatConfig config = mock(MatConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getComponentCssPrefix()).thenReturn("mat-");
        when(config.getTagPrefix()).thenReturn("mat-");
        testSubject = new MatTreeNode(locator, driver, config);
    }

    @Test
    void getComponentName() {
        assertEquals("TreeNode", testSubject.getComponentName());
    }

    @Test
    void validate() {
        when(locator.getAttribute("class")).thenReturn("mat-tree-node");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-other");
        assertFalse(testSubject.validate());
    }

    @Test
    void testToString() {
        when(locator.toString()).thenReturn("inner-locator");
        assertEquals("MatTreeNode{locator=inner-locator}", testSubject.toString());
    }

    @Test
    void isExpandedTrue() {
        when(locator.getAttribute("class")).thenReturn("mat-tree-node mat-tree-node-expanded");
        assertTrue(testSubject.isExpanded());
    }

    @Test
    void isExpandedFalse() {
        when(locator.getAttribute("class")).thenReturn("mat-tree-node");
        assertFalse(testSubject.isExpanded());
    }

    @Test
    void getToggleButton() {
        Locator toggleLocator = mock(Locator.class);
        when(locator.locator(".mat-tree-node-toggle")).thenReturn(toggleLocator);
        when(toggleLocator.first()).thenReturn(toggleLocator);
        assertNotNull(testSubject.getToggleButton());
    }

    @Test
    void expand() {
        Locator toggleLocator = mock(Locator.class);
        when(locator.locator(".mat-tree-node-toggle")).thenReturn(toggleLocator);
        when(toggleLocator.first()).thenReturn(toggleLocator);
        when(locator.getAttribute("class")).thenReturn("mat-tree-node");
        testSubject.expand();
        verify(toggleLocator).click();
    }

    @Test
    void expandWhenAlreadyExpanded() {
        Locator toggleLocator = mock(Locator.class);
        when(locator.locator(".mat-tree-node-toggle")).thenReturn(toggleLocator);
        when(toggleLocator.first()).thenReturn(toggleLocator);
        when(locator.getAttribute("class")).thenReturn("mat-tree-node mat-tree-node-expanded");
        testSubject.expand();
        verify(toggleLocator, never()).click();
    }

    @Test
    void collapse() {
        Locator toggleLocator = mock(Locator.class);
        when(locator.locator(".mat-tree-node-toggle")).thenReturn(toggleLocator);
        when(toggleLocator.first()).thenReturn(toggleLocator);
        when(locator.getAttribute("class")).thenReturn("mat-tree-node mat-tree-node-expanded");
        testSubject.collapse();
        verify(toggleLocator).click();
    }

    @Test
    void collapseWhenAlreadyCollapsed() {
        Locator toggleLocator = mock(Locator.class);
        when(locator.locator(".mat-tree-node-toggle")).thenReturn(toggleLocator);
        when(toggleLocator.first()).thenReturn(toggleLocator);
        when(locator.getAttribute("class")).thenReturn("mat-tree-node");
        testSubject.collapse();
        verify(toggleLocator, never()).click();
    }

    @Test
    void getChildren() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator("mat-tree-node.mat-tree-node")).thenReturn(childLocator);
        when(childLocator.all()).thenReturn(java.util.List.of(childLocator));
        assertEquals(1, testSubject.getChildren().size());
    }

    @Test
    void getToggleButtonException() {
        when(locator.locator(".mat-tree-node-toggle")).thenThrow(new RuntimeException("not found"));
        assertNull(testSubject.getToggleButton());
    }

}
