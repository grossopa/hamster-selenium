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
package com.github.grossopa.selenium.recorder.session;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.DefaultComponentWebDriver;
import com.github.grossopa.selenium.core.intercepting.InterceptingWebDriver;
import com.github.grossopa.selenium.recorder.codegen.HamsterPageObjectGenerator;
import com.github.grossopa.selenium.recorder.codegen.PageObjectGenerator;
import com.github.grossopa.selenium.recorder.component.ComponentDetector;
import com.github.grossopa.selenium.recorder.component.ComponentDetectors;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.model.DetectedComponent;
import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.PageElementModel;
import com.github.grossopa.selenium.recorder.model.PageModel;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.monitor.RecorderEventListener;
import com.github.grossopa.selenium.recorder.monitor.RecorderEventType;
import com.github.grossopa.selenium.recorder.monitor.RecordingInterceptingHandler;
import com.github.grossopa.selenium.recorder.page.PageIdentification;
import com.github.grossopa.selenium.recorder.page.PageIdentificationStrategy;
import com.github.grossopa.selenium.recorder.page.PageRegistry;
import com.github.grossopa.selenium.recorder.scan.DefaultElementScanner;
import com.github.grossopa.selenium.recorder.scan.ElementScanner;
import jakarta.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * The orchestration of one recorder session. It wraps the user provided {@link WebDriver} with the intercepting
 * mechanism for the runtime real-time monitoring, provides the scanning and guided selection of page elements,
 * manages the page classification and finally generates the page object model source code.
 *
 * <p>Example usage:
 * <pre>{@code
 * try (RecorderSession session = new RecorderSession(driver, RecorderConfig.builder().build())) {
 *     session.scan();
 *     session.select(3, "loginButton");
 *     session.generate();
 * }
 * }</pre>
 *
 * @author Jack Yin
 * @since 1.15
 * @see RecorderConfig
 * @see PageRegistry
 */
public class RecorderSession implements AutoCloseable {

    private final WebDriver rawDriver;
    private final RecorderConfig config;
    private final RecordingInterceptingHandler handler;
    private final ComponentWebDriver driver;
    private final ElementScanner scanner;
    private final List<ComponentDetector> detectors;
    private final PageRegistry registry;
    private final PageObjectGenerator generator;
    private List<ScannedElement> scannedElements = new ArrayList<>();

    /**
     * Constructs an instance with the user provided driver and the recorder configuration, using the default scanner,
     * the detectors of the configured framework and the Hamster page object generator.
     *
     * @param driver the user provided web driver to be wrapped for monitoring, must not be null
     * @param config the recorder configuration, must not be null
     */
    public RecorderSession(WebDriver driver, RecorderConfig config) {
        this(driver, config, new DefaultElementScanner(config), ComponentDetectors.forFramework(config),
                new HamsterPageObjectGenerator());
    }

    /**
     * Constructs an instance with all customizable collaborators, mainly used for testing.
     *
     * @param rawDriver the user provided web driver to be wrapped for monitoring, must not be null
     * @param config the recorder configuration, must not be null
     * @param scanner the element scanner, must not be null
     * @param detectors the component detectors of the selected framework, must not be null
     * @param generator the page object generator, must not be null
     */
    public RecorderSession(WebDriver rawDriver, RecorderConfig config, ElementScanner scanner,
            List<ComponentDetector> detectors, PageObjectGenerator generator) {
        this.rawDriver = requireNonNull(rawDriver);
        this.config = requireNonNull(config);
        this.scanner = requireNonNull(scanner);
        this.detectors = new ArrayList<>(requireNonNull(detectors));
        this.generator = requireNonNull(generator);
        this.registry = new PageRegistry(config.getPageStrategy());
        this.handler = new RecordingInterceptingHandler(rawDriver::getCurrentUrl);
        this.handler.addListener(event -> {
            if (event.getType() == RecorderEventType.PAGE_CHANGED && event.getUrl() != null) {
                registry.classify(event.getUrl());
            }
        });
        this.driver = new DefaultComponentWebDriver(new InterceptingWebDriver(rawDriver, handler));
        initialize();
    }

    /**
     * Gets the intercepting component web driver wrapped by this session, all user interactions through this driver
     * are monitored.
     *
     * @return the intercepting component web driver
     */
    public ComponentWebDriver getDriver() {
        return driver;
    }

    /**
     * Gets the recorder configuration of this session.
     *
     * @return the recorder configuration
     */
    public RecorderConfig getConfig() {
        return config;
    }

    /**
     * Scans the current page for the key interactive elements, marks them with temporary attributes and detects the
     * known components of the selected framework.
     *
     * @return the scanned elements with their locator candidates
     */
    public List<ScannedElement> scan() {
        scannedElements = scanner.scan(driver);
        for (ScannedElement element : scannedElements) {
            detectComponent(element);
        }
        return scannedElements;
    }

    /**
     * Gets the scanned elements of the last {@link #scan()} invocation.
     *
     * @return the scanned elements, empty if not scanned yet
     */
    public List<ScannedElement> getScannedElements() {
        return scannedElements;
    }

    /**
     * Selects the scanned element with the given index into the current page with the given field name.
     *
     * @param index the scan index of the element to select
     * @param fieldName the field/method name of the element in the generated page object, must be a valid java
     * identifier
     * @return the added page element
     * @throws IllegalArgumentException if the index is out of range, the field name is invalid or already exists
     */
    public PageElementModel select(int index, String fieldName) {
        ScannedElement scanned = findScanned(index);
        validateFieldName(fieldName);
        PageModel page = currentOrClassify();
        LocatorCandidate locator = Optional.ofNullable(scanned.getBestLocator())
                .orElseThrow(() -> new IllegalArgumentException("No locator candidate found for element: " + index));
        PageElementModel element = new PageElementModel(fieldName, locator, scanned.getDetectedComponent());
        if (!page.addElement(element)) {
            throw new IllegalArgumentException("Field name already exists in page " + page.getName() + ": " + fieldName);
        }
        return element;
    }

    /**
     * Gets all collected pages.
     *
     * @return all collected pages
     */
    public List<PageModel> getPages() {
        return registry.getPages();
    }

    /**
     * Gets the current page that new selections are added to.
     *
     * @return the current page, null if no page is classified yet
     */
    @Nullable
    public PageModel getCurrentPage() {
        return registry.getCurrentPage();
    }

    /**
     * Explicitly opens a new page with the given name, overriding the strategy decision. The page key is derived from
     * the current url with the configured strategy.
     *
     * @param name the display name of the new page
     * @return the created page
     */
    public PageModel newPage(String name) {
        String currentUrl = rawDriver.getCurrentUrl();
        PageIdentification identification = registry.getStrategy().identify(currentUrl, registry.getPages());
        String pageKey = identification.isMatched() ? identification.getMatchedPage().getPageKey()
                : identification.getSuggestedKey();
        return registry.newPage(name, pageKey);
    }

    /**
     * Explicitly switches to the existing page with the given name, overriding the strategy decision.
     *
     * @param name the name of the existing page to use
     * @return the matched page
     * @throws IllegalArgumentException if no page with the given name exists
     */
    public PageModel usePage(String name) {
        return registry.usePage(name);
    }

    /**
     * Replaces the page identification strategy at runtime.
     *
     * @param strategy the new page identification strategy to set
     */
    public void setPageStrategy(PageIdentificationStrategy strategy) {
        registry.setStrategy(strategy);
        config.setPageStrategy(strategy);
    }

    /**
     * Registers a listener to receive the recorder events such as interactions and page changes.
     *
     * @param listener the listener to add
     */
    public void addEventListener(RecorderEventListener listener) {
        handler.addListener(listener);
    }

    /**
     * Generates the page object source code for all collected pages into the configured output directory.
     *
     * @return the paths of the written source files
     */
    public List<Path> generate() {
        return generator.generate(registry.getPages(), config);
    }

    @Override
    public void close() {
        DefaultElementScanner.clearMarkers(driver);
        driver.quit();
    }

    private void initialize() {
        String url = rawDriver.getCurrentUrl();
        handler.initializeUrl(url);
        registry.classify(url);
    }

    private ScannedElement findScanned(int index) {
        return scannedElements.stream().filter(element -> element.getIndex() == index).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No scanned element found with index: " + index + ", please run scan first"));
    }

    private PageModel currentOrClassify() {
        PageModel current = registry.getCurrentPage();
        return current != null ? current : registry.classify(rawDriver.getCurrentUrl());
    }

    private void detectComponent(ScannedElement element) {
        if (detectors.isEmpty()) {
            return;
        }
        try {
            WebElement webElement = driver.findElement(DefaultElementScanner.markerLocator(element.getIndex()));
            for (ComponentDetector detector : detectors) {
                Optional<DetectedComponent> detected = detector.detect(webElement, driver);
                if (detected.isPresent()) {
                    element.setDetectedComponent(detected.get());
                    return;
                }
            }
        } catch (RuntimeException exception) {
            // the element may be gone (e.g. page changed), skip detection
        }
    }

    private void validateFieldName(String fieldName) {
        requireNonNull(fieldName);
        if (fieldName.isEmpty() || !Character.isJavaIdentifierStart(fieldName.charAt(0))) {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
        for (int i = 1; i < fieldName.length(); i++) {
            if (!Character.isJavaIdentifierPart(fieldName.charAt(i))) {
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
            }
        }
    }
}
