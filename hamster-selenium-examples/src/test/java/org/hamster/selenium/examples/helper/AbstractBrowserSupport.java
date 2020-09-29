package org.hamster.selenium.examples.helper;

import lombok.SneakyThrows;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.DefaultComponentWebDriver;
import org.hamster.selenium.core.driver.*;
import org.hamster.selenium.examples.StartDriverService;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;


/**
 * The parent class of managing the driver
 *
 * @author Jack Yin
 * @since 1.0
 */
public abstract class AbstractBrowserSupport {

    public static final String EXECUTABLE_PATH = "D://software/drivers/chromedriver-84.exe";

    protected ComponentWebDriver driver;

    @SneakyThrows
    public void setUpDriver(WebDriverType type) {
        DriverConfig config = new DriverConfig();
        config.setDriverExecutablePath(EXECUTABLE_PATH);
        config.setDriverVersion("85");
        config.setType(WebDriverType.CHROME);

        Capabilities options = config.getType().apply(new CreateOptionsAction(), null);
        WebDriver temp = config.getType()
                .apply(new CreateWebDriverFromRunningServiceAction(), new RunningServiceParams(options,
                        "http://localhost:" + StartDriverService.PORT));

        driver = new DefaultComponentWebDriver(temp);
    }

    public void stopDriver() {
        // driver.close();
    }

}
