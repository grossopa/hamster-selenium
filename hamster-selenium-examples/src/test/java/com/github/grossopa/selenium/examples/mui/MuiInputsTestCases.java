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

package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.inputs.*;
import com.github.grossopa.selenium.component.mui.navigation.*;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jack Yin
 * @since 1.0
 */
public class MuiInputsTestCases extends AbstractBrowserSupport {

    public void testButtonGroup() {
        driver.navigate().to("https://material-ui.com/components/buttons/");

        WebComponent contentDriverParent = driver
                .findComponents(By2.attr("href", "#contained-buttons").anyDepthAbsolute().contains().tag("a").build())
                .get(1).findComponent(By.xpath("parent::*"));

        MuiButtonGroup group = contentDriverParent.as(mui()).toButtonGroup();
        List<MuiButton> buttons = group.getButtons();

        assertEquals(5, buttons.size());
        assertEquals(5L, buttons.stream().filter(MuiButton::validate).count());
        assertFalse(buttons.get(3).isEnabled());
        assertTrue(buttons.get(0).isEnabled());
    }

    public void testCheckBox() {
        driver.navigate().to("https://material-ui.com/components/checkboxes/");

        WebComponent checkBoxContainer = driver
                .findComponent(By2.attr("class", "MuiCheckbox-root").contains().anyDepthAbsolute().build())
                .findComponent(By.xpath("parent::*"));
        List<MuiCheckbox> checkboxes = checkBoxContainer.findComponents(By2.contains("class", "MuiCheckbox-root"))
                .stream().map(checkbox -> checkbox.as(mui()).toCheckbox()).collect(toList());

        assertEquals(8, checkboxes.size());
        assertEquals(8L, checkboxes.stream().filter(MuiCheckbox::validate).count());
        Arrays.stream(new int[]{0, 1, 4, 5, 6, 7}).forEach(index -> assertTrue(checkboxes.get(index).isSelected()));
        Arrays.stream(new int[]{2, 3}).forEach(index -> assertFalse(checkboxes.get(index).isSelected()));
        Arrays.stream(new int[]{0, 1, 2, 5, 6, 7}).forEach(index -> assertTrue(checkboxes.get(index).isEnabled()));
        Arrays.stream(new int[]{3, 4}).forEach(index -> assertFalse(checkboxes.get(index).isEnabled()));
    }

    public void testSelect() {
        driver.navigate().to("https://material-ui.com/components/selects/");

        WebComponent selectContainer = driver
                .findComponent(By2.attr("class", "MuiFormControl-root").contains().anyDepthAbsolute().build())
                .findComponent(By2.xpath("parent::*"));

        List<MuiSelect> selects = selectContainer
                .findComponents(By2.attr("class", "MuiSelect-root").anyDepthChild().contains().build()).stream()
                .map(select -> select.as(mui()).toSelect(By2.attr("class", "MuiMenuItem-root").contains().build()))
                .collect(toList());

        assertEquals(12, selects.size());

        MuiSelect demoSimpleSelect = driver.findComponent(By2.id("demo-simple-select")).as(mui())
                .toSelect(By2.attr("class", "MuiMenuItem-root").contains().anyDepthChild().build());

        List<WebComponent> components = demoSimpleSelect.getOptions2(3000L);
        assertEquals(3, components.size());
        assertEquals("Ten", components.get(0).getText());
        assertEquals("Twenty", components.get(1).getText());
        assertEquals("Thirty", components.get(2).getText());
        demoSimpleSelect.closeOptions(800L);

        // multi-select test
        MuiSelect multiSelect = driver.findComponent(By.id("demo-mutiple-name")).as(mui())
                .toSelect(By2.attr("class", "MuiMenuItem-root").contains().anyDepthChild().build());

        multiSelect.selectByVisibleText("Oliver Hansen", 800L);
        multiSelect.selectByVisibleText("April Tucker");
        // test double select
        multiSelect.selectByVisibleText("April Tucker");
        multiSelect.selectByValue("Ralph Hubbard");
        multiSelect.selectByIndex(9);
        Set<String> selectedTexts = multiSelect.getAllSelectedOptions2(800L).stream().map(WebElement::getText)
                .collect(toSet());


        assertTrue(selectedTexts.contains("Oliver Hansen"));
        assertTrue(selectedTexts.contains("April Tucker"));
        assertTrue(selectedTexts.contains("Ralph Hubbard"));
        assertTrue(selectedTexts.contains("Kelly Snyder"));

        multiSelect.deselectByIndex(9);
        selectedTexts = multiSelect.getAllSelectedOptions2().stream().map(WebElement::getText).collect(toSet());
        assertTrue(selectedTexts.contains("Oliver Hansen"));
        assertTrue(selectedTexts.contains("April Tucker"));
        assertTrue(selectedTexts.contains("Ralph Hubbard"));
        assertFalse(selectedTexts.contains("Kelly Snyder"));

        multiSelect.deselectByVisibleText("Ralph Hubbard");
        selectedTexts = multiSelect.getAllSelectedOptions2().stream().map(WebElement::getText).collect(toSet());
        assertTrue(selectedTexts.contains("Oliver Hansen"));
        assertTrue(selectedTexts.contains("April Tucker"));
        assertFalse(selectedTexts.contains("Ralph Hubbard"));
        assertFalse(selectedTexts.contains("Kelly Snyder"));

        multiSelect.deselectByValue("April Tucker");
        selectedTexts = multiSelect.getAllSelectedOptions2().stream().map(WebElement::getText).collect(toSet());
        assertTrue(selectedTexts.contains("Oliver Hansen"));
        assertFalse(selectedTexts.contains("April Tucker"));
        assertFalse(selectedTexts.contains("Ralph Hubbard"));
        assertFalse(selectedTexts.contains("Kelly Snyder"));

        multiSelect.deselectAll();
        selectedTexts = multiSelect.getAllSelectedOptions2().stream().map(WebElement::getText).collect(toSet());
        assertEquals(0, selectedTexts.size());

        multiSelect.closeOptions(800L);
    }

    /**
     * Tests whether the select works as expected when Menu is present with additional presentation layer.
     */
    public void testSelectWithMenu() {
        driver.navigate().to("https://material-ui.com/components/menus/");

        MuiSelect languageSelect = driver
                .findComponent(By2.attr("aria-label", "Change language").anyDepthChild().build()).as(mui())
                .toSelect(By.tagName("a"));

        List<WebComponent> options = languageSelect.getOptions2(500L);
        assertEquals(9, options.size());
        languageSelect.closeOptions(1000L);
    }

    public void testSlider() {
        driver.navigate().to("https://material-ui.com/components/slider/");
        MuiSlider continuousSlider = driver.findComponent(By.id("continuous-slider"))
                .findComponent(By.xpath("parent::*")).findComponent(By.className("MuiSlider-root")).as(mui())
                .toSlider();
        assertEquals("0", continuousSlider.getMinValue());
        assertEquals("100", continuousSlider.getMaxValue());
        continuousSlider.moveThumb(0.8d);
        assertEquals("80", continuousSlider.getValue());
        continuousSlider.setValue(25);
        assertEquals("25", continuousSlider.getValue());
        continuousSlider.setValue(30);
        assertEquals("30", continuousSlider.getValue());
        continuousSlider.setValue(0);
        assertEquals("0", continuousSlider.getValue());
        continuousSlider.setValue(100);
        assertEquals("100", continuousSlider.getValue());

        MuiSlider disabledSlider = driver.findComponent(By.id("continuous-slider")).findComponent(By.xpath("parent::*"))
                .findComponents(By.className("MuiSlider-root")).get(1).as(mui()).toSlider();
        assertFalse(disabledSlider.isEnabled());

        MuiSlider discreteSlider = driver.findComponent(By.id("discrete-slider")).findComponent(By.xpath("parent::*"))
                .findComponent(By.className("MuiSlider-root")).as(mui()).toSlider();
        assertEquals("10", discreteSlider.getMinValue());
        assertEquals("110", discreteSlider.getMaxValue());
        discreteSlider.moveThumb(0.7d);
        assertEquals("80", discreteSlider.getValue());
        discreteSlider.setValue(50);
        assertEquals("50", discreteSlider.getValue());
        discreteSlider.setValue(30);
        assertEquals("30", discreteSlider.getValue());

        MuiSlider verticalSlider = driver.findComponent(By.id("vertical-slider")).findComponent(By.xpath("parent::*"))
                .findComponent(By.className("MuiSlider-root")).as(mui()).toSlider();
        assertEquals("0", verticalSlider.getMinValue());
        assertEquals("100", verticalSlider.getMaxValue());
        verticalSlider.moveThumb(0.7d);
        assertEquals("70", verticalSlider.getValue());
        verticalSlider.setValue(50);
        assertEquals("50", verticalSlider.getValue());
        verticalSlider.setValue(30);
        assertEquals("30", verticalSlider.getValue());
        verticalSlider.setValue(0);
        assertEquals("0", verticalSlider.getValue());
        verticalSlider.setValue(100);
        assertEquals("100", verticalSlider.getValue());

        MuiSlider trackInvertedSlider = driver.findComponent(By.id("track-inverted-slider"))
                .findComponent(By.xpath("parent::*")).findComponent(By.className("MuiSlider-root")).as(mui())
                .toSlider();
        assertEquals("0", trackInvertedSlider.getMinValue());
        assertEquals("100", trackInvertedSlider.getMaxValue());
        trackInvertedSlider.moveThumb(0.7d);
        assertEquals("70", trackInvertedSlider.getValue());
        assertTrue(trackInvertedSlider.isInverted());

        MuiSlider nonLinearSlider = driver.findComponent(By.id("non-linear-slider"))
                .findComponent(By.xpath("parent::*")).findComponent(By.className("MuiSlider-root")).as(mui())
                .toSlider(x -> Math.pow(x, 0.1));
        assertEquals("0", nonLinearSlider.getMinValue());
        assertEquals("60466176", nonLinearSlider.getMaxValue());
        nonLinearSlider.moveThumb(0.5d);
        assertEquals("59049", nonLinearSlider.getValue());
        nonLinearSlider.setValue(0);
        assertEquals("0", nonLinearSlider.getValue());
        nonLinearSlider.setValue(60466176);
        assertEquals("60466176", nonLinearSlider.getValue());
        nonLinearSlider.setValue(6492506.2108545d);
        assertEquals("6492506.2108545", nonLinearSlider.getValue());


        MuiSlider rangeSlider = driver.findComponent(By.id("track-false-slider")).findComponent(By.xpath("parent::*"))
                .findComponents(By.className("MuiSlider-root")).get(1).as(mui()).toSlider();
        List<MuiSliderThumb> thumbs = rangeSlider.getAllThumbs();
        assertEquals(3, thumbs.size());

        thumbs.forEach(thumb -> {
            assertEquals("0", thumb.getMinValue());
            assertEquals("100", thumb.getMaxValue());
        });

        assertEquals("20", thumbs.get(0).getValue());
        assertEquals("37", thumbs.get(1).getValue());
        assertEquals("50", thumbs.get(2).getValue());

        rangeSlider.setValue(0, 10);
        rangeSlider.setValue(1, 30);
        rangeSlider.setValue(2, 50);

        // refresh thumbs orders
        thumbs = rangeSlider.getAllThumbs();
        assertEquals("10", thumbs.get(0).getValue());
        assertEquals("30", thumbs.get(1).getValue());
        assertEquals("50", thumbs.get(2).getValue());
    }

    public void testSwitch() {
        driver.navigate().to("https://material-ui.com/components/switches/");

        List<MuiSwitch> switches = driver.findComponents(By2.className("MuiSwitch-root")).stream()
                .map(checkbox -> checkbox.as(mui()).toSwitch()).collect(toList());

        MuiSwitch first = switches.get(0);
        assertTrue(first.isEnabled());
        assertTrue(first.isSelected());
        first.click();
        assertFalse(first.isSelected());
        first.click();
        assertTrue(first.isSelected());

        MuiSwitch disabled = switches.get(4);
        assertTrue(disabled.isSelected());
        assertFalse(disabled.isEnabled());
    }

    public void testTextInput() {
        driver.navigate().to("https://material-ui.com/components/text-fields/");

        MuiTextField textField = driver.findComponent(By.id("standard-basic-label"))
                .findComponent(By.xpath("parent::*")).as(mui()).toTextField();
        assertEquals("Standard", textField.getLabel().getText());
        textField.sendText("ddd ccc fff");
        assertEquals("ddd ccc fff", textField.getInput().getAttribute("value"));
    }

    public void testRadio() {
        driver.navigate().to("https://material-ui.com/components/radio-buttons/");

        List<MuiRadio> radios = driver.findComponents(By2.className("MuiRadio-root")).stream()
                .map(radio -> radio.as(mui()).toRadio()).collect(toList());

        MuiRadio first = radios.get(0);
        MuiRadio second = radios.get(1);
        assertTrue(first.isEnabled());
        assertTrue(first.isSelected());
        second.click();
        assertFalse(first.isSelected());
        first.click();
        assertTrue(first.isSelected());

        MuiRadio disabled = radios.get(3);
        assertFalse(disabled.isSelected());
        assertFalse(disabled.isEnabled());
    }

    public void testRadioGroup() {
        driver.navigate().to("https://material-ui.com/components/radio-buttons/");

        WebComponent contentDriverParent = driver
                .findComponents(By2.attr("role", "radiogroup").anyDepthAbsolute().contains().build()).get(0);

        MuiRadioGroup group = contentDriverParent.as(mui()).toRadioGroup();
        List<MuiRadio> radios = group.getRadios();

        assertEquals(4, radios.size());
        assertEquals(4L, radios.stream().filter(MuiRadio::validate).count());
        assertFalse(radios.get(3).isEnabled());
        assertTrue(radios.get(0).isEnabled());
    }

    public void testBreadcrumbs() {
        driver.navigate().to("https://material-ui.com/components/breadcrumbs/");

        List<MuiBreadcrumbs> breadcrumbsList = driver.findComponents(By.className("MuiBreadcrumbs-root")).stream()
                .map(component -> component.as(mui()).toBreadcrumbs()).collect(toList());

        MuiBreadcrumbs breadcrumbs1 = breadcrumbsList.get(0);
        assertEquals(3, breadcrumbs1.getItems().size());
        assertEquals("Material-UI", breadcrumbs1.getItemAt(0).as(mui()).toLink().getText());
        assertEquals("Core", breadcrumbs1.getItemAt(1).as(mui()).toLink().getText());
        assertEquals("Breadcrumb", breadcrumbs1.getItemAt(2).getText());
        assertEquals("/", breadcrumbs1.getSeparators().get(0).getText());
        assertFalse(breadcrumbs1.isCollapsed());

        MuiBreadcrumbs collapsedBreadcrumbs = breadcrumbsList.get(6);
        assertEquals(2, collapsedBreadcrumbs.getItems().size());
        assertEquals("Home", collapsedBreadcrumbs.getItemAt(0).getText());
        assertEquals("Belts", collapsedBreadcrumbs.getItemAt(1).getText());
        assertTrue(collapsedBreadcrumbs.isCollapsed());

        collapsedBreadcrumbs.expand();
        assertEquals(5, collapsedBreadcrumbs.getItems().size());
        assertEquals("Home", collapsedBreadcrumbs.getItemAt(0).getText());
        assertEquals("Catalog", collapsedBreadcrumbs.getItemAt(1).getText());
        assertEquals("Accessories", collapsedBreadcrumbs.getItemAt(2).getText());
        assertEquals("New Collection", collapsedBreadcrumbs.getItemAt(3).getText());
        assertEquals("Belts", collapsedBreadcrumbs.getItemAt(4).getText());
        assertFalse(collapsedBreadcrumbs.isCollapsed());
    }

    public void testBottomNavigation() {
        driver.navigate().to("https://material-ui.com/components/bottom-navigation/");

        List<MuiBottomNavigation> bottomNavigationList = driver.findComponents(By.className("MuiBottomNavigation-root"))
                .stream().map(component -> component.as(mui()).toBottomNavigation()).collect(toList());
        MuiBottomNavigation bn1 = bottomNavigationList.get(0);
        List<MuiBottomNavigationAction> actions = bn1.getActions();
        assertEquals(3, actions.size());
        assertEquals("Recents", actions.get(0).getText());
        assertEquals("Favorites", actions.get(1).getText());
        assertEquals("Nearby", actions.get(2).getText());

        assertTrue(actions.get(0).isSelected());
    }

    @SuppressWarnings("squid:S2925")
    public void testTabs() throws InterruptedException {
        driver.navigate().to("https://material-ui.com/components/tabs/");

        List<MuiTabs> tabsList = driver.findComponents(By.className("MuiTabs-root")).stream()
                .map(component -> component.as(mui()).toTabs()).collect(toList());
        MuiTabs tabs1 = tabsList.get(0);
        List<MuiTab> actions = tabs1.getTabs();
        assertEquals(3, actions.size());
        assertEquals("ITEM ONE", actions.get(0).getText());
        assertEquals("ITEM TWO", actions.get(1).getText());
        assertEquals("ITEM THREE", actions.get(2).getText());

        assertTrue(actions.get(0).isSelected());

        MuiTabs automaticScrollTabs = tabsList.get(5);
        driver.moveTo(automaticScrollTabs);
        assertEquals(7, automaticScrollTabs.getTabs().size());
        assertTrue(automaticScrollTabs.getNextScrollButton().isPresent());
        assertTrue(automaticScrollTabs.getPreviousScrollButton().isPresent());

        automaticScrollTabs.getTabs().get(3).click();
        Thread.sleep(600L);
        //driver.moveTo(driver.findComponent(By.id("scrollable-force-tabpanel-3")));
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-3")).isDisplayed());
        automaticScrollTabs.getTabs().get(5).click();
        Thread.sleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-5")).isDisplayed());
        automaticScrollTabs.getTabs().get(6).click();
        Thread.sleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-6")).isDisplayed());
        automaticScrollTabs.getTabs().get(0).click();
        Thread.sleep(600L);
        assertTrue(driver.findComponent(By.id("scrollable-auto-tabpanel-0")).isDisplayed());
    }

    public static void main(String[] args) {
        MuiInputsTestCases test = new MuiInputsTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testBreadcrumbs();
            test.testTextInput();
            test.testSwitch();
            test.testSlider();
            test.testSelect();
            test.testSelectWithMenu();
            test.testButtonGroup();
            test.testCheckBox();
            test.testBottomNavigation();
            test.testTabs();
            test.testRadio();
            test.testRadioGroup();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            test.stopDriver();
        }
    }
}
