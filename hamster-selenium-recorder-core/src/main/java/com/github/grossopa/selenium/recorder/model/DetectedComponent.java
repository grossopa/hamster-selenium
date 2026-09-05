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
package com.github.grossopa.selenium.recorder.model;

import com.github.grossopa.selenium.recorder.config.ComponentFramework;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * The result of a component detection, describing which component library type a DOM element belongs to, e.g. an
 * element with css class {@code MuiButton-root} is detected as {@code MuiButton} with factory method {@code
 * toButton()}.
 *
 * @author Jack Yin
 * @since 1.15
 */
public class DetectedComponent {

    private final ComponentFramework framework;
    private final String componentName;
    private final String typeName;
    private final String typeQualifiedName;
    private final String factoryMethodName;
    private final boolean requiresArgs;

    /**
     * Constructs an instance with the detection details.
     *
     * @param framework the component framework that this component belongs to, must not be null
     * @param componentName the component name, e.g. "Button", must not be null
     * @param typeName the simple type name of the component class, e.g. "MuiButton", must not be null
     * @param typeQualifiedName the fully qualified name of the component class, e.g.
     * "com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton", must not be null
     * @param factoryMethodName the factory method name in the components class, e.g. "toButton", must not be null
     * @param requiresArgs whether the factory method requires additional arguments; if true the generated page object
     * falls back to plain {@code WebComponent} access
     */
    public DetectedComponent(ComponentFramework framework, String componentName, String typeName,
            String typeQualifiedName, String factoryMethodName, boolean requiresArgs) {
        this.framework = requireNonNull(framework);
        this.componentName = requireNonNull(componentName);
        this.typeName = requireNonNull(typeName);
        this.typeQualifiedName = requireNonNull(typeQualifiedName);
        this.factoryMethodName = requireNonNull(factoryMethodName);
        this.requiresArgs = requiresArgs;
    }

    /**
     * Gets the component framework that this component belongs to.
     *
     * @return the component framework
     */
    public ComponentFramework getFramework() {
        return framework;
    }

    /**
     * Gets the component name, e.g. "Button".
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
     * Gets the factory method name in the components class, e.g. "toButton".
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetectedComponent that)) {
            return false;
        }
        return requiresArgs == that.requiresArgs && framework == that.framework
                && componentName.equals(that.componentName) && typeName.equals(that.typeName)
                && typeQualifiedName.equals(that.typeQualifiedName)
                && factoryMethodName.equals(that.factoryMethodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(framework, componentName, typeName, typeQualifiedName, factoryMethodName, requiresArgs);
    }

    @Override
    public String toString() {
        return "DetectedComponent{" + "framework=" + framework + ", componentName='" + componentName + '\''
                + ", typeName='" + typeName + '\'' + ", factoryMethodName='" + factoryMethodName + '\''
                + ", requiresArgs=" + requiresArgs + '}';
    }
}
