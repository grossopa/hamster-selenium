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

package com.github.grossopa.selenium.component.mui.datadisplay;

import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import org.apache.commons.lang3.StringUtils;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

/**
 * Badge generates a small badge to the top-right of its child(ren).
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/badges/">https://material-ui.com/components/badges/</a>
 * @since 1.0
 */
public class MuiBadge extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Badge";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiBadge(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    /**
     * Finds the badge element. could be either visible or invisible. with a certain number or a dot indicates there is
     * new message(s).
     *
     * @return the badge element.
     */
    public WebComponent getBadge() {
        return this.findComponent(By.className(config.getCssPrefix() + "Badge-badge"));
    }

    /**
     * Returns the number of the badge. Note it will return 0 for dot as well even if it's displayed.
     *
     * @return the number of the badge.
     */
    public int getBadgeNumber() {
        String badgeText = getBadge().getText();
        if (endsWith(badgeText, "+")) {
            badgeText = badgeText.substring(0, badgeText.length() - 1);
        }
        return isParsable(badgeText) ? parseInt(badgeText) : 0;
    }

    /**
     * Whether the dot is displayed instead of numbers.
     *
     * @return whether the dot is displayed
     */
    public boolean isDotDisplayed() {
        return stream(getBadge().getAttribute("class").split(" "))
                .anyMatch(str -> str.equalsIgnoreCase(config.getCssPrefix() + "Badge-dot"));
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Whether the badge is displayed or not.
     *
     * @return whether the badge is displayed or not
     */
    public boolean isBadgeDisplayed() {
        return stream(getBadge().getAttribute("class").split(" ")).map(StringUtils::trim)
                .noneMatch(str -> str.equalsIgnoreCase(config.getCssPrefix() + "Badge-invisible"));
    }

    @Override
    public String toString() {
        return "MuiBadge{" + "element=" + element + '}';
    }
}
