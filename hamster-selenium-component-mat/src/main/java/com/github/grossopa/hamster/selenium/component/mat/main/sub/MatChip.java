/*
 * Copyright © 2021 the original author or authors.
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

package com.github.grossopa.hamster.selenium.component.mat.main.sub;

import com.github.grossopa.hamster.selenium.component.mat.AbstractMatComponent;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.github.grossopa.selenium.core.consts.HtmlConstants.CLASS;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;

/**
 * the inner chip of a chip list
 *
 * @author Jack Yin
 * @see <a href="https://material.angular.io/components/chips/overview">
 * https://material.angular.io/components/chips/overview</a>
 * @since 1.6
 */
public class MatChip extends AbstractMatComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Chip";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI Angular configuration
     */
    public MatChip(WebElement element, ComponentWebDriver driver, MatConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean validate() {
        return this.attributeContains(CLASS, config.getCssPrefix() + "chip");
    }

    /**
     * Gets the remove icon. Throws {@link NoSuchElementException} if not found.
     *
     * @return the remove icon.
     */
    public WebComponent getRemoveIcon() {
        return this.findComponent(removeIconLocator());
    }

    private By removeIconLocator() {
        return xpathBuilder().anywhereRelative(config.getTagPrefix() + "icon").attr(CLASS)
                .contains(config.getCssPrefix() + "chip-remove").build();
    }

    /**
     * Overrides due to Selenium does not support the given chip structure with remove icon:
     *
     * <pre>
     * {@code
     * <mat-chip>
     *   <div class="mat-chip-ripple"></div>
     *   " Lemon "
     *   <mat-icon>cancel</mat-icon>
     * </mat-chip>
     * }
     *
     * </pre>
     * <p>
     * The getText() result will be "Lemon \ncancel". so the logic will try to locate the delete icon, and if it exits
     * and selected text ends with the same text, then remove them from the get Text result.
     *
     * @return the inner text
     */
    @Override
    public String getText() {
        String fullText = super.getText();
        List<WebComponent> removeIcon = this.findComponents(removeIconLocator());
        if (!removeIcon.isEmpty()) {
            String removeIconText = removeIcon.get(0).getText();
            if (fullText.endsWith(removeIconText)) {
                return fullText.substring(0, fullText.length() - removeIconText.length() - 1);
            }
        }
        return fullText;
    }
}
