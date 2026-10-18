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
package com.github.grossopa.selenium.recorder.component;

import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link MatComponentDefinition}
 *
 * @author Jack Yin
 * @since 1.17
 */
class MatComponentDefinitionTest {

    MatComponentDefinition testSubject = new MatComponentDefinition("Button", "MatButton",
            "com.github.grossopa.hamster.selenium.component.mat.main.MatButton", "toButton", false,
            "mat-mdc-button-base");

    @Test
    void testGetters() {
        assertEquals("Button", testSubject.getComponentName());
        assertEquals("MatButton", testSubject.getTypeName());
        assertEquals("com.github.grossopa.hamster.selenium.component.mat.main.MatButton",
                testSubject.getTypeQualifiedName());
        assertEquals("toButton", testSubject.getFactoryMethodName());
        assertFalse(testSubject.isRequiresArgs());
        assertEquals("mat-mdc-button-base", testSubject.getRootCssClass());
    }

    @Test
    void testToDetectedComponent() {
        DetectedComponent detected = testSubject.toDetectedComponent();
        assertEquals(ComponentFramework.MAT, detected.getFramework());
        assertEquals("Button", detected.getComponentName());
        assertEquals("MatButton", detected.getTypeName());
        assertEquals("toButton", detected.getFactoryMethodName());
        assertFalse(detected.isRequiresArgs());
    }

    @Test
    void testEqualsHashCodeToString() {
        MatComponentDefinition same = new MatComponentDefinition("Button", "MatButton",
                "com.github.grossopa.hamster.selenium.component.mat.main.MatButton", "toButton", false,
                "mat-mdc-button-base");
        assertEquals(testSubject, testSubject);
        assertEquals(testSubject, same);
        assertEquals(testSubject.hashCode(), same.hashCode());
        assertNotEquals(testSubject, new MatComponentDefinition("Checkbox", "MatCheckbox",
                "com.github.grossopa.hamster.selenium.component.mat.main.MatCheckbox", "toCheckbox", false,
                "mat-mdc-checkbox"));
        assertNotEquals(testSubject, new Object());
        assertTrue(testSubject.toString().contains("Button"));
        assertTrue(testSubject.toString().contains("mat-mdc-button-base"));
    }

    @Test
    void testEqualsWithDifferentRootCss() {
        MatComponentDefinition different = new MatComponentDefinition("Button", "MatButton",
                "com.github.grossopa.hamster.selenium.component.mat.main.MatButton", "toButton", false,
                "mat-button");
        assertNotEquals(testSubject, different);
    }

    @Test
    void testRequiresArgs() {
        MatComponentDefinition withArgs = new MatComponentDefinition("Select", "MatSelect",
                "com.github.grossopa.hamster.selenium.component.mat.main.MatSelect", "toSelect", true,
                "mat-mdc-select");
        assertTrue(withArgs.isRequiresArgs());
    }
}
