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
package com.github.grossopa.selenium.examples.mat;

import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;

/**
 * Diagnostic tool to inspect the actual DOM structure of the Angular Material doc site.
 */
public class MatDomInspector extends AbstractBrowserSupport {

    public static void main(String[] args) throws Exception {
        MatDomInspector inspector = new MatDomInspector();
        inspector.setUpDriver(EDGE);

        try {
            // Navigate to button examples
            inspector.navigateToExamples("https://material.angular.dev/components/button/examples");
            
            // Wait for Angular to render
            Thread.sleep(5000);

            // Check what IDs exist on the page
            System.out.println("=== Checking button page structure ===");
            
            // Check if button-overview exists
            var overviewElements = driver.findElements(By.id("button-overview"));
            System.out.println("#button-overview found: " + overviewElements.size());
            
            // Check for button-overview-example
            var exampleElements = driver.findElements(By.tagName("button-overview-example"));
            System.out.println("<button-overview-example> found: " + exampleElements.size());
            
            // Print all example component tag names
            var allExamples = driver.findElements(By.cssSelector("[class*='mat-mdc']"));
            System.out.println("Elements with mat-mdc class: " + allExamples.size());
            
            // Print all IDs on the page
            String ids = (String) driver.executeScript(
                "return Array.from(document.querySelectorAll('[id]')).map(e => e.id).join(', ')");
            System.out.println("All IDs on page: " + ids);
            
            // Print all custom element tag names
            String tags = (String) driver.executeScript(
                "return Array.from(document.querySelectorAll('button-overview-example, [class*=button]'))" +
                ".map(e => e.tagName + '.' + e.className).join('\\n')");
            System.out.println("Button-related elements:\n" + tags);

            // Check sections inside button-overview
            String sections = (String) driver.executeScript(
                "var ex = document.getElementById('button-overview');" +
                "if(!ex) return 'button-overview not found';" +
                "var children = ex.children;" +
                "var result = 'Direct children: ' + children.length + '\\n';" +
                "for(var i = 0; i < children.length; i++) {" +
                "  result += i + ': ' + children[i].tagName + '.' + (children[i].className||'').substring(0,80) + '\\n';" +
                "}" +
                "return result;");
            System.out.println("=== button-overview children ===\n" + sections);

            // Check all sections and their buttons
            String allSections = (String) driver.executeScript(
                "var ex = document.getElementById('button-overview');" +
                "if(!ex) return 'not found';" +
                // Check for button-overview-example
                "var boe = ex.querySelectorAll('button-overview-example');" +
                "var result = 'button-overview-example count: ' + boe.length + '\\n';" +
                // Check the wrapper div structure
                "var wrapper = ex.querySelector('.docs-example-viewer-wrapper');" +
                "if(wrapper) {" +
                "  result += 'wrapper children: ' + wrapper.children.length + '\\n';" +
                "  for(var i = 0; i < wrapper.children.length; i++) {" +
                "    result += '  ' + i + ': ' + wrapper.children[i].tagName + '.' + (wrapper.children[i].className||'').substring(0,80) + '\\n';" +
                "  }" +
                "  var exampleDiv = wrapper.querySelector('.docs-example');" +
                "  if(exampleDiv) {" +
                "    result += 'exampleDiv children: ' + exampleDiv.children.length + '\\n';" +
                "    for(var i = 0; i < exampleDiv.children.length; i++) {" +
                "      result += '  ' + i + ': ' + exampleDiv.children[i].tagName + '\\n';" +
                "    }" +
                "  }" +
                "}" +
                // Check sections
                "var secs = ex.querySelectorAll('section');" +
                "result += '\\nTotal sections: ' + secs.length + '\\n';" +
                "for(var i = 0; i < Math.min(secs.length, 12); i++) {" +
                "  var btns = secs[i].querySelectorAll('[class*=button-base]');" +
                "  var texts = Array.from(btns).map(b => b.textContent.trim()).join(', ');" +
                "  result += 'section[' + i + ']: ' + btns.length + ' buttons: ' + texts + '\\n';" +
                "}" +
                "return result;");
            System.out.println("=== Detailed structure ===\n" + allSections);

        } finally {
            driver.quit();
            driver = null;
        }
    }
}
