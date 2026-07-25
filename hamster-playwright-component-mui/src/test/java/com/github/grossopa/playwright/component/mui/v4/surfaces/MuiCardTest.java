package com.github.grossopa.playwright.component.mui.v4.surfaces;

import com.github.grossopa.playwright.component.mui.MuiVersion;
import com.github.grossopa.playwright.component.mui.config.MuiConfig;
import com.github.grossopa.playwright.core.ComponentDriver;
import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MuiCardTest {
    MuiCard testSubject;
    Locator locator = mock(Locator.class);
    ComponentDriver driver = mock(ComponentDriver.class);
    MuiConfig config = new MuiConfig();

    @BeforeEach
    void setUp() { testSubject = new MuiCard(locator, driver, config); }

    @Test void getComponentName() { assertEquals("Card", testSubject.getComponentName()); }
    @Test void versions() { assertEquals(EnumSet.of(MuiVersion.V4, MuiVersion.V5, MuiVersion.V6), testSubject.versions()); }

    /**
     * Mocks the locator chain for getActions():
     * 1. findComponent(".MuiCardActions-root") → locator.locator(selector).first() → always non-null
     * 2. actionsContainer.findComponents("button") → wrappedLocator.locator("button").all()
     */
    private void mockFindActions(Locator... buttonLocators) {
        Locator actionsRootLocator = mock(Locator.class);
        Locator actionsFirstLocator = mock(Locator.class);
        Locator buttonChildLocator = mock(Locator.class);

        // findComponent(".MuiCardActions-root") → locator.locator(any).first()
        when(locator.locator(anyString())).thenReturn(actionsRootLocator);
        when(actionsRootLocator.first()).thenReturn(actionsFirstLocator);

        // actionsContainer.findComponents("button") → wrappedLocator.locator("button").all()
        when(actionsFirstLocator.locator(anyString())).thenReturn(buttonChildLocator);
        when(buttonChildLocator.all()).thenReturn(List.of(buttonLocators));
    }

    @Test void getActionCount() {
        mockFindActions(mock(Locator.class), mock(Locator.class));
        assertEquals(2, testSubject.getActionCount());
    }

    @Test void clickAction() {
        Locator btnLocator = mock(Locator.class);
        when(btnLocator.innerText()).thenReturn("Save");
        mockFindActions(btnLocator);
        testSubject.clickAction("Save");
        verify(btnLocator).click();
    }

    @Test void clickActionNotFound() {
        mockFindActions();
        assertThrows(IllegalArgumentException.class, () -> testSubject.clickAction("Save"));
    }
}
