package org.hamster.selenium.component.mui;

import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MuiRadio extends AbstractMuiComponent {

    /**
     * Constructs an MuiSwitch instance with the delegated element and root driver
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the Material UI configuration
     */
    public MuiRadio(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "Radio";
    }

    @Override
    public boolean isSelected() {
        return config.isChecked(getButton());
    }

    @Override
    public boolean isEnabled() {
        return !config.isDisabled(getButton());
    }

    private WebComponent getButton() {
        return this.findComponent(By.className(config.getCssPrefix() + "IconButton-root"));
    }
}
