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
package com.github.grossopa.hamster.selenium.component.mat;

import com.github.grossopa.hamster.selenium.component.mat.action.CloseOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.action.OpenOptionsAction;
import com.github.grossopa.hamster.selenium.component.mat.config.MatConfig;
import com.github.grossopa.hamster.selenium.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.hamster.selenium.component.mat.main.*;
import com.github.grossopa.hamster.selenium.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.selenium.core.component.AbstractComponents;
import org.openqa.selenium.By;

import jakarta.annotation.Nullable;

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

    /**
     * Wraps the given element to {@link MatButtonToggleGroup}.
     *
     * @return the {@link MatButtonToggleGroup} instance
     */
    public MatButtonToggleGroup toButtonToggleGroup() {
        return new MatButtonToggleGroup(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatButtonToggle}.
     *
     * @return the {@link MatButtonToggle} instance
     */
    public MatButtonToggle toButtonToggle() {
        return new MatButtonToggle(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatCheckbox}.
     *
     * @return the {@link MatCheckbox} instance
     */
    public MatCheckbox toCheckbox() {
        return new MatCheckbox(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatChipList}.
     *
     * @return the {@link MatChipList} instance
     */
    public MatChipList toChipList() {
        return new MatChipList(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatDialog}.
     *
     * @return the {@link MatDialog} instance
     */
    public MatDialog toDialog() {
        return new MatDialog(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatAccordion}.
     *
     * @return the {@link MatAccordion} instance
     */
    public MatAccordion toAccordion() {
        return new MatAccordion(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatFormField}.
     *
     * @return the {@link MatFormField} instance
     */
    public MatFormField toFormField() {
        return new MatFormField(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatGridList}.
     *
     * @return the {@link MatGridList} instance
     */
    public MatGridList toGridList() {
        return new MatGridList(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatGridTile}.
     *
     * @return the {@link MatGridTile} instance
     */
    public MatGridTile toGridTile() {
        return new MatGridTile(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatList}.
     *
     * @return the {@link MatList} instance
     */
    public MatList toList() {
        return new MatList(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatSelectionList}.
     *
     * @return the {@link MatSelectionList} instance
     */
    public MatSelectionList toSelectionList() {
        return new MatSelectionList(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatMenu}.
     *
     * @return the {@link MatMenu} instance
     */
    public MatMenu toMenu() {
        return new MatMenu(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatMenuItem}.
     *
     * @return the {@link MatMenuItem} instance
     */
    public MatMenuItem toMenuItem() {
        return new MatMenuItem(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatInput}.
     *
     * @return the {@link MatInput} instance
     */
    public MatInput toInput() {
        return new MatInput(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatCard}.
     *
     * @return the {@link MatCard} instance
     */
    public MatCard toCard() {
        return new MatCard(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatProgressBar}.
     *
     * @return the {@link MatProgressBar} instance
     */
    public MatProgressBar toProgressBar() {
        return new MatProgressBar(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatSlider}.
     *
     * @return the {@link MatSlider} instance
     */
    public MatSlider toSlider() {
        return new MatSlider(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatSlideToggle}.
     *
     * @return the {@link MatSlideToggle} instance
     */
    public MatSlideToggle toSlideToggle() {
        return new MatSlideToggle(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatSnackbar}.
     *
     * @return the {@link MatSnackbar} instance
     */
    public MatSnackbar toSnackbar() {
        return new MatSnackbar(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatRadioGroup}.
     *
     * @return the {@link MatRadioGroup} instance
     */
    public MatRadioGroup toRadioGroup() {
        return new MatRadioGroup(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatRadioButton}.
     *
     * @return the {@link MatRadioButton} instance
     */
    public MatRadioButton toRadioButton() {
        return new MatRadioButton(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatSelect}.
     *
     * @return the {@link MatSelect} instance
     */
    public MatSelect toSelect() {
        return new MatSelect(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatSelect} with customized overlayFinder.
     *
     * @param overlayFinder optional, the customized overlayFinder for locating the overlay
     * @return the {@link MatSelect} instance
     */
    public MatSelect toSelect(@Nullable MatOverlayFinder overlayFinder) {
        return new MatSelect(component, driver, config, overlayFinder);
    }

    /**
     * Wraps the given element to {@link MatTabGroup}.
     *
     * @return the {@link MatTabGroup} instance
     */
    public MatTabGroup toTabGroup() {
        return new MatTabGroup(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatTab}.
     *
     * @return the {@link MatTab} instance
     */
    public MatTab toTab() {
        return new MatTab(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatSidenavContainer}.
     *
     * @return the {@link MatSidenavContainer} instance
     */
    public MatSidenavContainer toSidenavContainer() {
        return new MatSidenavContainer(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatSidenav}.
     *
     * @return the {@link MatSidenav} instance
     */
    public MatSidenav toSidenav() {
        return new MatSidenav(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatStepper}.
     *
     * @return the {@link MatStepper} instance
     */
    public MatStepper toStepper() {
        return new MatStepper(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatStep}.
     *
     * @return the {@link MatStep} instance
     */
    public MatStep toStep() {
        return new MatStep(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatTree}.
     *
     * @return the {@link MatTree} instance
     */
    public MatTree toTree() {
        return new MatTree(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatTreeNode}.
     *
     * @return the {@link MatTreeNode} instance
     */
    public MatTreeNode toTreeNode() {
        return new MatTreeNode(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatTable}.
     *
     * @return the {@link MatTable} instance
     */
    public MatTable toTable() {
        return new MatTable(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatPaginator}.
     *
     * @return the {@link MatPaginator} instance
     */
    public MatPaginator toPaginator() {
        return new MatPaginator(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatDatepicker}.
     *
     * @return the {@link MatDatepicker} instance
     */
    public MatDatepicker toDatepicker() {
        return new MatDatepicker(component, driver, config);
    }

    /**
     * Wraps the given element to {@link MatDatepicker} with customized overlayFinder.
     *
     * @param overlayFinder optional, the customized overlayFinder for locating the overlay
     * @return the {@link MatDatepicker} instance
     */
    public MatDatepicker toDatepicker(@Nullable MatOverlayFinder overlayFinder) {
        return new MatDatepicker(component, driver, config, overlayFinder);
    }

    /**
     * Wraps the given element to {@link MatCalendar}.
     *
     * @return the {@link MatCalendar} instance
     */
    public MatCalendar toCalendar() {
        return new MatCalendar(component, driver, config);
    }
}
