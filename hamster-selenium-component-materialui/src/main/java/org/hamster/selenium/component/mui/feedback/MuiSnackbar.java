package org.hamster.selenium.component.mui.feedback;

import org.hamster.selenium.component.mui.AbstractMuiComponent;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Snackbars provide brief messages about app processes. The component is also known as a toast.
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/snackbars/">https://material-ui.com/components/snackbars/</a>
 * @since 1.0
 */
public class MuiSnackbar extends AbstractMuiComponent {

    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiSnackbar(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    /**
     * Gets the inner {@link MuiSnackbarContent} element.
     *
     * <p>
     * Note that the result would be null if the content is customized.
     * </p>
     *
     * @return the inner {@link MuiSnackbarContent} element.
     */
    @Nullable
    public MuiSnackbarContent getContent() {
        List<WebComponent> components = this.findComponents(By.className(config.getCssPrefix() + "SnackbarContent"));
        return components.isEmpty() ? null : new MuiSnackbarContent(components.get(0), driver, config);
    }

    @Override
    public String getComponentName() {
        return "Snackbar";
    }
}
