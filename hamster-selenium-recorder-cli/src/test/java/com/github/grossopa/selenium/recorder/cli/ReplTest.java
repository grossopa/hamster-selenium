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
package com.github.grossopa.selenium.recorder.cli;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.model.PageElementModel;
import com.github.grossopa.selenium.recorder.model.PageModel;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.page.ContextPathPageStrategy;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link Repl}
 *
 * @author Jack Yin
 * @since 1.15
 */
class ReplTest {

    RecorderSession session = mock(RecorderSession.class);
    LineReader lineReader = mock(LineReader.class);
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);

    PageModel page = new PageModel("UserLogin", "/user/login");

    LocatorCandidate locator = new LocatorCandidate(LocatorType.ID, "username", LocatorCandidate.PRIORITY_ID,
            "by id \"username\"");

    Repl testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new Repl(session, lineReader, out);
        when(session.getCurrentPage()).thenReturn(page);
        when(session.getPages()).thenReturn(List.of(page));
    }

    private String output() {
        return buffer.toString(StandardCharsets.UTF_8);
    }

    @Test
    void testScan() {
        ScannedElement element = new ScannedElement(0, "button", Map.of("id", "login"), "Login", List.of(locator));
        element.setDetectedComponent(new DetectedComponent(ComponentFramework.MUI, "Button", "MuiButton",
                "com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton", "toButton", false));
        when(session.scan()).thenReturn(List.of(element));

        assertTrue(testSubject.execute("scan"));
        assertTrue(output().contains("Scanned 1 elements"));
        assertTrue(output().contains("MuiButton"));
        assertTrue(output().contains("by id \"username\""));
    }

    @Test
    void testSelect() {
        when(session.select(0, "username")).thenReturn(new PageElementModel("username", locator, null));
        assertTrue(testSubject.execute("select 0 username"));
        assertTrue(output().contains("Selected element 0 as 'username'"));
        assertTrue(output().contains("UserLogin"));
    }

    @Test
    void testSelectWithMissingArgs() {
        assertTrue(testSubject.execute("select 0"));
        assertTrue(output().contains("Usage: select <index> <fieldName>"));
    }

    @Test
    void testSelectErrorReported() {
        when(session.select(any(Integer.class), anyString())).thenThrow(new IllegalArgumentException("bad"));
        assertTrue(testSubject.execute("select 9 username"));
        assertTrue(output().contains("Error: bad"));
    }

    @Test
    void testPages() {
        assertTrue(testSubject.execute("pages"));
        assertTrue(output().contains("* UserLogin"));
        assertTrue(output().contains("key=/user/login"));
    }

    @Test
    void testPageNewAndUse() {
        when(session.newPage("Custom")).thenReturn(new PageModel("Custom", "/custom"));
        assertTrue(testSubject.execute("page new Custom"));
        assertTrue(output().contains("Created new page 'Custom'"));

        when(session.usePage("UserLogin")).thenReturn(page);
        assertTrue(testSubject.execute("page use UserLogin"));
        assertTrue(output().contains("Switched to page 'UserLogin'"));
    }

    @Test
    void testPageWithInvalidArgs() {
        assertTrue(testSubject.execute("page foo bar"));
        assertTrue(output().contains("Usage: page new <name> | page use <name>"));
    }

    @Test
    void testStrategy() {
        assertTrue(testSubject.execute("strategy " + ContextPathPageStrategy.class.getName()));
        verify(session).setPageStrategy(any(ContextPathPageStrategy.class));
        assertTrue(output().contains("strategy set to"));
    }

    @Test
    void testStrategyWithUnknownClass() {
        assertTrue(testSubject.execute("strategy com.example.DoesNotExist"));
        assertTrue(output().contains("failed to load strategy class"));
    }

    @Test
    void testStrategyNotImplementingInterface() {
        assertTrue(testSubject.execute("strategy java.lang.String"));
        assertTrue(output().contains("does not implement PageIdentificationStrategy"));
    }

    @Test
    void testStatus() {
        ComponentWebDriver driver = mock(ComponentWebDriver.class);
        when(driver.getCurrentUrl()).thenReturn("http://localhost/user/login");
        when(session.getDriver()).thenReturn(driver);
        when(session.getScannedElements()).thenReturn(List.of());

        assertTrue(testSubject.execute("status"));
        assertTrue(output().contains("http://localhost/user/login"));
        assertTrue(output().contains("UserLogin"));
    }

    @Test
    void testGenerate() {
        when(session.generate()).thenReturn(List.of(Path.of("out/UserLoginPage.java")));
        assertTrue(testSubject.execute("generate"));
        assertTrue(output().contains("Generated 1 page object(s)"));
        assertTrue(output().contains("UserLoginPage.java"));
    }

    @Test
    void testHelpAndUnknownCommand() {
        assertTrue(testSubject.execute("help"));
        assertTrue(output().contains("Commands:"));
        assertTrue(testSubject.execute("foo"));
        assertTrue(output().contains("Unknown command: foo"));
    }

    @Test
    void testQuitAndBlankLine() {
        assertFalse(testSubject.execute("quit"));
        assertFalse(testSubject.execute("exit"));
        assertTrue(testSubject.execute(""));
        assertTrue(testSubject.execute("   "));
    }

    @Test
    void testRunStopsOnQuit() {
        when(lineReader.readLine(anyString())).thenReturn("help", "quit");
        testSubject.run();
        assertTrue(output().contains("Hamster Selenium Recorder"));
        assertTrue(output().contains("Commands:"));
    }

    @Test
    void testRunStopsOnEndOfFile() {
        when(lineReader.readLine(anyString())).thenThrow(new EndOfFileException("eof"));
        testSubject.run();
        assertTrue(output().contains("Hamster Selenium Recorder"));
        verify(lineReader).readLine(anyString());
    }
}
