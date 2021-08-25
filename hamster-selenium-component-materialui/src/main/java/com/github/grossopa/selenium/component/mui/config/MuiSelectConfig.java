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

package com.github.grossopa.selenium.component.mui.config;

import com.github.grossopa.selenium.component.mui.action.CloseOptionsAction;
import com.github.grossopa.selenium.component.mui.action.DefaultCloseOptionsAction;
import com.github.grossopa.selenium.component.mui.action.DefaultOpenOptionsAction;
import com.github.grossopa.selenium.component.mui.action.OpenOptionsAction;
import com.github.grossopa.selenium.component.mui.inputs.MuiSelect;
import org.apache.commons.lang3.builder.Builder;
import org.openqa.selenium.By;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

/**
 * The configuration for {@link MuiSelect}.
 *
 * @author Jack Yin
 * @since 1.6
 */
public class MuiSelectConfig {

    private final String optionValueAttribute;
    private final By optionsLocator;
    private final OpenOptionsAction openOptionsAction;
    private final CloseOptionsAction closeOptionsAction;
    private final boolean isMultiple;

    /**
     * Constructs an instance, all params are required.
     *
     * @param optionsLocator the options locator
     * @param optionValueAttribute the option value attribute name
     * @param openOptionsAction the action to open the options
     * @param closeOptionsAction the action to close the options
     * @param isMultiple whether the component supports multiple selection
     */
    public MuiSelectConfig(By optionsLocator, String optionValueAttribute, OpenOptionsAction openOptionsAction,
            CloseOptionsAction closeOptionsAction, boolean isMultiple) {
        requireNonNull(optionsLocator);
        requireNonNull(optionValueAttribute);
        requireNonNull(openOptionsAction);
        requireNonNull(closeOptionsAction);

        this.optionsLocator = optionsLocator;
        this.optionValueAttribute = optionValueAttribute;
        this.openOptionsAction = openOptionsAction;
        this.closeOptionsAction = closeOptionsAction;
        this.isMultiple = isMultiple;
    }

    /**
     * Gets the option value attribute configuration, the default value is "data-value".
     *
     * @return the name of option value attribute
     */
    public String getOptionValueAttribute() {
        return optionValueAttribute;
    }

    /**
     * Gets the locator for locating the popup options. {@link MuiSelect} will first locate the popover element then use
     * the locator for finding the option elements. Use {@code * By.className("MuiMenuItem-root")} if the MuiSelect code
     * is copied from Material UI official website.
     *
     * @return the locator for locating the popup options.
     */
    public By getOptionsLocator() {
        return optionsLocator;
    }

    /**
     * Gets the action to open the options layer, default value is {@link DefaultOpenOptionsAction}
     *
     * @return the action to open the options layer
     */
    public OpenOptionsAction getOpenOptionsAction() {
        return openOptionsAction;
    }

    /**
     * Gets the action to close the options layer, default value is {@link DefaultCloseOptionsAction}
     *
     * @return the action to close the options layer
     */
    public CloseOptionsAction getCloseOptionsAction() {
        return closeOptionsAction;
    }

    /**
     * whether the select component allows multiple selection, default value is false.
     *
     * @return whether the select component allows multiple selection
     */
    public boolean isMultiple() {
        return isMultiple;
    }

    /**
     * Creates the builder.
     *
     * @param optionsLocator the locator for locating the popup options. {@link MuiSelect} will first locate the popover
     * element then use the locator for finding the option elements. Use {@code By.className("MuiMenuItem-root")} if the
     * MuiSelect code is copied from Material UI official website.
     * @return the newly created builder instance
     */
    public static MuiSelectConfigBuilder builder(By optionsLocator) {
        return new MuiSelectConfigBuilder(optionsLocator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiSelectConfig)) {
            return false;
        }
        MuiSelectConfig that = (MuiSelectConfig) o;
        return isMultiple == that.isMultiple && Objects.equals(optionValueAttribute, that.optionValueAttribute)
                && Objects.equals(optionsLocator, that.optionsLocator) && Objects.equals(openOptionsAction,
                that.openOptionsAction) && Objects.equals(closeOptionsAction, that.closeOptionsAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionValueAttribute, optionsLocator, openOptionsAction, closeOptionsAction, isMultiple);
    }

    @Override
    public String toString() {
        return "MuiSelectConfig{" + "optionValueAttribute='" + optionValueAttribute + '\'' + ", optionsLocator="
                + optionsLocator + ", openOptionsAction=" + openOptionsAction + ", closeOptionsAction="
                + closeOptionsAction + ", isMultiple=" + isMultiple + '}';
    }

    /**
     * The builder for {@link MuiSelectConfig}.
     *
     * @author Jack Yin
     * @since 1.6
     */
    public static class MuiSelectConfigBuilder implements Builder<MuiSelectConfig> {
        private final By optionsLocator;

        private String optionValueAttribute;
        private OpenOptionsAction openOptionsAction;
        private CloseOptionsAction closeOptionsAction;
        private boolean isMultiple = false;

        /**
         * Constructs an instance with options locator.
         *
         * @param optionsLocator the locator for locating the popup options. {@link MuiSelect} will first locate the
         * popover element then * use the locator for finding the option elements. Use {@code
         * By.className("MuiMenuItem-root")} if the MuiSelect code is copied from Material UI official website.
         */
        public MuiSelectConfigBuilder(By optionsLocator) {
            requireNonNull(optionsLocator);
            this.optionsLocator = optionsLocator;
        }

        /**
         * Sets the value attribute of each option item
         *
         * @param optionValueAttribute the option value attribute, by default it is "data-value".
         * @return this builder instance
         */
        public MuiSelectConfigBuilder optionValueAttribute(@Nullable String optionValueAttribute) {
            this.optionValueAttribute = optionValueAttribute;
            return this;
        }

        /**
         * Sets the action to open the options.
         *
         * @param openOptionsAction the action to open the options layer, default value is {@link
         * DefaultOpenOptionsAction}
         * @return this builder instance
         */
        public MuiSelectConfigBuilder openOptionsAction(OpenOptionsAction openOptionsAction) {
            this.openOptionsAction = openOptionsAction;
            return this;
        }

        /**
         * Sets the action to close the options.
         *
         * @param closeOptionsAction the action to close the options layer, default value is {@link
         * DefaultCloseOptionsAction}
         * @return this builder instance
         */
        public MuiSelectConfigBuilder closeOptionsAction(CloseOptionsAction closeOptionsAction) {
            this.closeOptionsAction = closeOptionsAction;
            return this;
        }

        /**
         * As of current Material UI version there is no flags indicates whether the component is single or multiple
         * select. So developers should set their own isMultiple flag in the configuration.
         *
         * @param isMultiple whether the select component allows multiple selection, default value is false.
         * @return this builder instance
         */
        public MuiSelectConfigBuilder multiple(boolean isMultiple) {
            this.isMultiple = isMultiple;
            return this;
        }


        @Override
        public MuiSelectConfig build() {
            String optionValueAttributeVal = defaultIfBlank(this.optionValueAttribute, "data-value");
            OpenOptionsAction openOptionsActionVal = defaultIfNull(this.openOptionsAction,
                    DefaultOpenOptionsAction::new);
            CloseOptionsAction closeOptionsActionVal = defaultIfNull(this.closeOptionsAction,
                    DefaultCloseOptionsAction::new);

            return new MuiSelectConfig(this.optionsLocator, optionValueAttributeVal, openOptionsActionVal,
                    closeOptionsActionVal, this.isMultiple);
        }

        private <T> T defaultIfNull(T object, Supplier<T> supplier) {
            if (object == null) {
                return supplier.get();
            }
            return object;
        }
    }
}
