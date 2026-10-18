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

import com.github.grossopa.selenium.recorder.config.ComponentFramework;

/**
 * Defines one Material UI component for detection and code generation, including the component name used for building
 * the root css class (e.g. {@code Button} to {@code MuiButton-root}), the mapped component type and the factory method
 * of {@code MuiComponents}.
 *
 * @author Jack Yin
 * @since 1.15
 * @see MuiComponentDefinitions
 */
public class MuiComponentDefinition extends AbstractComponentDefinition {

    /**
     * Constructs an instance with the component details.
     *
     * @param componentName the component name used for building the root css, e.g. "Button", must not be null
     * @param typeName the simple type name of the component class, e.g. "MuiButton", must not be null
     * @param typeQualifiedName the fully qualified name of the component class, must not be null
     * @param factoryMethodName the factory method name in {@code MuiComponents}, e.g. "toButton", must not be null
     * @param requiresArgs whether the factory method requires additional arguments
     */
    public MuiComponentDefinition(String componentName, String typeName, String typeQualifiedName,
            String factoryMethodName, boolean requiresArgs) {
        super(componentName, typeName, typeQualifiedName, factoryMethodName, requiresArgs);
    }

    @Override
    protected ComponentFramework framework() {
        return ComponentFramework.MUI;
    }

    @Override
    public String toString() {
        return "MuiComponentDefinition{" + "componentName='" + getComponentName() + '\'' + ", typeName='" + getTypeName()
                + '\'' + ", factoryMethodName='" + getFactoryMethodName() + '\'' + ", requiresArgs=" + isRequiresArgs()
                + '}';
    }
}
