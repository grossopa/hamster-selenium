package org.hamster.selenium.component.mui;

import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MuiRadioGroup}
 *
 * @author Chenyu Wang
 * @since 1.0
 */
public class MuiRadioGroupTest {
    MuiRadioGroup testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        testSubject = new MuiRadioGroup(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("RadioGroup", testSubject.getComponentName());
    }

    @Test
    void getRadios() {
        when(config.radioLocator()).thenReturn(By.cssSelector(".MuiRadio-root"));
        when(element.findElements(eq(config.radioLocator())))
                .thenReturn(asList(mock(MuiRadio.class), mock(MuiRadio.class), mock(MuiRadio.class)));
        assertEquals(3, testSubject.getRadios().size());
    }
}
