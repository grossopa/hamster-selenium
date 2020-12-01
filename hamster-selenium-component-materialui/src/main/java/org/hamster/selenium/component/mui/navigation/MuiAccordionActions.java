package org.hamster.selenium.component.mui.navigation;

import org.hamster.selenium.component.mui.AbstractMuiComponent;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.openqa.selenium.WebElement;

/**
 * The actions container of {@link MuiAccordion}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MuiAccordionActions extends AbstractMuiComponent {

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiAccordionActions(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "AccordionActions";
    }
}
