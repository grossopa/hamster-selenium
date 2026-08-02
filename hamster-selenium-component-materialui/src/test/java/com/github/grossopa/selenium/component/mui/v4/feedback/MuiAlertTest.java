package com.github.grossopa.selenium.component.mui.v4.feedback;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.component.mui.config.MuiConfig;
import com.github.grossopa.selenium.core.ComponentWebDriver;
import com.github.grossopa.selenium.core.component.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.github.grossopa.selenium.component.mui.MuiVersion.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiAlertTest {

    MuiAlert testSubject;
    WebElement element = mock(WebElement.class);
    ComponentWebDriver driver = mock(ComponentWebDriver.class);
    MuiConfig config = mock(MuiConfig.class);

    @BeforeEach
    void setUp() {
        when(config.getCssPrefix()).thenReturn("Mui");
        testSubject = new MuiAlert(element, driver, config);
    }

    @Test
    void versions() {
        assertArrayEquals(new MuiVersion[]{V4, V5, V6}, testSubject.versions().toArray());
    }

    @Test
    void getComponentName() {
        assertEquals("Alert", testSubject.getComponentName());
    }

    @Test
    void getSeveritySuccess() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test
    void getSeveritySuccessFilled() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-filledSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test
    void getSeveritySuccessOutlined() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-outlinedSuccess");
        assertEquals("success", testSubject.getSeverity());
    }

    @Test
    void getSeverityInfo() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getSeverityInfoFilled() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-filledInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getSeverityInfoOutlined() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-outlinedInfo");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getSeverityWarning() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test
    void getSeverityWarningFilled() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-filledWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test
    void getSeverityWarningOutlined() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-outlinedWarning");
        assertEquals("warning", testSubject.getSeverity());
    }

    @Test
    void getSeverityError() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-standardError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test
    void getSeverityErrorFilled() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-filledError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test
    void getSeverityErrorOutlined() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-outlinedError");
        assertEquals("error", testSubject.getSeverity());
    }

    @Test
    void getSeverityDefault() {
        when(element.getAttribute("class")).thenReturn("MuiAlert-root");
        assertEquals("info", testSubject.getSeverity());
    }

    @Test
    void getMessage() {
        WebElement messageElement = mock(WebElement.class);
        when(messageElement.getText()).thenReturn("Alert message");
        when(element.findElement(By.className("MuiAlert-message"))).thenReturn(messageElement);
        assertEquals("Alert message", testSubject.getMessage());
    }

    @Test
    void close() {
        WebElement closeElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiAlert-closeButton"))).thenReturn(closeElement);
        testSubject.close();
        verify(closeElement).click();
    }

    @Test
    void closeWithoutButton() {
        when(element.findElement(By.className("MuiAlert-closeButton"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        assertThrows(UnsupportedOperationException.class, () -> testSubject.close());
    }

    @Test
    void hasIcon() {
        WebElement iconElement = mock(WebElement.class);
        when(element.findElement(By.className("MuiAlert-icon"))).thenReturn(iconElement);
        assertTrue(testSubject.hasIcon());
    }

    @Test
    void hasIconNegative() {
        when(element.findElement(By.className("MuiAlert-icon"))).thenThrow(new org.openqa.selenium.NoSuchElementException("not found"));
        assertFalse(testSubject.hasIcon());
    }

    @Test
    void testToString() {
        when(element.toString()).thenReturn("element-toString");
        assertEquals("MuiAlert{element=element-toString}", testSubject.toString());
    }
}
