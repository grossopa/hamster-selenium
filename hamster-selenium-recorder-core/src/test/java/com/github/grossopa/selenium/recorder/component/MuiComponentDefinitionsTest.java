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
package com.github.grossopa.selenium.recorder.component;

import com.github.grossopa.selenium.component.mui.MuiComponent;
import com.github.grossopa.selenium.component.mui.MuiComponents;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link MuiComponentDefinitions}
 *
 * @author Jack Yin
 * @since 1.15
 */
class MuiComponentDefinitionsTest {

    List<MuiComponentDefinition> defaults = MuiComponentDefinitions.defaults();

    @Test
    void testDefaultsNotEmpty() {
        assertFalse(defaults.isEmpty());
    }

    @Test
    void testComponentNamesUnique() {
        Set<String> names = defaults.stream().map(MuiComponentDefinition::getComponentName)
                .collect(Collectors.toSet());
        assertEquals(defaults.size(), names.size(), "duplicated component names found");
    }

    @Test
    void testFactoryMethodsExistOnMuiComponents() {
        for (MuiComponentDefinition definition : defaults) {
            List<Method> methods = Arrays.stream(MuiComponents.class.getMethods())
                    .filter(method -> method.getName().equals(definition.getFactoryMethodName()))
                    .toList();
            assertFalse(methods.isEmpty(),
                    "factory method not found on MuiComponents: " + definition.getFactoryMethodName());
            if (!definition.isRequiresArgs()) {
                assertTrue(methods.stream().anyMatch(method -> method.getParameterCount() == 0),
                        "factory method without args not found: " + definition.getFactoryMethodName());
            }
        }
    }

    @Test
    void testAutoDiscoveredComponentsHaveNoRequiresArgs() {
        defaults.stream().filter(d -> !d.isRequiresArgs()).forEach(d -> assertTrue(Arrays.stream(MuiComponents.class.getMethods()).anyMatch(m -> m.getName()
                        .equals(d.getFactoryMethodName()) && m.getParameterCount() == 0
                        && MuiComponent.class.isAssignableFrom(m.getReturnType())),
                "no-arg factory method not found for: " + d.getFactoryMethodName()));
    }

    @Test
    void testSelectRequiresArgs() {
        List<MuiComponentDefinition> selectDefs = defaults.stream()
                .filter(d -> d.getComponentName().equals("Select")).toList();
        assertEquals(1, selectDefs.size());
        assertTrue(selectDefs.get(0).isRequiresArgs());
    }

    @Test
    void testAllNoArgFactoryMethodsDiscovered() {
        Map<String, MuiComponentDefinition> byFactoryMethod = defaults.stream()
                .collect(Collectors.toMap(MuiComponentDefinition::getFactoryMethodName, d -> d));

        for (Method method : MuiComponents.class.getMethods()) {
            if (!method.getName().startsWith("to")) {
                continue;
            }
            if (method.getParameterCount() != 0) {
                continue;
            }
            if (!MuiComponent.class.isAssignableFrom(method.getReturnType())) {
                continue;
            }
            if (method.getReturnType().getName().contains(".finder.")) {
                continue;
            }
            assertTrue(byFactoryMethod.containsKey(method.getName()),
                    "auto-discovered method missing from defaults: " + method.getName());
        }
    }
}
