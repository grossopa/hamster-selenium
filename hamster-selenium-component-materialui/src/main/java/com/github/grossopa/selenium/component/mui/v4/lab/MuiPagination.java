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

package com.github.grossopa.selenium.component.mui.v4.lab;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.v4.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.exception.PaginationNotFoundException;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.api.Pagination;
import com.github.grossopa.selenium.core.locator.By2;
import com.google.common.collect.Sets;
import org.openqa.selenium.WebElement;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiVersion.V4;
import static com.github.grossopa.selenium.component.mui.MuiVersion.V5;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * The Pagination component enables the user to select a specific page from a range of pages.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/pagination/">https://material-ui.com/components/pagination/</a>
 * @since 1.3
 */
public class MuiPagination extends AbstractMuiComponent implements Pagination<MuiButton, MuiButton, MuiButton> {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Pagination";

    private final MuiPaginationLocators locators;

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiPagination(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        this(element, driver, config, null);
    }

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     * @param locators optional, the locators for buttons
     */
    public MuiPagination(WebElement element, ComponentWebDriver driver, MuiConfig config,
            MuiPaginationLocators locators) {
        super(element, driver, config);
        this.locators = defaultIfNull(locators, MuiPaginationLocators.createDefault());
    }

    @Override
    public Set<MuiVersion> versions() {
        return EnumSet.of(V4, V5);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @Override
    public boolean isEnabled() {
        MuiButton currentButton = this.findComponentAs(By2.attrExact("aria-current", "true"),
                component -> new MuiButton(component, driver, config));
        return currentButton.isEnabled();
    }

    @Override
    public List<MuiButton> pageButtons() {
        return locators.getPageButtonsFinder().apply(this);
    }

    @Override
    public MuiButton previousButton() {
        return locators.getPreviousButtonFinder().apply(this);
    }

    @Override
    public MuiButton nextButton() {
        return locators.getNextButtonFinder().apply(this);
    }

    @Override
    public MuiButton firstButton() {
        return locators.getFirstButtonFinder().apply(this);
    }

    @Override
    public MuiButton lastButton() {
        return locators.getLastButtonFinder().apply(this);
    }

    @Override
    @SuppressWarnings({"java:S6212", "java:S135"})
    public void setPageIndex(int index) {
        if (getCurrentPageIndex() == index) {
            return;
        }

        Set<Integer> scannedIndices = Sets.newHashSet();

        List<MuiButton> pageButtons = pageButtons();
        int firstIndex = locators.getPageIndexFinder().apply(pageButtons.get(0));
        scannedIndices.add(firstIndex);
        if (firstIndex == index) {
            pageButtons.get(0).click();
            return;
        }

        int lastIndex = locators.getPageIndexFinder().apply(pageButtons.get(pageButtons.size() - 1));
        scannedIndices.add(lastIndex);
        if (lastIndex == index) {
            pageButtons.get(pageButtons.size() - 1).click();
            return;
        }

        int lastMinIndex = -1;
        int lastMaxIndex = -1;

        while (true) {
            int minIndex = locators.getPageIndexFinder().apply(pageButtons.get(1));
            int maxIndex = locators.getPageIndexFinder().apply(pageButtons.get(pageButtons.size() - 2));
            scannedIndices.add(minIndex);
            scannedIndices.add(maxIndex);

            if (minIndex == lastMinIndex && maxIndex == lastMaxIndex) {
                throw new PaginationNotFoundException(index, scannedIndices.stream().sorted().collect(toList()));
            }

            if (index < minIndex) {
                pageButtons.get(1).click();
                pageButtons = pageButtons();
            } else if (index > maxIndex) {
                pageButtons.get(pageButtons.size() - 2).click();
                pageButtons = pageButtons();
            } else {
                pageButtons.get(index - minIndex + 1).click();
                return;
            }

            lastMinIndex = minIndex;
            lastMaxIndex = maxIndex;
        }
    }

    @Override
    public int getCurrentPageIndex() {
        List<MuiButton> currentButtons = this.findComponentsAs(By2.attrExact("aria-current", "true"),
                component -> new MuiButton(component, driver, config));
        return currentButtons.isEmpty() ? -1 : locators.getPageIndexFinder().apply(currentButtons.get(0));
    }

    /**
     * Gets the locators
     *
     * @return the locators
     */
    public MuiPaginationLocators getLocators() {
        return locators;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiPagination)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        MuiPagination that = (MuiPagination) o;
        return locators.equals(that.locators);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), locators);
    }

    @Override
    public String toString() {
        return "MuiPagination{" + "locators=" + locators + ", element=" + element + '}';
    }
}
