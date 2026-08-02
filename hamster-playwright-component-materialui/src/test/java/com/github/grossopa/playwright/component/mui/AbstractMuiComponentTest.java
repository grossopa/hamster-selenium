package com.github.grossopa.playwright.component.mui;

import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractMuiComponentTest {

    // Concrete subclass for testing
    static class TestMuiComponent extends AbstractMuiComponent {
        TestMuiComponent(Locator locator, ComponentDriver driver, MuiConfig config) {
            super(locator, driver, config);
        }

        @Override
        public String getComponentName() {
            return "TestComponent";
        }
    }

    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    TestMuiComponent testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new TestMuiComponent(locator, driver, config);
    }

    @Test void getComponentName() {
        assertEquals("TestComponent", testSubject.getComponentName());
    }

    @Test void versionsDefault() {
        assertEquals(EnumSet.of(MuiVersion.V4), testSubject.versions());
    }

    @Test void locatorReturnsDelegate() {
        assertSame(locator, testSubject.locator());
    }

    @Test void driverReturnsDelegate() {
        assertSame(driver, testSubject.driver());
    }

    @Test void equalsSameInstance() {
        assertEquals(testSubject, testSubject);
    }

    @Test void equalsDifferentInstance() {
        // super.equals() uses Object.equals (identity), so different instances are never equal
        TestMuiComponent other = new TestMuiComponent(locator, driver, config);
        assertNotEquals(testSubject, other);
    }

    @Test void equalsNull() {
        assertNotEquals(null, testSubject);
    }

    @Test void equalsDifferentType() {
        assertNotEquals("string", testSubject);
    }

    @Test void hashCodeConsistent() {
        int hash1 = testSubject.hashCode();
        int hash2 = testSubject.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test void toStringContainsClassName() {
        String str = testSubject.toString();
        assertTrue(str.contains("TestMuiComponent"));
    }

    @Test void toStringContainsLocator() {
        String str = testSubject.toString();
        assertTrue(str.contains("locator="));
    }
}
