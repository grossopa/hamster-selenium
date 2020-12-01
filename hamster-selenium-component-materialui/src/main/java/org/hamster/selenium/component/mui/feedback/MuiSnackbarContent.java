package org.hamster.selenium.component.mui.feedback;

import org.hamster.selenium.component.mui.AbstractMuiComponent;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * The content element of {@link MuiSnackbar}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiSnackbarContent extends AbstractMuiComponent {

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiSnackbarContent(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "SnackbarContent";
    }

    /**
     * Finds the message component.
     *
     * @return the message component.
     */
    public WebComponent getMessage() {
        return this.findComponent(By.className(config.getCssPrefix() + getComponentName() + "-message"));
    }

    public WebComponent getAction() {
        return this.findComponent(By.className(config.getCssPrefix() + getComponentName() + "-action"));
    }
}
