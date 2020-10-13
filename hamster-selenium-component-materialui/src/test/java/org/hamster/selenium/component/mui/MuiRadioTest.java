package org.hamster.selenium.component.mui;

import org.hamster.selenium.component.mui.config.MuiConfig;
import org.hamster.selenium.core.ComponentWebDriver;
import org.hamster.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MuiRadioTest {

    MuiRadio testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    WebElement button = mock(WebElement.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        when(config.getIsCheckedCss()).thenReturn("checked");
        when(config.getIsDisabledCss()).thenReturn("disabled");

        when(element.findElement(eq(By.className("MuiIconButton-root")))).thenReturn(button);
        testSubject = new MuiRadio(element, driver, config);
    }


    @Test
    void getComponentName() {
        assertEquals("Radio", testSubject.getComponentName());
    }

    @Test
    void isSelected() {
        when(config.isChecked(any())).then(answer -> {
            WebComponent component = answer.getArgument(0);
            assertEquals(component.getWrappedElement(), button);
            return true;
        });
        assertTrue(testSubject.isSelected());
    }

    @Test
    void isSelectedNegative() {
        when(config.isChecked(any())).then(answer -> {
            WebComponent component = answer.getArgument(0);
            assertEquals(component.getWrappedElement(), button);
            return false;
        });
        assertFalse(testSubject.isSelected());
    }

    @Test
    void isEnabled() {
        when(config.isSelected(any())).then(answer -> {
            WebComponent component = answer.getArgument(0);
            assertEquals(component.getWrappedElement(), button);
            return true;
        });
        assertTrue(testSubject.isEnabled());
    }

    @Test
    void isEnabledNegative() {
        when(config.isSelected(any())).then(answer -> {
            WebComponent component = answer.getArgument(0);
            assertEquals(component.getWrappedElement(), button);
            return false;
        });
        assertTrue(testSubject.isEnabled());
    }
}