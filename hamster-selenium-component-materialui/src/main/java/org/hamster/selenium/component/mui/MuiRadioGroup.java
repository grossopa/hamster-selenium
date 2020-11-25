package org.hamster.selenium.component.mui;

import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * The Material UI Radio implementation
 *
 * @author Chenyu Wang
 * @see <a href="https://material-ui.com/components/radio-buttons/">
 * https://material-ui.com/components/radio-buttons/</a>
 * @since 1.0
 */
public class MuiRadioGroup extends AbstractMuiComponent {

    /**
     * Constructs an MuiRadioGroup instance with the delegated element and root driver
     *
     * @param element
     *         the delegated element
     * @param driver
     *         the root driver
     * @param config
     *         the Material UI configuration
     */
    public MuiRadioGroup(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    @Override
    public String getComponentName() {
        return "RadioGroup";
    }

    /**
     * Finds and returns the radios belongs to this container.
     *
     * @return the found radios
     */
    public List<MuiRadio> getRadios() {
        return element.findElements(config.radioLocator()).stream().map(radio -> new MuiRadio(radio, driver, config))
                .collect(toList());
    }

    private WebComponent getFormGroup() {
        return this.findComponent(By.className(config.getCssPrefix() + "MuiFormGroup-root"));
    }
}
