package com.github.grossopa.playwright.core;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultWebComponentTest {

    private Locator locator;
    private ComponentDriver driver;
    private DefaultWebComponent component;

    @BeforeEach
    void setUp() {
        locator = mock(Locator.class);
        driver = mock(ComponentDriver.class);
        component = new DefaultWebComponent(locator, driver);
    }

    @Test
    void testConstructor() {
        assertNotNull(component);
        assertEquals(locator, component.locator());
        assertEquals(driver, component.driver());
    }

    @Test
    void testFindComponents() {
        Locator childLocator1 = mock(Locator.class);
        Locator childLocator2 = mock(Locator.class);
        when(locator.locator(".child")).thenReturn(locator);
        when(locator.all()).thenReturn(List.of(childLocator1, childLocator2));

        List<WebComponent> result = component.findComponents(".child");

        assertEquals(2, result.size());
        assertInstanceOf(DefaultWebComponent.class, result.get(0));
        assertInstanceOf(DefaultWebComponent.class, result.get(1));
    }

    @Test
    void testFindComponent() {
        Locator childLocator = mock(Locator.class);
        when(locator.locator(".child")).thenReturn(locator);
        when(locator.first()).thenReturn(childLocator);

        WebComponent result = component.findComponent(".child");

        assertNotNull(result);
        assertInstanceOf(DefaultWebComponent.class, result);
    }

    @Test
    void testAs() {
        Function<WebComponent, String> mapper = wc -> "mapped";
        String result = component.as(mapper);
        assertEquals("mapped", result);
    }

    @Test
    void testLocator() {
        assertEquals(locator, component.locator());
    }

    @Test
    void testClick() {
        component.click();
        verify(locator).click();
    }

    @Test
    void testHover() {
        component.hover();
        verify(locator).hover();
    }

    @Test
    void testTextContent() {
        when(locator.textContent()).thenReturn("text");
        assertEquals("text", component.textContent());
    }

    @Test
    void testInnerText() {
        when(locator.innerText()).thenReturn("inner");
        assertEquals("inner", component.innerText());
    }

    @Test
    void testInnerHTML() {
        when(locator.innerHTML()).thenReturn("<div>html</div>");
        assertEquals("<div>html</div>", component.innerHTML());
    }

    @Test
    void testGetAttribute() {
        when(locator.getAttribute("class")).thenReturn("my-class");
        assertEquals("my-class", component.getAttribute("class"));
    }

    @Test
    void testIsVisible() {
        when(locator.isVisible()).thenReturn(true);
        assertTrue(component.isVisible());
    }

    @Test
    void testIsEnabled() {
        when(locator.isEnabled()).thenReturn(true);
        assertTrue(component.isEnabled());
    }

    @Test
    void testIsDisabled() {
        when(locator.isDisabled()).thenReturn(true);
        assertTrue(component.isDisabled());
    }

    @Test
    void testFill() {
        component.fill("value");
        verify(locator).fill("value");
    }

    @Test
    void testDriver() {
        assertEquals(driver, component.driver());
    }

    @Test
    void testGetComponentTagName() {
        assertEquals("DefaultWebComponent", component.getComponentTagName());
    }
}
