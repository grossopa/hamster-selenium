package org.hamster.selenium.component.mui.feedback;

import org.hamster.selenium.component.mui.AbstractMuiComponent;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

/**
 * The backdrop component is used to provide emphasis on a particular element or parts of it.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/backdrop/">https://material-ui.com/components/backdrop/</a>
 * @since 1.0
 */
public class MuiBackdrop extends AbstractMuiComponent {

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiBackdrop(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "Backdrop";
    }
}
