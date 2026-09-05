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
package com.github.grossopa.playwright.component.mat.main.sub;

import com.github.grossopa.playwright.component.mat.AbstractMatComponent;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.github.grossopa.playwright.core.WebComponent;
import com.microsoft.playwright.Locator;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * {@code <mat-chip>} is the single chip within the {@link com.github.grossopa.playwright.component.mat.main.MatChipList}.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class MatChip extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Chip";

    /**
     * Constructs an instance with the delegated locator and root driver.
     *
     * @param locator the delegated locator
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatChip(Locator locator, ComponentDriver driver, MatConfig config) {
        super(locator, driver, config);
    }

    /**
     * Constructs an instance with the delegated web component and root driver.
     *
     * @param component the delegated web component
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatChip(WebComponent component, ComponentDriver driver, MatConfig config) {
        super(component.locator(), driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getCssPrefix() + "chip");
    }

    /**
     * Gets the remove icon of the chip.
     *
     * @return the remove icon
     */
    public WebComponent getRemoveIcon() {
        return this.findComponent(removeIconSelector());
    }

    /**
     * Removes the chip by clicking its remove icon.
     */
    public void remove() {
        getRemoveIcon().click();
    }

    /**
     * Overrides the default text retrieval to exclude the remove icon text:
     *
     * <pre>
     * {@code
     * <mat-chip>
     *   <div class="mat-chip-ripple"></div>
     *   " Lemon "
     *   <button class="mat-chip-remove">
     *     <mat-icon>cancel</mat-icon>
     *   </button>
     * </mat-chip>
     * }
     * </pre>
     *
     * @return the inner text without the remove icon text
     */
    public String getText() {
        String fullText = super.innerText();
        List<WebComponent> removeIcons = this.findComponents(removeIconSelector());
        if (StringUtils.isNotEmpty(fullText) && !removeIcons.isEmpty()) {
            String removeIconText = removeIcons.get(0).textContent();
            if (removeIconText != null && fullText.endsWith(removeIconText)) {
                return fullText.substring(0, fullText.length() - removeIconText.length()).trim();
            }
        }
        return StringUtils.defaultString(fullText).trim();
    }

    private String removeIconSelector() {
        // the mat-chip-remove class is on the wrapping button element, not on the inner mat-icon
        return "." + config.getCssPrefix() + "chip-remove";
    }
}
