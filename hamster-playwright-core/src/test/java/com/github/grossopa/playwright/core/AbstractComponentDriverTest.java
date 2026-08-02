package com.github.grossopa.playwright.core;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AbstractComponentDriverTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private TestComponentDriver driver;

    static class TestComponentDriver extends AbstractComponentDriver {
        TestComponentDriver(Playwright playwright, Browser browser, BrowserContext context, Page page) {
            super(playwright, browser, context, page);
        }

        @Override
        public WebComponent mapLocator(Object locator) {
            return new DefaultWebComponent((Locator) locator, this);
        }
    }

    @BeforeEach
    void setUp() {
        playwright = mock(Playwright.class);
        browser = mock(Browser.class);
        context = mock(BrowserContext.class);
        page = mock(Page.class);
        driver = new TestComponentDriver(playwright, browser, context, page);
    }

    @Test
    void testFindComponents() {
        Locator pageLocator = mock(Locator.class);
        Locator locator1 = mock(Locator.class);
        Locator locator2 = mock(Locator.class);
        when(page.locator(".item")).thenReturn(pageLocator);
        when(pageLocator.all()).thenReturn(List.of(locator1, locator2));

        List<WebComponent> result = driver.findComponents(".item");
        assertEquals(2, result.size());
    }

    @Test
    void testFindComponentAs() {
        Locator pageLocator = mock(Locator.class);
        when(page.locator(".btn")).thenReturn(pageLocator);

        String result = driver.findComponentAs(".btn", wc -> "converted");
        assertEquals("converted", result);
    }

    @Test
    void testFindComponentsAs() {
        Locator pageLocator = mock(Locator.class);
        Locator locator1 = mock(Locator.class);
        Locator locator2 = mock(Locator.class);
        when(page.locator(".item")).thenReturn(pageLocator);
        when(pageLocator.all()).thenReturn(List.of(locator1, locator2));

        List<String> result = driver.findComponentsAs(".item", wc -> "item");
        assertEquals(2, result.size());
        assertEquals("item", result.get(0));
    }

    @Test
    void testFindComponent() {
        Locator pageLocator = mock(Locator.class);
        when(page.locator(".elem")).thenReturn(pageLocator);

        WebComponent result = driver.findComponent(".elem");
        assertNotNull(result);
    }

    @Test
    void testPlaywright() {
        assertEquals(playwright, driver.playwright());
    }

    @Test
    void testBrowser() {
        assertEquals(browser, driver.browser());
    }

    @Test
    void testContext() {
        assertEquals(context, driver.context());
    }

    @Test
    void testPage() {
        assertEquals(page, driver.page());
    }

    @Test
    void testNavigate() {
        driver.navigate("https://example.com");
        verify(page).navigate("https://example.com");
    }

    @Test
    void testNavigateWithTimeout() {
        driver.navigate("https://example.com", 5000L);
        verify(page).navigate(eq("https://example.com"), any(Page.NavigateOptions.class));
    }
}
