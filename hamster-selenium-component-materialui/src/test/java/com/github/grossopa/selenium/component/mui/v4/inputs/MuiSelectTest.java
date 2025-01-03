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

package com.github.grossopa.selenium.component.mui.v4.inputs;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.action.CloseOptionsAction;
import com.github.grossopa.selenium.component.mui.action.OpenOptionsAction;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.component.mui.config.MuiSelectConfig;
import com.github.grossopa.selenium.component.mui.v4.core.MuiPopover;
import com.github.grossopa.selenium.component.mui.exception.OptionNotClosedException;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.util.SimpleEqualsTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MuiSelect}
 *
 * @author Jack Yin
 * @since 1.0
 */
@SuppressWarnings("java:S6068")
class MuiSelectTest {

    MuiSelect testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);
    OpenOptionsAction openOptionsAction = mock(OpenOptionsAction.class);
    CloseOptionsAction closeOptionsAction = mock(CloseOptionsAction.class);

    WebDriverWait webDriverWait = mock(WebDriverWait.class);

    WebComponent optionContainer = mock(WebComponent.class);
    WebComponent menuPagerContainer = mock(WebComponent.class);
    List<WebComponent> options;
    MuiSelectConfig selectConfig;

    private WebComponent createOptions(String value, String label, boolean selected) {
        WebComponent option = mock(WebComponent.class);
        when(option.getDomAttribute("attr-val")).thenReturn(value);
        when(option.getText()).thenReturn(label);
        when(config.isSelected(option)).thenReturn(selected);
        doAnswer(a -> {
            when(config.isSelected(option)).thenReturn(!selected);
            return null;
        }).when(option).click();
        return option;
    }

    private boolean optionOpen = false;

    @SuppressWarnings("deprecation")
    private void mockOptionOpen() {
        when(driver.findComponent(By.xpath("/html/body/div"))).thenReturn(optionContainer);
        when(driver.findComponents(By.xpath("/html/body/div"))).thenReturn(singletonList(optionContainer));
        when(driver.findElement(eq(By.xpath("/html/body/div")))).thenReturn(optionContainer);
        when(driver.mapElement(optionContainer)).thenReturn(optionContainer);
        when(optionContainer.findComponents(By.className("option"))).thenReturn(options);
        when(optionContainer.isDisplayed()).thenReturn(true);
        optionOpen = true;
    }

    @SuppressWarnings("deprecation")
    private void mockOptionsClose() {
        when(driver.findComponent(By.xpath("/html/body/div"))).thenReturn(null);
        when(driver.findComponents(By.xpath("/html/body/div"))).thenReturn(emptyList());
        when(driver.findElement(eq(By.xpath("/html/body/div")))).thenThrow(new NoSuchElementException("some"));
        when(driver.mapElement(optionContainer)).thenReturn(null);
        when(optionContainer.findComponents(By.className("option"))).thenReturn(emptyList());
        when(optionContainer.isDisplayed()).thenReturn(false);
        optionOpen = false;
    }

    /**
     * embed the menu pager to the presentation layer search, the select is expected to ignore such Popover.
     */
    private void mockMenuPager() {
        WebComponent menuPagerComponent = mock(WebComponent.class);
        when(menuPagerComponent.isDisplayed()).thenReturn(true);
        when(menuPagerContainer.findComponents(config.menuPagerLocator())).thenReturn(
                singletonList(menuPagerComponent));
        when(driver.findComponents(By.xpath("/html/body/div"))).thenReturn(asList(menuPagerContainer, optionContainer));
    }

    @BeforeEach
    void setUp() {
        element = mock(WebElement.class);
        driver = mock(ComponentWebDriver.class);
        config = mock(MuiConfig.class);
        openOptionsAction = mock(OpenOptionsAction.class);
        closeOptionsAction = mock(CloseOptionsAction.class);

        webDriverWait = mock(WebDriverWait.class);

        optionContainer = mock(WebComponent.class);
        menuPagerContainer = mock(WebComponent.class);

        selectConfig = MuiSelectConfig.builder(By.className("option")).optionValueAttribute("attr-val")
                .openOptionsAction(openOptionsAction).closeOptionsAction(closeOptionsAction).multiple(false).build();

        when(driver.findComponents(By.xpath("/html/body/div"))).thenReturn(singletonList(optionContainer));
        when(driver.createWait(anyLong())).thenReturn(webDriverWait);

        when(webDriverWait.until(any())).then(answer -> {
            Function<ComponentWebDriver, ?> isTrueFunction = answer.getArgument(0);
            return isTrueFunction.apply(driver);
        });

        when(optionContainer.getDomAttribute("class")).thenReturn("MuiPopover-root");
        when(optionContainer.isDisplayed()).thenReturn(true);

        when(config.getOverlayAbsolutePath()).thenReturn("/html/body");
        when(config.menuPagerLocator()).thenReturn(By.className("MuiMenu-pager"));
        when(config.getRootCss(MuiPopover.COMPONENT_NAME)).thenReturn("MuiPopover-root");

        options = asList(createOptions("val-0", "Label 0", false),
                createOptions("val-1", "Label 1 some label 123", true),
                createOptions("val-2", "Label 2 some label 123", false),
                createOptions("val-3", "Label 3 some label 123", false),
                createOptions("val-4", "Label 4 some label 123", true),
                createOptions("val-5", "Label 5 some label 123", true));


        testSubject = new MuiSelect(element, driver, config, selectConfig);

        mockOptionsClose();

        doAnswer(a -> {
            this.mockOptionOpen();
            return null;
        }).when(openOptionsAction).open(testSubject, driver);

        doAnswer(a -> {
            this.mockOptionsClose();
            return null;
        }).when(closeOptionsAction).close(testSubject, options, driver);
    }

    @Test
    void getComponentName() {
        assertEquals("Select", testSubject.getComponentName());
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void isMultiple() {
        assertFalse(testSubject.isMultiple());
    }

    @Test
    void getOptions2() {
        assertEquals(6, testSubject.getOptions2().size());
    }

    @Test
    void getOptions2WithDelay() {
        assertEquals(6, testSubject.getOptions2(500L).size());
    }

    @Test
    void getAllSelectedOptions2() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void getAllSelectedOptions2WithDelay() {
        assertEquals(3, testSubject.getAllSelectedOptions2(500L).size());
    }

    @Test
    @SuppressWarnings("deprecation")
    void getOptions() {
        assertEquals(6, testSubject.getOptions().size());
    }

    @Test
    @SuppressWarnings("deprecation")
    void getAllSelectedOptions() {
        assertEquals(3, testSubject.getAllSelectedOptions().size());
    }

    @Test
    void openOptions() {
        WebComponent container = testSubject.openOptions();
        assertEquals(this.optionContainer, container);
        verify(openOptionsAction, only()).open(any(), any());
        verify(driver, never()).mapElement(any());
        assertTrue(optionOpen);
    }

    @Test
    void openOptionsWithDelay() {
        WebComponent container = testSubject.openOptions(500L);
        assertEquals(this.optionContainer, container);
        verify(openOptionsAction, only()).open(any(), any());
        verify(driver, times(1)).mapElement(any());
        assertTrue(optionOpen);
    }

    @Test
    void openOptionsAlreadyOpened() {
        this.mockOptionOpen();
        WebComponent container = testSubject.openOptions();
        assertEquals(this.optionContainer, container);
        verify(openOptionsAction, never()).open(any(), any());
        verify(driver, only()).findComponents(By.xpath("/html/body/div"));
        assertTrue(optionOpen);
    }

    @Test
    void closeOptions() {
        this.mockOptionOpen();
        testSubject.closeOptions();
        verify(closeOptionsAction, only()).close(any(), any(), any());
        assertFalse(optionOpen);
    }

    @Test
    void closeOptionsWithDelay() {
        this.mockOptionOpen();
        testSubject.closeOptions(500L);
        verify(closeOptionsAction, only()).close(any(), any(), any());
        assertFalse(optionOpen);
    }

    @Test
    void closeOptionsAlreadyClosedNull() {
        this.mockOptionsClose();
        testSubject.closeOptions();
        verify(closeOptionsAction, never()).close(any(), any(), any());
        assertFalse(optionOpen);
    }

    @Test
    void closeOptionsAlreadyClosedContainerNotDisplayed() {
        this.mockOptionsClose();
        testSubject.closeOptions();
        verify(closeOptionsAction, never()).close(any(), any(), any());
        assertFalse(optionOpen);
    }

    @Test
    void closeOptionsFailed() {
        this.mockOptionOpen();
        doAnswer(a -> {
            // do nothing
            return null;
        }).when(closeOptionsAction).close(eq(testSubject), eq(options), eq(driver));
        assertThrows(OptionNotClosedException.class, () -> testSubject.closeOptions());
    }

    @Test
    void getFirstSelectedOption() {
        assertEquals(options.get(1), testSubject.getFirstSelectedOption());
    }

    @Test
    void getFirstSelectedOptionWithDelay() {
        assertEquals(options.get(1), testSubject.getFirstSelectedOption(500L));
    }

    @Test
    void selectByVisibleText() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByVisibleText("Label 3 some label 123");
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void selectByVisibleTextAlreadySelected() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByVisibleText("Label 3 some label 123");
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByVisibleText("Label 3 some label 123");
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void selectByVisibleTextWithDelay() {
        testSubject.selectByVisibleText("Label 3 some label 123");
        assertEquals(4, testSubject.getAllSelectedOptions2(500L).size());
    }

    @Test
    void selectByIndex() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByIndex(3);
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void selectByIndexAlreadySelected() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByIndex(3);
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByIndex(3);
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void selectByIndexWithDelay() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByIndex(3, 500L);
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void selectByValue() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByValue("val-3");
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void selectByValueAlreadySelected() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByValue("val-3");
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByValue("val-3");
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void selectByValueWithDelay() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.selectByValue("val-3", 500L);
        assertEquals(4, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectAll() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectAll();
        assertEquals(0, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectAllAlreadyDeselected() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectAll();
        assertEquals(0, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectAll();
        assertEquals(0, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectAllWithDelay() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectAll(500L);
        assertEquals(0, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByValue() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByValue("val-5");
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByValueAlreadyDeselected() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByValue("val-5");
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByValue("val-5");
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByValueWithDelay() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByValue("val-5", 500L);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByIndex() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByIndex(5);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByIndexAlreadyDeselected() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByIndex(5);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByIndex(5);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByIndexWithDelay() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByIndex(5, 500L);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByVisibleText() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByVisibleText("Label 5 some label 123");
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByVisibleTextAlreadyDeselected() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByVisibleText("Label 5 some label 123");
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByVisibleText("Label 5 some label 123");
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void deselectByVisibleTextWithDelay() {
        assertEquals(3, testSubject.getAllSelectedOptions2().size());
        testSubject.deselectByVisibleText("Label 5 some label 123", 500L);
        assertEquals(2, testSubject.getAllSelectedOptions2().size());
    }

    @Test
    void getOptionsWhenMenuPagerPresent() {
        mockMenuPager();
        // it will not be impacted by the additional menuPagerContainer
        assertEquals(6, testSubject.getOptions2().size());
    }

    @Test
    void openOptionsWhenMenuPagerPresent() {
        mockMenuPager();
        // it will not be impacted by the additional menuPagerContainer
        WebComponent container = testSubject.openOptions();
        assertEquals(this.optionContainer, container);
        verify(openOptionsAction, only()).open(any(), any());
        verify(driver, never()).mapElement(any());
        assertTrue(optionOpen);
    }

    @Test
    void closeOptionsWhenMenuPagerPresent() {
        mockMenuPager();
        // it will not be impacted by the additional menuPagerContainer
        this.mockOptionOpen();
        testSubject.closeOptions();
        verify(closeOptionsAction, only()).close(any(), any(), any());
        assertFalse(optionOpen);
    }

    @Test
    void closeOptionsWithDelayWhenMenuPagerPresent() {
        mockMenuPager();
        // it will not be impacted by the additional menuPagerContainer
        this.mockOptionOpen();
        testSubject.closeOptions(500L);
        verify(closeOptionsAction, only()).close(any(), any(), any());
        assertFalse(optionOpen);
    }

    @Test
    void closeOptionsAlreadyClosedNullWhenMenuPagerPresent() {
        mockMenuPager();
        // it will not be impacted by the additional menuPagerContainer
        this.mockOptionsClose();
        testSubject.closeOptions();
        verify(closeOptionsAction, never()).close(any(), any(), any());
        assertFalse(optionOpen);
    }

    @Test
    void closeOptionsAlreadyClosedContainerNotDisplayedWhenMenuPagerPresent() {
        mockMenuPager();
        // it will not be impacted by the additional menuPagerContainer
        this.mockOptionsClose();
        testSubject.closeOptions();
        verify(closeOptionsAction, never()).close(any(), any(), any());
        assertFalse(optionOpen);
    }

    @Test
    void closeOptionsFailedWhenMenuPagerPresent() {
        mockMenuPager();
        // it will not be impacted by the additional menuPagerContainer
        this.mockOptionOpen();
        doAnswer(a -> {
            // do nothing
            return null;
        }).when(closeOptionsAction).close(eq(testSubject), eq(options), eq(driver));
        assertThrows(OptionNotClosedException.class, () -> testSubject.closeOptions());
    }

    @Test
    void selectConfig() {
        assertEquals(selectConfig, testSubject.selectConfig());
    }

    @Test
    void testEquals() {
        WebElement element1 = mock(WebElement.class);
        WebElement element2 = mock(WebElement.class);
        ComponentWebDriver driver1 = mock(ComponentWebDriver.class);
        ComponentWebDriver driver2 = mock(ComponentWebDriver.class);
        MuiConfig config1 = mock(MuiConfig.class);
        MuiConfig config2 = mock(MuiConfig.class);

        MuiSelectConfig selectConfig1 = MuiSelectConfig.builder(By.className("option")).optionValueAttribute("attr-val")
                .openOptionsAction(openOptionsAction).closeOptionsAction(closeOptionsAction).multiple(false).build();
        MuiSelectConfig selectConfig2 = MuiSelectConfig.builder(By.className("option2"))
                .optionValueAttribute("attr-val").openOptionsAction(openOptionsAction)
                .closeOptionsAction(closeOptionsAction).multiple(false).build();

        SimpleEqualsTester tester = new SimpleEqualsTester();

        tester.addEqualityGroup(new MuiSelect(element1, driver1, config1, selectConfig1),
                new MuiSelect(element1, driver1, config1, selectConfig1));
        tester.addEqualityGroup(new MuiSelect(element2, driver1, config1, selectConfig1));
        tester.addEqualityGroup(new MuiSelect(element1, driver2, config1, selectConfig1));
        tester.addEqualityGroup(new MuiSelect(element1, driver1, config2, selectConfig1));
        tester.addEqualityGroup(new MuiSelect(element1, driver1, config1, selectConfig2));

        tester.testEquals();
    }

    @Test
    void testToString() {
        when(openOptionsAction.toString()).thenReturn("openOptionsAction-toString");
        when(closeOptionsAction.toString()).thenReturn("closeOptionsAction-toString");
        when(driver.toString()).thenReturn("driver-toString");
        when(element.toString()).thenReturn("element-toString");
        when(config.toString()).thenReturn("config-toString");
        assertEquals("MuiSelect{modalFinder=MuiModalFinder{driver=driver-toString, config=config-toString}, "
                + "selectConfig=MuiSelectConfig{optionValueAttribute='attr-val', optionsLocator=By.className:"
                + " option, openOptionsAction=openOptionsAction-toString, closeOptionsAction=closeOptionsAction"
                + "-toString, isMultiple=false}, element=element-toString}", testSubject.toString());
    }
}
