/*
 * Copyright © 2020 the original author or authors.
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
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Lists are continuous, vertical indexes of text or images.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/lists/">https://material-ui.com/components/lists/</a>
 * @since 1.0
 */
public class MuiList extends AbstractMuiComponent {

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiList(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "List";
    }

    /**
     * Finds all the {@link MuiListItem} under this list.
     *
     * @return all the {@link MuiListItem} under this list.
     */
    public List<MuiListItem> getListItems() {
        List<WebComponent> components = this.findComponents(
                By2.attr("class", config.getRootCss("ListItem")).depthRelative().contains().build());
        return components.stream().map(c -> new MuiListItem(c, driver, config)).collect(toList());
    }
}