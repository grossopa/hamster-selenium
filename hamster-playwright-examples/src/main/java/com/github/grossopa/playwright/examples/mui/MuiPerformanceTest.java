/*
 * Copyright © 2023 the original author or authors.
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

package com.github.grossopa.playwright.examples.mui;

import com.github.grossopa.playwright.component.mui.MuiComponents;
import com.github.grossopa.playwright.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

/**
 * Performance tests for MUI components with Playwright
 * Measures component interaction speed and reliability
 *
 * @author Jack Yin
 * @since 1.12
 */
@SuppressWarnings("all")
public class MuiPerformanceTest extends AbstractBrowserSupport {

    private static final MuiComponents mui = MuiComponents.mui();

    /**
     * Measure button click performance
     */
    public void testButtonClickPerformance() {
        System.out.println("=== Button Click Performance Test ===");
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        
        List<WebComponent> buttons = driver.findComponents(".MuiButton-root");
        if (buttons.isEmpty()) {
            System.out.println("No buttons found - skipping test");
            return;
        }
        
        int iterations = 10;
        long totalTime = 0;
        
        System.out.println("Running " + iterations + " iterations...");
        
        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();
            
            // Find and interact with button
            List<WebComponent> currentButtons = driver.findComponents(".MuiButton-root");
            if (!currentButtons.isEmpty()) {
                mui.setContext(currentButtons.get(0), driver);
                MuiButton button = mui.toButton();
                String text = button.innerText();
                boolean enabled = button.isEnabled();
            }
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            totalTime += duration;
            
            System.out.println("  Iteration " + (i + 1) + ": " + duration + "ms");
        }
        
        double avgTime = (double) totalTime / iterations;
        System.out.println("\nResults:");
        System.out.println("  Total time: " + totalTime + "ms");
        System.out.println("  Average time: " + String.format("%.2f", avgTime) + "ms");
        System.out.println("  Performance: " + (avgTime < 100 ? "Excellent" : avgTime < 500 ? "Good" : "Needs optimization"));
    }

    /**
     * Measure form filling performance
     */
    public void testFormFillingPerformance() {
        System.out.println("\n=== Form Filling Performance Test ===");
        driver.navigate("https://mui.com/material-ui/react-text-field/", 600_000);
        
        List<WebComponent> fields = driver.findComponents(".MuiTextField-root");
        if (fields.isEmpty()) {
            System.out.println("No text fields found - skipping test");
            return;
        }
        
        int iterations = 5;
        long totalTime = 0;
        
        System.out.println("Filling " + fields.size() + " fields, " + iterations + " iterations...");
        
        for (int i = 0; i < iterations; i++) {
            long startTime = System.currentTimeMillis();
            
            // Fill multiple fields
            List<WebComponent> currentFields = driver.findComponents(".MuiTextField-root");
            for (int j = 0; j < Math.min(currentFields.size(), 5); j++) {
                mui.setContext(currentFields.get(j), driver);
                var field = mui.toTextField();
                field.fill("Test value " + j);
            }
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            totalTime += duration;
            
            System.out.println("  Iteration " + (i + 1) + ": " + duration + "ms");
        }
        
        double avgTime = (double) totalTime / iterations;
        System.out.println("\nResults:");
        System.out.println("  Total time: " + totalTime + "ms");
        System.out.println("  Average time: " + String.format("%.2f", avgTime) + "ms");
        System.out.println("  Fields per second: " + String.format("%.2f", (5.0 / avgTime) * 1000));
    }

    /**
     * Measure component finding performance
     */
    public void testComponentFindingPerformance() {
        System.out.println("\n=== Component Finding Performance Test ===");
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        
        String[] selectors = {
            ".MuiButton-root",
            "button",
            "[class*='MuiButton']"
        };
        
        for (String selector : selectors) {
            int iterations = 10;
            long totalTime = 0;
            
            System.out.println("\nTesting selector: " + selector);
            
            for (int i = 0; i < iterations; i++) {
                long startTime = System.currentTimeMillis();
                
                List<WebComponent> components = driver.findComponents(selector);
                int count = components.size();
                
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                totalTime += duration;
            }
            
            double avgTime = (double) totalTime / iterations;
            System.out.println("  Average: " + String.format("%.2f", avgTime) + "ms");
        }
    }

    /**
     * Measure page load and component readiness
     */
    public void testPageLoadPerformance() {
        System.out.println("\n=== Page Load Performance Test ===");
        
        String[] urls = {
            "https://mui.com/material-ui/react-button/",
            "https://mui.com/material-ui/react-text-field/",
            "https://mui.com/material-ui/react-select/"
        };
        
        for (String url : urls) {
            long startTime = System.currentTimeMillis();
            
            driver.navigate(url, 600_000);
            
            // Wait for components to be ready
            List<WebComponent> components = driver.findComponents(".MuiButton-root, .MuiTextField-root, .MuiSelect-root");
            
            long endTime = System.currentTimeMillis();
            long loadTime = endTime - startTime;
            
            System.out.println("  " + url);
            System.out.println("    Load time: " + loadTime + "ms");
            System.out.println("    Components found: " + components.size());
        }
    }

    /**
     * Stress test - rapid component interactions
     */
    public void testStressTest() {
        System.out.println("\n=== Stress Test - Rapid Interactions ===");
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        
        int totalInteractions = 50;
        long startTime = System.currentTimeMillis();
        
        System.out.println("Performing " + totalInteractions + " rapid interactions...");
        
        for (int i = 0; i < totalInteractions; i++) {
            List<WebComponent> buttons = driver.findComponents(".MuiButton-root");
            if (!buttons.isEmpty()) {
                mui.setContext(buttons.get(i % buttons.size()), driver);
                MuiButton button = mui.toButton();
                button.isEnabled(); // Quick check without clicking
            }
            
            if ((i + 1) % 10 == 0) {
                System.out.println("  Completed " + (i + 1) + " interactions");
            }
        }
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        System.out.println("\nResults:");
        System.out.println("  Total interactions: " + totalInteractions);
        System.out.println("  Total time: " + totalTime + "ms");
        System.out.println("  Interactions per second: " + String.format("%.2f", (totalInteractions / (double) totalTime) * 1000));
    }

    /**
     * Compare different locator strategies
     */
    public void testLocatorStrategyComparison() {
        System.out.println("\n=== Locator Strategy Comparison ===");
        driver.navigate("https://mui.com/material-ui/react-button/", 600_000);
        
        // Strategy 1: CSS Class selector
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            driver.findComponents(".MuiButton-root");
        }
        long time1 = System.currentTimeMillis() - start1;
        
        // Strategy 2: Tag name selector
        long start2 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            driver.findComponents("button");
        }
        long time2 = System.currentTimeMillis() - start2;
        
        // Strategy 3: Attribute selector
        long start3 = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            driver.findComponents("[class*='MuiButton']");
        }
        long time3 = System.currentTimeMillis() - start3;
        
        System.out.println("Results (20 iterations each):");
        System.out.println("  CSS Class (.MuiButton-root): " + time1 + "ms");
        System.out.println("  Tag Name (button): " + time2 + "ms");
        System.out.println("  Attribute ([class*='MuiButton']): " + time3 + "ms");
        System.out.println("\nFastest strategy: " + 
            (time1 <= time2 && time1 <= time3 ? "CSS Class" : 
             time2 <= time1 && time2 <= time3 ? "Tag Name" : "Attribute"));
    }

    public static void main(String[] args) {
        MuiPerformanceTest test = new MuiPerformanceTest();
        test.setUpDriver();
        
        try {
            test.testButtonClickPerformance();
            test.testFormFillingPerformance();
            test.testComponentFindingPerformance();
            test.testPageLoadPerformance();
            test.testStressTest();
            test.testLocatorStrategyComparison();
            
            System.out.println("\n=== All Performance tests completed! ===");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Keep browser open for manual inspection
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
