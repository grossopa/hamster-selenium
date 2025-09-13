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

package com.github.grossopa.playwright.examples.html;

import com.github.grossopa.playwright.component.html.HtmlSelect;
import com.github.grossopa.playwright.component.html.HtmlTable;
import com.github.grossopa.playwright.component.html.HtmlTableRow;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests HTML with Playwright
 *
 * @author Jack Yin
 * @since 1.12
 */
public class HtmlShowCase extends AbstractBrowserSupport {

    public void testTable() {
        driver.navigate("https://www.w3schools.com/html/html_tables.asp", 600_000);
        WebComponent tableComponent = driver.findComponent("#customers");
        System.out.println(tableComponent.innerHTML());
        HtmlTable table = new HtmlTable(tableComponent, driver);
        
        WebComponent header = table.getHeader();
        System.out.println(header.innerHTML());
        List<WebComponent> headerCells = header.findComponents("th");
        assertEquals("Company", headerCells.get(0).innerText());
        assertEquals("Contact", headerCells.get(1).innerText());
        assertEquals("Country", headerCells.get(2).innerText());
        
        List<HtmlTableRow> dataRows = table.getDataRows();
        assertEquals(6, dataRows.size());
        assertEquals("Alfreds Futterkiste", dataRows.get(0).getCell(0).innerText());
        assertEquals("Maria Anders", dataRows.get(0).getCell(1).innerText());
        assertEquals("Germany", dataRows.get(0).getCell(2).innerText());
    }

    public void testSelect() {
        driver.navigate("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select", 600_000L);
        var frame = driver.page().frame("iframeResult");

        HtmlSelect select = new HtmlSelect(frame.locator("#cars"), driver);
        
        List<WebComponent> options = select.findComponents("option");
        assertEquals(4, options.size());
        assertEquals("Volvo", options.get(0).innerText());
        assertEquals("Saab", options.get(1).innerText());
        assertEquals("Opel", options.get(2).innerText());
        assertEquals("Audi", options.get(3).innerText());

        select.selectByValue("audi");
        // Note: Playwright's selectOption behavior is different from Selenium's
        // In Playwright, you would typically check the selected value differently
    }

    public static void main(String[] args) {
        HtmlShowCase test = new HtmlShowCase();
        test.setUpDriver();
        test.testTable();
        test.testSelect();
        // Keep browser open for manual inspection
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}