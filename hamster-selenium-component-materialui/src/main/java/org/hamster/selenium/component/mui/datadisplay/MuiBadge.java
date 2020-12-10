package org.hamster.selenium.component.mui.datadisplay;

import org.apache.commons.lang3.StringUtils;
import org.hamster.selenium.component.mui.AbstractMuiComponent;
import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static org.apache.commons.lang3.StringUtils.endsWith;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

/**
 * Badge generates a small badge to the top-right of its child(ren).
 *
 * @author Jack Yin
 * @see <a href="https://material-ui.com/components/badges/">https://material-ui.com/components/badges/</a>
 * @since 1.0
 */
public class MuiBadge extends AbstractMuiComponent {
    /**
     * Constructs an instance with the delegated element and root driver
     *
     * @param element the delegated element
     * @param driver the root driver
     * @param config the Material UI configuration
     */
    public MuiBadge(WebElement element, ComponentWebDriver driver, MuiConfig config) {
        super(element, driver, config);
    }

    /**
     * Finds the badge element. could be either visible or invisible. with a certain number or a dot indicates there is
     * new message(s).
     *
     * @return the badge element.
     */
    public WebComponent getBadge() {
        return this.findComponent(By.className(config.getCssPrefix() + "Badge-badge"));
    }

    /**
     * Returns the number of the badge. Note it will return 0 for dot as well even if it's displayed.
     *
     * @return the number of the badge.
     */
    public int getBadgeNumber() {
        String badgeText = getBadge().getText();
        if (endsWith(badgeText, "+")) {
            badgeText = badgeText.substring(0, badgeText.length() - 1);
        }
        return isParsable(badgeText) ? parseInt(badgeText) : 0;
    }

    /**
     * Whether the dot is displayed instead of numbers.
     *
     * @return whether the dot is displayed
     */
    public boolean isDotDisplayed() {
        return stream(getBadge().getAttribute("class").split(" "))
                .anyMatch(str -> str.equalsIgnoreCase(config.getCssPrefix() + "Badge-dot"));
    }

    @Override
    public String getComponentName() {
        return "Badge";
    }

    /**
     * Whether the badge is displayed or not.
     *
     * @return whether the badge is displayed or not
     */
    public boolean isBadgeDisplayed() {
        return stream(getBadge().getAttribute("class").split(" ")).map(StringUtils::trim)
                .noneMatch(str -> str.equalsIgnoreCase(config.getCssPrefix() + "Badge-invisible"));
    }
}
