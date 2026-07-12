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

package com.github.grossopa.playwright.component.mui.v4.surfaces;

import static com.github.grossopa.utils.consts.HtmlConstants.BUTTON;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Cards contain content and actions about a single subject.
 *
 * <p>Cards are flexible containers that can display various types of content including 
 * headers, media, content sections, and action buttons.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/cards/">
 * https://material-ui.com/components/cards/</a>
 * @since 1.12
 */
public class MuiCard extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Card";

    /**
     * Constructs an MuiCard instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiCard(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the card title if available.
     *
     * @return the card title or null if not found
     */
    public String getTitle() {
        WebComponent titleElement = findComponent("." + config.getCssPrefix() + "CardHeader-title");
        if (titleElement != null) {
            return titleElement.innerText();
        }
        
        // Try to find title in other common locations
        WebComponent h1 = findComponent("h1");
        if (h1 != null) return h1.innerText();
        
        WebComponent h2 = findComponent("h2");
        return h2 != null ? h2.innerText() : null;
    }

    /**
     * Gets the card subtitle if available.
     *
     * @return the subtitle or null if not found
     */
    public String getSubtitle() {
        WebComponent subtitleElement = findComponent("." + config.getCssPrefix() + "CardHeader-subheader");
        return subtitleElement != null ? subtitleElement.innerText() : null;
    }

    /**
     * Gets the card content.
     *
     * @return the card content text
     */
    public String getContent() {
        WebComponent contentElement = findComponent("." + config.getCssPrefix() + "CardContent-root");
        return contentElement != null ? contentElement.innerText() : locator.innerText();
    }

    /**
     * Gets the card actions (buttons).
     *
     * @return list of action button WebComponents
     */
    public List<WebComponent> getActions() {
        WebComponent actionsContainer = findComponent("." + config.getCssPrefix() + "CardActions-root");
        if (actionsContainer != null) {
            return actionsContainer.findComponents(BUTTON);
        }
        return findComponents(BUTTON);
    }

    /**
     * Gets the count of action buttons.
     *
     * @return the number of action buttons
     */
    public int getActionCount() {
        return getActions().size();
    }

    /**
     * Clicks an action button by its text.
     *
     * @param buttonText the text of the button to click
     * @throws IllegalArgumentException if button not found
     */
    public void clickAction(String buttonText) {
        List<WebComponent> actions = getActions();
        WebComponent targetButton = actions.stream()
                .filter(button -> buttonText.equals(button.innerText()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Action button with text '" + buttonText + "' not found"));
        targetButton.click();
    }

    /**
     * Checks if the card has a media element (image, video, etc.).
     *
     * @return true if the card has media, false otherwise
     */
    public boolean hasMedia() {
        return findComponent("." + config.getCssPrefix() + "CardMedia-root") != null;
    }

    /**
     * Gets the card media image source URL.
     *
     * @return the image URL, or null if no media
     */
    public String getMediaSrc() {
        WebComponent media = findComponent("." + config.getCssPrefix() + "CardMedia-root");
        if (media != null) {
            WebComponent img = media.findComponent("img");
            return img != null ? img.getAttribute("src") : null;
        }
        return null;
    }

    /**
     * Checks if the card has a header section.
     *
     * @return true if card has header, false otherwise
     */
    public boolean hasHeader() {
        return findComponent("." + config.getCssPrefix() + "CardHeader-root") != null;
    }
}
