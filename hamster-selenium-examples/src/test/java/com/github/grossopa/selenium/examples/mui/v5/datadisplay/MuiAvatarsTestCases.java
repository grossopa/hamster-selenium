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

import com.github.grossopa.selenium.component.mui.v4.datadisplay.MuiAvatar;
import com.github.grossopa.selenium.core.locator.By2;
import com.github.grossopa.selenium.examples.helper.AbstractBrowserSupport;
import org.openqa.selenium.By;

import java.util.List;

import static com.github.grossopa.selenium.component.mui.MuiComponents.muiV5;
import static com.github.grossopa.selenium.core.driver.WebDriverType.EDGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test cases for {@link MuiAvatar}.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class MuiAvatarsTestCases extends AbstractBrowserSupport {

    /**
     * Tests the image avatars.
     *
     * @see <a href="https://mui.com/components/avatars/#image-avatars">
     * https://mui.com/components/avatars/#image-avatars</a>
     */
    public void testImageAvatars() {
        List<MuiAvatar> imageAvatarList = driver.findComponent(By.id("ImageAvatars.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiAvatar-root"), c -> c.as(muiV5()).toAvatar());
        assertEquals(3, imageAvatarList.size());

        assertEquals("Remy Sharp", imageAvatarList.get(0).getAlt());
        assertEquals("Travis Howard", imageAvatarList.get(1).getAlt());
        assertEquals("Cindy Baker", imageAvatarList.get(2).getAlt());

        assertTrue(imageAvatarList.get(0).getSrc().endsWith("/static/images/avatar/1.jpg"));
        assertTrue(imageAvatarList.get(1).getSrc().endsWith("/static/images/avatar/2.jpg"));
        assertTrue(imageAvatarList.get(2).getSrc().endsWith("/static/images/avatar/3.jpg"));
    }

    /**
     * Tests the letter avatars.
     *
     * @see <a href="https://mui.com/components/avatars/#letter-avatars">
     * https://mui.com/components/avatars/#letter-avatars</a>
     */
    public void testLetterAvatars() {
        List<MuiAvatar> letterAvatarList = driver.findComponent(By.id("LetterAvatars.js")).findComponent(By2.parent())
                .findComponentsAs(By.className("MuiAvatar-root"), c -> c.as(muiV5()).toAvatar());
        assertEquals(3, letterAvatarList.size());

        assertEquals("H", letterAvatarList.get(0).getText());
        assertEquals("N", letterAvatarList.get(1).getText());
        assertEquals("OP", letterAvatarList.get(2).getText());
    }

    public static void main(String[] args) {
        MuiAvatarsTestCases test = new MuiAvatarsTestCases();
        test.setUpDriver(EDGE);
        test.driver.navigate().to("https://mui.com/components/avatars/");

        test.testImageAvatars();
        test.testLetterAvatars();
    }
}
