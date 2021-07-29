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

package com.github.grossopa.hamster.selenium.component.mat;

import com.github.grossopa.hamster.selenium.component.mat.action.CloseOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.action.OpenOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.MatAutocomplete;
import com.github.grossopa.hamster.selenium.component.mat.main.MatBadge;
import com.github.grossopa.hamster.selenium.component.mat.main.MatBottomSheet;
import com.github.grossopa.hamster.selenium.component.mat.main.MatButton;
import com.github.grossopa.selenium.core.component.AbstractComponents;
import org.openqa.selenium.By;

import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Contains the converters of Material UI Angular components.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MatComponents extends AbstractComponents {

    private final MatConfig config;

    /**
     * Constructs an instance with default {@link MatConfig}.
     */
    public MatComponents() {
        this(new MatConfig());
    }

    /**
     * Constructs an instance with provided {@link MatConfig}.
     *
     * @param config the Material UI Angular configuration instance
     */
    public MatComponents(MatConfig config) {
        this.config = requireNonNull(config);
    }

    /**
     * Constructs an instance with default {@link MatConfig}.
     *
     * @return the created instance.
     */
    public static MatComponents mat() {
        return new MatComponents();
    }

    /**
     * Constructs an instance with given {@link MatConfig}.
     *
     * @param config the mat config instance
     * @return the created instance.
     */
    public static MatComponents mat(MatConfig config) {
        return new MatComponents(config);
    }

    /**
     * Wraps the given component to {@link MatAutocomplete}.
     *
     * @return the {@link MatAutocomplete} instance.
     */
    public MatAutocomplete toAutocomplete() {
        return new MatAutocomplete(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatAutocomplete} with customized overlayFinder.
     *
     * @param overlayFinder optional, the customized overlayFinder for locating the overlay.
     * @return the {@link MatAutocomplete} instance
     */
    public MatAutocomplete toAutocomplete(@Nullable MatOverlayFinder overlayFinder) {
        return new MatAutocomplete(component, driver, config, overlayFinder);
    }

    /**
     * Wraps the given element to {@link MatAutocomplete} with customized overlayFinder.
     *
     * @param overlayFinder optional, the customized overlayFinder for locating the overlay.
     * @param optionLocator optional, the customized by locator for finding the options.
     * @return the {@link MatAutocomplete} instance
     */
    public MatAutocomplete toAutocomplete(@Nullable MatOverlayFinder overlayFinder, @Nullable By optionLocator) {
        return new MatAutocomplete(component, driver, config, overlayFinder, optionLocator);
    }

    /**
     * Wraps the given element to {@link MatAutocomplete} with customized overlayFinder.
     *
     * @param overlayFinder optional, the customized overlayFinder for locating the overlay.
     * @param optionLocator optional, the customized by locator for finding the options.
     * @param openOptionsAction optional, the actions to open the options
     * @param closeOptionsAction optional, the actions to close the options
     * @return the {@link MatAutocomplete} instance
     */
    public MatAutocomplete toAutocomplete(@Nullable MatOverlayFinder overlayFinder, @Nullable By optionLocator,
            OpenOptionsAction openOptionsAction, CloseOptionsAction closeOptionsAction) {
        return new MatAutocomplete(component, driver, config, overlayFinder, optionLocator, openOptionsAction,
                closeOptionsAction);
    }

    /**
     * Wraps the given element to {@link MatBadge}.
     *
     * @return the {@link MatBadge} instance
     */
    public MatBadge toBadge() {
        return new MatBadge(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatBottomSheet}.
     *
     * @return the {@link MatBottomSheet} instance
     */
    public MatBottomSheet toBottomSheet() {
        return new MatBottomSheet(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatButton}.
     *
     * @return the {@link MatButton} instance
     */
    public MatButton toButton() {
        return new MatButton(component, driver, config);
    }
}
