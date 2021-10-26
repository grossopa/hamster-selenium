# Hamster Selenium

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=grossopa_hamster-selenium&metric=alert_status)](https://sonarcloud.io/dashboard?id=grossopa_hamster-selenium)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=grossopa_hamster-selenium&metric=coverage)](https://sonarcloud.io/dashboard?id=grossopa_hamster-selenium)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=grossopa_hamster-selenium&metric=code_smells)](https://sonarcloud.io/dashboard?id=grossopa_hamster-selenium)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=grossopa_hamster-selenium&metric=ncloc)](https://sonarcloud.io/dashboard?id=grossopa_hamster-selenium)

On top of Selenium (web browser automation tool) for providing component-based abstraction of Html DOM with interaction
APIs for automating the web pages built by modern front-end frameworks such as Material UI.

It depends on selenium-java 3.141.59 and JDK 11. please visit https://github.com/SeleniumHQ/selenium to understand how
to use Selenium.

## Material UI (React)

Most of the commonly used components have been implemented in hamster-selenium-component-materialui module.

All the supported MUI components could be found in `MuiComponents` class. They have been tested against the examples in
the Material UI official page:

Version 4: https://v4.mui.com

Version 5: https://mui.com

Check hamster-selenium-examples module for the sample code and
https://github.com/grossopa/hamster-selenium/wiki/Supported-Material-UI-Components for supported Material UI
components.

## Material UI (Angular)

The framework also supports the Angular version of Material UI.

## How to Use

Plain HTML:

    <dependency>
      <groupId>com.github.grossopa</groupId>
      <artifactId>hamster-selenium-component-html</artifactId>
      <version>1.7</version>
    </dependency>

Material UI (React):

    <dependency>
      <groupId>com.github.grossopa</groupId>
      <artifactId>hamster-selenium-component-materialui</artifactId>
      <version>1.7</version>
    </dependency>

Material UI (Angular):

    <dependency>
      <groupId>com.github.grossopa</groupId>
      <artifactId>hamster-selenium-component-mats</artifactId>
      <version>1.7</version>
    </dependency>

Create a ComponentWebDriver from existing WebDriver:

`ComponentWebDriver driver = new DefaultComponentWebDriver(webDriver);`

Locate the element root by class name (e.g. class="MuiSelect-root") or other indicators.

`WebComponent component = driver.findComponent(By.className("MuiSelect-root"));`

Convert the WebComponent to the target class.

`MuiSelect select = component.as(MuiComponents.mui()).toSelect(By2.xpathBuilder().anywhereRelative().attr("class").contains("MuiMenuItem-root").build());`

# License

https://mit-license.org/
