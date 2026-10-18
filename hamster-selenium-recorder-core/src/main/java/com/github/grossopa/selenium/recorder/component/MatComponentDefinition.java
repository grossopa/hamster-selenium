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

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Defines one Angular Material component for detection and code generation, including the root CSS class used for
 * matching (e.g. {@code mat-mdc-button-base} or {@code mat-accordion}), the mapped component type and the factory
 * method of {@code MatComponents}.
 *
 * <p>Unlike Material UI where all components follow the {@code MuiXxx-root} naming convention, Angular Material
 * components use a mix of {@code mat-mdc-*} and {@code mat-*} CSS prefixes with sometimes non-obvious root class
 * names. This definition captures the exact root CSS class for each component.</p>
 *
 * @author Jack Yin
 * @since 1.17
 * @see MatComponentDefinitions
 * @see MatComponentDetector
 */
public class MatComponentDefinition extends AbstractComponentDefinition {

    private final String rootCssClass;

    /**
     * Constructs an instance with the component details.
     *
     * @param componentName the component name, e.g. "Button", must not be null
     * @param typeName the simple type name of the component class, e.g. "MatButton", must not be null
     * @param typeQualifiedName the fully qualified name of the component class, must not be null
     * @param factoryMethodName the factory method name in {@code MatComponents}, e.g. "toButton", must not be null
     * @param requiresArgs whether the factory method requires additional arguments
     * @param rootCssClass the root CSS class for detection matching, e.g. "mat-mdc-button-base", must not be null
     */
    public MatComponentDefinition(String componentName, String typeName, String typeQualifiedName,
            String factoryMethodName, boolean requiresArgs, String rootCssClass) {
        super(componentName, typeName, typeQualifiedName, factoryMethodName, requiresArgs);
        this.rootCssClass = requireNonNull(rootCssClass);
    }

    /**
     * Gets the root CSS class used for detection matching, e.g. "mat-mdc-button-base".
     *
     * @return the root CSS class
     */
    public String getRootCssClass() {
        return rootCssClass;
    }

    @Override
    protected ComponentFramework framework() {
        return ComponentFramework.MAT;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof MatComponentDefinition that)) {
            return false;
        }
        return rootCssClass.equals(that.rootCssClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rootCssClass);
    }

    @Override
    public String toString() {
        return "MatComponentDefinition{" + "componentName='" + getComponentName() + '\'' + ", typeName='" + getTypeName()
                + '\'' + ", factoryMethodName='" + getFactoryMethodName() + '\'' + ", requiresArgs=" + isRequiresArgs()
                + ", rootCssClass='" + rootCssClass + '\'' + '}';
    }
}
