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
package com.github.grossopa.selenium.examples.recorder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Generates static HTML pages that simulate the DOM structure of Angular Material non-MDC (legacy, v12-v14)
 * components. The pages use the {@code mat-*} CSS class prefix instead of the MDC {@code mat-mdc-*} prefix.
 *
 * <p>The generated pages are written to {@code target/recorder-examples/non-mdc-mat/} and can be opened via
 * {@code file://} URLs for recorder testing.</p>
 *
 * @author Jack Yin
 * @since 1.18
 */
public final class NonMdcMatTestAppPages {

    private static final String PAGE_HEADER = """
            <!DOCTYPE html>
            <html lang="en">
            <head><meta charset="UTF-8"><title>%s</title>
            <style>
              body { font-family: Roboto, sans-serif; padding: 16px; }
              .section { margin-bottom: 24px; }
              .row { display: flex; gap: 8px; align-items: center; flex-wrap: wrap; }
              .column { display: flex; flex-direction: column; gap: 8px; }
              .mat-button { padding: 8px 16px; border: none; background: #e0e0e0; cursor: pointer; border-radius: 4px; }
              .mat-button[disabled] { opacity: 0.5; cursor: default; }
              .mat-form-field { display: flex; flex-direction: column; padding: 8px 0; }
              .mat-input-element { padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
              .mat-select { padding: 8px 16px; border: 1px solid #ccc; border-radius: 4px; cursor: pointer; min-width: 200px; }
              .mat-checkbox, .mat-radio-button, .mat-slide-toggle { display: flex; align-items: center; gap: 8px; padding: 4px 0; }
              .mat-slider { width: 300px; height: 40px; background: #e0e0e0; border-radius: 4px; }
              .mat-card { border: 1px solid #e0e0e0; border-radius: 4px; padding: 16px; margin-bottom: 16px; }
              .mat-toolbar { display: flex; align-items: center; padding: 0 16px; height: 48px; background: #f5f5f5; border-radius: 4px; }
              .mat-table { width: 100%%; border-collapse: collapse; }
              .mat-table th, .mat-table td { border: 1px solid #e0e0e0; padding: 8px; text-align: left; }
              .mat-tab-group { border-bottom: 2px solid #e0e0e0; display: flex; gap: 16px; }
              .mat-tab { padding: 8px 16px; cursor: pointer; }
              .mat-menu-panel { border: 1px solid #e0e0e0; border-radius: 4px; padding: 8px 0; }
              .mat-list { list-style: none; padding: 0; }
              .mat-list-item { padding: 8px 16px; border-bottom: 1px solid #f0f0f0; }
              .mat-button-toggle-group { display: inline-flex; border: 1px solid #ccc; border-radius: 4px; }
              .mat-button-toggle { padding: 8px 16px; border: none; background: #f5f5f5; cursor: pointer; }
              .mat-chip-set { display: flex; gap: 8px; flex-wrap: wrap; }
              .mat-chip { padding: 4px 12px; border-radius: 16px; background: #e0e0e0; }
              .mat-stepper-horizontal { border: 1px solid #e0e0e0; border-radius: 4px; padding: 16px; }
              .mat-step { margin-bottom: 8px; }
              .mat-progress-bar { height: 8px; background: #e0e0e0; border-radius: 4px; margin: 8px 0; }
              .mat-expansion-panel { border: 1px solid #e0e0e0; border-radius: 4px; margin-bottom: 8px; }
              .mat-expansion-panel-header { padding: 12px 16px; cursor: pointer; font-weight: 500; }
              .mat-accordion { display: flex; flex-direction: column; }
            </style>
            </head>
            <body>
            """;

    private static final String PAGE_FOOTER = "</body></html>";

    private static final String INPUTS_PAGE = PAGE_HEADER.formatted("Inputs") + """
            <h4 id="page-title">Inputs</h4>

            <div class="section">
              <h5>Buttons</h5>
              <div class="row" style="margin-bottom: 8px;">
                <button class="mat-button" id="btn-contained">Contained</button>
                <button class="mat-button" id="btn-outlined">Outlined</button>
                <button class="mat-button" id="btn-text">Text</button>
                <button class="mat-button" id="btn-disabled" disabled>Disabled</button>
                <button class="mat-button" id="btn-with-icon">Send</button>
              </div>
              <div class="row" style="margin-bottom: 8px;">
                <button class="mat-button" id="icon-btn-add">Add</button>
                <button class="mat-button" id="icon-btn-edit">Edit</button>
                <button class="mat-button" id="icon-btn-delete">Delete</button>
              </div>
              <div class="row">
                <div class="mat-button-toggle-group" id="btn-group">
                  <button class="mat-button-toggle" id="btn-group-left">Left</button>
                  <button class="mat-button-toggle" id="btn-group-center">Center</button>
                  <button class="mat-button-toggle" id="btn-group-right">Right</button>
                </div>
              </div>
            </div>

            <div class="section">
              <h5>Floating Action Button</h5>
              <div class="row">
                <button class="mat-button" id="fab-primary">Add</button>
                <button class="mat-button" id="fab-extended">Extended</button>
              </div>
            </div>

            <div class="section">
              <h5>Text Fields</h5>
              <div class="column">
                <div class="mat-form-field">
                  <label>Standard</label>
                  <input class="mat-input-element" id="tf-standard" type="text">
                </div>
                <div class="mat-form-field">
                  <label>Outlined</label>
                  <input class="mat-input-element" id="tf-outlined" type="text" value="Default value">
                </div>
                <div class="mat-form-field">
                  <label>Filled</label>
                  <input class="mat-input-element" id="tf-filled" type="text">
                </div>
                <div class="mat-form-field">
                  <label>Password</label>
                  <input class="mat-input-element" id="tf-password" type="password">
                </div>
                <div class="mat-form-field">
                  <label>Error</label>
                  <input class="mat-input-element" id="tf-error" type="text" value="Error state">
                </div>
                <div class="mat-form-field">
                  <label>Multiline</label>
                  <textarea class="mat-input-element" id="tf-multiline" rows="3">Multiline text</textarea>
                </div>
              </div>
            </div>

            <div class="section">
              <h5>Select</h5>
              <div class="row">
                <div class="mat-form-field">
                  <label>Framework</label>
                  <select class="mat-select" id="select-framework">
                    <option value="">None</option>
                    <option value="react">React</option>
                    <option value="vue">Vue</option>
                    <option value="angular">Angular</option>
                  </select>
                </div>
                <div class="mat-form-field">
                  <label>Size</label>
                  <select class="mat-select" id="select-size">
                    <option value="xs">Extra Small</option>
                    <option value="s">Small</option>
                    <option value="m">Medium</option>
                    <option value="l">Large</option>
                  </select>
                </div>
              </div>
            </div>

            <div class="section">
              <h5>Checkbox &amp; Radio</h5>
              <div class="row">
                <div>
                  <label class="mat-checkbox" id="checkbox-basic"><input type="checkbox"> Basic checkbox</label><br>
                  <label class="mat-checkbox" id="checkbox-disabled"><input type="checkbox" disabled> Disabled</label><br>
                  <label class="mat-checkbox" id="checkbox-checked"><input type="checkbox" checked> Checked</label>
                </div>
                <div>
                  <div class="mat-radio-group" id="radio-group">
                    <label class="mat-radio-button" id="radio-female"><input type="radio" name="gender" value="female"> Female</label><br>
                    <label class="mat-radio-button" id="radio-male"><input type="radio" name="gender" value="male"> Male</label><br>
                    <label class="mat-radio-button" id="radio-other"><input type="radio" name="gender" value="other"> Other</label>
                  </div>
                </div>
              </div>
            </div>

            <div class="section">
              <h5>Switch</h5>
              <div class="row">
                <label class="mat-slide-toggle" id="switch-basic"><input type="checkbox"> Basic switch</label>
                <label class="mat-slide-toggle" id="switch-disabled"><input type="checkbox" disabled> Disabled</label>
                <label class="mat-slide-toggle" id="switch-checked"><input type="checkbox" checked> Checked</label>
              </div>
            </div>

            <div class="section">
              <h5>Slider</h5>
              <div style="max-width: 400px;">
                <div class="mat-slider" id="slider-basic"></div>
                <div class="mat-slider" id="slider-disabled"></div>
              </div>
            </div>
            """ + PAGE_FOOTER;

    private static final String DATA_DISPLAY_PAGE = PAGE_HEADER.formatted("Data Display") + """
            <h4 id="page-title">Data Display</h4>

            <div class="section">
              <h5>Table</h5>
              <div class="mat-card" style="max-width: 650px;">
                <table class="mat-table" id="users-table" aria-label="users table">
                  <thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Role</th></tr></thead>
                  <tbody>
                    <tr id="table-row-1"><td>1</td><td>Alice</td><td>alice@example.com</td><td>Admin</td></tr>
                    <tr id="table-row-2"><td>2</td><td>Bob</td><td>bob@example.com</td><td>User</td></tr>
                    <tr id="table-row-3"><td>3</td><td>Charlie</td><td>charlie@example.com</td><td>User</td></tr>
                  </tbody>
                </table>
              </div>
            </div>
            """ + PAGE_FOOTER;

    private static final String FEEDBACK_PAGE = PAGE_HEADER.formatted("Feedback") + """
            <h4 id="page-title">Feedback</h4>

            <div class="section">
              <h5>Dialog</h5>
              <button class="mat-button" id="btn-open-dialog">Open Dialog</button>
            </div>

            <div class="section">
              <h5>Snackbar</h5>
              <button class="mat-button" id="btn-open-snackbar">Show Snackbar</button>
            </div>

            <div class="section">
              <h5>Bottom Sheet</h5>
              <button class="mat-button" id="btn-open-bottom-sheet">Open Bottom Sheet</button>
            </div>
            """ + PAGE_FOOTER;

    private static final String NAVIGATION_PAGE = PAGE_HEADER.formatted("Navigation") + """
            <h4 id="page-title">Navigation</h4>

            <div class="section">
              <h5>Tabs</h5>
              <div class="mat-tab-group" id="basic-tabs">
                <button class="mat-tab" id="tab-overview">Overview</button>
                <button class="mat-tab" id="tab-details">Details</button>
                <button class="mat-tab" id="tab-settings">Settings</button>
              </div>
            </div>

            <div class="section">
              <h5>Accordion</h5>
              <div class="mat-accordion">
                <div class="mat-expansion-panel" id="accordion-personal">
                  <button class="mat-expansion-panel-header">Personal Information</button>
                </div>
                <div class="mat-expansion-panel" id="accordion-account">
                  <button class="mat-expansion-panel-header">Account Settings</button>
                </div>
              </div>
            </div>

            <div class="section">
              <h5>Menu</h5>
              <button class="mat-button" id="btn-open-menu">Open Menu</button>
              <div class="mat-menu-panel" id="test-menu" style="display:none;">
                <button class="mat-button" id="menu-profile">Profile</button>
                <button class="mat-button" id="menu-my-account">My Account</button>
                <button class="mat-button" id="menu-logout">Logout</button>
              </div>
            </div>

            <div class="section">
              <h5>Stepper</h5>
              <div class="mat-stepper-horizontal" id="test-stepper">
                <div class="mat-step" id="step-1">
                  <p>Step 1: Personal Info</p>
                  <div class="row">
                    <button class="mat-button" id="stepper-back" disabled>Back</button>
                    <button class="mat-button" id="stepper-next">Next</button>
                  </div>
                </div>
              </div>
            </div>
            """ + PAGE_FOOTER;

    private static final String SURFACES_PAGE = PAGE_HEADER.formatted("Surfaces") + """
            <h4 id="page-title">Surfaces</h4>

            <div class="section">
              <h5>Cards</h5>
              <div class="row">
                <div class="mat-card" id="card-basic" style="max-width: 350px;">
                  <h6>Basic Card</h6>
                  <p>A basic card with text content.</p>
                </div>
                <div class="mat-card" id="card-with-media" style="max-width: 350px;">
                  <h6>Card with Media</h6>
                  <p>This card includes action buttons.</p>
                  <div class="row">
                    <button class="mat-button" id="card-btn-like">Like</button>
                    <button class="mat-button" id="card-btn-share">Share</button>
                  </div>
                </div>
              </div>
            </div>

            <div class="section">
              <h5>Toolbar</h5>
              <div class="mat-toolbar" id="toolbar-basic">
                <span style="flex: 1;">Toolbar Title</span>
                <button class="mat-button" id="toolbar-btn-fav">Favorite</button>
                <button class="mat-button" id="toolbar-btn-share">Share</button>
              </div>
            </div>
            """ + PAGE_FOOTER;

    private static final String LOCATOR_STRATEGIES_PAGE = PAGE_HEADER.formatted("Locator Strategies") + """
            <h4 id="page-title">Locator Strategies</h4>
            <p>This page tests all recorder locator strategies.</p>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>1. ID-based Locator</h5>
              <div class="row">
                <button class="mat-button" id="unique-submit-btn">Submit</button>
                <button class="mat-button" id="unique-cancel-btn">Cancel</button>
                <div class="mat-form-field">
                  <input class="mat-input-element" id="unique-username" type="text">
                </div>
                <div class="mat-form-field">
                  <input class="mat-input-element" id="unique-email" type="text">
                </div>
              </div>
            </div>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>2. Name-based Locator</h5>
              <div class="column">
                <div class="mat-form-field"><input class="mat-input-element" name="firstName" type="text"></div>
                <div class="mat-form-field"><input class="mat-input-element" name="lastName" type="text"></div>
                <div class="mat-form-field"><input class="mat-input-element" name="phone" type="text"></div>
              </div>
              <div class="mat-radio-group" name="contactMethod" style="margin-top: 8px;">
                <label class="mat-radio-button"><input type="radio" name="contactMethod" value="email"> Email</label><br>
                <label class="mat-radio-button"><input type="radio" name="contactMethod" value="phone"> Phone</label><br>
                <label class="mat-radio-button"><input type="radio" name="contactMethod" value="sms"> SMS</label>
              </div>
            </div>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>3. Custom Attribute (data-testid)</h5>
              <div class="row">
                <button class="mat-button" data-testid="action-save">Save</button>
                <button class="mat-button" data-testid="action-delete">Delete</button>
                <button class="mat-button" data-testid="action-export">Export</button>
                <span data-testid="status-label">Status: Active</span>
              </div>
            </div>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>4. Ancestor Anchor Locator</h5>
              <div id="user-profile-section" style="padding: 16px; border: 1px solid #ccc; margin-bottom: 16px; border-radius: 4px;">
                <h6>User Profile Section</h6>
                <button class="mat-button">Edit Profile</button>
                <button class="mat-button" style="margin-left: 8px;">View History</button>
              </div>
              <div data-testid="order-section" style="padding: 16px; border: 1px solid #ccc; margin-bottom: 16px; border-radius: 4px;">
                <h6>Order Section</h6>
                <button class="mat-button">Place Order</button>
                <button class="mat-button" style="margin-left: 8px;">View Cart</button>
              </div>
            </div>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>5. Sibling List Locator</h5>
              <div id="button-group-test">
                <div class="row" style="margin-bottom: 8px;">
                  <button class="mat-button">Action A</button>
                  <button class="mat-button">Action B</button>
                  <button class="mat-button">Action C</button>
                  <button class="mat-button">Action D</button>
                </div>
              </div>
              <div id="input-group-test">
                <div class="column" style="margin-bottom: 8px;">
                  <div class="mat-form-field"><input class="mat-input-element" type="text"></div>
                  <div class="mat-form-field"><input class="mat-input-element" type="text"></div>
                  <div class="mat-form-field"><input class="mat-input-element" type="text"></div>
                </div>
              </div>
            </div>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>6. Text-based Locator</h5>
              <div class="row">
                <button class="mat-button">Confirm Changes</button>
                <button class="mat-button">Discard Draft</button>
                <button class="mat-button">Learn More</button>
              </div>
            </div>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>7. Mixed Strategies</h5>
              <div class="row">
                <button class="mat-button" id="mixed-1" name="primaryAction" data-testid="btn-primary">Primary Action</button>
                <div class="mat-form-field" id="mixed-2" style="min-width: 200px;">
                  <input class="mat-input-element" name="searchQuery" data-testid="search-field-mixed" type="text">
                </div>
                <label class="mat-checkbox" id="mixed-3" name="acceptTerms" data-testid="terms-checkbox">
                  <input type="checkbox"> Accept Terms
                </label>
              </div>
            </div>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>8. Same Text Degradation</h5>
              <section id="same-text-section" style="padding: 16px; border: 1px solid #ccc; border-radius: 4px;">
                <button class="mat-button" style="margin-right: 8px;">OK</button>
                <button class="mat-button" style="margin-right: 8px;">OK</button>
                <button class="mat-button">OK</button>
              </section>
            </div>
            """ + PAGE_FOOTER;

    private static final String HTML_ELEMENTS_PAGE = PAGE_HEADER.formatted("HTML Elements") + """
            <h4 id="page-title">HTML Elements</h4>
            <p>Native HTML elements for testing HtmlSelect, HtmlTable detection and basic form handling.</p>

            <div style="padding: 16px; margin-bottom: 16px;">
              <h5>Native Form</h5>
              <form id="native-form" name="registrationForm" style="max-width: 400px; display: flex; flex-direction: column; gap: 12px;">
                <label>Full Name: <input id="html-input-name" name="fullName" type="text"></label>
                <label>Email: <input id="html-input-email" name="email" type="email"></label>
                <label>Password: <input id="html-input-password" name="password" type="password"></label>
                <label>Bio: <textarea id="html-textarea" name="bio" rows="4"></textarea></label>
                <label><input id="html-checkbox-terms" name="acceptTerms" type="checkbox"> I accept the terms</label>
                <div style="display: flex; gap: 8px;">
                  <button id="html-submit-btn" type="submit">Submit</button>
                  <button id="html-reset-btn" type="reset">Reset</button>
                </div>
              </form>
            </div>

            <div style="padding: 16px; margin-bottom: 16px;">
              <h5>Native Select</h5>
              <div class="row">
                <div>
                  <label for="html-select-country">Country:</label><br>
                  <select id="html-select-country" name="country">
                    <option value="">Select a country</option>
                    <option value="us">United States</option>
                    <option value="uk">United Kingdom</option>
                    <option value="cn">China</option>
                  </select>
                </div>
                <div>
                  <label for="html-select-multi">Skills:</label><br>
                  <select id="html-select-multi" name="skills" multiple size="4">
                    <option value="java">Java</option>
                    <option value="python">Python</option>
                    <option value="javascript">JavaScript</option>
                  </select>
                </div>
              </div>
            </div>

            <div style="padding: 16px; margin-bottom: 16px;">
              <h5>Native Table</h5>
              <table id="html-table-products" name="productsTable" border="1" cellpadding="8" cellspacing="0">
                <thead><tr><th>Product</th><th>Price</th><th>Quantity</th></tr></thead>
                <tbody>
                  <tr id="product-row-1"><td>Widget A</td><td>$10.00</td><td>100</td></tr>
                  <tr id="product-row-2"><td>Widget B</td><td>$25.00</td><td>50</td></tr>
                </tbody>
              </table>
            </div>

            <div style="padding: 16px; margin-bottom: 16px;">
              <h5>Native Links</h5>
              <div class="row">
                <a id="html-link-home" href="#">Home</a>
                <a id="html-link-external" href="https://example.com" target="_blank">External Link</a>
              </div>
            </div>
            """ + PAGE_FOOTER;

    private static final String DYNAMIC_ROUTE_PAGE = PAGE_HEADER.formatted("Dynamic Route") + """
            <h4 id="page-title">Entity Detail</h4>
            <p>Current path: <code>/users/1</code> — ID: <strong>1</strong></p>

            <div class="mat-card" style="padding: 16px; margin-bottom: 16px;">
              <h5>Entity #1 Information</h5>
              <div class="row" style="margin-bottom: 8px;">
                <span class="mat-chip" id="entity-type-chip">User</span>
                <span class="mat-chip" id="entity-id-chip">ID: 1</span>
              </div>
              <div class="column">
                <div class="mat-form-field">
                  <label>Entity Name</label>
                  <input class="mat-input-element" id="user-name" type="text" value="User 1">
                </div>
                <div class="row">
                  <button class="mat-button" id="btn-save-entity">Save</button>
                  <button class="mat-button" id="btn-delete-entity">Delete</button>
                </div>
              </div>
            </div>

            <div class="mat-card" style="padding: 16px;">
              <h6>Navigate to Other IDs</h6>
              <div class="row">
                <a class="mat-button" href="dynamic-route.html?id=1" style="margin-right: 8px;">User 1</a>
                <a class="mat-button" href="dynamic-route.html?id=2" style="margin-right: 8px;">User 2</a>
                <a class="mat-button" href="dynamic-route.html?id=99">User 99</a>
              </div>
            </div>
            """ + PAGE_FOOTER;

    /**
     * private constructor
     */
    private NonMdcMatTestAppPages() {
        throw new AssertionError();
    }

    /**
     * Writes all static HTML pages to {@code target/recorder-examples/non-mdc-mat/} and returns the base directory
     * path.
     *
     * @return the absolute file URL of the written directory
     */
    public static String writePages() {
        try {
            Path dir = Path.of("target", "recorder-examples", "non-mdc-mat");
            Files.createDirectories(dir);
            Files.writeString(dir.resolve("inputs.html"), INPUTS_PAGE);
            Files.writeString(dir.resolve("data-display.html"), DATA_DISPLAY_PAGE);
            Files.writeString(dir.resolve("feedback.html"), FEEDBACK_PAGE);
            Files.writeString(dir.resolve("navigation.html"), NAVIGATION_PAGE);
            Files.writeString(dir.resolve("surfaces.html"), SURFACES_PAGE);
            Files.writeString(dir.resolve("locator-strategies.html"), LOCATOR_STRATEGIES_PAGE);
            Files.writeString(dir.resolve("html-elements.html"), HTML_ELEMENTS_PAGE);
            Files.writeString(dir.resolve("dynamic-route.html"), DYNAMIC_ROUTE_PAGE);
            return dir.toAbsolutePath().toUri().toString();
        } catch (IOException exception) {
            throw new UncheckedIOException("failed to write non-MDC MAT test pages", exception);
        }
    }
}
