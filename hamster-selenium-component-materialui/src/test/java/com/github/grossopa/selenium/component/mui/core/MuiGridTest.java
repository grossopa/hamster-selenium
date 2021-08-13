package com.github.grossopa.selenium.component.mui.core;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MuiGridTest {

    MuiGrid testGrid;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testGrid = new MuiGrid(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals(MuiPopover.COMPONENT_NAME, testGrid.getComponentName());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiGrid{element=element-toString}", testGrid.toString());
    }
}
