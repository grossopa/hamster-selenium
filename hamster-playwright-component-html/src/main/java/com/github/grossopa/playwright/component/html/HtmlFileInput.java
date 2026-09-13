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
import com.github.grossopa.playwright.core.DefaultWebComponent;
import com.microsoft.playwright.Locator;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * The HTML file input component wrapping {@code <input type="file">}.
 *
 * <p>
 * Provides semantic operations for file upload elements including setting files, querying current
 * file names, and validating the element type.
 * </p>
 *
 * <p>Example usage:
 * <pre>{@code
 * HtmlFileInput fileInput = driver.findComponentAs("#upload", HtmlComponents.html(driver)::fileInput);
 * fileInput.setFiles(Path.of("/path/to/document.pdf"));
 * List<String> files = fileInput.getFileNames();
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.15
 */
public class HtmlFileInput extends DefaultWebComponent {

    /**
     * Constructs an instance with the given locator and driver.
     *
     * @param locator the locator pointing to the file input element
     * @param driver the component driver
     */
    public HtmlFileInput(Locator locator, ComponentDriver driver) {
        super(locator, driver);
    }

    @Override
    public String getComponentTagName() {
        return "input";
    }

    /**
     * Validates that the current locator points to a {@code <input type="file">} element.
     *
     * @return true if the element is an input with type "file"
     */
    public boolean validate() {
        return "input".equalsIgnoreCase(locator.evaluate("el => el.tagName").toString())
                && "file".equalsIgnoreCase(locator.evaluate("el => el.type").toString());
    }

    /**
     * Sets the file(s) to be uploaded.
     *
     * @param files the file paths to upload
     */
    public void setFiles(Path... files) {
        locator.setInputFiles(files);
    }

    /**
     * Sets the file(s) to be uploaded using string paths.
     *
     * @param files the file path strings to upload
     */
    public void setFiles(String... files) {
        Path[] paths = Arrays.stream(files).map(Path::of).toArray(Path[]::new);
        locator.setInputFiles(paths);
    }

    /**
     * Gets the file names currently selected in this file input.
     *
     * @return list of file names
     */
    @SuppressWarnings("unchecked")
    public List<String> getFileNames() {
        Object result = locator.evaluate("el => Array.from(el.files).map(f => f.name)");
        return result instanceof List ? (List<String>) result : List.of();
    }

    /**
     * Gets the accept attribute value of this file input.
     *
     * @return the accept attribute value, or null if not set
     */
    public String getAccept() {
        return locator.getAttribute("accept");
    }

    /**
     * Checks whether this file input allows multiple file selection.
     *
     * @return true if multiple file selection is supported
     */
    public boolean isMultiple() {
        return locator.getAttribute("multiple") != null;
    }

    /**
     * Clears all selected files.
     */
    public void clearFiles() {
        locator.setInputFiles(new Path[0]);
    }
}
