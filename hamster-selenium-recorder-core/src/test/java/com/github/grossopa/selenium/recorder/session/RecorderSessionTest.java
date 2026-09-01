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
package com.github.grossopa.selenium.recorder.session;

import com.github.grossopa.selenium.recorder.codegen.PageObjectGenerator;
import com.github.grossopa.selenium.recorder.component.ComponentDetector;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.model.PageElementModel;
import com.github.grossopa.selenium.recorder.model.PageModel;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.monitor.RecorderEvent;
import com.github.grossopa.selenium.recorder.monitor.RecorderEventType;
import com.github.grossopa.selenium.recorder.page.PageIdentificationStrategy;
import com.github.grossopa.selenium.recorder.scan.ElementScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

/**
 * Tests for {@link RecorderSession}
 *
 * @author Jack Yin
 * @since 1.15
 */
class RecorderSessionTest {

    WebDriver rawDriver = mock(WebDriver.class, withSettings().extraInterfaces(JavascriptExecutor.class));
    ElementScanner scanner = mock(ElementScanner.class);
    ComponentDetector detector = mock(ComponentDetector.class);
    PageObjectGenerator generator = mock(PageObjectGenerator.class);
    AtomicReference<String> currentUrl = new AtomicReference<>("http://localhost/user/login");
    RecorderConfig config = RecorderConfig.builder().build();

    DetectedComponent detected = new DetectedComponent(ComponentFramework.MUI, "TextField", "MuiTextField",
            "com.github.grossopa.selenium.component.mui.v4.inputs.MuiTextField", "toTextField", false);

    LocatorCandidate candidate = new LocatorCandidate(LocatorType.ID, "username", LocatorCandidate.PRIORITY_ID,
            "by id \"username\"");

    RecorderSession testSubject;

    @BeforeEach
    void setUp() {
        when(rawDriver.getCurrentUrl()).thenAnswer(invocation -> currentUrl.get());
        testSubject = new RecorderSession(rawDriver, config, scanner, List.of(detector), generator);
    }

    @Test
    void testConstructorClassifiesCurrentUrl() {
        assertNotNull(testSubject.getDriver());
        assertEquals(config, testSubject.getConfig());
        assertEquals(1, testSubject.getPages().size());
        assertEquals("UserLogin", testSubject.getCurrentPage().getName());
        assertEquals("/user/login", testSubject.getCurrentPage().getPageKey());
    }

    @Test
    void testScanDetectsComponents() {
        ScannedElement scanned = new ScannedElement(0, "input", Map.of("id", "username"), "", List.of(candidate));
        when(scanner.scan(any())).thenReturn(List.of(scanned));
        when(rawDriver.findElement(any(By.class))).thenReturn(mock(WebElement.class));
        when(detector.detect(any(), any())).thenReturn(Optional.of(detected));

        List<ScannedElement> result = testSubject.scan();
        assertEquals(1, result.size());
        assertEquals(detected, result.get(0).getDetectedComponent());
        assertEquals(result, testSubject.getScannedElements());
    }

    @Test
    void testSelect() {
        stubScan();
        testSubject.scan();
        PageElementModel selected = testSubject.select(0, "username");
        assertEquals("username", selected.getFieldName());
        assertEquals(candidate, selected.getLocator());
        assertTrue(testSubject.getCurrentPage().hasField("username"));
    }

    @Test
    void testSelectWithDuplicateFieldName() {
        stubScan();
        testSubject.scan();
        testSubject.select(0, "username");
        assertThrows(IllegalArgumentException.class, () -> testSubject.select(0, "username"));
    }

    @Test
    void testSelectWithInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> testSubject.select(9, "username"));
    }

    @Test
    void testSelectWithInvalidFieldName() {
        stubScan();
        testSubject.scan();
        assertThrows(IllegalArgumentException.class, () -> testSubject.select(0, "9bad"));
        assertThrows(IllegalArgumentException.class, () -> testSubject.select(0, "bad-name"));
    }

    @Test
    void testNewPageAndUsePage() {
        PageModel custom = testSubject.newPage("Custom");
        assertEquals("Custom", custom.getName());
        // the page key is derived from the current url via the strategy
        assertEquals("/user/login", custom.getPageKey());
        assertEquals(custom, testSubject.getCurrentPage());
        assertEquals(2, testSubject.getPages().size());

        PageModel original = testSubject.usePage("UserLogin");
        assertEquals(original, testSubject.getCurrentPage());
        assertThrows(IllegalArgumentException.class, () -> testSubject.usePage("unknown"));
    }

    @Test
    void testSetPageStrategy() {
        PageIdentificationStrategy strategy = mock(PageIdentificationStrategy.class);
        testSubject.setPageStrategy(strategy);
        assertEquals(strategy, config.getPageStrategy());
    }

    @Test
    void testGenerateDelegates() {
        List<Path> files = List.of(Path.of("a.java"));
        when(generator.generate(any(), any())).thenReturn(files);
        assertEquals(files, testSubject.generate());
        verify(generator).generate(testSubject.getPages(), config);
    }

    @Test
    void testPageChangedOnNavigation() {
        List<RecorderEvent> events = new ArrayList<>();
        testSubject.addEventListener(events::add);

        currentUrl.set("http://localhost/order/list");
        testSubject.getDriver().get("http://localhost/order/list");

        assertEquals(2, testSubject.getPages().size());
        assertEquals("OrderList", testSubject.getCurrentPage().getName());
        assertTrue(events.stream().anyMatch(event -> event.getType() == RecorderEventType.NAVIGATION));
        assertTrue(events.stream().anyMatch(event -> event.getType() == RecorderEventType.PAGE_CHANGED));
    }

    @Test
    void testClose() {
        testSubject.close();
        verify(rawDriver).quit();
    }

    private void stubScan() {
        ScannedElement scanned = new ScannedElement(0, "input", Map.of("id", "username"), "", List.of(candidate));
        when(scanner.scan(any())).thenReturn(List.of(scanned));
        when(rawDriver.findElement(any(By.class))).thenReturn(mock(WebElement.class));
        when(detector.detect(any(), any())).thenReturn(Optional.of(detected));
    }
}
