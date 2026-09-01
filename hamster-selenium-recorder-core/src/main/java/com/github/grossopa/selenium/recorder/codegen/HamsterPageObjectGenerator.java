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
package com.github.grossopa.selenium.recorder.codegen;

import com.github.grossopa.selenium.component.html.HtmlComponents;
import com.github.grossopa.selenium.component.mui.MuiComponents;
import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.PageElementModel;
import com.github.grossopa.selenium.recorder.model.PageModel;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * The default {@link PageObjectGenerator} implementation based on JavaPoet that generates page object classes in the
 * Hamster component API style. Each page element becomes a lazy locating method, elements detected as known
 * components are converted via {@code findComponent(by).as(components).toXxx()} and the other elements are returned
 * as plain {@code WebComponent}.
 *
 * <p>Example of the generated code:
 * <pre>{@code
 * public class LoginPage {
 *     private final ComponentWebDriver driver;
 *     private final MuiComponents components;
 *
 *     public LoginPage(ComponentWebDriver driver) {
 *         this.driver = driver;
 *         this.components = MuiComponents.mui();
 *     }
 *
 *     public MuiTextField username() {
 *         return driver.findComponent(By.id("username")).as(components).toTextField();
 *     }
 *
 *     public WebComponent banner() {
 *         return driver.findComponent(By.cssSelector("[data-testid=\"banner\"]"));
 *     }
 * }
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.15
 */
public class HamsterPageObjectGenerator implements PageObjectGenerator {

    /**
     * The field name of the component factory instance in the generated page object.
     */
    public static final String COMPONENTS_FIELD = "components";

    /**
     * The field name of the component web driver in the generated page object.
     */
    public static final String DRIVER_FIELD = "driver";

    private static final ClassName COMPONENT_WEB_DRIVER = ClassName.get("com.github.grossopa.selenium.core",
            "ComponentWebDriver");
    private static final ClassName WEB_COMPONENT = ClassName.get("com.github.grossopa.selenium.core.component",
            "WebComponent");
    private static final ClassName BY = ClassName.get("org.openqa.selenium", "By");
    private static final ClassName MUI_COMPONENTS = ClassName.get(MuiComponents.class);
    private static final ClassName HTML_COMPONENTS = ClassName.get(HtmlComponents.class);

    @Override
    public List<Path> generate(List<PageModel> pages, RecorderConfig config) {
        requireNonNull(pages);
        requireNonNull(config);
        List<Path> writtenFiles = new ArrayList<>();
        for (PageModel page : pages) {
            if (page.getElements().isEmpty()) {
                // skip pages without any selected element such as the initial blank page
                continue;
            }
            JavaFile javaFile = buildJavaFile(page, config);
            try {
                javaFile.writeTo(config.getOutputDir());
                writtenFiles.add(config.getOutputDir()
                        .resolve(config.getBasePackage().replace('.', '/'))
                        .resolve(PageObjectNaming.toClassName(page.getName()) + ".java"));
            } catch (IOException exception) {
                throw new UncheckedIOException("Failed to write page object of page: " + page.getName(), exception);
            }
        }
        return writtenFiles;
    }

    /**
     * Builds the {@link JavaFile} of the given page model.
     *
     * @param page the page model to build the java file for
     * @param config the recorder configuration
     * @return the built java file
     */
    public JavaFile buildJavaFile(PageModel page, RecorderConfig config) {
        String className = PageObjectNaming.toClassName(page.getName());
        ClassName componentsType = resolveComponentsType(config);
        TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC)
                .addJavadoc("The page object of page \"$L\" ($L) generated by the hamster selenium recorder.\n",
                        page.getName(), page.getPageKey())
                .addJavadoc("\n@author hamster-selenium-recorder\n@since 1.15\n")
                .addField(FieldSpec.builder(COMPONENT_WEB_DRIVER, DRIVER_FIELD, Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addField(FieldSpec.builder(componentsType, COMPONENTS_FIELD, Modifier.PRIVATE, Modifier.FINAL)
                        .build())
                .addMethod(buildConstructor(className, componentsType, config));
        for (PageElementModel element : page.getElements()) {
            typeBuilder.addMethod(buildElementMethod(element));
        }
        return JavaFile.builder(config.getBasePackage(), typeBuilder.build()).skipJavaLangImports(true)
                .indent("    ").build();
    }

    private MethodSpec buildConstructor(String className, ClassName componentsType, RecorderConfig config) {
        String factoryExpression = switch (config.getFramework()) {
            case MUI -> config.getMuiVersion() == MuiVersion.V5 ? "$T.muiV5()" : "$T.mui()";
            case HTML -> "$T.html()";
            default -> "$T.html()";
        };
        return MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addJavadoc("Constructs an instance with the component web driver.\n\n"
                        + "@param driver the component web driver, must not be null\n")
                .addParameter(COMPONENT_WEB_DRIVER, DRIVER_FIELD)
                .addStatement("this.$N = $N", DRIVER_FIELD, DRIVER_FIELD)
                .addStatement(CodeBlock.of("this.$N = " + factoryExpression, COMPONENTS_FIELD, componentsType))
                .build();
    }

    private MethodSpec buildElementMethod(PageElementModel element) {
        DetectedComponent detected = element.getDetectedComponent();
        CodeBlock byCode = buildByCode(element.getLocator());
        if (detected != null && !detected.isRequiresArgs()) {
            ClassName componentType = ClassName.bestGuess(detected.getTypeQualifiedName());
            return MethodSpec.methodBuilder(element.getFieldName()).addModifiers(Modifier.PUBLIC)
                    .addJavadoc("Finds the $L component \"$L\".\n\n@return the found $L component\n",
                            detected.getTypeName(), element.getFieldName(), detected.getTypeName())
                    .returns(componentType)
                    .addStatement("return $N.findComponent($L).as($N).$N()", DRIVER_FIELD, byCode, COMPONENTS_FIELD,
                            detected.getFactoryMethodName())
                    .build();
        }
        return MethodSpec.methodBuilder(element.getFieldName()).addModifiers(Modifier.PUBLIC)
                .addJavadoc("Finds the web component \"$L\".\n\n@return the found web component\n",
                        element.getFieldName())
                .returns(WEB_COMPONENT)
                .addStatement("return $N.findComponent($L)", DRIVER_FIELD, byCode)
                .build();
    }

    private CodeBlock buildByCode(LocatorCandidate locator) {
        return switch (locator.getType()) {
            case ID -> CodeBlock.of("$T.id($S)", BY, locator.getValue());
            case NAME -> CodeBlock.of("$T.name($S)", BY, locator.getValue());
            case CSS_SELECTOR -> CodeBlock.of("$T.cssSelector($S)", BY, locator.getValue());
            case XPATH -> CodeBlock.of("$T.xpath($S)", BY, locator.getValue());
        };
    }

    private ClassName resolveComponentsType(RecorderConfig config) {
        return switch (config.getFramework()) {
            case MUI -> MUI_COMPONENTS;
            case HTML -> HTML_COMPONENTS;
            default -> HTML_COMPONENTS;
        };
    }
}
