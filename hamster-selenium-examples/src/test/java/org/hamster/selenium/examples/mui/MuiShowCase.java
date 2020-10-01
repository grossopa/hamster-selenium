package org.hamster.selenium.examples.mui;

import org.hamster.selenium.component.mui.*;
import org.hamster.selenium.core.component.WebComponent;
import org.hamster.selenium.core.locator.By2;
import org.hamster.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.hamster.selenium.component.mui.MuiComponents.mui;
import static org.hamster.selenium.core.driver.WebDriverType.CHROME;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Jack Yin
 * @since 1.0
 */
public class MuiShowCase extends AbstractBrowserSupport {

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

    public void testSlider() throws InterruptedException {
        driver.navigate().to("https://material-ui.com/components/slider/");
        MuiSlider continuousSlider = driver.findComponent(By.id("continuous-slider"))
                .findComponent(By.xpath("parent::*")).findComponent(By.className("MuiSlider-root")).as(mui())
                .toSlider();
        assertEquals("0", continuousSlider.getMinValue());
        assertEquals("100", continuousSlider.getMaxValue());
        continuousSlider.moveThumb(0.8d);
        assertEquals("80", continuousSlider.getValue());
        Thread.sleep(2000L);
        continuousSlider.setValue(25);
        assertEquals("25", continuousSlider.getValue());
        Thread.sleep(2000L);
        continuousSlider.setValue(30);
        assertEquals("30", continuousSlider.getValue());
        continuousSlider.setValue(0);
        assertEquals("0", continuousSlider.getValue());
        continuousSlider.setValue(100);
        assertEquals("100", continuousSlider.getValue());

        MuiSlider disabledSlider = driver.findComponent(By.id("continuous-slider"))
                .findComponent(By.xpath("parent::*")).findComponents(By.className("MuiSlider-root")).get(1).as(mui())
                .toSlider();
        assertFalse(disabledSlider.isEnabled());

        MuiSlider discreteSlider = driver.findComponent(By.id("discrete-slider")).findComponent(By.xpath("parent::*"))
                .findComponent(By.className("MuiSlider-root")).as(mui()).toSlider();
        assertEquals("10", discreteSlider.getMinValue());
        assertEquals("110", discreteSlider.getMaxValue());
        discreteSlider.moveThumb(0.7d);
        assertEquals("80", discreteSlider.getValue());
        discreteSlider.setValue(50);
        assertEquals("50", discreteSlider.getValue());
        Thread.sleep(2000L);
        discreteSlider.setValue(30);
        assertEquals("30", discreteSlider.getValue());
    }

    public static void main(String[] args) {
        MuiShowCase test = new MuiShowCase();
        try {
            test.setUpDriver(CHROME);
            test.testSlider();
            //            test.testSelect();
            //            test.testButtonGroup();
            //            test.testCheckBox();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            test.stopDriver();
        }
    }
}
