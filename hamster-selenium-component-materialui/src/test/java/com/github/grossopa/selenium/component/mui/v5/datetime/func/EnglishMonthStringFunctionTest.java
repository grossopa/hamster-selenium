/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the “Software”), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.selenium.component.mui.v5.datetime.func;

import com.github.grossopa.selenium.component.mui.exception.NoSuchMonthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link EnglishMonthStringFunction}
 *
 * @author Jack Yin
 * @since 1.8
 */
class EnglishMonthStringFunctionTest {

    EnglishMonthStringFunction testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new EnglishMonthStringFunction();
    }


    @Test
    void stringToMonth() {
        String[] months = new String[]{"January", "February", "March", "april", "may", "june", "july", "august",
                "SEPTEMBER", "oCtObEr", "NOV", "DEC"};

        assertDoesNotThrow(() -> {
            for (int i = 0; i < months.length; i++) {
                assertEquals(Month.of(i + 1), testSubject.stringToMonth(months[i]));
            }
        });
    }

    @Test
    void stringToMonthThrow() {
        assertThrows(NoSuchMonthException.class, () -> testSubject.stringToMonth("sdfsdf"));
    }

    @Test
    void monthToString() {
        assertEquals("Feb", testSubject.monthToString(Month.FEBRUARY));
    }

    @Test
    void getInstance() {
        assertSame(EnglishMonthStringFunction.getInstance(), EnglishMonthStringFunction.getInstance());
    }

    @Test
    void testToString() {
        assertEquals(
                "EnglishStringToMonthFunction{MONTHS=[Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec]}",
                EnglishMonthStringFunction.getInstance().toString());
    }

    @Test
    void privateSingletonConstructor() {
        boolean asserted = false;
        Constructor<?> constructor = EnglishMonthStringFunction.Singleton.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            assertEquals(AssertionError.class, e.getCause().getClass());
            asserted = true;
        }

        assertTrue(asserted);
    }

}
