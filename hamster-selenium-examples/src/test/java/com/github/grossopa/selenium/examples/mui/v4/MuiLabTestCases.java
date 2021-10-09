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

package com.github.grossopa.selenium.examples.mui.v4;

import com.github.grossopa.selenium.component.mui.v4.lab.MuiAutocomplete;
import com.github.grossopa.selenium.component.mui.v4.lab.MuiPagination;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.core.util.SeleniumUtils;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

/**
 * Test cases for lab components
 *
 * @author Jack Yin
 * @since 1.2
 */
public class MuiLabTestCases extends AbstractBrowserSupport {

    public void testAutocompleteComboBox() {
        driver.navigate().to("https://v4.mui.com/components/autocomplete/");

        MuiAutocomplete autoComplete = driver.findComponent(By.id("combo-box-demo-label")).findComponent(By2.parent())
                .findComponent(By2.parent()).as(mui()).toAutocomplete();

        assertTrue(autoComplete.validate());
        assertEquals("Combo box", autoComplete.getLabel().getText());

        assertEquals("", autoComplete.getInput().getAttribute("value"));
        autoComplete.selectByIndex(0);
        driver.threadSleep(200L);
        assertEquals("The Shawshank Redemption", autoComplete.getInput().getAttribute("value"));
        autoComplete.closeOptions();

        autoComplete.getClearButton().click();
        assertEquals("", autoComplete.getInput().getAttribute("value"));

        autoComplete.getInput().sendKeys("Godfat");
        assertEquals("The Godfather", autoComplete.getOptions2().get(0).getText());
        autoComplete.selectByValue("The Godfather");
        autoComplete.closeOptions();

        autoComplete.getClearButton().click();
        autoComplete.selectByVisibleText("3 Idiots");
        assertEquals("3 Idiots", autoComplete.getInput().getAttribute("value"));

        autoComplete.getInput()
                .sendKeys(BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE,
                        BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE);
        assertFalse(autoComplete.isNoOptions());
        autoComplete.getInput().sendKeys("fffffffffffff");
        assertTrue(autoComplete.isNoOptions());
        SeleniumUtils.cleanText(autoComplete.getInput());

        autoComplete.closeOptions();

        autoComplete.getPopupButton().click();
        autoComplete.closeOptions();
    }

    public void testAutocompleteComboBoxWithDelays() {
        driver.navigate().to("https://v4.mui.com/components/autocomplete/");

        MuiAutocomplete autoComplete = driver.findComponent(By.id("combo-box-demo-label")).findComponent(By2.parent())
                .findComponent(By2.parent()).as(mui()).toAutocomplete();

        assertTrue(autoComplete.validate());
        assertEquals("Combo box", autoComplete.getLabel().getText());

        assertEquals("", autoComplete.getInput().getAttribute("value"));
        autoComplete.selectByIndex(0, 500L);
        assertEquals("The Shawshank Redemption", autoComplete.getInput().getAttribute("value"));
        autoComplete.closeOptions(500L);


        autoComplete.getClearButton().click();
        assertEquals("", autoComplete.getInput().getAttribute("value"));

        autoComplete.getInput().sendKeys("Godfat");
        assertEquals("The Godfather", autoComplete.getOptions2(200L).get(0).getText());
        autoComplete.selectByValue("The Godfather");
        autoComplete.closeOptions(500L);

        autoComplete.getClearButton().click();
        autoComplete.selectByVisibleText("3 Idiots");
        assertEquals("3 Idiots", autoComplete.getInput().getAttribute("value"));

        autoComplete.getInput()
                .sendKeys(BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE,
                        BACK_SPACE, BACK_SPACE, BACK_SPACE, BACK_SPACE);
        assertFalse(autoComplete.isNoOptions());
        autoComplete.getInput().sendKeys("fffffffffffff");
        assertTrue(autoComplete.isNoOptions());
        SeleniumUtils.cleanText(autoComplete.getInput());

        autoComplete.closeOptions(500L);

        autoComplete.getPopupButton().click();
        autoComplete.closeOptions(500L);
    }

    public void testAutocompleteDisabled() {
        driver.navigate().to("https://v4.mui.com/components/autocomplete/");

        MuiAutocomplete disabledAutocomplete = driver.findComponent(By.id("disabled-label")).findComponent(By2.parent())
                .findComponent(By2.parent()).as(mui()).toAutocomplete();

        assertTrue(disabledAutocomplete.validate());
        assertFalse(disabledAutocomplete.isEnabled());
        assertEquals("disabled", disabledAutocomplete.getLabel().getText());
    }

    public void testAutocompleteMultipleValues() {
        driver.navigate().to("https://v4.mui.com/components/autocomplete/");
        MuiAutocomplete multiAutocomplete = driver.findComponent(By.id("tags-standard-label"))
                .findComponent(By2.parent()).findComponent(By2.parent()).as(mui()).toAutocomplete();

        assertTrue(multiAutocomplete.validate());

        multiAutocomplete.selectByVisibleText("The Lord of the Rings: The Fellowship of the Ring");
        assertEquals(2, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.selectByValue("The Godfather");
        assertEquals(3, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.selectByVisibleText("Spirited Away");
        assertEquals(4, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.selectByIndex(0);
        //The Shawshank Redemption
        assertEquals(5, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.deselectByIndex(0);
        driver.threadSleep(200L);
        assertEquals(4, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.deselectByValue("Spirited Away");
        driver.threadSleep(200L);
        assertEquals(3, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.deselectByVisibleText("The Shawshank Redemption");
        driver.threadSleep(200L);
        assertEquals(2, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.selectByVisibleText("Spirited Away");
        assertEquals(3, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.getInput().sendKeys("pia");
        assertEquals("The Pianist", multiAutocomplete.getOptions2().get(0).getText());
        multiAutocomplete.selectByIndex(0);

        assertEquals(4, multiAutocomplete.getAllSelectedOptions2().size());

        multiAutocomplete.deselectAll();
        assertEquals(0, multiAutocomplete.getAllSelectedOptions2().size());
    }

    public void testAutocompleteAsynchronousRequests() {
        driver.navigate().to("https://v4.mui.com/components/autocomplete/");

        MuiAutocomplete autocomplete = driver.findComponent(By.id("Asynchronous.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiAutocomplete-root")).as(mui()).toAutocomplete();

        assertTrue(autocomplete.validate());
        assertFalse(autocomplete.isLoading());

        driver.moveTo(autocomplete.getInput());
        autocomplete.getInput().click();
        assertTrue(autocomplete.isLoading());
    }

    public void testAutocompleteFixedOptions() {
        driver.navigate().to("https://v4.mui.com/components/autocomplete/");

        MuiAutocomplete fixedOptionsAutocomplete = driver.findComponent(By.id("fixed-tags-demo-label"))
                .findComponent(By2.parent()).findComponent(By2.parent()).as(mui()).toAutocomplete();

        assertTrue(fixedOptionsAutocomplete.validate());
        driver.moveTo(fixedOptionsAutocomplete);
        fixedOptionsAutocomplete.deselectAll();
        assertEquals(1, fixedOptionsAutocomplete.getAllSelectedOptions2().size());
    }

    public void testPaginationBasic() {
        driver.navigate().to("https://v4.mui.com/components/pagination/");

        MuiPagination pagination = driver.findComponent(By.id("BasicPagination.js")).findComponent(By2.parent())
                .findComponent(By.className("MuiPagination-root")).as(mui()).toPagination();
        assertTrue(pagination.validate());
        assertEquals(1, pagination.getCurrentPageIndex());
        assertFalse(pagination.previousButton().isEnabled());
        assertTrue(pagination.nextButton().isEnabled());

        pagination.setPageIndex(3);
        assertEquals(3, pagination.getCurrentPageIndex());
        pagination.setPageIndex(8);
        assertEquals(8, pagination.getCurrentPageIndex());
        pagination.nextButton().click();
        pagination.nextButton().click();
        assertEquals(10, pagination.getCurrentPageIndex());
        assertTrue(pagination.previousButton().isEnabled());
        assertFalse(pagination.nextButton().isEnabled());

        MuiPagination disabledPagination = driver.findComponent(By.id("BasicPagination.js")).findComponent(By2.parent())
                .findComponents(By.className("MuiPagination-root")).get(3).as(mui()).toPagination();
        assertFalse(disabledPagination.isEnabled());
    }

    public void testPaginationButtons() {
        driver.navigate().to("https://v4.mui.com/components/pagination/");

        List<MuiPagination> paginationList = driver.findComponent(By.id("PaginationButtons.js"))
                .findComponent(By2.parent())
                .findComponentsAs(By.className("MuiPagination-root"), component -> component.as(mui()).toPagination());

        MuiPagination pagination = paginationList.get(0);
        assertNotNull(pagination.firstButton());
        assertNotNull(pagination.lastButton());
        assertNotNull(pagination.previousButton());
        assertNotNull(pagination.nextButton());
        pagination.setPageIndex(6);

        assertEquals(6, pagination.getCurrentPageIndex());

        pagination.firstButton().click();
        assertEquals(1, pagination.getCurrentPageIndex());
        pagination.lastButton().click();
        assertEquals(10, pagination.getCurrentPageIndex());

        MuiPagination pagination2 = paginationList.get(1);
        assertThrows(NoSuchElementException.class, pagination2::firstButton);
        assertThrows(NoSuchElementException.class, pagination2::lastButton);
        assertThrows(NoSuchElementException.class, pagination2::previousButton);
        assertThrows(NoSuchElementException.class, pagination2::nextButton);
    }

    public static void main(String[] args) {
        MuiLabTestCases test = new MuiLabTestCases();
        test.setUpDriver(EDGE);
        test.testAutocompleteComboBox();
        test.testAutocompleteComboBoxWithDelays();
        test.testAutocompleteAsynchronousRequests();
        test.testAutocompleteDisabled();
        test.testAutocompleteMultipleValues();
        test.testAutocompleteFixedOptions();
        test.testPaginationBasic();
        test.testPaginationButtons();
    }
}
