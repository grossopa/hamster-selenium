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

package com.github.grossopa.selenium.examples.mui.v5.datadisplay;

import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiList;
import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiListItem;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openqa.selenium.By.className;

/**
 * Test cases for {@link MuiList} and {@link MuiListItem}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiListTestCases extends AbstractBrowserSupport {

    /**
     * Tests the basic lists.
     *
     * @see <a href="https://mui.com/components/dividers/#basic-list">
     * https://mui.com/components/dividers/#basic-list</a>
     */
    public void testBasicList() {
        List<MuiList> lists = driver.findComponent(By.id("BasicList.js")).findComponent(By2.parent())
                .findComponentsAs(className("MuiList-root"), c -> c.as(muiV5()).toList());
        assertEquals(2, lists.size());
        lists.forEach(list -> assertTrue(list.validate()));

        MuiList list1 = lists.get(0);
        List<MuiListItem> listItems1 = list1.getListItems();
        assertEquals(2, listItems1.size());
        assertEquals("Inbox", listItems1.get(0).getText());
        assertEquals("Drafts", listItems1.get(1).getText());

        MuiList list2 = lists.get(1);
        List<MuiListItem> listItems2 = list2.getListItems();
        assertEquals(2, listItems2.size());
        assertEquals("Trash", listItems2.get(0).getText());
        assertEquals("Spam", listItems2.get(1).getText());
    }

    public static void main(String[] args) {
        MuiListTestCases test = new MuiListTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/lists/");

        test.testBasicList();
    }
}
