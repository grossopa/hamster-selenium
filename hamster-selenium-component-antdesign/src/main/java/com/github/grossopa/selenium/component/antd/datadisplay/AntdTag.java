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
package com.github.grossopa.selenium.component.antd.datadisplay;

import com.github.grossopa.selenium.component.antd.AbstractAntdComponent;
import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

/**
 * Tag for categorizing or markup.
 *
 * @author Jack Yin
 * @see <a href="https://ant.design/components/tag">https://ant.design/components/tag</a>
 * @since 1.15
 */
public class AntdTag extends AbstractAntdComponent {

    /**
     * The component name
     */
    public static final String NAME = "tag";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver root driver
     * @param config the global Antd configuration
     */
    public AntdTag(WebElement element, ComponentWebDriver driver, AntdConfig config) {
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
     * Whether the tag is closable, i.e. it contains a close icon.
     *
     * @return true if the tag is closable
     */
    public boolean isClosable() {
        return !findComponents(By.className(config.getPrefixCls() + "-" + NAME + "-close-icon")).isEmpty();
    }

    /**
     * Gets the color of the tag if it is a colored tag.
     *
     * @return the color of the tag, null if the tag has no explicit color
     */
    public String getColor() {
        String prefix = config.getPrefixCls() + "-" + NAME + "-";
        String classAttribute = element.getDomAttribute(CLASS);
        if (classAttribute == null) {
            return null;
        }
        return java.util.Arrays.stream(classAttribute.split(" ")).filter(token -> token.startsWith(prefix))
                .map(token -> token.substring(prefix.length())).filter(color -> !color.isEmpty())
                .filter(color -> !"close-icon".equals(color) && !"has-color".equals(color))
                .findFirst().orElse(null);
    }
}
