package com.github.grossopa.selenium.component.mui;

import com.github.grossopa.selenium.component.mui.v4.inputs.MuiButton;
import com.github.grossopa.selenium.component.mui.v4.inputs.MuiTextField;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiComponentFinderTest {

    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    WebComponent webComponent = mock(WebComponent.class);
    By by = By.id("test");

    @BeforeEach
    void setUp() {
        when(driver.findComponent(by)).thenReturn(webComponent);
    }

    @Test
    void testPrivateConstructor() throws Exception {
        java.lang.reflect.Constructor<MuiComponentFinder> constructor = MuiComponentFinder.class.getDeclaredConstructor();
        assertFalse(constructor.canAccess(null));
        assertTrue(java.lang.reflect.Modifier.isPrivate(constructor.getModifiers()));
    }

    @Test
    void findMuiButton() {
        MuiComponents muiComponents = mock(MuiComponents.class);
        MuiButton muiButton = mock(MuiButton.class);
        when(webComponent.as(any())).thenReturn(muiComponents);
        when(muiComponents.toButton()).thenReturn(muiButton);

        MuiButton result = MuiComponentFinder.findMuiButton(driver, by);
        assertEquals(muiButton, result);
    }

    @Test
    void findMuiTextField() {
        MuiComponents muiComponents = mock(MuiComponents.class);
        MuiTextField muiTextField = mock(MuiTextField.class);
        when(webComponent.as(any())).thenReturn(muiComponents);
        when(muiComponents.toTextField()).thenReturn(muiTextField);

        MuiTextField result = MuiComponentFinder.findMuiTextField(driver, by);
        assertEquals(muiTextField, result);
    }

    @Test
    void findMuiComponent() {
        java.util.function.Function<WebComponent, String> func = mock(java.util.function.Function.class);
        when(func.apply(webComponent)).thenReturn("result");

        String result = MuiComponentFinder.findMuiComponent(driver, by, func);
        assertEquals("result", result);
    }
}
