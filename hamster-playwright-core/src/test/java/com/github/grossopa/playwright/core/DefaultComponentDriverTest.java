package com.github.grossopa.playwright.core;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DefaultComponentDriverTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    void setUp() {
        playwright = mock(Playwright.class);
        browser = mock(Browser.class);
        context = mock(BrowserContext.class);
        page = mock(Page.class);
    }

    @Test
    void testConstructorWithAllParams() {
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser, context, page);
        assertEquals(playwright, driver.playwright());
        assertEquals(browser, driver.browser());
        assertEquals(context, driver.context());
        assertEquals(page, driver.page());
    }

    @Test
    void testConstructorWithPlaywrightBrowserContext() {
        when(context.newPage()).thenReturn(page);
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser, context);
        assertEquals(playwright, driver.playwright());
        assertEquals(page, driver.page());
    }

    @Test
    void testConstructorWithPlaywrightBrowser() {
        when(browser.newContext()).thenReturn(context);
        when(context.newPage()).thenReturn(page);
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser);
        assertEquals(playwright, driver.playwright());
    }

    @Test
    void testMapLocatorWithWebComponent() {
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser, context, page);
        WebComponent wc = mock(WebComponent.class);
        WebComponent result = driver.mapLocator(wc);
        assertSame(wc, result);
    }

    @Test
    void testMapLocatorWithLocator() {
        DefaultComponentDriver driver = new DefaultComponentDriver(playwright, browser, context, page);
        Locator locator = mock(Locator.class);
        WebComponent result = driver.mapLocator(locator);
        assertNotNull(result);
        assertInstanceOf(DefaultWebComponent.class, result);
    }
}
