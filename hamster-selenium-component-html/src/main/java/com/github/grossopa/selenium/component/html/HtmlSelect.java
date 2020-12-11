/*
 * Copyright © 2020 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.selenium.component.html;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.DefaultWebComponent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * the simple HTML Select elements delegates selenium {@link Select}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class HtmlSelect extends DefaultWebComponent implements ISelect {

    private final ISelect selectComponent;

    /**
     * Constructs an instance with element and driver.
     *
     * @param element
     *         the web element to wrap with, should be with tag select.
     * @param driver
     *         the current web driver
     */
    public HtmlSelect(WebElement element, ComponentWebDriver driver) {
        super(element, driver);
        selectComponent = new Select(element);
    }

    @Override
    public boolean isMultiple() {
        return selectComponent.isMultiple();
    }

    @Override
    public List<WebElement> getOptions() {
        return selectComponent.getOptions();
    }

    @Override
    public List<WebElement> getAllSelectedOptions() {
        return selectComponent.getAllSelectedOptions();
    }

    @Override
    public WebElement getFirstSelectedOption() {
        return selectComponent.getFirstSelectedOption();
    }

    @Override
    public void selectByVisibleText(String text) {
        selectComponent.selectByVisibleText(text);
    }

    @Override
    public void selectByIndex(int index) {
        selectComponent.selectByIndex(index);
    }

    @Override
    public void selectByValue(String value) {
        selectComponent.selectByValue(value);
    }

    @Override
    public void deselectAll() {
        selectComponent.deselectAll();
    }

    @Override
    public void deselectByValue(String value) {
        selectComponent.deselectByValue(value);
    }

    @Override
    public void deselectByIndex(int index) {
        selectComponent.deselectByIndex(index);
    }

    @Override
    public void deselectByVisibleText(String text) {
        selectComponent.deselectByVisibleText(text);
    }
}
