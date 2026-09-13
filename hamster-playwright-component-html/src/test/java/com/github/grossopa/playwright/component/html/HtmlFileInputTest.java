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
package com.github.grossopa.playwright.component.html;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link HtmlFileInput}
 *
 * @author Jack Yin
 * @since 1.15
 */
class HtmlFileInputTest {

    HtmlFileInput testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);

    @BeforeEach
    void setUp() {
        testSubject = new HtmlFileInput(locator, driver);
    }

    @Test
    void getComponentTagName() {
        assertEquals("input", testSubject.getComponentTagName());
    }

    @Test
    void validateTrue() {
        when(locator.evaluate("el => el.tagName")).thenReturn("INPUT");
        when(locator.evaluate("el => el.type")).thenReturn("file");
        assertTrue(testSubject.validate());
    }

    @Test
    void validateFalseWrongTag() {
        when(locator.evaluate("el => el.tagName")).thenReturn("div");
        when(locator.evaluate("el => el.type")).thenReturn("file");
        assertFalse(testSubject.validate());
    }

    @Test
    void validateFalseWrongType() {
        when(locator.evaluate("el => el.tagName")).thenReturn("INPUT");
        when(locator.evaluate("el => el.type")).thenReturn("text");
        assertFalse(testSubject.validate());
    }

    @Test
    void setFilesByPath() {
        Path file1 = Path.of("/tmp/test1.txt");
        Path file2 = Path.of("/tmp/test2.txt");
        testSubject.setFiles(file1, file2);
        verify(locator).setInputFiles(new Path[]{file1, file2});
    }

    @Test
    void setFilesByString() {
        testSubject.setFiles("/tmp/test1.txt", "/tmp/test2.txt");
        verify(locator).setInputFiles(new Path[]{Path.of("/tmp/test1.txt"), Path.of("/tmp/test2.txt")});
    }

    @Test
    void getFileNames() {
        when(locator.evaluate("el => Array.from(el.files).map(f => f.name)"))
                .thenReturn(Arrays.asList("file1.txt", "file2.pdf"));

        List<String> fileNames = testSubject.getFileNames();
        assertEquals(2, fileNames.size());
        assertEquals("file1.txt", fileNames.get(0));
        assertEquals("file2.pdf", fileNames.get(1));
    }

    @Test
    void getFileNamesEmpty() {
        when(locator.evaluate("el => Array.from(el.files).map(f => f.name)"))
                .thenReturn("not-a-list");

        List<String> fileNames = testSubject.getFileNames();
        assertTrue(fileNames.isEmpty());
    }

    @Test
    void getAccept() {
        when(locator.getAttribute("accept")).thenReturn(".pdf,.doc");
        assertEquals(".pdf,.doc", testSubject.getAccept());
    }

    @Test
    void isMultipleTrue() {
        when(locator.getAttribute("multiple")).thenReturn("true");
        assertTrue(testSubject.isMultiple());
    }

    @Test
    void isMultipleFalse() {
        when(locator.getAttribute("multiple")).thenReturn(null);
        assertFalse(testSubject.isMultiple());
    }

    @Test
    void clearFiles() {
        testSubject.clearFiles();
        verify(locator).setInputFiles(new java.nio.file.Path[0]);
    }
}
