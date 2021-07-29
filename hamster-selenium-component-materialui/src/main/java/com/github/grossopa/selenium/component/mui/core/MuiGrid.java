package com.github.grossopa.selenium.component.mui.core;

import com.github.grossopa.selenium.component.mui.AbstractMuiComponent;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.openqa.selenium.WebElement;

/**
 * The grid creates visual consistency between layouts while allowing flexibility across a wide variety of designs.
 * Material Design’s responsive UI is based on a 12-column grid layout.
 *
 * @author: Elena Wang
 * @see <a href="https://material-ui.com/components/grid/">
 * https://material-ui.com/components/grid/</a>
 * @date: 7/22/2021 10:02 PM
 * @since: 1.6
 */
public class MuiGrid extends AbstractMuiComponent {

    /**
     * the component name
     */
    public static final String COMPONENT_NAME = "Grid";

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver  the root driver
     * @param config  the Material UI configuration
     */
    protected MuiGrid(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    public boolean isContainer() {
        String className = this.getAttribute("class");
        return className.contains("MuiGrid-container");
    }

    public boolean isItem() {
        String className = this.getAttribute("class");
        return className.contains("MuiGrid-item");
    }

    /**
     * @param num the number defined in front
     * @return true if it is a Grid component
     */
    public Boolean checkGridItemSpacingValue(int num) {
        int spaceNumber = num * 4;

    }

    @Override
    public String toString() {
        return "MuiGrid{" + "element=" + element + '}';
    }

    /**
     *
     * @return true if it is a Grid component
     */
    @Override
    public boolean validate() {
        return config.validateByCss(this, config.getCssPrefix() + COMPONENT_NAME);
    }
}
