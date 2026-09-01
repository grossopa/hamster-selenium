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
import com.github.grossopa.selenium.recorder.model.DetectedComponent;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Defines one Material UI component for detection and code generation, including the component name used for building
 * the root css class (e.g. {@code Button} to {@code MuiButton-root}), the mapped component type and the factory method
 * of {@code MuiComponents}.
 *
 * @author Jack Yin
 * @since 1.15
 * @see MuiComponentDefinitions
 */
public class MuiComponentDefinition {

    private final String componentName;
    private final String typeName;
    private final String typeQualifiedName;
    private final String factoryMethodName;
    private final boolean requiresArgs;

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
        this.componentName = requireNonNull(componentName);
        this.typeName = requireNonNull(typeName);
        this.typeQualifiedName = requireNonNull(typeQualifiedName);
        this.factoryMethodName = requireNonNull(factoryMethodName);
        this.requiresArgs = requiresArgs;
    }

    /**
     * Gets the component name used for building the root css, e.g. "Button".
     *
     * @return the component name
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * Gets the simple type name of the component class, e.g. "MuiButton".
     *
     * @return the simple type name of the component class
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Gets the fully qualified name of the component class.
     *
     * @return the fully qualified name of the component class
     */
    public String getTypeQualifiedName() {
        return typeQualifiedName;
    }

    /**
     * Gets the factory method name in {@code MuiComponents}, e.g. "toButton".
     *
     * @return the factory method name
     */
    public String getFactoryMethodName() {
        return factoryMethodName;
    }

    /**
     * Whether the factory method requires additional arguments.
     *
     * @return whether the factory method requires additional arguments
     */
    public boolean isRequiresArgs() {
        return requiresArgs;
    }

    /**
     * Creates the {@link DetectedComponent} from this definition.
     *
     * @return the created {@link DetectedComponent}
     */
    public DetectedComponent toDetectedComponent() {
        return new DetectedComponent(ComponentFramework.MUI, componentName, typeName, typeQualifiedName,
                factoryMethodName, requiresArgs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MuiComponentDefinition that)) {
            return false;
        }
        return requiresArgs == that.requiresArgs && componentName.equals(that.componentName)
                && typeName.equals(that.typeName) && typeQualifiedName.equals(that.typeQualifiedName)
                && factoryMethodName.equals(that.factoryMethodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(componentName, typeName, typeQualifiedName, factoryMethodName, requiresArgs);
    }

    @Override
    public String toString() {
        return "MuiComponentDefinition{" + "componentName='" + componentName + '\'' + ", typeName='" + typeName + '\''
                + ", factoryMethodName='" + factoryMethodName + '\'' + ", requiresArgs=" + requiresArgs + '}';
    }
}
