package com.github.grossopa.playwright.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractComponentsTest {

    private TestComponents components;

    static class TestComponents extends AbstractComponents {
        // concrete implementation for testing
    }

    @BeforeEach
    void setUp() {
        components = new TestComponents();
    }

    @Test
    void testSetContext() {
        WebComponent component = mock(WebComponent.class);
        ComponentDriver driver = mock(ComponentDriver.class);

        components.setContext(component, driver);

        assertEquals(component, components.getComponent());
        assertEquals(driver, components.getDriver());
    }

    @Test
    void testSetContextWithNullComponent() {
        ComponentDriver driver = mock(ComponentDriver.class);
        assertThrows(NullPointerException.class, () -> components.setContext(null, driver));
    }

    @Test
    void testSetContextWithNullDriver() {
        WebComponent component = mock(WebComponent.class);
        assertThrows(NullPointerException.class, () -> components.setContext(component, null));
    }

    @Test
    void testGetComponent() {
        assertNull(components.getComponent());
    }

    @Test
    void testGetDriver() {
        assertNull(components.getDriver());
    }
}
