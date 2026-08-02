package com.github.grossopa.playwright.core.intercepting;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterceptingPlaywrightTest {

    private Playwright playwright;
    private InterceptingHandler handler;
    private InterceptingPlaywright interceptingPlaywright;

    @BeforeEach
    void setUp() {
        playwright = mock(Playwright.class);
        handler = mock(InterceptingHandler.class);
        when(handler.execute(any(), any())).thenAnswer(invocation -> {
            java.util.function.Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        interceptingPlaywright = new InterceptingPlaywright(playwright, handler);
    }

    @Test
    void testConstructorWithNullPlaywright() {
        assertThrows(NullPointerException.class, () -> new InterceptingPlaywright(null, handler));
    }

    @Test
    void testConstructorWithNullHandler() {
        assertThrows(NullPointerException.class, () -> new InterceptingPlaywright(playwright, null));
    }

    @Test
    void testCreate() {
        InterceptingPlaywright created = InterceptingPlaywright.create(playwright);
        assertNotNull(created);
    }

    @Test
    void testChromium() {
        BrowserType browserType = mock(BrowserType.class);
        when(playwright.chromium()).thenReturn(browserType);
        assertEquals(browserType, interceptingPlaywright.chromium());
    }

    @Test
    void testFirefox() {
        BrowserType browserType = mock(BrowserType.class);
        when(playwright.firefox()).thenReturn(browserType);
        assertEquals(browserType, interceptingPlaywright.firefox());
    }

    @Test
    void testWebkit() {
        BrowserType browserType = mock(BrowserType.class);
        when(playwright.webkit()).thenReturn(browserType);
        assertEquals(browserType, interceptingPlaywright.webkit());
    }

    @Test
    void testRequest() {
        APIRequest apiRequest = mock(APIRequest.class);
        when(playwright.request()).thenReturn(apiRequest);
        assertEquals(apiRequest, interceptingPlaywright.request());
    }

    @Test
    void testSelectors() {
        Selectors selectors = mock(Selectors.class);
        when(playwright.selectors()).thenReturn(selectors);
        assertEquals(selectors, interceptingPlaywright.selectors());
    }

    @Test
    void testClose() {
        interceptingPlaywright.close();
        verify(playwright).close();
    }

    @Test
    void testToString() {
        when(playwright.toString()).thenReturn("Playwright@123");
        assertEquals("Playwright@123", interceptingPlaywright.toString());
    }
}
