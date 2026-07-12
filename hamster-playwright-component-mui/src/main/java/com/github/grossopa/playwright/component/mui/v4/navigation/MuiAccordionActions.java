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

package com.github.grossopa.playwright.component.mui.v4.navigation;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.*;
import static com.github.grossopa.utils.consts.HtmlConstants.BUTTON;

/**
 * The accordion actions section contains buttons for performing actions related to the accordion content.
 *
 * <p>This is typically placed at the bottom of the accordion details and contains action buttons
 * like Save, Cancel, Delete, etc.</p>
 *
 * @author Jack Yin
 * @see MuiAccordion
 * @since 1.12
 */
public class MuiAccordionActions extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "AccordionActions";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiAccordionActions(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<com.github.grossopa.playwright.component.mui.MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Gets all action buttons within the actions section.
     *
     * @return list of button WebComponents
     */
    public List<WebComponent> getButtons() {
        return findComponents(BUTTON);
    }

    /**
     * Gets the count of action buttons.
     *
     * @return the number of buttons
     */
    public int getButtonCount() {
        return getButtons().size();
    }

    /**
     * Clicks an action button by its text.
     *
     * @param buttonText the text of the button to click
     * @throws IllegalArgumentException if button not found
     */
    public void clickButton(String buttonText) {
        List<WebComponent> buttons = getButtons();
        WebComponent targetButton = buttons.stream()
                .filter(button -> buttonText.equals(button.innerText()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Button with text '" + buttonText + "' not found in accordion actions"));
        targetButton.click();
    }

    /**
     * Clicks an action button by its index.
     *
     * @param index the zero-based index of the button
     * @throws IndexOutOfBoundsException if index is out of range
     */
    public void clickButton(int index) {
        List<WebComponent> buttons = getButtons();
        if (index < 0 || index >= buttons.size()) {
            throw new IndexOutOfBoundsException(
                    "Button index " + index + " is out of bounds. Available buttons: " + buttons.size());
        }
        buttons.get(index).click();
    }
}
