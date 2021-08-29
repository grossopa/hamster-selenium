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
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiSelectConfig}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MuiSelectConfigTest {

    By optionsLocator = mock(By.class);
    String optionValueAttribute = "value1";
    OpenOptionsAction openOptionsAction = mock(OpenOptionsAction.class);
    CloseOptionsAction closeOptionsAction = mock(CloseOptionsAction.class);

    MuiSelectConfig testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MuiSelectConfig(optionsLocator, optionValueAttribute, openOptionsAction, closeOptionsAction,
                true);
    }


    @Test
    void getOptionValueAttribute() {
        assertEquals(optionValueAttribute, testSubject.getOptionValueAttribute());
    }

    @Test
    void getOptionsLocator() {
        assertEquals(optionsLocator, testSubject.getOptionsLocator());
    }

    @Test
    void getOpenOptionsAction() {
        assertEquals(openOptionsAction, testSubject.getOpenOptionsAction());
    }

    @Test
    void getCloseOptionsAction() {
        assertEquals(closeOptionsAction, testSubject.getCloseOptionsAction());
    }

    @Test
    void isMultiple() {
        assertTrue(testSubject.isMultiple());
    }

    @Test
    void builder() {
        MuiSelectConfig config = MuiSelectConfig.builder(optionsLocator).multiple(true)
                .openOptionsAction(openOptionsAction).closeOptionsAction(closeOptionsAction)
                .optionValueAttribute(optionValueAttribute).build();

        assertEquals(optionValueAttribute, config.getOptionValueAttribute());
        assertEquals(optionsLocator, config.getOptionsLocator());
        assertEquals(openOptionsAction, config.getOpenOptionsAction());
        assertEquals(closeOptionsAction, config.getCloseOptionsAction());
        assertTrue(config.isMultiple());
    }

    @Test
    void builderDefault() {
        MuiSelectConfig config = MuiSelectConfig.builder(optionsLocator).build();

        assertEquals(optionsLocator, config.getOptionsLocator());
        assertEquals("data-value", config.getOptionValueAttribute());
        assertEquals(DefaultOpenOptionsAction.class, config.getOpenOptionsAction().getClass());
        assertEquals(DefaultCloseOptionsAction.class, config.getCloseOptionsAction().getClass());
        assertFalse(config.isMultiple());
    }

    @Test
    void testEquals() {
        By optionsLocator1 = mock(By.class);
        By optionsLocator2 = mock(By.class);
        String optionValueAttribute1 = "value1";
        String optionValueAttribute2 = "value2";
        OpenOptionsAction openOptionsAction1 = mock(OpenOptionsAction.class);
        OpenOptionsAction openOptionsAction2 = mock(OpenOptionsAction.class);
        CloseOptionsAction closeOptionsAction1 = mock(CloseOptionsAction.class);
        CloseOptionsAction closeOptionsAction2 = mock(CloseOptionsAction.class);

        SimpleEqualsTester tester = new SimpleEqualsTester();
        tester.addEqualityGroup(
                new MuiSelectConfig(optionsLocator1, optionValueAttribute1, openOptionsAction1, closeOptionsAction1,
                        true),
                new MuiSelectConfig(optionsLocator1, optionValueAttribute1, openOptionsAction1, closeOptionsAction1,
                        true));

        tester.addEqualityGroup(
                new MuiSelectConfig(optionsLocator2, optionValueAttribute1, openOptionsAction1, closeOptionsAction1,
                        true));
        tester.addEqualityGroup(
                new MuiSelectConfig(optionsLocator1, optionValueAttribute2, openOptionsAction1, closeOptionsAction1,
                        true));
        tester.addEqualityGroup(
                new MuiSelectConfig(optionsLocator1, optionValueAttribute1, openOptionsAction2, closeOptionsAction1,
                        true));
        tester.addEqualityGroup(
                new MuiSelectConfig(optionsLocator1, optionValueAttribute1, openOptionsAction1, closeOptionsAction2,
                        true));
        tester.addEqualityGroup(
                new MuiSelectConfig(optionsLocator1, optionValueAttribute1, openOptionsAction1, closeOptionsAction1,
                        false));

        tester.testEquals();
    }

    @Test
    void testToString() {
        when(optionsLocator.toString()).thenReturn("options-locator");
        when(openOptionsAction.toString()).thenReturn("open-options-action");
        when(closeOptionsAction.toString()).thenReturn("close-options-action");
        assertEquals("MuiSelectConfig{optionValueAttribute='value1', optionsLocator=options-locator, "
                + "openOptionsAction=open-options-action, closeOptionsAction=close-options-action, "
                + "isMultiple=true}", testSubject.toString());
    }
}
