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
package com.github.grossopa.selenium.examples.recorder;

import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.PageModel;
import com.github.grossopa.selenium.recorder.page.PageIdentification;
import com.github.grossopa.selenium.recorder.page.PageIdentificationStrategy;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;
import java.util.List;

/**
 * The example of using a custom {@link PageIdentificationStrategy}. The custom strategy groups all pages of the
 * sample application into one single page so that the elements collected on both the login page and the dashboard
 * page are merged into one generated page object.
 *
 * <p>The browser driver executable is resolved automatically by Selenium Manager. Run this class as a normal Java
 * application.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see PageIdentificationStrategy
 */
public class RecorderCustomStrategyShowCase {

    /**
     * Runs the example.
     *
     * @param args the command line arguments, not used
     */
    public static void main(String[] args) {
        String loginUrl = RecorderExampleSupport.writeSamplePages();
        String dashboardUrl = loginUrl.replace("login.html", "dashboard.html");
        RecorderConfig config = RecorderConfig.builder()
                .framework(ComponentFramework.of("html"))
                .extraSelector("table")
                .outputDir(Path.of("target", "recorder-generated"))
                .basePackage("com.example.pageobjects")
                .build();
        WebDriver driver = RecorderExampleSupport.createDriver();
        try (RecorderSession session = new RecorderSession(driver, config)) {
            session.setPageStrategy(new SinglePageStrategy());

            session.getDriver().get(loginUrl);
            session.scan();
            session.select(0, "username");
            session.select(3, "loginButton");

            // navigating to another page still classifies into the same page due to the custom strategy
            session.getDriver().get(dashboardUrl);
            session.scan();
            session.select(0, "searchBox");
            session.select(2, "userTable");

            session.getPages().forEach(page -> System.out.println(
                    "Page " + page.getName() + " collected " + page.getElements().size() + " elements"));
            RecorderExampleSupport.printGeneratedFiles(session.generate());
        }
    }

    /**
     * The custom strategy that groups all urls of the sample application into one single page.
     *
     * @author Jack Yin
     * @since 1.15
     */
    static class SinglePageStrategy implements PageIdentificationStrategy {

        private static final String PAGE_KEY = "sample-app";
        private static final String PAGE_NAME = "SampleApplication";

        @Override
        public PageIdentification identify(String currentUrl, List<PageModel> existingPages) {
            return existingPages.stream().filter(page -> PAGE_KEY.equals(page.getPageKey())).findFirst()
                    .map(PageIdentification::matched)
                    .orElseGet(() -> PageIdentification.newPage(PAGE_KEY, PAGE_NAME));
        }
    }
}
