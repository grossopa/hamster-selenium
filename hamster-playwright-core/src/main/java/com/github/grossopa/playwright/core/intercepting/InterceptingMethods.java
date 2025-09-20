/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.core.intercepting;

import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Path;

/**
 * Defines all intercepting method names for logging and monitoring purpose.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class InterceptingMethods {

    /**
     * private constructor
     */
    private InterceptingMethods() {
        throw new AssertionError();
    }

    /**
     * Represents the {@link ComponentDriver#findComponents(String)}
     */
    public static final String DRIVER_FIND_COMPONENTS = "driver.findComponents";

    /**
     * Represents the {@link ComponentDriver#findComponentAs(String, java.util.function.Function)}
     */
    public static final String DRIVER_FIND_COMPONENT_AS = "driver.findComponentAs";

    /**
     * Represents the {@link ComponentDriver#findComponentsAs(String, java.util.function.Function)}
     */
    public static final String DRIVER_FIND_COMPONENTS_AS = "driver.findComponentsAs";

    /**
     * Represents the {@link ComponentDriver#findComponent(String)}
     */
    public static final String DRIVER_FIND_COMPONENT = "driver.findComponent";

    /**
     * Represents the {@link ComponentDriver#navigate(String)}
     */
    public static final String DRIVER_NAVIGATE = "driver.navigate";

    /**
     * Represents the {@link WebComponent#findComponents(String)}
     */
    public static final String COMPONENT_FIND_COMPONENTS = "component.findComponents";

    /**
     * Represents the {@link WebComponent#findComponent(String)}
     */
    public static final String COMPONENT_FIND_COMPONENT = "component.findComponent";

    /**
     * Represents the {@link Locator#click()}
     */
    public static final String COMPONENT_CLICK = "component.click";

    /**
     * Represents the {@link Locator#hover()}
     */
    public static final String COMPONENT_HOVER = "component.hover";

    /**
     * Represents the {@link Locator#textContent()}
     */
    public static final String COMPONENT_TEXT_CONTENT = "component.textContent";

    /**
     * Represents the {@link Locator#innerText()}
     */
    public static final String COMPONENT_INNER_TEXT = "component.innerText";

    /**
     * Represents the {@link Locator#innerHTML()}
     */
    public static final String COMPONENT_INNER_HTML = "component.innerHTML";

    /**
     * Represents the {@link Locator#getAttribute(String)}
     */
    public static final String COMPONENT_GET_ATTRIBUTE = "component.getAttribute";

    /**
     * Represents the {@link Locator#isVisible()}
     */
    public static final String COMPONENT_IS_VISIBLE = "component.isVisible";

    /**
     * Represents the {@link Locator#isEnabled()}
     */
    public static final String COMPONENT_IS_ENABLED = "component.isEnabled";

    /**
     * Represents the {@link Locator#isDisabled()}
     */
    public static final String COMPONENT_IS_DISABLED = "component.isDisabled";

    /**
     * Represents the {@link Locator#fill(String)}
     */
    public static final String COMPONENT_FILL = "component.fill";

    /**
     * Represents the {@link Locator#click()}
     */
    public static final String LOCATOR_CLICK = "locator.click";

    /**
     * Represents the {@link Locator#dblclick()}
     */
    public static final String LOCATOR_DBLCLICK = "locator.dblclick";

    /**
     * Represents the {@link Locator#describe(String)}
     */
    public static final String LOCATOR_DESCRIBE = "locator.describe";

    /**
     * Represents the {@link Locator#fill(String)}
     */
    public static final String LOCATOR_FILL = "locator.fill";

    /**
     * Represents the {@link Locator#hover()}
     */
    public static final String LOCATOR_HOVER = "locator.hover";

    /**
     * Represents the {@link Locator#innerHTML()}
     */
    public static final String LOCATOR_INNER_HTML = "locator.innerHTML";

    /**
     * Represents the {@link Locator#innerText()}
     */
    public static final String LOCATOR_INNER_TEXT = "locator.innerText";

    /**
     * Represents the {@link Locator#textContent()}
     */
    public static final String LOCATOR_TEXT_CONTENT = "locator.textContent";

    /**
     * Represents the {@link Locator#getAttribute(String)}
     */
    public static final String LOCATOR_GET_ATTRIBUTE = "locator.getAttribute";

    /**
     * Represents the {@link Locator#isDisabled()}
     */
    public static final String LOCATOR_IS_DISABLED = "locator.isDisabled";

    /**
     * Represents the {@link Locator#isEnabled()}
     */
    public static final String LOCATOR_IS_ENABLED = "locator.isEnabled";

    /**
     * Represents the {@link Locator#isVisible()}
     */
    public static final String LOCATOR_IS_VISIBLE = "locator.isVisible";

    /**
     * Represents the {@link Locator#selectOption(String[])}
     */
    public static final String LOCATOR_SELECT_OPTION = "locator.selectOption";

    /**
     * Represents the {@link Locator#dragTo(Locator)}
     */
    public static final String LOCATOR_DRAG_TO = "locator.dragTo";

    /**
     * Represents the {@link Locator#focus()}
     */
    public static final String LOCATOR_FOCUS = "locator.focus";

    /**
     * Represents the {@link Locator#blur()}
     */
    public static final String LOCATOR_BLUR = "locator.blur";

    /**
     * Represents the {@link Locator#boundingBox()}
     */
    public static final String LOCATOR_BOUNDING_BOX = "locator.boundingBox";

    /**
     * Represents the {@link Locator#check()}
     */
    public static final String LOCATOR_CHECK = "locator.check";

    /**
     * Represents the {@link Locator#inputValue()}
     */
    public static final String LOCATOR_INPUT_VALUE = "locator.inputValue";

    /**
     * Represents the {@link Locator#press(String)}
     */
    public static final String LOCATOR_PRESS = "locator.press";

    /**
     * Represents the {@link Locator#pressSequentially(String)}
     */
    public static final String LOCATOR_PRESS_SEQUENTIALLY = "locator.pressSequentially";
    /**
     * Represents the {@link Locator#isChecked()}
     */
    public static final String LOCATOR_IS_CHECKED = "locator.isChecked";

    /**
     * Represents the {@link Locator#isEditable()}
     */
    public static final String LOCATOR_IS_EDITABLE = "locator.isEditable";

    /**
     * Represents the {@link Locator#selectText()}
     */
    public static final String LOCATOR_SELECT_TEXT = "locator.selectText";

    /**
     * Represents the {@link Locator#setChecked(boolean)}
     */
    public static final String LOCATOR_SET_CHECKED = "locator.setChecked";

    /**
     * Represents the {@link Locator#setInputFiles(Path)}
     */
    public static final String LOCATOR_SET_INPUT_FILES = "locator.setInputFiles";

    /**
     * Represents the {@link Locator#tap()}
     */
    public static final String LOCATOR_TAP = "locator.tap";

    /**
     * Represents the {@link Locator#type(String)}
     */
    public static final String LOCATOR_TYPE = "locator.type";

    /**
     * Represents the {@link Locator#uncheck()}
     */
    public static final String LOCATOR_UNCHECK = "locator.uncheck";

    /**
     * Represents the {@link Locator#clear()}
     */
    public static final String LOCATOR_CLEAR = "locator.clear";

    /**
     * Represents the {@link Locator#dispatchEvent(String)}
     */
    public static final String LOCATOR_DISPATCH_EVENT = "locator.dispatchEvent";

    /**
     * Represents the {@link Locator#evaluate(String)}
     */
    public static final String LOCATOR_EVALUATE = "locator.evaluate";

    /**
     * Represents the {@link Locator#evaluateAll(String)}
     */
    public static final String LOCATOR_EVALUATE_ALL = "locator.evaluateAll";

    /**
     * Represents the {@link Locator#filter()}
     */
    public static final String LOCATOR_FILTER = "locator.filter";

    /**
     * Represents the {@link Locator#first()}
     */
    public static final String LOCATOR_FIRST = "locator.first";

    /**
     * Represents the {@link Locator#last()}
     */
    public static final String LOCATOR_LAST = "locator.last";

    /**
     * Represents the {@link Locator#or(Locator)}
     */
    public static final String LOCATOR_OR = "locator.or";

    /**
     * Represents the {@link Locator#locator(String)}
     */
    public static final String LOCATOR_LOCATOR = "locator.locator";

    /**
     * Represents the {@link Locator#getByAltText(String)}
     */
    public static final String LOCATOR_GET_BY_ALT_TEXT = "locator.getByAltText";

    /**
     * Represents the {@link Locator#getByLabel(String)}
     */
    public static final String LOCATOR_GET_BY_LABEL = "locator.getByLabel";

    /**
     * Represents the {@link Locator#getByPlaceholder(String)}
     */
    public static final String LOCATOR_GET_BY_PLACEHOLDER = "locator.getByPlaceholder";

    /**
     * Represents the {@link Locator#getByRole(AriaRole)}
     */
    public static final String LOCATOR_GET_BY_ROLE = "locator.getByRole";

    /**
     * Represents the {@link Locator#getByTestId(String)}
     */
    public static final String LOCATOR_GET_BY_TEST_ID = "locator.getByTestId";

    /**
     * Represents the {@link Locator#getByText(String)}
     */
    public static final String LOCATOR_GET_BY_TEXT = "locator.getByText";

    /**
     * Represents the {@link Locator#getByTitle(String)}
     */
    public static final String LOCATOR_GET_BY_TITLE = "locator.getByTitle";

    /**
     * Represents the {@link Locator#all()}
     */
    public static final String LOCATOR_ALL = "locator.all";

    /**
     * Represents the {@link Locator#highlight()}
     */
    public static final String LOCATOR_HIGHLIGHT = "locator.highlight";

    /**
     * Represents the {@link Locator#screenshot()}
     */
    public static final String LOCATOR_SCREENSHOT = "locator.screenshot";

    /**
     * Represents the {@link Locator#allInnerTexts()}
     */
    public static final String LOCATOR_ALL_INNER_TEXTS = "locator.allInnerTexts";

    /**
     * Represents the {@link Locator#allTextContents()}
     */
    public static final String LOCATOR_ALL_TEXT_CONTENTS = "locator.allTextContents";

    /**
     * Represents the {@link Locator#and(Locator)}
     */
    public static final String LOCATOR_AND = "locator.and";

    /**
     * Represents the {@link Locator#ariaSnapshot()}
     */
    public static final String LOCATOR_ARIA_SNAPSHOT = "locator.ariaSnapshot";

    /**
     * Represents the {@link Locator#elementHandle()}
     */
    public static final String LOCATOR_ELEMENT_HANDLE = "locator.elementHandle";

    /**
     * Represents the {@link Locator#elementHandles()}
     */
    public static final String LOCATOR_ELEMENT_HANDLES = "locator.elementHandles";

    /**
     * Represents the {@link Locator#evaluateHandle(String)}
     */
    public static final String LOCATOR_EVALUATE_HANDLE = "locator.evaluateHandle";

    /**
     * Represents the {@link Locator#scrollIntoViewIfNeeded()}
     */
    public static final String LOCATOR_SCROLL_INTO_VIEW_IF_NEEDED = "locator.scrollIntoViewIfNeeded";

    /**
     * Represents the {@link Locator#waitFor()}
     */
    public static final String LOCATOR_WAIT_FOR = "locator.waitFor";

    /**
     * Represents the {@link Locator#isHidden()}
     */
    public static final String LOCATOR_IS_HIDDEN = "locator.isHidden";
}