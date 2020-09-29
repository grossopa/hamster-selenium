package org.hamster.selenium.examples;

import org.hamster.selenium.core.driver.CreateDriverServiceAction;
import org.hamster.selenium.core.driver.CreateOptionsAction;
import org.hamster.selenium.core.driver.DriverConfig;
import org.hamster.selenium.core.driver.WebDriverType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.service.DriverService;

import java.io.IOException;

/**
 * Starts a DriverService
 *
 * @author Jack Yin
 * @since 1.0
 */
public class StartDriverService {

    public static final String EXECUTABLE_PATH = "D://software/drivers/chromedriver-84.exe";
    public static final int PORT = 38383;

    public static void main(String[] args) throws IOException {
        DriverConfig config = new DriverConfig();
        config.setDriverExecutablePath(EXECUTABLE_PATH);
        config.setDriverVersion("85");
        config.setType(WebDriverType.CHROME);
        config.setPort(PORT);

        DriverService driverService = config.getType().apply(new CreateDriverServiceAction(), config);
        driverService.start();
    }
}
