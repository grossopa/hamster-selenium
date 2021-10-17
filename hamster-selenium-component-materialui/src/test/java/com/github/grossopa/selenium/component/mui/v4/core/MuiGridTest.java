package com.github.grossopa.selenium.component.mui.v4.core;

import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiGrid}.
 *
 * @author Elena Wang
 * @since 1.6
 */
class MuiGridTest {

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
        assertEquals(MuiGrid.COMPONENT_NAME, testGrid.getComponentName());
    }

    @Test
    void isContainer() {
        when(config.isGridContainer(any())).thenReturn(true);
        assertTrue(testGrid.isContainer());
    }

    @Test
    void isItem() {
        when(config.isGridItem(any())).thenReturn(true);
        assertTrue(testGrid.isItem());
    }

    @Test
    void isItemAndContainer() {
        when(config.isGridItem(any())).thenReturn(true);
        when(config.isGridContainer(any())).thenReturn(true);
        assertTrue(testGrid.isItem());
        assertTrue(testGrid.isContainer());
    }

    @Test
    void getSpacingValue() {
        assertEquals(8, testGrid.gridItemSpacingValue(2));
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiGrid{element=element-toString}", testGrid.toString());
    }
}
