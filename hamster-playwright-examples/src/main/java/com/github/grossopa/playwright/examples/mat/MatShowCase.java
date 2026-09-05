/*
 * Copyright © 2021 the original author or authors.
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
package com.github.grossopa.playwright.examples.mat;

import com.github.grossopa.playwright.component.mat.MatComponents;
import com.github.grossopa.playwright.component.mat.config.MatConfig;
import com.github.grossopa.playwright.component.mat.exception.MenuItemNotFoundException;
import com.github.grossopa.playwright.component.mat.finder.MatMenuItemFinder;
import com.github.grossopa.playwright.component.mat.finder.MatOverlayFinder;
import com.github.grossopa.playwright.component.mat.main.MatAccordion;
import com.github.grossopa.playwright.component.mat.main.MatAutocomplete;
import com.github.grossopa.playwright.component.mat.main.MatBadge;
import com.github.grossopa.playwright.component.mat.main.MatBottomSheet;
import com.github.grossopa.playwright.component.mat.main.MatButton;
import com.github.grossopa.playwright.component.mat.main.MatButtonToggle;
import com.github.grossopa.playwright.component.mat.main.MatButtonToggleGroup;
import com.github.grossopa.playwright.component.mat.main.MatCheckbox;
import com.github.grossopa.playwright.component.mat.main.MatChipList;
import com.github.grossopa.playwright.component.mat.main.MatDialog;
import com.github.grossopa.playwright.component.mat.main.MatExpansionPanel;
import com.github.grossopa.playwright.component.mat.main.MatFormField;
import com.github.grossopa.playwright.component.mat.main.MatGridList;
import com.github.grossopa.playwright.component.mat.main.MatGridTile;
import com.github.grossopa.playwright.component.mat.main.MatList;
import com.github.grossopa.playwright.component.mat.main.MatMenu;
import com.github.grossopa.playwright.component.mat.main.MatOverlayContainer;
import com.github.grossopa.playwright.component.mat.main.MatProgressBar;
import com.github.grossopa.playwright.component.mat.main.MatSelectionList;
import com.github.grossopa.playwright.component.mat.main.MatSlideToggle;
import com.github.grossopa.playwright.component.mat.main.MatSlider;
import com.github.grossopa.playwright.component.mat.main.MatSnackbar;
import com.github.grossopa.playwright.component.mat.main.sub.MatChip;
import com.github.grossopa.playwright.component.mat.main.sub.MatListOption;
import com.github.grossopa.playwright.component.mat.main.sub.MatMenuItem;
import com.github.grossopa.playwright.component.mat.main.sub.MatOption;
import com.github.grossopa.playwright.core.WebComponent;
import com.github.grossopa.playwright.examples.helper.AbstractBrowserSupport;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.microsoft.playwright.options.WaitUntilState;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the Angular Material (legacy class structure) components with Playwright against
 * the v12 material.angular.io documentation site.
 *
 * @author Jack Yin
 * @since 1.15
 */
@SuppressWarnings("all")
public class MatShowCase extends AbstractBrowserSupport {

    /**
     * The v12 documentation site which still renders the legacy Angular Material DOM structure
     * that the mat component library targets.
     */
    private static final String BASE_URL = "https://v12.material.angular.io/components/";

    private static final long NAV_TIMEOUT = 300_000L;

    private static final MatComponents mat = MatComponents.mat();

    /**
     * Sets the mat converter context and applies the given converter.
     *
     * @param component the source component
     * @param converter the converter function on {@link MatComponents}
     * @return the converted mat component
     */
    private <T> T as(WebComponent component, Function<MatComponents, T> converter) {
        mat.setContext(component, driver);
        return converter.apply(mat);
    }

    private List<WebComponent> findComponents(WebComponent parent, String selector) {
        return parent.findComponents(selector);
    }

    private void navigateTo(String path) {
        // wait for DOMContentLoaded only as the legacy doc site may never fire the load event;
        // retry up to 3 times as the legacy site is occasionally slow to respond
        String url = BASE_URL + path;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                driver.page().navigate(url, new Page.NavigateOptions()
                        .setWaitUntil(WaitUntilState.DOMCONTENTLOADED).setTimeout(NAV_TIMEOUT));
                break;
            } catch (PlaywrightException ex) {
                System.out.println("Navigation attempt " + attempt + " failed: " + ex.getMessage());
                if (attempt == 3) {
                    throw ex;
                }
            }
        }
        dismissCookieAlert();
        waitForExamplesPageRendered();
    }

    private void waitForExamplesPageRendered() {
        // the archived doc site occasionally fails to bootstrap; reload once and wait again
        for (int attempt = 0; attempt < 2; attempt++) {
            Locator matElements = driver.page().locator("[class*=mat-]");
            for (int i = 0; i < 60; i++) {
                if (matElements.count() > 0) {
                    return;
                }
                driver.page().waitForTimeout(250);
            }
            driver.page().reload(new Page.ReloadOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        }
    }

    private void dismissCookieAlert() {
        Locator cookieButton = driver.page().locator("button:has-text('Got it')");
        if (cookieButton.count() > 0) {
            cookieButton.first().click();
            System.out.println("Cookie alert dismissed");
        }
    }

    private void waitFor(String selector) {
        driver.page().waitForSelector(selector);
    }

    private void waitForHidden(String selector) {
        driver.page().waitForSelector(selector,
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.HIDDEN));
    }

    // =====================================================================
    // Input components
    // =====================================================================

    public void testButtons() {
        navigateTo("button/examples");
        waitFor("button-overview-example .mat-button-base");
        WebComponent example = driver.findComponent("#button-overview").findComponent("button-overview-example");
        WebComponent section = example.findComponents("section").get(1);

        List<MatButton> buttons = section.findComponents(".mat-button-base").stream()
                .map(c -> as(c, MatComponents::toButton)).collect(Collectors.toList());
        assertEquals(6, buttons.size());
        assertEquals(6, buttons.stream().filter(MatButton::validate).count());
        assertTrue(buttons.get(0).isEnabled());
        assertFalse(buttons.get(4).isEnabled());
        assertEquals("Basic", buttons.get(0).innerText());
        assertEquals("Primary", buttons.get(1).innerText());
        assertEquals("Accent", buttons.get(2).innerText());
        assertEquals("Warn", buttons.get(3).innerText());
        assertEquals("Disabled", buttons.get(4).innerText());
        assertEquals("Link", buttons.get(5).innerText());
        assertDoesNotThrow(() -> buttons.get(2).click());
        System.out.println("Verified 6 buttons: Basic/Primary/Accent/Warn/Disabled/Link");
    }

    public void testCheckbox() {
        navigateTo("checkbox/examples");
        waitFor("#checkbox-configurable mat-checkbox");
        List<MatCheckbox> checkboxes = driver.findComponent("#checkbox-configurable")
                .findComponents("mat-checkbox").stream()
                .map(c -> as(c, MatComponents::toCheckbox)).collect(Collectors.toList());

        assertEquals(4, checkboxes.size());
        assertTrue(checkboxes.stream().allMatch(MatCheckbox::validate));
        assertTrue(checkboxes.stream().allMatch(MatCheckbox::isEnabled));
        assertTrue(checkboxes.stream().noneMatch(MatCheckbox::isSelected));

        assertEquals("Checked", checkboxes.get(0).innerText());
        assertEquals("Indeterminate", checkboxes.get(1).innerText());
        assertEquals("Disabled", checkboxes.get(2).innerText());
        assertEquals("I'm a checkbox", checkboxes.get(3).innerText());

        checkboxes.get(0).click();
        assertTrue(checkboxes.get(0).isSelected());

        checkboxes.get(2).click();
        assertFalse(checkboxes.get(3).isEnabled());
        System.out.println("Verified checkbox selection and disabling behavior");
    }

    public void testSlideToggle() {
        navigateTo("slide-toggle/examples");
        WebComponent container = driver.findComponent("#slide-toggle-configurable");

        MatSlideToggle slideToggle = as(container.findComponent(".mat-slide-toggle"), MatComponents::toSlideToggle);
        assertFalse(slideToggle.isSelected());
        assertTrue(slideToggle.isEnabled());
        assertTrue(slideToggle.validate());
        assertEquals("Slide me!", slideToggle.getLabel().innerText());

        MatCheckbox checkedBox = as(container.findComponent("#mat-checkbox-1"), MatComponents::toCheckbox);
        MatCheckbox disabledBox = as(container.findComponent("#mat-checkbox-2"), MatComponents::toCheckbox);

        slideToggle.click();
        assertTrue(slideToggle.isSelected());

        slideToggle.click();
        checkedBox.click();
        assertTrue(slideToggle.isSelected());

        disabledBox.click();
        assertTrue(slideToggle.isSelected());
        assertFalse(slideToggle.isEnabled());
        System.out.println("Verified slide toggle toggling and disabling behavior");
    }

    public void testBadge() {
        navigateTo("badge/examples");
        waitFor("badge-overview-example .mat-badge");
        WebComponent container = driver.findComponent("badge-overview-example");
        List<MatBadge> badges = container.findComponents(".mat-badge").stream()
                .map(c -> as(c, MatComponents::toBadge)).collect(Collectors.toList());

        assertEquals(5, badges.size());
        badges.forEach(badge -> assertTrue(badge.validate()));
        badges.forEach(badge -> assertTrue(badge.getBadgeContent().validate()));

        assertEquals("4", badges.get(0).getBadgeContent().innerText());
        assertEquals("1", badges.get(1).getBadgeContent().innerText());
        assertEquals("8", badges.get(2).getBadgeContent().innerText());
        assertEquals("7", badges.get(3).getBadgeContent().innerText());
        assertEquals("15", badges.get(4).getBadgeContent().innerText());
        System.out.println("Verified 5 badges with contents 4/1/8/7/15");
    }

    public void testButtonToggleGroup() {
        navigateTo("button-toggle/examples");
        MatButtonToggleGroup group = as(driver.findComponent("#button-toggle-exclusive")
                .findComponent("button-toggle-exclusive-example")
                .findComponent("mat-button-toggle-group"), MatComponents::toButtonToggleGroup);
        assertTrue(group.validate());

        List<MatButtonToggle> toggles = group.getButtonToggles();
        assertEquals(4, toggles.size());
        assertTrue(toggles.stream().allMatch(MatButtonToggle::validate));
        assertTrue(toggles.get(0).isEnabled());
        assertTrue(toggles.get(1).isEnabled());
        assertTrue(toggles.get(2).isEnabled());
        assertFalse(toggles.get(3).isEnabled());

        toggles.get(1).click();
        assertFalse(toggles.get(0).isSelected());
        assertTrue(toggles.get(1).isSelected());

        toggles.get(2).click();
        assertFalse(toggles.get(1).isSelected());
        assertTrue(toggles.get(2).isSelected());
        System.out.println("Verified exclusive button toggle group selection");
    }

    public void testProgressBar() {
        navigateTo("progress-bar/examples");
        MatProgressBar bufferBar = as(driver.findComponent("progress-bar-buffer-example")
                .findComponent("mat-progress-bar"), MatComponents::toProgressBar);
        assertTrue(bufferBar.validate());
        assertEquals(MatProgressBar.Mode.BUFFER, bufferBar.getMode());

        MatProgressBar determinateBar = as(driver.findComponent("progress-bar-configurable-example")
                .findComponent("mat-progress-bar"), MatComponents::toProgressBar);
        assertTrue(determinateBar.validate());
        assertEquals("0", determinateBar.getMinValue());
        assertEquals("100", determinateBar.getMaxValue());
        assertEquals("50", determinateBar.getValue());
        assertEquals(MatProgressBar.Mode.DETERMINATE, determinateBar.getMode());

        MatProgressBar indeterminateBar = as(driver.findComponent("progress-bar-indeterminate-example")
                .findComponent("mat-progress-bar"), MatComponents::toProgressBar);
        assertEquals(MatProgressBar.Mode.INDETERMINATE, indeterminateBar.getMode());

        MatProgressBar queryBar = as(driver.findComponent("progress-bar-query-example")
                .findComponent("mat-progress-bar"), MatComponents::toProgressBar);
        assertEquals(MatProgressBar.Mode.QUERY, queryBar.getMode());
        System.out.println("Verified all 4 progress bar modes");
    }

    public void testSlider() {
        navigateTo("slider/examples");
        MatSlider slider = as(driver.findComponent("slider-configurable-example")
                .findComponent("mat-slider"), MatComponents::toSlider);
        assertTrue(slider.validate());
        assertEquals(0, slider.getMinValueInteger());
        assertEquals(100, slider.getMaxValueInteger());
        System.out.println("Slider initial value: " + slider.getValueInteger());

        slider.scrollIntoViewIfNeeded();
        slider.setValue(30);
        sleep(500L);
        System.out.println("Slider value after setValue(30): " + slider.getValueInteger());
        assertTrue(Math.abs(30 - slider.getValueInteger()) <= 2);

        // deterministic keyboard-based verification of the value range
        slider.getFirstThumb().click();
        slider.getFirstThumb().press("End");
        assertEquals(100, slider.getValueInteger());
        slider.getFirstThumb().press("Home");
        assertEquals(0, slider.getValueInteger());
        System.out.println("Verified slider value set and keyboard navigation");
    }

    // =====================================================================
    // Layout components
    // =====================================================================

    public void testExpansionPanel() {
        navigateTo("expansion/examples");
        MatAccordion accordion = as(driver.findComponent("#expansion-expand-collapse-all")
                .findComponent("mat-accordion"), MatComponents::toAccordion);
        assertTrue(accordion.validate());

        List<MatExpansionPanel> panels = accordion.getExpansionPanels();
        assertEquals(3, panels.size());
        assertTrue(panels.stream().allMatch(MatExpansionPanel::validate));
        assertTrue(panels.stream().noneMatch(MatExpansionPanel::isExpanded));

        assertTrue(panels.get(0).isEnabled());
        assertFalse(panels.get(1).isEnabled());
        assertTrue(panels.get(2).isEnabled());

        panels.get(0).expand();
        assertTrue(panels.get(0).isExpanded());
        assertEquals("Personal data",
                panels.get(0).findComponent(".mat-expansion-panel-header-title").innerText());
        assertEquals(2, panels.get(0).getExpansionPanelBody().findComponents("input").size());

        panels.get(0).collapse();
        assertFalse(panels.get(0).isExpanded());
        System.out.println("Verified expansion panel expand/collapse");
    }

    public void testGridList() {
        navigateTo("grid-list/examples");
        MatGridList gridList = as(driver.findComponent("grid-list-dynamic-example")
                .findComponent(".mat-grid-list"), MatComponents::toGridList);
        assertTrue(gridList.validate());
        assertEquals(4, gridList.getCols());

        List<MatGridTile> tiles = gridList.getGridTiles();
        assertEquals(4, tiles.size());
        tiles.forEach(tile -> assertTrue(tile.validate()));

        assertEquals("One", tiles.get(0).innerText());
        assertEquals("Two", tiles.get(1).innerText());
        assertEquals("Three", tiles.get(2).innerText());
        assertEquals("Four", tiles.get(3).innerText());

        assertEquals(3, tiles.get(0).getColSpan());
        assertEquals(1, tiles.get(0).getRowSpan());
        assertEquals(1, tiles.get(1).getColSpan());
        assertEquals(2, tiles.get(1).getRowSpan());
        assertEquals(1, tiles.get(2).getColSpan());
        assertEquals(1, tiles.get(2).getRowSpan());
        assertEquals(2, tiles.get(3).getColSpan());
        assertEquals(1, tiles.get(3).getRowSpan());
        System.out.println("Verified grid list with 4 tiles and spans");
    }

    public void testListAndSelectionList() {
        navigateTo("list/examples");

        MatList list = as(driver.findComponent("list-overview-example")
                .findComponent(".mat-list"), MatComponents::toList);
        assertTrue(list.validate());
        List<WebComponent> items = list.getListItems();
        assertEquals(3, items.size());
        assertEquals("Item 1", items.get(0).innerText());
        assertEquals("Item 2", items.get(1).innerText());
        assertEquals("Item 3", items.get(2).innerText());

        MatSelectionList selectionList = as(driver.findComponent("list-selection-example")
                .findComponent(".mat-selection-list"), MatComponents::toSelectionList);
        assertTrue(selectionList.validate());
        List<MatListOption> options = selectionList.getListOptions();
        assertEquals(5, options.size());
        assertEquals("Boots", options.get(0).innerText());
        assertEquals("Clogs", options.get(1).innerText());
        assertEquals("Loafers", options.get(2).innerText());
        assertEquals("Moccasins", options.get(3).innerText());
        assertEquals("Sneakers", options.get(4).innerText());
        options.forEach(option -> assertFalse(option.isSelected()));

        options.get(3).click();
        assertTrue(options.get(3).isSelected());

        options.get(2).click();
        assertTrue(options.get(2).isSelected());
        assertTrue(options.get(3).isSelected());
        assertTrue(options.get(2).getCheckbox().isSelected());
        assertTrue(options.get(3).getCheckbox().isSelected());
        System.out.println("Verified list items and selection list multi-select");
    }

    public void testFormField() {
        navigateTo("form-field/examples");
        waitFor("#form-field-appearance mat-form-field");

        List<MatFormField> appearanceFields = driver.findComponent("#form-field-appearance")
                .findComponents("mat-form-field").stream()
                .map(c -> as(c, MatComponents::toFormField)).collect(Collectors.toList());
        assertEquals(4, appearanceFields.size());
        assertTrue(appearanceFields.stream().allMatch(MatFormField::validate));
        assertTrue(appearanceFields.stream().allMatch(MatFormField::isEnabled));
        assertEquals("Hint", appearanceFields.get(0).getHint().innerText());

        MatFormField errorField = as(driver.findComponent("#form-field-error")
                .findComponent("form-field-error-example").findComponent("mat-form-field"),
                MatComponents::toFormField);
        errorField.getInput().fill("ddddd");
        // send Tab key to make the input lose focus, and trigger the error check
        errorField.getInput().press("Tab");
        // the label text includes the required asterisk rendered as a separate element
        assertTrue(errorField.getLabel().innerText().startsWith("Enter your email"));
        assertEquals("Not a valid email", errorField.getError().innerText());

        List<MatFormField> hintFields = driver.findComponent("#form-field-hint")
                .findComponents("mat-form-field").stream()
                .map(c -> as(c, MatComponents::toFormField)).collect(Collectors.toList());
        assertEquals("Max 10 characters", hintFields.get(0).getHint().innerText());
        assertEquals("Here's the dropdown arrow ^", hintFields.get(1).getHint().innerText());

        List<MatFormField> prefixSuffixFields = driver.findComponent("#form-field-prefix-suffix")
                .findComponents("mat-form-field").stream()
                .map(c -> as(c, MatComponents::toFormField)).collect(Collectors.toList());
        assertEquals("$", prefixSuffixFields.get(1).getPrefix().innerText().replace('\u00A0', ' ').trim());
        assertEquals(".00", prefixSuffixFields.get(1).getSuffix().innerText());
        System.out.println("Verified form field appearance, error, hints and prefix/suffix");
    }

    // =====================================================================
    // Overlay components
    // =====================================================================

    public void testDialog() {
        navigateTo("dialog/examples");
        MatOverlayFinder overlayFinder = new MatOverlayFinder(driver, new MatConfig());

        MatButton openButton = as(driver.findComponent("dialog-content-example").findComponent("button"),
                MatComponents::toButton);
        openButton.click();
        waitFor("mat-dialog-container");

        MatOverlayContainer container = overlayFinder.findTopVisibleContainer();
        assertNotNull(container);
        MatDialog dialog = as(container.findComponent("mat-dialog-container"), MatComponents::toDialog);
        assertTrue(dialog.validate());
        assertEquals("Install Angular", dialog.getDialogTitle().innerText());
        assertTrue(dialog.getDialogContent().innerText().startsWith("Develop across all platforms"));

        List<MatButton> buttons = dialog.getDialogActions().findComponents("button").stream()
                .map(c -> as(c, MatComponents::toButton)).collect(Collectors.toList());
        assertEquals("Cancel", buttons.get(0).innerText());
        assertEquals("Install", buttons.get(1).innerText());

        buttons.get(0).click();
        waitForHidden("mat-dialog-container");
        System.out.println("Verified dialog open, title/content/actions and close");
    }

    public void testSnackbar() {
        navigateTo("snack-bar/examples");
        driver.findComponent("snack-bar-overview-example button").click();
        waitFor("simple-snack-bar");

        MatOverlayFinder finder = new MatOverlayFinder(driver, new MatConfig());
        MatOverlayContainer container = finder.findTopVisibleContainer();
        assertNotNull(container);
        MatSnackbar snackbar = as(container.findComponent("simple-snack-bar"), MatComponents::toSnackbar);
        assertEquals("Disco party!", snackbar.getLabel().innerText());
        assertEquals("Dance", snackbar.getActionButton().innerText());

        snackbar.getActionButton().click();
        waitForHidden("simple-snack-bar");
        System.out.println("Verified snackbar message, action and dismiss");
    }

    public void testBottomSheet() {
        navigateTo("bottom-sheet/examples");
        WebComponent openFileButton = driver.findComponent("bottom-sheet-overview-example button");
        assertEquals("Open file", openFileButton.innerText());
        openFileButton.click();
        waitFor(".mat-bottom-sheet-container");

        MatOverlayFinder overlayFinder = new MatOverlayFinder(driver, new MatConfig());
        MatOverlayContainer container = overlayFinder.findTopVisibleContainer();
        MatBottomSheet bottomSheet = as(container.findComponent(".mat-bottom-sheet-container"),
                MatComponents::toBottomSheet);
        assertTrue(bottomSheet.validate());
        assertEquals(4, bottomSheet.findComponents("a").size());

        driver.page().keyboard().press("Escape");
        waitForHidden(".mat-bottom-sheet-container");
        System.out.println("Verified bottom sheet open with 4 links and close");
    }

    public void testMenu() {
        navigateTo("menu/examples");
        MatMenuItemFinder finder = new MatMenuItemFinder(driver, new MatConfig());

        // menu with icons
        as(driver.findComponent("menu-icons-example button"), MatComponents::toButton).click();
        waitFor(".mat-menu-panel");
        MatMenu menu = finder.findTopMenu();
        List<MatMenuItem> menuItems = menu.getMenuItems();
        assertEquals(3, menuItems.size());
        assertTrue(menuItems.get(0).isEnabled());
        assertFalse(menuItems.get(1).isEnabled());
        assertTrue(menuItems.get(2).isEnabled());
        assertEquals("Redial", menuItems.get(0).findComponent("span").innerText());
        assertEquals("Check voice mail", menuItems.get(1).findComponent("span").innerText());
        assertEquals("Disable alerts", menuItems.get(2).findComponent("span").innerText());
        menu.close();

        // nested menu
        as(driver.findComponent("menu-nested-example button"), MatComponents::toButton).click();
        waitFor(".mat-menu-panel");
        MatMenu nestedMenu = finder.findTopMenu();
        nestedMenu.expandItemByText("Vertebrates").expandItemByText("Amphibians");

        List<MatMenu> menus = finder.findMenus();
        assertEquals(3, menus.size());
        menus.forEach(openMenu -> assertTrue(openMenu.validate()));

        MatMenu menuToClose = finder.findTopMenu();
        while (menuToClose != null) {
            menuToClose.close();
            sleep(500L);
            try {
                menuToClose = finder.findTopMenu();
            } catch (MenuItemNotFoundException ex) {
                menuToClose = null;
            }
        }
        assertThrows(MenuItemNotFoundException.class, finder::findTopMenu);
        System.out.println("Verified menu items, nested menu expansion and close");
    }

    public void testAutocomplete() {
        navigateTo("autocomplete/examples");
        MatAutocomplete autocomplete = as(driver.findComponent("#autocomplete-auto-active-first-option")
                .findComponent("mat-form-field"), MatComponents::toAutocomplete);

        // click the input and wait for the async panel rendering before locating it
        autocomplete.getInput().click();
        waitFor(".mat-autocomplete-panel");
        autocomplete.openOptions();
        List<MatOption> options = autocomplete.getOptions();
        assertEquals(3, options.size());
        assertEquals("One", options.get(0).innerText());
        assertEquals("Two", options.get(1).innerText());
        assertEquals("Three", options.get(2).innerText());
        assertNull(autocomplete.getFirstSelectedOption());

        autocomplete.selectByIndex(0);
        assertEquals("One", autocomplete.getInput().inputValue());
        // selection closes the panel, closing again is a safe no-op
        autocomplete.closeOptions();
        System.out.println("Verified autocomplete options and selection");
    }

    public void testChipList() {
        navigateTo("chips/examples");
        waitFor("chips-autocomplete-example mat-chip-list");
        MatFormField formField = as(driver.findComponent("chips-autocomplete-example")
                .findComponent("mat-form-field"), MatComponents::toFormField);

        MatChipList chipList = as(formField.getInfix().findComponent("mat-chip-list"), MatComponents::toChipList);
        assertEquals(1, chipList.getChips().size());
        MatChip lemonChip = chipList.getChips().get(0);
        assertEquals("Lemon", lemonChip.getText());
        lemonChip.remove();
        assertTrue(chipList.getChips().isEmpty());

        MatAutocomplete autocomplete = as(formField, MatComponents::toAutocomplete);
        for (String fruit : List.of("Apple", "Orange", "Strawberry")) {
            autocomplete.selectByVisibleText(fruit);
            // the selection keeps the input focused and the overlay is destroyed;
            // press Tab to blur so the next click on the input reopens the panel
            autocomplete.getInput().press("Tab");
        }

        List<MatChip> newChips = as(formField.getInfix().findComponent("mat-chip-list"), MatComponents::toChipList)
                .getChips();
        assertEquals(3, newChips.size());
        assertEquals("Apple", newChips.get(0).getText());
        assertEquals("Orange", newChips.get(1).getText());
        assertEquals("Strawberry", newChips.get(2).getText());
        System.out.println("Verified chip list remove and add via autocomplete");
    }

    // =====================================================================
    // Main entry point
    // =====================================================================

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        MatShowCase test = new MatShowCase();
        test.setUpDriver();
        // the archived v12 doc site is occasionally slow; give every action more headroom
        driver.page().setDefaultTimeout(60_000);
        String filter = args.length > 0 ? args[0] : System.getenv("MAT_FILTER");
        try {
            test.runTestClass("MatShowCase", () -> {
                test.runIf(filter, "testButtons", test::testButtons);
                test.runIf(filter, "testCheckbox", test::testCheckbox);
                test.runIf(filter, "testSlideToggle", test::testSlideToggle);
                test.runIf(filter, "testBadge", test::testBadge);
                test.runIf(filter, "testButtonToggleGroup", test::testButtonToggleGroup);
                test.runIf(filter, "testProgressBar", test::testProgressBar);
                test.runIf(filter, "testSlider", test::testSlider);
                test.runIf(filter, "testExpansionPanel", test::testExpansionPanel);
                test.runIf(filter, "testGridList", test::testGridList);
                test.runIf(filter, "testListAndSelectionList", test::testListAndSelectionList);
                test.runIf(filter, "testFormField", test::testFormField);
                test.runIf(filter, "testDialog", test::testDialog);
                test.runIf(filter, "testSnackbar", test::testSnackbar);
                test.runIf(filter, "testBottomSheet", test::testBottomSheet);
                test.runIf(filter, "testMenu", test::testMenu);
                test.runIf(filter, "testAutocomplete", test::testAutocomplete);
                test.runIf(filter, "testChipList", test::testChipList);
            });
        } finally {
            test.tearDownAndReport();
        }
        if (test.anyFailure) {
            System.exit(1);
        }
    }
}
