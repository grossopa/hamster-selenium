/*
 * Copyright © 2020 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.selenium.examples.html;

import com.github.grossopa.selenium.component.html.HtmlSelect;
import com.github.grossopa.selenium.component.html.HtmlTable;
import com.github.grossopa.selenium.core.component.api.TableRow;
import com.github.grossopa.selenium.core.driver.WebDriverType;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.github.grossopa.selenium.component.html.HtmlComponents.html;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Tests HTML
 *
 * @author Jack Yin
 * @since 1.0
 */
public class HtmlShowCase extends AbstractBrowserSupport {

    public void testTable() {
        driver.navigate().to("https://www.w3schools.com/html/html_tables.asp");
        WebDriverWait wait = new WebDriverWait(driver, 10L);
        wait.until(visibilityOfElementLocated(By.id("customers")));
        HtmlTable table = driver.findComponent(By.id("customers")).as(html()).toTable();
        TableRow header = table.getHeaderRow();
        assertEquals("Company", header.getCells().get(0).getText());
        assertEquals("Contact", header.getCells().get(1).getText());
        assertEquals("Country", header.getCells().get(2).getText());
        assertEquals("Contact", table.getHeaderLabels().get(1));
        assertEquals("Yoshi Tannamuri", table.getBodyRow(4).getCell("Contact").getText());
        assertEquals(6, table.getBodyRows().size());
        assertEquals("Alfreds Futterkiste", table.getBodyRows().get(0).getCells().get(0).getText());
    }

    public void testSelect() {
        driver.navigate().to("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select");
        WebDriverWait wait = new WebDriverWait(driver, 10L);
        wait.until(visibilityOfElementLocated(By.id("iframeResult")));
        WebDriver frameDriver = driver.switchTo().frame("iframeResult");

        HtmlSelect select = driver.findComponent(By.id("cars")).as(html()).toSelect();
        assertEquals(4, select.getOptions().size());
        assertEquals("Volvo", select.getOptions().get(0).getText());
        assertEquals("Saab", select.getOptions().get(1).getText());
        assertEquals("Opel", select.getOptions().get(2).getText());
        assertEquals("Audi", select.getOptions().get(3).getText());

        select.selectByIndex(3);
        assertEquals("Audi", select.getFirstSelectedOption().getText());
    }

    public static void main(String[] args) {
        HtmlShowCase test = new HtmlShowCase();
        test.setUpDriver(WebDriverType.CHROME);
        test.testTable();
        test.testSelect();
        test.stopDriver();
    }
}
