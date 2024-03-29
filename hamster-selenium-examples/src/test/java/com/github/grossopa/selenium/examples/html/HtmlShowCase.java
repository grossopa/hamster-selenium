/*
 * Copyright © 2021 the original author or authors.
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
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.component.api.TableRow;
import com.github.grossopa.selenium.core.driver.WebDriverType;
import com.github.grossopa.selenium.core.element.TextNodeElement;
import com.github.grossopa.selenium.core.util.SeleniumUtils;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static com.github.grossopa.selenium.component.html.HtmlComponents.html;
import static com.github.grossopa.selenium.core.element.TextNodeType.COMMENT;
import static com.github.grossopa.selenium.core.element.TextNodeType.TEXT;
import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10L));
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

    public void testTableNoHeader() {
        driver.navigate().to("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_table_basic");
        driver.switchTo().frame("iframeResult");

        HtmlTable table = driver.findComponent(
                        xpathBuilder().anywhere().text().exact("3 Rows and 3 Columns:").followingSibling("table").build())
                .as(html()).toTable();
        assertEquals(3, table.getBodyRows().size());

        assertEquals("100", table.getBodyRow(0).getCells().get(0).getText());
        assertEquals("200", table.getBodyRow(0).getCells().get(1).getText());
        assertEquals("300", table.getBodyRow(0).getCells().get(2).getText());
        assertEquals("400", table.getBodyRow(1).getCells().get(0).getText());
        assertEquals("500", table.getBodyRow(1).getCells().get(1).getText());
        assertEquals("600", table.getBodyRow(1).getCells().get(2).getText());
        assertEquals("700", table.getBodyRow(2).getCells().get(0).getText());
        assertEquals("800", table.getBodyRow(2).getCells().get(1).getText());
        assertEquals("900", table.getBodyRow(2).getCells().get(2).getText());

        List<WebComponent> components = table.findComponents(
                xpathBuilder().anywhereRelative("td").text().not().exact("200").build());
        assertEquals(8, components.size());
        assertEquals("100", components.get(0).getText());
        assertEquals("300", components.get(1).getText());
        assertEquals("400", components.get(2).getText());
        assertEquals("500", components.get(3).getText());
        assertEquals("600", components.get(4).getText());
        assertEquals("700", components.get(5).getText());
        assertEquals("800", components.get(6).getText());
        assertEquals("900", components.get(7).getText());


        components = table.findComponents(
                xpathBuilder().anywhereRelative("td").text().exact("400").or().text().contains("6").build());
        assertEquals(2, components.size());
        assertEquals("400", components.get(0).getText());
        assertEquals("600", components.get(1).getText());
    }

    public void testSelect() {
        driver.navigate().to("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10L));
        wait.until(visibilityOfElementLocated(By.id("iframeResult")));
        driver.switchTo().frame("iframeResult");

        HtmlSelect select = driver.findComponent(By.id("cars")).as(html()).toSelect();
        assertEquals(4, select.getOptions().size());
        assertEquals("Volvo", select.getOptions().get(0).getText());
        assertEquals("Saab", select.getOptions().get(1).getText());
        assertEquals("Opel", select.getOptions().get(2).getText());
        assertEquals("Audi", select.getOptions().get(3).getText());

        select.selectByIndex(3);
        assertEquals("Audi", select.getFirstSelectedOption().getText());
    }

    public void testTextNode() {
        // Use W3schools to test
        String htmlContent = "\"<div id='test-container'>\\n" + "  <!-- this is comment -->\\n" + "  plain text\\n"
                + "  <span>this is span tag</span>\\n" + "  <!-- this is another comment -->\\n"
                + "  another plain text\\n" + "</div>\"";

        driver.navigate().to("https://www.w3schools.com/html/tryit.asp?filename=tryhtml_default");
        WebComponent iframeWrapper = driver.findComponent(By.id("iframewrapper"));
        driver.executeScript("arguments[0].innerHTML=" + htmlContent, iframeWrapper);

        WebComponent testContainer = driver.findComponent(By.id("test-container"));

        List<Object> items = SeleniumUtils.findChildNodes(driver, testContainer.getWrappedElement());
        assertTrue(items.stream().anyMatch(object -> object instanceof Map));
        assertTrue(items.stream().anyMatch(object -> object instanceof WebElement));

        List<TextNodeElement> textNodeElements = SeleniumUtils.findChildTextNodes(driver, testContainer, true);
        assertTrue(textNodeElements.stream()
                .anyMatch(t -> COMMENT == t.getType() && t.getText().equals("this is comment")));
        assertTrue(textNodeElements.stream()
                .anyMatch(t -> COMMENT == t.getType() && t.getText().equals("this is another comment")));
        assertTrue(textNodeElements.stream().anyMatch(t -> TEXT == t.getType() && t.getText().equals("plain text")));
        assertTrue(textNodeElements.stream()
                .anyMatch(t -> TEXT == t.getType() && t.getText().equals("another plain text")));
    }

    public static void main(String[] args) {
        HtmlShowCase test = new HtmlShowCase();
        test.setUpDriver(WebDriverType.EDGE);
        test.testTable();
        test.testTableNoHeader();
        test.testSelect();
        test.testTextNode();
    }
}
