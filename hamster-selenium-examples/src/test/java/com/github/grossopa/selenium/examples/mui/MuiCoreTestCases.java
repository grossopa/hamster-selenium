package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.v4.core.MuiGrid;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for Core components.
 *
 * @author Chenyu Wang
 * @since 1.6
 */
public class MuiCoreTestCases extends AbstractBrowserSupport {

    public void testGrid() {
        driver.navigate().to("https://v4.mui.com/components/grid/");
        WebComponent divForLocate = driver.findComponents(By.id("SpacingGrid.js")).get(0).findComponent(By2.parent());
        WebComponent testGridParentDiv = divForLocate.findComponents(By.tagName("div")).get(0)
                .findComponent(By.tagName("div"));
        MuiGrid testGridParent = testGridParentDiv.findComponents(By.className("MuiGrid-root")).get(0).as(mui())
                .toGrid();
        assertTrue(testGridParent.isItem());
        MuiGrid testGrid = testGridParent.findComponent(By.className("MuiGrid-root")).as(mui()).toGrid();
        assertTrue(testGrid.isContainer());
        List<MuiGrid> testedItemGrids = testGrid.findComponents(By.className("MuiGrid-root")).stream()
                .map(c -> c.as(mui()).toGrid()).collect(toList());
        List<MuiGrid> testItemGrids = testedItemGrids.stream().filter(MuiGrid::isItem).collect(toList());
        assertEquals(3, testItemGrids.toArray().length);
        testItemGrids.forEach(testItemGrid -> assertEquals(8, testItemGrid.gridItemSpacingValue(2)));
    }

    public static void main(String[] args) {
        MuiCoreTestCases test = new MuiCoreTestCases();
        test.setUpDriver(EDGE);
        test.testGrid();
    }
}
