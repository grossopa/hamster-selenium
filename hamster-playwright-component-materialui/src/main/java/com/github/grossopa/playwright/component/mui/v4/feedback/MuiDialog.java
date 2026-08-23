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
package com.github.grossopa.playwright.component.mui.v4.feedback;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.Set;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Dialogs inform users about a task and can contain critical information, require decisions, or involve multiple tasks.
 *
 * <p>Dialogs are modal windows that appear in front of app content to provide critical information 
 * or ask for a decision. They disable all app functionality until they have been addressed.</p>
 *
 * @see <a href="https://material-ui.com/components/dialogs/">
 * https://material-ui.com/components/dialogs/</a>
 * @author Jack Yin
 * @since 1.12
 */
public class MuiDialog extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Dialog";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material-UI configuration
     */
    public MuiDialog(Locator locator, ComponentDriver driver, MuiConfig config) {
        super(locator, driver, config);
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Finds the title container.
     *
     * @return the title WebComponent, or null if it doesn't exist
     */
    public WebComponent getDialogTitle() {
        return findComponent("." + config.getCssPrefix() + "DialogTitle-root");
    }

    /**
     * Gets the dialog title text.
     *
     * @return the title text, or null if no title exists
     */
    public String getTitleText() {
        WebComponent title = getDialogTitle();
        return title != null ? title.innerText() : null;
    }

    /**
     * Finds the dialog content container.
     *
     * @return the content WebComponent, or null if it doesn't exist
     */
    public WebComponent getDialogContent() {
        return findComponent("." + config.getCssPrefix() + "DialogContent-root");
    }

    /**
     * Gets the dialog content text.
     *
     * @return the content text, or null if no content exists
     */
    public String getContentText() {
        WebComponent content = getDialogContent();
        return content != null ? content.innerText() : null;
    }

    /**
     * Finds the dialog actions container (buttons area).
     *
     * @return the actions WebComponent, or null if it doesn't exist
     */
    public WebComponent getDialogActions() {
        return findComponent("." + config.getCssPrefix() + "DialogActions-root");
    }

    /**
     * Checks if the dialog is currently open/visible.
     *
     * @return true if dialog is visible, false otherwise
     */
    public boolean isOpen() {
        String role = getAttribute("role");
        return "dialog".equals(role) && isVisible();
    }

    /**
     * Closes the dialog by pressing Escape key.
     */
    public void close() {
        locator.press("Escape");
    }

    /**
     * Clicks a button in the dialog actions by its text.
     *
     * @param buttonText the text of the button to click
     * @throws IllegalArgumentException if button not found
     */
    public void clickActionButton(String buttonText) {
        WebComponent actions = getDialogActions();
        if (actions == null) {
            throw new IllegalStateException("Dialog has no actions container");
        }
        
        WebComponent button = actions.findComponent("button:text('" + buttonText + "')");
        if (button == null) {
            throw new IllegalArgumentException("Button with text '" + buttonText + "' not found in dialog actions");
        }
        button.click();
    }
}
