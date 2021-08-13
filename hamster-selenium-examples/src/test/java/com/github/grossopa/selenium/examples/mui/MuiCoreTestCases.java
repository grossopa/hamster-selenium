package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.core.MuiGrid;
import com.github.grossopa.selenium.component.mui.datadisplay.MuiAvatar;
import com.github.grossopa.selenium.component.mui.lab.MuiAutocompleteTag;
import com.github.grossopa.selenium.core.component.WebComponent;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.mui;
import static com.github.grossopa.selenium.core.driver.WebDriverType.CHROME;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for Core components.
 *
 * @author Chenyu Wang
 * @since 1.6
 */
public class MuiCoreTestCases extends AbstractBrowserSupport {
    public void testGrid() {
        driver.navigate().to("https://material-ui.com/components/grid/");
        List<WebComponent> gridDivs = driver.findComponents(By.className("jss54"));
        WebComponent testGridParentDiv = gridDivs.get(0);
        List<MuiGrid> testedGrids = testGridParentDiv.findComponents(By.className("MuiGrid-root")).stream().map(c -> c.as(mui()).toGrid()).collect(toList());;

        List<MuiGrid> testContainerGrids = testedGrids.stream().filter(c -> c.isContainer()).collect(toList());
        assertEquals(testContainerGrids.toArray().length,1);

        List<MuiGrid> testItemGrids = testedGrids.stream().filter(c -> c.isItem()).collect(toList());
        assertEquals(testItemGrids.toArray().length,2);

        testItemGrids.forEach(testItemGrid -> testItemGrid.gridItemSpacingValue(2).equals(Integer.valueOf(8)));
    }

    public static void main(String[] args) {
        MuiCoreTestCases test = new MuiCoreTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testGrid();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            test.stopDriver();
        }
    }
}
