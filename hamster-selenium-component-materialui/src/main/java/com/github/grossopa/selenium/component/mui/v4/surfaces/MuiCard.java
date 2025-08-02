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

package com.github.grossopa.selenium.component.mui.v4.surfaces;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V6;
import static java.util.stream.Collectors.toList;

/**
 * The Material UI Card implementation
 *
 * <p>Cards contain content and actions about a single subject.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/cards/">
 * https://material-ui.com/components/cards/</a>
 * @since 1.0
 */
public class MuiCard extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Card";

    /**
     * Constructs an MuiCard instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiCard(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5, V6);
    }

    /**
     * Gets the card title if available.
     *
     * @return the card title or null if not found
     */
    public String getTitle() {
        try {
            WebComponent titleElement = this.findComponent(By.className(config.getCssPrefix() + "CardHeader-title"));
            return titleElement.getText();
        } catch (Exception e) {
            // Try to find title in other common locations
            try {
                WebComponent titleElement = this.findComponent(By.tagName("h1"));
                return titleElement.getText();
            } catch (Exception ex) {
                try {
                    WebComponent titleElement = this.findComponent(By.tagName("h2"));
                    return titleElement.getText();
                } catch (Exception exc) {
                    return null;
                }
            }
        }
    }

    /**
     * Gets the card content.
     *
     * @return the card content text
     */
    public String getContent() {
        try {
            WebComponent contentElement = this.findComponent(By.className(config.getCssPrefix() + "CardContent-root"));
            return contentElement.getText();
        } catch (Exception e) {
            // Return text content of the card if CardContent is not found
            return element.getText();
        }
    }

    /**
     * Gets the card actions if available.
     *
     * @return list of action components
     */
    public List<WebComponent> getActions() {
        try {
            WebComponent actionsContainer = this.findComponent(By.className(config.getCssPrefix() + "CardActions-root"));
            return actionsContainer.findComponents(By.tagName("button"));
        } catch (Exception e) {
            return findComponents(By.tagName("button"));
        }
    }

    /**
     * Checks if the card has a media element (image, video, etc.).
     *
     * @return true if the card has a media element, false otherwise
     */
    public boolean hasMedia() {
        try {
            this.findComponent(By.className(config.getCssPrefix() + "CardMedia-root"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}