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
package com.github.grossopa.selenium.recorder.codegen;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorType;
import com.github.grossopa.selenium.recorder.model.PageElementModel;
import com.github.grossopa.selenium.recorder.model.PageModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link HamsterPageObjectGenerator}
 *
 * @author Jack Yin
 * @since 1.15
 */
class HamsterPageObjectGeneratorTest {

    @TempDir
    Path tempDir;

    HamsterPageObjectGenerator testSubject = new HamsterPageObjectGenerator();

    DetectedComponent muiTextField = new DetectedComponent(ComponentFramework.MUI, "TextField", "MuiTextField",
            "com.github.grossopa.selenium.component.mui.v4.inputs.MuiTextField", "toTextField", false);

    DetectedComponent muiSelect = new DetectedComponent(ComponentFramework.MUI, "Select", "MuiSelect",
            "com.github.grossopa.selenium.component.mui.v4.inputs.MuiSelect", "toSelect", true);

    DetectedComponent htmlSelect = new DetectedComponent(ComponentFramework.HTML, "Select", "HtmlSelect",
            "com.github.grossopa.selenium.component.html.HtmlSelect", "toSelect", false);

    private RecorderConfig config(ComponentFramework framework, MuiVersion muiVersion) {
        return RecorderConfig.builder().framework(framework).muiVersion(muiVersion).outputDir(tempDir)
                .basePackage("com.example.po").build();
    }

    private PageModel page() {
        PageModel page = new PageModel("User Login", "/user/login");
        page.addElement(new PageElementModel("username",
                new LocatorCandidate(LocatorType.ID, "username", LocatorCandidate.PRIORITY_ID, "by id \"username\""),
                muiTextField));
        page.addElement(new PageElementModel("banner",
                new LocatorCandidate(LocatorType.CSS_SELECTOR, "[data-testid=\"banner\"]",
                        LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE, "by attribute data-testid"),
                null));
        return page;
    }

    private String generateAndRead(RecorderConfig config, PageModel page) throws IOException {
        List<Path> files = testSubject.generate(List.of(page), config);
        assertEquals(1, files.size());
        assertEquals(tempDir.resolve("com/example/po/UserLoginPage.java"), files.get(0));
        return Files.readString(files.get(0));
    }

    @Test
    void testGenerateMuiV4Page() throws IOException {
        String code = generateAndRead(config(ComponentFramework.MUI, MuiVersion.V4), page());
        assertTrue(code.contains("package com.example.po;"));
        assertTrue(code.contains("public class UserLoginPage"));
        assertTrue(code.contains("this.components = MuiComponents.mui();"));
        assertTrue(code.contains("public MuiTextField username()"));
        assertTrue(code.contains("driver.findComponent(By.id(\"username\")).as(components).toTextField()"));
        assertTrue(code.contains("public WebComponent banner()"));
        assertTrue(code.contains("driver.findComponent(By.cssSelector(\"[data-testid=\\\"banner\\\"]\"))"));
        assertTrue(code.contains("import org.openqa.selenium.By;"));
    }

    @Test
    void testGenerateMuiV5Page() throws IOException {
        String code = generateAndRead(config(ComponentFramework.MUI, MuiVersion.V5), page());
        assertTrue(code.contains("this.components = MuiComponents.muiV5();"));
    }

    @Test
    void testGenerateHtmlPage() throws IOException {
        PageModel page = new PageModel("User Login", "/user/login");
        page.addElement(new PageElementModel("country",
                new LocatorCandidate(LocatorType.NAME, "country", LocatorCandidate.PRIORITY_NAME,
                        "by name \"country\""), htmlSelect));
        String code = generateAndRead(config(ComponentFramework.HTML, MuiVersion.V4), page);
        assertTrue(code.contains("this.components = HtmlComponents.html();"));
        assertTrue(code.contains("public HtmlSelect country()"));
        assertTrue(code.contains("driver.findComponent(By.name(\"country\")).as(components).toSelect()"));
    }

    @Test
    void testGenerateWithXpathLocator() throws IOException {
        PageModel page = new PageModel("User Login", "/user/login");
        page.addElement(new PageElementModel("comment",
                new LocatorCandidate(LocatorType.XPATH, "//button[normalize-space()='Comment']",
                        LocatorCandidate.PRIORITY_TEXT, "by text \"Comment\""), null));
        String code = generateAndRead(config(ComponentFramework.MUI, MuiVersion.V4), page);
        assertTrue(code.contains("driver.findComponent(By.xpath(\"//button[normalize-space()='Comment']\"))"));
    }

    @Test
    void testGenerateSkipsEmptyPages() {
        PageModel emptyPage = new PageModel("Home", "/");
        List<Path> files = testSubject.generate(List.of(emptyPage, page()),
                config(ComponentFramework.MUI, MuiVersion.V4));
        assertEquals(1, files.size());
        assertEquals(tempDir.resolve("com/example/po/UserLoginPage.java"), files.get(0));
    }

    @Test
    void testGenerateFallsBackToWebComponentWhenFactoryRequiresArgs() throws IOException {
        PageModel page = new PageModel("User Login", "/user/login");
        page.addElement(new PageElementModel("select",
                new LocatorCandidate(LocatorType.ID, "sel", LocatorCandidate.PRIORITY_ID, "by id \"sel\""),
                muiSelect));
        String code = generateAndRead(config(ComponentFramework.MUI, MuiVersion.V4), page);
        assertTrue(code.contains("public WebComponent select()"));
        assertTrue(code.contains("driver.findComponent(By.id(\"sel\"))"));
    }
}
