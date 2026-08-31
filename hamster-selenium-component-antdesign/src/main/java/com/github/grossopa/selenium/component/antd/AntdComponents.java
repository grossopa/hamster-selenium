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
package com.github.grossopa.selenium.component.antd;

import com.github.grossopa.selenium.component.antd.config.AntdConfig;
import com.github.grossopa.selenium.component.antd.dataentry.*;
import com.github.grossopa.selenium.component.antd.datadisplay.*;
import com.github.grossopa.selenium.component.antd.feedback.*;
import com.github.grossopa.selenium.component.antd.general.AntdButton;
import com.github.grossopa.selenium.component.antd.layout.*;
import com.github.grossopa.selenium.component.antd.navigation.*;
import com.github.grossopa.selenium.core.component.AbstractComponents;
import com.github.grossopa.selenium.core.component.WebComponent;

import static java.util.Objects.requireNonNull;

/**
 * This class contains all Antd components that a {@link com.github.grossopa.selenium.core.component.WebComponent} could
 * be converted to.
 *
 * <p>The components are organized following the Ant Design documentation categories:
 * <ul>
 *   <li><strong>General:</strong> Button</li>
 *   <li><strong>Layout:</strong> Divider, Space</li>
 *   <li><strong>Navigation:</strong> Breadcrumb, Menu, Pagination, Steps</li>
 *   <li><strong>Data Entry:</strong> Checkbox, Radio, Select, Input, Switch</li>
 *   <li><strong>Data Display:</strong> Avatar, Badge, Card, Collapse, List, Tabs, Tag, Empty</li>
 *   <li><strong>Feedback:</strong> Alert, Drawer, Modal, Progress, Skeleton, Spin</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.4
 */
public class AntdComponents extends AbstractComponents {

    AntdConfig config;

    /**
     * Constructs an instance with default {@link AntdConfig}.
     */
    public AntdComponents() {
        this(new AntdConfig());
    }

    /**
     * Constructs an instance with provided {@link AntdConfig}.
     *
     * @param config the Antd configuration instance
     */
    public AntdComponents(AntdConfig config) {
        this.config = requireNonNull(config);
    }

    /**
     * Creates an instance of {@link AntdComponents} with default {@link AntdConfig}.
     *
     * @return the newly created instance with default {@link AntdConfig}.
     */
    public static AntdComponents antd() {
        return new AntdComponents();
    }

    /**
     * Creates an instance of {@link AntdComponents} with given {@link AntdConfig}.
     *
     * @param config the config instance
     * @return the instance of {@link AntdComponents} with given {@link AntdConfig}.
     */
    public static AntdComponents antd(AntdConfig config) {
        return new AntdComponents(config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdButton} instance.
     *
     * @return wrapped {@link AntdButton} instance on the given component
     */
    public AntdButton toButton() {
        return new AntdButton(component, driver, config);
    }

    // Layout components

    /**
     * Wraps the current {@link WebComponent} to {@link AntdDivider} instance.
     *
     * @return wrapped {@link AntdDivider} instance on the given component
     * @since 1.15
     */
    public AntdDivider toDivider() {
        return new AntdDivider(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdSpace} instance.
     *
     * @return wrapped {@link AntdSpace} instance on the given component
     * @since 1.15
     */
    public AntdSpace toSpace() {
        return new AntdSpace(component, driver, config);
    }

    // Navigation components

    /**
     * Wraps the current {@link WebComponent} to {@link AntdBreadcrumb} instance.
     *
     * @return wrapped {@link AntdBreadcrumb} instance on the given component
     * @since 1.15
     */
    public AntdBreadcrumb toBreadcrumb() {
        return new AntdBreadcrumb(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdMenu} instance.
     *
     * @return wrapped {@link AntdMenu} instance on the given component
     * @since 1.15
     */
    public AntdMenu toMenu() {
        return new AntdMenu(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdPagination} instance.
     *
     * @return wrapped {@link AntdPagination} instance on the given component
     * @since 1.15
     */
    public AntdPagination toPagination() {
        return new AntdPagination(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdSteps} instance.
     *
     * @return wrapped {@link AntdSteps} instance on the given component
     * @since 1.15
     */
    public AntdSteps toSteps() {
        return new AntdSteps(component, driver, config);
    }

    // Data Entry components

    /**
     * Wraps the current {@link WebComponent} to {@link AntdCheckbox} instance.
     *
     * @return wrapped {@link AntdCheckbox} instance on the given component
     * @since 1.15
     */
    public AntdCheckbox toCheckbox() {
        return new AntdCheckbox(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdRadio} instance.
     *
     * @return wrapped {@link AntdRadio} instance on the given component
     * @since 1.15
     */
    public AntdRadio toRadio() {
        return new AntdRadio(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdSwitch} instance.
     *
     * @return wrapped {@link AntdSwitch} instance on the given component
     * @since 1.15
     */
    public AntdSwitch toSwitch() {
        return new AntdSwitch(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdInput} instance.
     *
     * @return wrapped {@link AntdInput} instance on the given component
     * @since 1.15
     */
    public AntdInput toInput() {
        return new AntdInput(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdSelect} instance.
     *
     * @return wrapped {@link AntdSelect} instance on the given component
     * @since 1.15
     */
    public AntdSelect toSelect() {
        return new AntdSelect(component, driver, config);
    }

    // Data Display components

    /**
     * Wraps the current {@link WebComponent} to {@link AntdAvatar} instance.
     *
     * @return wrapped {@link AntdAvatar} instance on the given component
     * @since 1.15
     */
    public AntdAvatar toAvatar() {
        return new AntdAvatar(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdBadge} instance.
     *
     * @return wrapped {@link AntdBadge} instance on the given component
     * @since 1.15
     */
    public AntdBadge toBadge() {
        return new AntdBadge(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdCard} instance.
     *
     * @return wrapped {@link AntdCard} instance on the given component
     * @since 1.15
     */
    public AntdCard toCard() {
        return new AntdCard(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdCollapse} instance.
     *
     * @return wrapped {@link AntdCollapse} instance on the given component
     * @since 1.15
     */
    public AntdCollapse toCollapse() {
        return new AntdCollapse(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdList} instance.
     *
     * @return wrapped {@link AntdList} instance on the given component
     * @since 1.15
     */
    public AntdList toList() {
        return new AntdList(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdTabs} instance.
     *
     * @return wrapped {@link AntdTabs} instance on the given component
     * @since 1.15
     */
    public AntdTabs toTabs() {
        return new AntdTabs(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdTag} instance.
     *
     * @return wrapped {@link AntdTag} instance on the given component
     * @since 1.15
     */
    public AntdTag toTag() {
        return new AntdTag(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdEmpty} instance.
     *
     * @return wrapped {@link AntdEmpty} instance on the given component
     * @since 1.15
     */
    public AntdEmpty toEmpty() {
        return new AntdEmpty(component, driver, config);
    }

    // Feedback components

    /**
     * Wraps the current {@link WebComponent} to {@link AntdAlert} instance.
     *
     * @return wrapped {@link AntdAlert} instance on the given component
     * @since 1.15
     */
    public AntdAlert toAlert() {
        return new AntdAlert(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdModal} instance.
     *
     * @return wrapped {@link AntdModal} instance on the given component
     * @since 1.15
     */
    public AntdModal toModal() {
        return new AntdModal(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdProgress} instance.
     *
     * @return wrapped {@link AntdProgress} instance on the given component
     * @since 1.15
     */
    public AntdProgress toProgress() {
        return new AntdProgress(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdSkeleton} instance.
     *
     * @return wrapped {@link AntdSkeleton} instance on the given component
     * @since 1.15
     */
    public AntdSkeleton toSkeleton() {
        return new AntdSkeleton(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdSpin} instance.
     *
     * @return wrapped {@link AntdSpin} instance on the given component
     * @since 1.15
     */
    public AntdSpin toSpin() {
        return new AntdSpin(component, driver, config);
    }

    /**
     * Wraps the current {@link WebComponent} to {@link AntdDrawer} instance.
     *
     * @return wrapped {@link AntdDrawer} instance on the given component
     * @since 1.15
     */
    public AntdDrawer toDrawer() {
        return new AntdDrawer(component, driver, config);
    }
}
