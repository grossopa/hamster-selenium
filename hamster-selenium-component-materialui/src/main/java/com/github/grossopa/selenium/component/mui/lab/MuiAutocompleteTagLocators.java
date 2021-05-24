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

package com.github.grossopa.selenium.component.mui.lab;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.By;

import java.util.Objects;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * The locators for {@link MuiAutocompleteTag}
 *
 * @author Jack Yin
 * @since 1.3
 */
public class MuiAutocompleteTagLocators {

    private final Function<MuiAutocompleteTag, String> labelFinder;
    private final Function<MuiAutocompleteTag, String> valueFinder;
    private final By deleteButtonLocator;

    /**
     * Constructs the instance with label, value and deleteButton locators.
     *
     * @param labelFinder the label finder for locating the label element and find the label value
     * @param valueFinder the value finder for locating the value element and find the value
     * @param deleteButtonLocator the delete button locator
     */
    public MuiAutocompleteTagLocators(Function<MuiAutocompleteTag, String> labelFinder,
            Function<MuiAutocompleteTag, String> valueFinder, By deleteButtonLocator) {
        requireNonNull(labelFinder);
        requireNonNull(valueFinder);
        requireNonNull(deleteButtonLocator);
        this.labelFinder = labelFinder;
        this.valueFinder = valueFinder;
        this.deleteButtonLocator = deleteButtonLocator;
    }

    /**
     * Gets the label finder for locating the label element and find the label value
     *
     * @return the label finder function
     */
    public Function<MuiAutocompleteTag, String> getLabelFinder() {
        return labelFinder;
    }

    /**
     * Gets the value finder for locating the value element and find the value
     *
     * @return the value finder function
     */
    public Function<MuiAutocompleteTag, String> getValueFinder() {
        return valueFinder;
    }

    /**
     * Gets the delete button locator
     *
     * @return the delete button locator
     */
    public By getDeleteButtonLocator() {
        return deleteButtonLocator;
    }

    /**
     * Creates an autocomplete tag locators for locating the Chip as tag item.
     *
     * @param config the mui configuration
     * @return create {@link MuiAutocompleteTagLocators} for locating the Chip as tag item
     */
    @SuppressWarnings("java:S6212")
    public static MuiAutocompleteTagLocators chipLocators(MuiConfig config) {
        Function<MuiAutocompleteTag, String> labelFinder = tag -> {
            WebComponent component = tag.findComponent(By.tagName("span"));
            return component.getText();
        };
        By deleteButtonLocator = By.className(config.getCssPrefix() + "Chip-deleteIcon");
        return new MuiAutocompleteTagLocators(labelFinder, labelFinder, deleteButtonLocator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiAutocompleteTagLocators)) {
            return false;
        }
        MuiAutocompleteTagLocators that = (MuiAutocompleteTagLocators) o;
        return labelFinder.equals(that.labelFinder) && valueFinder.equals(that.valueFinder) && deleteButtonLocator
                .equals(that.deleteButtonLocator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labelFinder, valueFinder, deleteButtonLocator);
    }

    @Override
    public String toString() {
        return "MuiAutocompleteTagLocators{" + "labelFinder=" + labelFinder + ", valueFinder=" + valueFinder
                + ", deleteButtonLocator=" + deleteButtonLocator + '}';
    }
}
