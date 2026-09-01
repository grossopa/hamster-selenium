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
package com.github.grossopa.playwright.component.mat;

import com.github.grossopa.playwright.component.mat.action.CloseOptionsAction;
import com.github.grossopa.playwright.component.mat.action.OpenOptionsAction;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.playwright.component.mat.main.*;
import com.github.grossopa.playwright.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.playwright.core.AbstractComponents;

import jakarta.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Contains the converters of Material UI Angular components for Playwright.
 *
 * @author Jack Yin
 * @since 1.15
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
     * @return the created instance
     */
    public static MatComponents mat() {
        return new MatComponents();
    }

    /**
     * Constructs an instance with given {@link MatConfig}.
     *
     * @param config the mat config instance
     * @return the created instance
     */
    public static MatComponents mat(MatConfig config) {
        return new MatComponents(config);
    }

    /**
     * Wraps the given component to {@link MatAutocomplete}.
     *
     * @return the {@link MatAutocomplete} instance
     */
    public MatAutocomplete toAutocomplete() {
        return new MatAutocomplete(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatAutocomplete} with customized overlayFinder.
     *
     * @param overlayFinder optional, the customized overlayFinder for locating the overlay
     * @return the {@link MatAutocomplete} instance
     */
    public MatAutocomplete toAutocomplete(@Nullable MatOverlayFinder overlayFinder) {
        return new MatAutocomplete(component.locator(), driver, config, overlayFinder);
    }

    /**
     * Wraps the given component to {@link MatAutocomplete} with customized overlayFinder and option selector.
     *
     * @param overlayFinder optional, the customized overlayFinder for locating the overlay
     * @param optionSelector optional, the customized selector for finding the options
     * @return the {@link MatAutocomplete} instance
     */
    public MatAutocomplete toAutocomplete(@Nullable MatOverlayFinder overlayFinder,
            @Nullable String optionSelector) {
        return new MatAutocomplete(component.locator(), driver, config, overlayFinder, optionSelector);
    }

    /**
     * Wraps the given component to {@link MatAutocomplete} with all customized attributes.
     *
     * @param overlayFinder optional, the customized overlayFinder for locating the overlay
     * @param optionSelector optional, the customized selector for finding the options
     * @param openOptionsAction optional, the actions to open the options
     * @param closeOptionsAction optional, the actions to close the options
     * @return the {@link MatAutocomplete} instance
     */
    public MatAutocomplete toAutocomplete(@Nullable MatOverlayFinder overlayFinder,
            @Nullable String optionSelector, OpenOptionsAction openOptionsAction,
            CloseOptionsAction closeOptionsAction) {
        return new MatAutocomplete(component.locator(), driver, config, overlayFinder, optionSelector,
                openOptionsAction, closeOptionsAction);
    }

    /**
     * Wraps the given component to {@link MatBadge}.
     *
     * @return the {@link MatBadge} instance
     */
    public MatBadge toBadge() {
        return new MatBadge(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatBottomSheet}.
     *
     * @return the {@link MatBottomSheet} instance
     */
    public MatBottomSheet toBottomSheet() {
        return new MatBottomSheet(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatButton}.
     *
     * @return the {@link MatButton} instance
     */
    public MatButton toButton() {
        return new MatButton(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatButtonToggleGroup}.
     *
     * @return the {@link MatButtonToggleGroup} instance
     */
    public MatButtonToggleGroup toButtonToggleGroup() {
        return new MatButtonToggleGroup(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatButtonToggle}.
     *
     * @return the {@link MatButtonToggle} instance
     */
    public MatButtonToggle toButtonToggle() {
        return new MatButtonToggle(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatCheckbox}.
     *
     * @return the {@link MatCheckbox} instance
     */
    public MatCheckbox toCheckbox() {
        return new MatCheckbox(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatChipList}.
     *
     * @return the {@link MatChipList} instance
     */
    public MatChipList toChipList() {
        return new MatChipList(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatDialog}.
     *
     * @return the {@link MatDialog} instance
     */
    public MatDialog toDialog() {
        return new MatDialog(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatAccordion}.
     *
     * @return the {@link MatAccordion} instance
     */
    public MatAccordion toAccordion() {
        return new MatAccordion(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatFormField}.
     *
     * @return the {@link MatFormField} instance
     */
    public MatFormField toFormField() {
        return new MatFormField(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatGridList}.
     *
     * @return the {@link MatGridList} instance
     */
    public MatGridList toGridList() {
        return new MatGridList(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatGridTile}.
     *
     * @return the {@link MatGridTile} instance
     */
    public MatGridTile toGridTile() {
        return new MatGridTile(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatList}.
     *
     * @return the {@link MatList} instance
     */
    public MatList toList() {
        return new MatList(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatSelectionList}.
     *
     * @return the {@link MatSelectionList} instance
     */
    public MatSelectionList toSelectionList() {
        return new MatSelectionList(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatMenuItem}.
     *
     * @return the {@link MatMenuItem} instance
     */
    public MatMenuItem toMenuItem() {
        return new MatMenuItem(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatProgressBar}.
     *
     * @return the {@link MatProgressBar} instance
     */
    public MatProgressBar toProgressBar() {
        return new MatProgressBar(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatSlider}.
     *
     * @return the {@link MatSlider} instance
     */
    public MatSlider toSlider() {
        return new MatSlider(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatSlideToggle}.
     *
     * @return the {@link MatSlideToggle} instance
     */
    public MatSlideToggle toSlideToggle() {
        return new MatSlideToggle(component.locator(), driver, config);
    }

    /**
     * Wraps the given component to {@link MatSnackbar}.
     *
     * @return the {@link MatSnackbar} instance
     */
    public MatSnackbar toSnackbar() {
        return new MatSnackbar(component.locator(), driver, config);
    }
}
