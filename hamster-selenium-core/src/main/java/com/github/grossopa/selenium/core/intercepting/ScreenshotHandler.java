package com.github.grossopa.selenium.core.intercepting;

import com.github.grossopa.selenium.core.ComponentWebDriver;
import org.openqa.selenium.OutputType;

public class ScreenshotHandler implements InterceptingHandler{

    private ComponentWebDriver driver;
    private final OutputType outputType;

    public ScreenshotHandler(ComponentWebDriver driver, OutputType outputType) {
        this.driver = driver;
        this.outputType = outputType;
    }

    @Override
    public void onBefore(MethodInfo<?> methodInfo) {
        driver.getScreenshotAs(outputType);
    }

    @Override
    public void onAfter(MethodInfo<?> methodInfo, Object resultValue) {
        driver.getScreenshotAs(outputType);
    }

    @Override
    public void onException(MethodInfo<?> methodInfo, Exception exception) {
        driver.getScreenshotAs(outputType);
    }
}
