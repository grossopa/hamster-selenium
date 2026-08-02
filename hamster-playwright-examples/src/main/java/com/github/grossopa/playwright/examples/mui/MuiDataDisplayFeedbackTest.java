/*
 * Copyright © 2023 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.grossopa.playwright.examples.mui;

import com.github.grossopa.playwright.component.mui.MuiComponents;
import com.github.grossopa.playwright.component.mui.v4.datadisplay.*;
import com.github.grossopa.playwright.component.mui.v4.feedback.*;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for MUI Data Display and Feedback components
 * Covers Avatar, Badge, Chip, Divider, List, Tooltip, Alert, Dialog, Snackbar, etc.
 *
 * @author Jack Yin
 * @since 1.12
 */
@SuppressWarnings("all")
public class MuiDataDisplayFeedbackTest extends AbstractBrowserSupport {

    private static final MuiComponents mui = MuiComponents.mui();

    /**
     * Test MUI Avatar component
     */
    public void testAvatar() {
        System.out.println("=== Testing MUI Avatar ===");
        driver.navigate("https://mui.com/material-ui/react-avatar/", 600_000);
        
        List<WebComponent> avatars = driver.findComponents(".MuiAvatar-root");
        System.out.println("Found " + avatars.size() + " avatars");
        
        if (!avatars.isEmpty()) {
            mui.setContext(avatars.get(0), driver);
            MuiAvatar avatar = mui.toAvatar();
            System.out.println("Avatar found");
            
            // Check if avatar has image
            String imgSrc = avatar.getAttribute("src");
            System.out.println("Avatar source: " + (imgSrc != null ? "Has image" : "Text/Icon avatar"));
        }
    }

    /**
     * Test MUI Badge component
     */
    public void testBadge() {
        System.out.println("\n=== Testing MUI Badge ===");
        driver.navigate("https://mui.com/material-ui/react-badge/", 600_000);
        
        List<WebComponent> badges = driver.findComponents(".MuiBadge-root");
        System.out.println("Found " + badges.size() + " badges");
        
        if (!badges.isEmpty()) {
            mui.setContext(badges.get(0), driver);
            MuiBadge badge = mui.toBadge();
            System.out.println("Badge component found");
            
            // Get badge content
            List<WebComponent> badgeContent = badge.findComponents(".MuiBadge-badge");
            if (!badgeContent.isEmpty()) {
                System.out.println("Badge text: " + badgeContent.get(0).innerText());
            }
        }
    }

    /**
     * Test MUI Chip component with actions
     */
    public void testChipWithActions() {
        System.out.println("\n=== Testing MUI Chip with Actions ===");
        driver.navigate("https://mui.com/material-ui/react-chip/", 600_000);
        
        List<WebComponent> chips = driver.findComponents(".MuiChip-root");
        System.out.println("Found " + chips.size() + " chips");
        
        if (chips.size() >= 2) {
            // Test regular chip
            mui.setContext(chips.get(0), driver);
            MuiChip regularChip = mui.toChip();
            System.out.println("Chip label: " + regularChip.innerText());
            
            // Test chip with delete action
            List<WebComponent> deletableChips = driver.findComponents(".MuiChip-deletable");
            if (!deletableChips.isEmpty()) {
                mui.setContext(deletableChips.get(0), driver);
                MuiChip deletableChip = mui.toChip();
                System.out.println("Deletable chip found");
                
                // Check for delete icon
                List<WebComponent> deleteIcons = deletableChip.findComponents(".MuiChip-deleteIcon");
                System.out.println("Has delete icon: " + !deleteIcons.isEmpty());
            }
        }
    }

    /**
     * Test MUI Divider component
     */
    public void testDivider() {
        System.out.println("\n=== Testing MUI Divider ===");
        driver.navigate("https://mui.com/material-ui/react-divider/", 600_000);
        
        List<WebComponent> dividers = driver.findComponents(".MuiDivider-root");
        System.out.println("Found " + dividers.size() + " dividers");
        
        if (!dividers.isEmpty()) {
            mui.setContext(dividers.get(0), driver);
            MuiDivider divider = mui.toDivider();
            System.out.println("Divider component found");
            
            // Check divider orientation
            String orientation = divider.getAttribute("aria-orientation");
            System.out.println("Divider orientation: " + (orientation != null ? orientation : "horizontal"));
        }
    }

    /**
     * Test MUI List component
     */
    public void testList() {
        System.out.println("\n=== Testing MUI List ===");
        driver.navigate("https://mui.com/material-ui/react-list/", 600_000);
        
        List<WebComponent> lists = driver.findComponents(".MuiList-root");
        System.out.println("Found " + lists.size() + " lists");
        
        if (!lists.isEmpty()) {
            mui.setContext(lists.get(0), driver);
            MuiList list = mui.toList();
            System.out.println("List component found");
            
            // Get list items
            List<WebComponent> listItems = list.findComponents(".MuiListItem-root");
            System.out.println("List contains " + listItems.size() + " items");
            
            if (!listItems.isEmpty()) {
                System.out.println("First item: " + listItems.get(0).innerText());
            }
        }
    }

    /**
     * Test MUI Tooltip component
     */
    public void testTooltip() {
        System.out.println("\n=== Testing MUI Tooltip ===");
        driver.navigate("https://mui.com/material-ui/react-tooltip/", 600_000);
        
        List<WebComponent> tooltips = driver.findComponents("[title]");
        System.out.println("Found " + tooltips.size() + " elements with title attribute");
        
        // Find tooltip wrapped elements
        List<WebComponent> tooltipWrappers = driver.findComponents(".MuiTooltip-popper");
        System.out.println("Found " + tooltipWrappers.size() + " tooltip poppers");
        
        if (!tooltipWrappers.isEmpty()) {
            System.out.println("Tooltip component structure found");
        }
    }

    /**
     * Test MUI Alert component
     */
    public void testAlert() {
        System.out.println("\n=== Testing MUI Alert ===");
        driver.navigate("https://mui.com/material-ui/react-alert/", 600_000);
        
        List<WebComponent> alerts = driver.findComponents(".MuiAlert-root");
        System.out.println("Found " + alerts.size() + " alerts");
        
        if (!alerts.isEmpty()) {
            mui.setContext(alerts.get(0), driver);
            MuiAlert alert = mui.toAlert();
            System.out.println("Alert component found");
            
            // Check alert severity
            String severity = alert.getAttribute("data-severity");
            System.out.println("Alert severity: " + (severity != null ? severity : "default"));
            
            // Get alert message
            System.out.println("Alert message: " + alert.innerText());
        }
    }

    /**
     * Test MUI Snackbar component
     */
    public void testSnackbar() {
        System.out.println("\n=== Testing MUI Snackbar ===");
        driver.navigate("https://mui.com/material-ui/react-snackbar/", 600_000);
        
        // Find snackbar trigger buttons
        List<WebComponent> buttons = driver.findComponents(".MuiButton-root");
        System.out.println("Found " + buttons.size() + " buttons (potential snackbar triggers)");
        
        // Look for snackbar container
        List<WebComponent> snackbars = driver.findComponents(".MuiSnackbar-root");
        System.out.println("Found " + snackbars.size() + " snackbar containers");
        
        if (!snackbars.isEmpty()) {
            mui.setContext(snackbars.get(0), driver);
            MuiSnackbar snackbar = mui.toSnackbar();
            System.out.println("Snackbar component found");
        }
    }

    /**
     * Test MUI Backdrop component
     */
    public void testBackdrop() {
        System.out.println("\n=== Testing MUI Backdrop ===");
        driver.navigate("https://mui.com/material-ui/react-backdrop/", 600_000);
        
        List<WebComponent> backdrops = driver.findComponents(".MuiBackdrop-root");
        System.out.println("Found " + backdrops.size() + " backdrop components");
        
        if (!backdrops.isEmpty()) {
            mui.setContext(backdrops.get(0), driver);
            MuiBackdrop backdrop = mui.toBackdrop();
            System.out.println("Backdrop component found");
            
            // Check visibility
            boolean isVisible = backdrop.isVisible();
            System.out.println("Backdrop visible: " + isVisible);
        }
    }

    /**
     * Test MUI Skeleton component (loading state)
     */
    public void testSkeleton() {
        System.out.println("\n=== Testing MUI Skeleton ===");
        driver.navigate("https://mui.com/material-ui/react-skeleton/", 600_000);
        
        List<WebComponent> skeletons = driver.findComponents(".MuiSkeleton-root");
        System.out.println("Found " + skeletons.size() + " skeleton loaders");
        
        if (!skeletons.isEmpty()) {
            mui.setContext(skeletons.get(0), driver);
            var skeleton = mui.toSkeleton();
            System.out.println("Skeleton loader found");
            
            // Check skeleton variant
            String variant = skeleton.getAttribute("data-variant");
            System.out.println("Skeleton variant: " + (variant != null ? variant : "text"));
        }
    }

    /**
     * Test combined data display scenario
     */
    public void testCombinedDataDisplay() {
        System.out.println("\n=== Testing Combined Data Display Scenario ===");
        driver.navigate("https://mui.com/material-ui/react-list/", 600_000);
        
        // Find list with avatars and badges
        List<WebComponent> lists = driver.findComponents(".MuiList-root");
        if (!lists.isEmpty()) {
            System.out.println("Found list component");
            
            // Look for avatars within list
            List<WebComponent> avatarsInList = lists.get(0).findComponents(".MuiAvatar-root");
            System.out.println("Avatars in list: " + avatarsInList.size());
            
            // Look for badges within list
            List<WebComponent> badgesInList = lists.get(0).findComponents(".MuiBadge-root");
            System.out.println("Badges in list: " + badgesInList.size());
        }
        
        // Find chips
        List<WebComponent> chips = driver.findComponents(".MuiChip-root");
        System.out.println("Total chips on page: " + chips.size());
    }

    public static void main(String[] args) {
        MuiDataDisplayFeedbackTest test = new MuiDataDisplayFeedbackTest();
        test.setUpDriver();

        test.runTestClass("MuiDataDisplayFeedbackTest", () -> {
            test.runTest("testAvatar", test::testAvatar);
            test.runTest("testBadge", test::testBadge);
            test.runTest("testChipWithActions", test::testChipWithActions);
            test.runTest("testDivider", test::testDivider);
            test.runTest("testList", test::testList);
            test.runTest("testTooltip", test::testTooltip);
            test.runTest("testAlert", test::testAlert);
            test.runTest("testSnackbar", test::testSnackbar);
            test.runTest("testBackdrop", test::testBackdrop);
            test.runTest("testSkeleton", test::testSkeleton);
            test.runTest("testCombinedDataDisplay", test::testCombinedDataDisplay);
        });

        // Keep browser open for manual inspection
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
