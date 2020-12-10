package org.hamster.selenium.component.mui;

import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

/**
 * The Material UI Radio implementation
 *
 * @author Chenyu Wang
 * @see <a href="https://material-ui.com/components/radio-buttons/">
 * https://material-ui.com/components/radio-buttons/</a>
 * @since 1.0
 */
public class MuiRadio extends AbstractMuiComponent {

    /**
     * Constructs an MuiRadio instance with the delegated element and root driver
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
        return config.isChecked(this);
    }
}
