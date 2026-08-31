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
package com.github.grossopa.selenium.component.antd.navigation;

import com.github.grossopa.selenium.component.antd.AbstractAntdComponent;
import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * A long list can be separated into several pages using a pagination and a page selector will be shown.
 *
 * <p>Note that the Ant Design Pagination does not provide first and last buttons by default, hence the pagination
 * navigation is achieved by the previous and next buttons combined with the displayed page items.</p>
 *
 * @author Jack Yin
 * @see <a href="https://ant.design/components/pagination">https://ant.design/components/pagination</a>
 * @since 1.15
 */
public class AntdPagination extends AbstractAntdComponent {

    /**
     * The component name
     */
    public static final String NAME = "pagination";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     * @param config the global Antd configuration
     */
    public AntdPagination(WebElement element, ComponentWebDriver driver, AntdConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return NAME;
    }

    @Override
    public boolean validate() {
        return attributeContains(CLASS, config.getPrefixCls() + "-" + NAME);
    }

    /**
     * Gets all currently displayed page item components.
     *
     * @return all currently displayed page item components
     */
    public List<WebComponent> getPageItems() {
        return findComponents(By.className(config.getPrefixCls() + "-" + NAME + "-item"));
    }

    /**
     * Gets the previous page button.
     *
     * @return the previous page button
     * @throws NoSuchElementException if the button is not found
     */
    public WebComponent previousButton() {
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME + "-prev"));
    }

    /**
     * Gets the next page button.
     *
     * @return the next page button
     * @throws NoSuchElementException if the button is not found
     */
    public WebComponent nextButton() {
        return findComponent(By.className(config.getPrefixCls() + "-" + NAME + "-next"));
    }

    /**
     * Gets the current selected page index, it is read from the {@code title} attribute of the active page item.
     *
     * @return the current selected page index (1-based), -1 if nothing is selected
     */
    public int getCurrentPageIndex() {
        String activeClass = config.getPrefixCls() + "-" + NAME + "-item-active";
        return getPageItems().stream().filter(item -> item.attributeContains(CLASS, activeClass)).findFirst()
                .map(AntdPagination::toPageIndex).orElse(-1);
    }

    /**
     * Selects the page by index (1-based). If the target page item is not currently displayed, the component will
     * keep clicking the previous or next button until the page item appears.
     *
     * @param index the index to be selected
     * @throws NoSuchElementException if the page index can not be reached
     */
    @SuppressWarnings("java:S135")
    public void setPageIndex(int index) {
        if (getCurrentPageIndex() == index) {
            return;
        }

        int lastMinIndex = Integer.MAX_VALUE;
        int lastMaxIndex = Integer.MIN_VALUE;

        while (true) {
            Optional<WebComponent> targetItem = getPageItems().stream()
                    .filter(item -> String.valueOf(index).equals(item.getDomAttribute("title"))).findFirst();
            if (targetItem.isPresent()) {
                targetItem.get().click();
                return;
            }

            List<Integer> displayedIndices = getPageItems().stream().map(AntdPagination::toPageIndex)
                    .filter(pageIndex -> pageIndex > 0).toList();
            int minIndex = displayedIndices.stream().min(Integer::compareTo).orElse(-1);
            int maxIndex = displayedIndices.stream().max(Integer::compareTo).orElse(-1);
            if (minIndex == -1 || minIndex == lastMinIndex && maxIndex == lastMaxIndex) {
                throw new NoSuchElementException("The page index " + index + " could not be reached");
            }

            if (index < minIndex) {
                previousButton().click();
            } else {
                nextButton().click();
            }

            lastMinIndex = minIndex;
            lastMaxIndex = maxIndex;
        }
    }

    private static Integer toPageIndex(WebComponent item) {
        String title = item.getDomAttribute("title");
        return title == null ? -1 : Integer.parseInt(title);
    }
}
