package com.github.grossopa.selenium.examples.mui;

import com.github.grossopa.selenium.component.mui.core.MuiGrid;
import com.github.grossopa.selenium.core.component.WebComponent;
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
        List<MuiGrid> testedGrids = testGridParentDiv.findComponents(By.className("MuiGrid-root")).stream()
                .map(c -> c.as(mui()).toGrid()).collect(toList());

        assertEquals(1, testedGrids.stream().filter(MuiGrid::isContainer).toArray().length);

        List<MuiGrid> testItemGrids = testedGrids.stream().filter(MuiGrid::isItem).collect(toList());
        assertEquals(2, testItemGrids.toArray().length);

        testItemGrids.forEach(testItemGrid -> assertEquals(8, testItemGrid.gridItemSpacingValue(2)));
    }

    public static void main(String[] args) {
        MuiCoreTestCases test = new MuiCoreTestCases();
        try {
            test.setUpDriver(CHROME);
            test.testGrid();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
