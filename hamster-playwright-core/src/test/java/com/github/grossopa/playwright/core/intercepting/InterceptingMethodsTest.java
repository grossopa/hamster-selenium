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
package com.github.grossopa.playwright.core.intercepting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterceptingMethodsTest {

    @Test
    void testPrivateConstructor() {
        java.lang.reflect.InvocationTargetException ex = assertThrows(
                java.lang.reflect.InvocationTargetException.class, () -> {
            java.lang.reflect.Constructor<InterceptingMethods> constructor = InterceptingMethods.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
        assertInstanceOf(AssertionError.class, ex.getCause());
    }

    @Test
    void testDriverConstants() {
        assertEquals("driver.findComponents", InterceptingMethods.DRIVER_FIND_COMPONENTS);
        assertEquals("driver.findComponentAs", InterceptingMethods.DRIVER_FIND_COMPONENT_AS);
        assertEquals("driver.findComponentsAs", InterceptingMethods.DRIVER_FIND_COMPONENTS_AS);
        assertEquals("driver.findComponent", InterceptingMethods.DRIVER_FIND_COMPONENT);
        assertEquals("driver.navigate", InterceptingMethods.DRIVER_NAVIGATE);
    }

    @Test
    void testComponentConstants() {
        assertEquals("component.findComponents", InterceptingMethods.COMPONENT_FIND_COMPONENTS);
        assertEquals("component.findComponent", InterceptingMethods.COMPONENT_FIND_COMPONENT);
        assertEquals("component.click", InterceptingMethods.COMPONENT_CLICK);
        assertEquals("component.hover", InterceptingMethods.COMPONENT_HOVER);
        assertEquals("component.textContent", InterceptingMethods.COMPONENT_TEXT_CONTENT);
        assertEquals("component.innerText", InterceptingMethods.COMPONENT_INNER_TEXT);
        assertEquals("component.innerHTML", InterceptingMethods.COMPONENT_INNER_HTML);
        assertEquals("component.getAttribute", InterceptingMethods.COMPONENT_GET_ATTRIBUTE);
        assertEquals("component.isVisible", InterceptingMethods.COMPONENT_IS_VISIBLE);
        assertEquals("component.isEnabled", InterceptingMethods.COMPONENT_IS_ENABLED);
        assertEquals("component.isDisabled", InterceptingMethods.COMPONENT_IS_DISABLED);
        assertEquals("component.fill", InterceptingMethods.COMPONENT_FILL);
    }

    @Test
    void testLocatorActionConstants() {
        assertEquals("locator.click", InterceptingMethods.LOCATOR_CLICK);
        assertEquals("locator.dblclick", InterceptingMethods.LOCATOR_DBLCLICK);
        assertEquals("locator.fill", InterceptingMethods.LOCATOR_FILL);
        assertEquals("locator.hover", InterceptingMethods.LOCATOR_HOVER);
        assertEquals("locator.innerHTML", InterceptingMethods.LOCATOR_INNER_HTML);
        assertEquals("locator.innerText", InterceptingMethods.LOCATOR_INNER_TEXT);
        assertEquals("locator.textContent", InterceptingMethods.LOCATOR_TEXT_CONTENT);
        assertEquals("locator.getAttribute", InterceptingMethods.LOCATOR_GET_ATTRIBUTE);
        assertEquals("locator.isDisabled", InterceptingMethods.LOCATOR_IS_DISABLED);
        assertEquals("locator.isEnabled", InterceptingMethods.LOCATOR_IS_ENABLED);
        assertEquals("locator.isVisible", InterceptingMethods.LOCATOR_IS_VISIBLE);
        assertEquals("locator.selectOption", InterceptingMethods.LOCATOR_SELECT_OPTION);
    }

    @Test
    void testLocatorNavigationConstants() {
        assertEquals("locator.dragTo", InterceptingMethods.LOCATOR_DRAG_TO);
        assertEquals("locator.focus", InterceptingMethods.LOCATOR_FOCUS);
        assertEquals("locator.blur", InterceptingMethods.LOCATOR_BLUR);
        assertEquals("locator.boundingBox", InterceptingMethods.LOCATOR_BOUNDING_BOX);
        assertEquals("locator.check", InterceptingMethods.LOCATOR_CHECK);
        assertEquals("locator.inputValue", InterceptingMethods.LOCATOR_INPUT_VALUE);
        assertEquals("locator.press", InterceptingMethods.LOCATOR_PRESS);
        assertEquals("locator.isChecked", InterceptingMethods.LOCATOR_IS_CHECKED);
        assertEquals("locator.isEditable", InterceptingMethods.LOCATOR_IS_EDITABLE);
        assertEquals("locator.selectText", InterceptingMethods.LOCATOR_SELECT_TEXT);
        assertEquals("locator.setChecked", InterceptingMethods.LOCATOR_SET_CHECKED);
        assertEquals("locator.setInputFiles", InterceptingMethods.LOCATOR_SET_INPUT_FILES);
        assertEquals("locator.tap", InterceptingMethods.LOCATOR_TAP);
        assertEquals("locator.type", InterceptingMethods.LOCATOR_TYPE);
        assertEquals("locator.uncheck", InterceptingMethods.LOCATOR_UNCHECK);
        assertEquals("locator.clear", InterceptingMethods.LOCATOR_CLEAR);
        assertEquals("locator.dispatchEvent", InterceptingMethods.LOCATOR_DISPATCH_EVENT);
        assertEquals("locator.evaluate", InterceptingMethods.LOCATOR_EVALUATE);
        assertEquals("locator.evaluateAll", InterceptingMethods.LOCATOR_EVALUATE_ALL);
        assertEquals("locator.filter", InterceptingMethods.LOCATOR_FILTER);
        assertEquals("locator.first", InterceptingMethods.LOCATOR_FIRST);
        assertEquals("locator.last", InterceptingMethods.LOCATOR_LAST);
        assertEquals("locator.or", InterceptingMethods.LOCATOR_OR);
    }

    @Test
    void testLocatorQueryConstants() {
        assertEquals("locator.locator", InterceptingMethods.LOCATOR_LOCATOR);
        assertEquals("locator.getByAltText", InterceptingMethods.LOCATOR_GET_BY_ALT_TEXT);
        assertEquals("locator.getByLabel", InterceptingMethods.LOCATOR_GET_BY_LABEL);
        assertEquals("locator.getByPlaceholder", InterceptingMethods.LOCATOR_GET_BY_PLACEHOLDER);
        assertEquals("locator.getByRole", InterceptingMethods.LOCATOR_GET_BY_ROLE);
        assertEquals("locator.getByTestId", InterceptingMethods.LOCATOR_GET_BY_TEST_ID);
        assertEquals("locator.getByText", InterceptingMethods.LOCATOR_GET_BY_TEXT);
        assertEquals("locator.getByTitle", InterceptingMethods.LOCATOR_GET_BY_TITLE);
        assertEquals("locator.all", InterceptingMethods.LOCATOR_ALL);
        assertEquals("locator.highlight", InterceptingMethods.LOCATOR_HIGHLIGHT);
        assertEquals("locator.screenshot", InterceptingMethods.LOCATOR_SCREENSHOT);
        assertEquals("locator.allInnerTexts", InterceptingMethods.LOCATOR_ALL_INNER_TEXTS);
        assertEquals("locator.allTextContents", InterceptingMethods.LOCATOR_ALL_TEXT_CONTENTS);
        assertEquals("locator.and", InterceptingMethods.LOCATOR_AND);
        assertEquals("locator.ariaSnapshot", InterceptingMethods.LOCATOR_ARIA_SNAPSHOT);
        assertEquals("locator.elementHandle", InterceptingMethods.LOCATOR_ELEMENT_HANDLE);
        assertEquals("locator.elementHandles", InterceptingMethods.LOCATOR_ELEMENT_HANDLES);
        assertEquals("locator.evaluateHandle", InterceptingMethods.LOCATOR_EVALUATE_HANDLE);
        assertEquals("locator.scrollIntoViewIfNeeded", InterceptingMethods.LOCATOR_SCROLL_INTO_VIEW_IF_NEEDED);
        assertEquals("locator.waitFor", InterceptingMethods.LOCATOR_WAIT_FOR);
        assertEquals("locator.isHidden", InterceptingMethods.LOCATOR_IS_HIDDEN);
        assertEquals("locator.contentFrame", InterceptingMethods.LOCATOR_CONTENT_FRAME);
        assertEquals("locator.frameLocator", InterceptingMethods.LOCATOR_FRAME_LOCATOR);
    }

    @Test
    void testFrameLocatorConstants() {
        assertEquals("frameLocator.locator", InterceptingMethods.FRAME_LOCATOR_LOCATOR);
        assertEquals("frameLocator.frameLocator", InterceptingMethods.FRAME_LOCATOR_FRAME_LOCATOR);
        assertEquals("frameLocator.getByAltText", InterceptingMethods.FRAME_LOCATOR_GET_BY_ALT_TEXT);
        assertEquals("frameLocator.getByLabel", InterceptingMethods.FRAME_LOCATOR_GET_BY_LABEL);
        assertEquals("frameLocator.getByPlaceholder", InterceptingMethods.FRAME_LOCATOR_GET_BY_PLACEHOLDER);
        assertEquals("frameLocator.getByRole", InterceptingMethods.FRAME_LOCATOR_GET_BY_ROLE);
        assertEquals("frameLocator.getByTestId", InterceptingMethods.FRAME_LOCATOR_GET_BY_TEST_ID);
        assertEquals("frameLocator.getByText", InterceptingMethods.FRAME_LOCATOR_GET_BY_TEXT);
        assertEquals("frameLocator.getByTitle", InterceptingMethods.FRAME_LOCATOR_GET_BY_TITLE);
        assertEquals("frameLocator.first", InterceptingMethods.FRAME_LOCATOR_FIRST);
        assertEquals("frameLocator.last", InterceptingMethods.FRAME_LOCATOR_LAST);
        assertEquals("frameLocator.nth", InterceptingMethods.FRAME_LOCATOR_NTH);
        assertEquals("frameLocator.owner", InterceptingMethods.FRAME_LOCATOR_OWNER);
    }
}
