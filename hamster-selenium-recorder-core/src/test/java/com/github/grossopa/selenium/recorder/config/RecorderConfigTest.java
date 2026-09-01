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
package com.github.grossopa.selenium.recorder.config;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.recorder.page.ContextPathPageStrategy;
import com.github.grossopa.selenium.recorder.page.PageIdentificationStrategy;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link RecorderConfig}
 *
 * @author Jack Yin
 * @since 1.15
 */
class RecorderConfigTest {

    @Test
    void testDefaultValues() {
        RecorderConfig testSubject = RecorderConfig.builder().build();
        assertEquals(RecorderConfig.DEFAULT_KEY_ATTRIBUTES, testSubject.getKeyAttributes());
        assertTrue(testSubject.getExtraSelectors().isEmpty());
        assertEquals(ComponentFramework.MUI, testSubject.getFramework());
        assertEquals(MuiVersion.V4, testSubject.getMuiVersion());
        assertEquals(Path.of("generated-pageobjects"), testSubject.getOutputDir());
        assertEquals(RecorderConfig.DEFAULT_BASE_PACKAGE, testSubject.getBasePackage());
        assertTrue(testSubject.getPageStrategy() instanceof ContextPathPageStrategy);
    }

    @Test
    void testCustomizedValues() {
        PageIdentificationStrategy strategy = mock(PageIdentificationStrategy.class);
        RecorderConfig testSubject = RecorderConfig.builder().keyAttribute("data-testid").keyAttribute("data-testid")
                .extraSelector("[data-clickable]").framework(ComponentFramework.HTML).muiVersion(MuiVersion.V5)
                .outputDir(Path.of("out")).basePackage("com.example.po").pageStrategy(strategy).build();
        assertEquals(3, testSubject.getKeyAttributes().size());
        assertTrue(testSubject.getKeyAttributes().contains("data-testid"));
        assertEquals(1, testSubject.getExtraSelectors().size());
        assertEquals(ComponentFramework.HTML, testSubject.getFramework());
        assertEquals(MuiVersion.V5, testSubject.getMuiVersion());
        assertEquals(Path.of("out"), testSubject.getOutputDir());
        assertEquals("com.example.po", testSubject.getBasePackage());
        assertEquals(strategy, testSubject.getPageStrategy());
    }

    @Test
    void testSetPageStrategy() {
        RecorderConfig testSubject = RecorderConfig.builder().build();
        PageIdentificationStrategy strategy = mock(PageIdentificationStrategy.class);
        testSubject.setPageStrategy(strategy);
        assertEquals(strategy, testSubject.getPageStrategy());
    }
}
