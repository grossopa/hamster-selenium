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

package com.github.grossopa.playwright.component.mui.v4.feedback;

import static com.github.grossopa.utils.consts.HtmlConstants.CLASS;

import com.github.grossopa.playwright.component.mui.AbstractMuiComponent;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.grossopa.playwright.component.mui.MuiVersion.V4;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V5;
import static com.github.grossopa.playwright.component.mui.MuiVersion.V6;

/**
 * Skeleton screens are used to display a loading state while content is being fetched.
 *
 * <p>Skeleton provides a simple placeholder UI that shows the general shape of content 
 * before it's fully loaded, improving perceived performance.</p>
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/skeleton/">
 * https://material-ui.com/components/skeleton/</a>
 * @since 1.12
 */
public class MuiSkeleton extends AbstractMuiComponent {

    /**
     * The component name
     */
    public static final String COMPONENT_NAME = "Skeleton";

    /**
     * Constructs an instance with the delegated locator and driver
     *
     * @param locator the delegated Locator
     * @param driver the ComponentDriver
     * @param config the Material UI configuration
     */
    public MuiSkeleton(Locator locator, ComponentDriver driver, MuiConfig config) {
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
     * Gets the variant type of the skeleton.
     *
     * @return the variant ("text", "rectangular", "circular", or "rounded")
     */
    public String getVariant() {
        String className = getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();
        
        if (className.contains(cssPrefix + "Skeleton-text")) {
            return "text";
        } else if (className.contains(cssPrefix + "Skeleton-rectangular")) {
            return "rectangular";
        } else if (className.contains(cssPrefix + "Skeleton-circular")) {
            return "circular";
        } else if (className.contains(cssPrefix + "Skeleton-rounded")) {
            return "rounded";
        }
        
        return "text"; // default
    }

    /**
     * Checks if the skeleton has animation enabled.
     *
     * @return true if animated, false otherwise
     */
    public boolean isAnimated() {
        String className = getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();
        
        return !className.contains(cssPrefix + "Skeleton-pulse") && 
               !className.contains(cssPrefix + "Skeleton-wave");
    }

    /**
     * Gets the animation type.
     *
     * @return the animation type ("pulse", "wave", or "none")
     */
    public String getAnimation() {
        String className = getAttribute(CLASS);
        String cssPrefix = config.getCssPrefix();
        
        if (className.contains(cssPrefix + "Skeleton-pulse")) {
            return "pulse";
        } else if (className.contains(cssPrefix + "Skeleton-wave")) {
            return "wave";
        }
        
        return "none";
    }

    /**
     * Checks if the skeleton is currently visible/loading.
     *
     * @return true if skeleton is displayed, false if hidden
     */
    public boolean isLoading() {
        return isVisible();
    }
}
