package com.github.grossopa.playwright.core;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.FilePayload;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import static org.mockito.Mockito.*;

class AbstractDelegatedLocatorTest {

    private Locator locator;
    private ComponentDriver driver;
    private DefaultWebComponent component;

    @BeforeEach
    void setUp() {
        locator = mock(Locator.class);
        driver = mock(ComponentDriver.class);
        component = new DefaultWebComponent(locator, driver);
    }

    @Test
    void testAll() {
        component.all();
        verify(locator).all();
    }

    @Test
    void testAllInnerTexts() {
        component.allInnerTexts();
        verify(locator).allInnerTexts();
    }

    @Test
    void testAllTextContents() {
        component.allTextContents();
        verify(locator).allTextContents();
    }

    @Test
    void testBlur() {
        component.blur();
        verify(locator).blur();
    }

    @Test
    void testBlurWithOptions() {
        component.blur(new Locator.BlurOptions());
        verify(locator).blur(any(Locator.BlurOptions.class));
    }

    @Test
    void testBoundingBox() {
        component.boundingBox();
        verify(locator).boundingBox();
    }

    @Test
    void testBoundingBoxWithOptions() {
        component.boundingBox(new Locator.BoundingBoxOptions());
        verify(locator).boundingBox(any(Locator.BoundingBoxOptions.class));
    }

    @Test
    void testCheck() {
        component.check();
        verify(locator).check();
    }

    @Test
    void testCheckWithOptions() {
        component.check(new Locator.CheckOptions());
        verify(locator).check(any(Locator.CheckOptions.class));
    }

    @Test
    void testClear() {
        component.clear();
        verify(locator).clear();
    }

    @Test
    void testClearWithOptions() {
        component.clear(new Locator.ClearOptions());
        verify(locator).clear(any(Locator.ClearOptions.class));
    }

    @Test
    void testClickWithOptions() {
        component.click(new Locator.ClickOptions());
        verify(locator).click(any(Locator.ClickOptions.class));
    }

    @Test
    void testCount() {
        component.count();
        verify(locator).count();
    }

    @Test
    void testDblclick() {
        component.dblclick();
        verify(locator).dblclick();
    }

    @Test
    void testDblclickWithOptions() {
        component.dblclick(new Locator.DblclickOptions());
        verify(locator).dblclick(any(Locator.DblclickOptions.class));
    }

    @Test
    void testDispatchEventTwoArgs() {
        component.dispatchEvent("click", null);
        verify(locator).dispatchEvent("click", null);
    }

    @Test
    void testDispatchEventOneArg() {
        component.dispatchEvent("click");
        verify(locator).dispatchEvent("click");
    }

    @Test
    void testDispatchEventThreeArgs() {
        component.dispatchEvent("click", null, null);
        verify(locator).dispatchEvent("click", null, null);
    }

    @Test
    void testDragTo() {
        Locator target = mock(Locator.class);
        component.dragTo(target);
        verify(locator).dragTo(target);
    }

    @Test
    void testDragToWithOptions() {
        Locator target = mock(Locator.class);
        component.dragTo(target, new Locator.DragToOptions());
        verify(locator).dragTo(eq(target), any(Locator.DragToOptions.class));
    }

    @Test
    void testElementHandle() {
        component.elementHandle();
        verify(locator).elementHandle();
    }

    @Test
    void testElementHandleWithOptions() {
        component.elementHandle(new Locator.ElementHandleOptions());
        verify(locator).elementHandle(any(Locator.ElementHandleOptions.class));
    }

    @Test
    void testElementHandles() {
        component.elementHandles();
        verify(locator).elementHandles();
    }

    @Test
    void testEvaluateTwoArgs() {
        component.evaluate("expr", null);
        verify(locator).evaluate("expr", null);
    }

    @Test
    void testEvaluateOneArg() {
        component.evaluate("expr");
        verify(locator).evaluate("expr");
    }

    @Test
    void testEvaluateThreeArgs() {
        component.evaluate("expr", null, null);
        verify(locator).evaluate("expr", null, null);
    }

    @Test
    void testEvaluateAllOneArg() {
        component.evaluateAll("expr");
        verify(locator).evaluateAll("expr");
    }

    @Test
    void testEvaluateAllTwoArgs() {
        component.evaluateAll("expr", null);
        verify(locator).evaluateAll("expr", null);
    }

    @Test
    void testEvaluateHandleTwoArgs() {
        component.evaluateHandle("expr", null);
        verify(locator).evaluateHandle("expr", null);
    }

    @Test
    void testEvaluateHandleOneArg() {
        component.evaluateHandle("expr");
        verify(locator).evaluateHandle("expr");
    }

    @Test
    void testEvaluateHandleThreeArgs() {
        component.evaluateHandle("expr", null, null);
        verify(locator).evaluateHandle("expr", null, null);
    }

    @Test
    void testFillWithOptions() {
        component.fill("v", new Locator.FillOptions());
        verify(locator).fill(eq("v"), any(Locator.FillOptions.class));
    }

    @Test
    void testFilter() {
        component.filter();
        verify(locator).filter();
    }

    @Test
    void testFilterWithOptions() {
        component.filter(new Locator.FilterOptions());
        verify(locator).filter(any(Locator.FilterOptions.class));
    }

    @Test
    void testFirst() {
        component.first();
        verify(locator).first();
    }

    @Test
    void testFocus() {
        component.focus();
        verify(locator).focus();
    }

    @Test
    void testFocusWithOptions() {
        component.focus(new Locator.FocusOptions());
        verify(locator).focus(any(Locator.FocusOptions.class));
    }

    @Test
    void testFrameLocator() {
        component.frameLocator("iframe");
        verify(locator).frameLocator("iframe");
    }

    @Test
    void testGetAttributeWithOptions() {
        component.getAttribute("name", new Locator.GetAttributeOptions());
        verify(locator).getAttribute(eq("name"), any(Locator.GetAttributeOptions.class));
    }

    @Test
    void testGetByAltTextString() {
        component.getByAltText("alt");
        verify(locator).getByAltText("alt");
    }

    @Test
    void testGetByAltTextStringWithOptions() {
        component.getByAltText("alt", new Locator.GetByAltTextOptions());
        verify(locator).getByAltText(eq("alt"), any(Locator.GetByAltTextOptions.class));
    }

    @Test
    void testGetByAltTextPattern() {
        component.getByAltText(Pattern.compile("alt"));
        verify(locator).getByAltText(any(Pattern.class));
    }

    @Test
    void testGetByAltTextPatternWithOptions() {
        component.getByAltText(Pattern.compile("alt"), new Locator.GetByAltTextOptions());
        verify(locator).getByAltText(any(Pattern.class), any(Locator.GetByAltTextOptions.class));
    }

    @Test
    void testGetByLabelString() {
        component.getByLabel("label");
        verify(locator).getByLabel("label");
    }

    @Test
    void testGetByLabelStringWithOptions() {
        component.getByLabel("label", new Locator.GetByLabelOptions());
        verify(locator).getByLabel(eq("label"), any(Locator.GetByLabelOptions.class));
    }

    @Test
    void testGetByLabelPattern() {
        component.getByLabel(Pattern.compile("label"));
        verify(locator).getByLabel(any(Pattern.class));
    }

    @Test
    void testGetByLabelPatternWithOptions() {
        component.getByLabel(Pattern.compile("label"), new Locator.GetByLabelOptions());
        verify(locator).getByLabel(any(Pattern.class), any(Locator.GetByLabelOptions.class));
    }

    @Test
    void testGetByPlaceholderString() {
        component.getByPlaceholder("ph");
        verify(locator).getByPlaceholder("ph");
    }

    @Test
    void testGetByPlaceholderStringWithOptions() {
        component.getByPlaceholder("ph", new Locator.GetByPlaceholderOptions());
        verify(locator).getByPlaceholder(eq("ph"), any(Locator.GetByPlaceholderOptions.class));
    }

    @Test
    void testGetByPlaceholderPattern() {
        component.getByPlaceholder(Pattern.compile("ph"));
        verify(locator).getByPlaceholder(any(Pattern.class));
    }

    @Test
    void testGetByPlaceholderPatternWithOptions() {
        component.getByPlaceholder(Pattern.compile("ph"), new Locator.GetByPlaceholderOptions());
        verify(locator).getByPlaceholder(any(Pattern.class), any(Locator.GetByPlaceholderOptions.class));
    }

    @Test
    void testGetByRole() {
        component.getByRole(AriaRole.BUTTON);
        verify(locator).getByRole(AriaRole.BUTTON);
    }

    @Test
    void testGetByRoleWithOptions() {
        component.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions());
        verify(locator).getByRole(eq(AriaRole.BUTTON), any(Locator.GetByRoleOptions.class));
    }

    @Test
    void testGetByTestIdString() {
        component.getByTestId("id");
        verify(locator).getByTestId("id");
    }

    @Test
    void testGetByTestIdPattern() {
        component.getByTestId(Pattern.compile("id"));
        verify(locator).getByTestId(any(Pattern.class));
    }

    @Test
    void testGetByTextString() {
        component.getByText("text");
        verify(locator).getByText("text");
    }

    @Test
    void testGetByTextStringWithOptions() {
        component.getByText("text", new Locator.GetByTextOptions());
        verify(locator).getByText(eq("text"), any(Locator.GetByTextOptions.class));
    }

    @Test
    void testGetByTextPattern() {
        component.getByText(Pattern.compile("text"));
        verify(locator).getByText(any(Pattern.class));
    }

    @Test
    void testGetByTextPatternWithOptions() {
        component.getByText(Pattern.compile("text"), new Locator.GetByTextOptions());
        verify(locator).getByText(any(Pattern.class), any(Locator.GetByTextOptions.class));
    }

    @Test
    void testGetByTitleString() {
        component.getByTitle("title");
        verify(locator).getByTitle("title");
    }

    @Test
    void testGetByTitleStringWithOptions() {
        component.getByTitle("title", new Locator.GetByTitleOptions());
        verify(locator).getByTitle(eq("title"), any(Locator.GetByTitleOptions.class));
    }

    @Test
    void testGetByTitlePattern() {
        component.getByTitle(Pattern.compile("title"));
        verify(locator).getByTitle(any(Pattern.class));
    }

    @Test
    void testGetByTitlePatternWithOptions() {
        component.getByTitle(Pattern.compile("title"), new Locator.GetByTitleOptions());
        verify(locator).getByTitle(any(Pattern.class), any(Locator.GetByTitleOptions.class));
    }

    @Test
    void testHighlight() {
        component.highlight();
        verify(locator).highlight();
    }

    @Test
    void testHoverWithOptions() {
        component.hover(new Locator.HoverOptions());
        verify(locator).hover(any(Locator.HoverOptions.class));
    }

    @Test
    void testInnerHTMLOptions() {
        component.innerHTML(new Locator.InnerHTMLOptions());
        verify(locator).innerHTML(any(Locator.InnerHTMLOptions.class));
    }

    @Test
    void testInnerTextOptions() {
        component.innerText(new Locator.InnerTextOptions());
        verify(locator).innerText(any(Locator.InnerTextOptions.class));
    }

    @Test
    void testInputValue() {
        component.inputValue();
        verify(locator).inputValue();
    }

    @Test
    void testInputValueWithOptions() {
        component.inputValue(new Locator.InputValueOptions());
        verify(locator).inputValue(any(Locator.InputValueOptions.class));
    }

    @Test
    void testIsChecked() {
        component.isChecked();
        verify(locator).isChecked();
    }

    @Test
    void testIsCheckedWithOptions() {
        component.isChecked(new Locator.IsCheckedOptions());
        verify(locator).isChecked(any(Locator.IsCheckedOptions.class));
    }

    @Test
    void testIsDisabledWithOptions() {
        component.isDisabled(new Locator.IsDisabledOptions());
        verify(locator).isDisabled(any(Locator.IsDisabledOptions.class));
    }

    @Test
    void testIsEditable() {
        component.isEditable();
        verify(locator).isEditable();
    }

    @Test
    void testIsEditableWithOptions() {
        component.isEditable(new Locator.IsEditableOptions());
        verify(locator).isEditable(any(Locator.IsEditableOptions.class));
    }

    @Test
    void testIsEnabledWithOptions() {
        component.isEnabled(new Locator.IsEnabledOptions());
        verify(locator).isEnabled(any(Locator.IsEnabledOptions.class));
    }

    @Test
    void testIsHidden() {
        component.isHidden();
        verify(locator).isHidden();
    }

    @Test
    void testIsHiddenWithOptions() {
        component.isHidden(new Locator.IsHiddenOptions());
        verify(locator).isHidden(any(Locator.IsHiddenOptions.class));
    }

    @Test
    void testIsVisibleWithOptions() {
        component.isVisible(new Locator.IsVisibleOptions());
        verify(locator).isVisible(any(Locator.IsVisibleOptions.class));
    }

    @Test
    void testLast() {
        component.last();
        verify(locator).last();
    }

    @Test
    void testLocatorString() {
        component.locator(".sel");
        verify(locator).locator(".sel");
    }

    @Test
    void testLocatorStringWithOptions() {
        component.locator(".sel", new Locator.LocatorOptions());
        verify(locator).locator(eq(".sel"), any(Locator.LocatorOptions.class));
    }

    @Test
    void testNth() {
        component.nth(3);
        verify(locator).nth(3);
    }

    @Test
    void testPage() {
        component.page();
        verify(locator).page();
    }

    @Test
    void testPress() {
        component.press("Enter");
        verify(locator).press("Enter");
    }

    @Test
    void testPressWithOptions() {
        component.press("Enter", new Locator.PressOptions());
        verify(locator).press(eq("Enter"), any(Locator.PressOptions.class));
    }

    @Test
    void testScreenshot() {
        component.screenshot();
        verify(locator).screenshot();
    }

    @Test
    void testScreenshotWithOptions() {
        component.screenshot(new Locator.ScreenshotOptions());
        verify(locator).screenshot(any(Locator.ScreenshotOptions.class));
    }

    @Test
    void testScrollIntoViewIfNeeded() {
        component.scrollIntoViewIfNeeded();
        verify(locator).scrollIntoViewIfNeeded();
    }

    @Test
    void testScrollIntoViewIfNeededWithOptions() {
        component.scrollIntoViewIfNeeded(new Locator.ScrollIntoViewIfNeededOptions());
        verify(locator).scrollIntoViewIfNeeded(any(Locator.ScrollIntoViewIfNeededOptions.class));
    }

    @Test
    void testSelectOptionString() {
        component.selectOption("val");
        verify(locator).selectOption("val");
    }

    @Test
    void testSelectOptionStringWithOptions() {
        component.selectOption("val", new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq("val"), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionElementHandle() {
        ElementHandle eh = mock(ElementHandle.class);
        component.selectOption(eh);
        verify(locator).selectOption(eh);
    }

    @Test
    void testSelectOptionElementHandleWithOptions() {
        ElementHandle eh = mock(ElementHandle.class);
        component.selectOption(eh, new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq(eh), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionStringArray() {
        component.selectOption(new String[]{"a"});
        verify(locator).selectOption((String[]) any());
    }

    @Test
    void testSelectOptionStringArrayWithOptions() {
        component.selectOption(new String[]{"a"}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((String[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionSelectOption() {
        SelectOption so = new SelectOption();
        component.selectOption(so);
        verify(locator).selectOption(so);
    }

    @Test
    void testSelectOptionSelectOptionWithOptions() {
        SelectOption so = new SelectOption();
        component.selectOption(so, new Locator.SelectOptionOptions());
        verify(locator).selectOption(eq(so), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionElementHandleArray() {
        component.selectOption(new ElementHandle[]{mock(ElementHandle.class)});
        verify(locator).selectOption((ElementHandle[]) any());
    }

    @Test
    void testSelectOptionElementHandleArrayWithOptions() {
        component.selectOption(new ElementHandle[]{mock(ElementHandle.class)}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((ElementHandle[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectOptionSelectOptionArray() {
        component.selectOption(new SelectOption[]{new SelectOption()});
        verify(locator).selectOption((SelectOption[]) any());
    }

    @Test
    void testSelectOptionSelectOptionArrayWithOptions() {
        component.selectOption(new SelectOption[]{new SelectOption()}, new Locator.SelectOptionOptions());
        verify(locator).selectOption((SelectOption[]) any(), any(Locator.SelectOptionOptions.class));
    }

    @Test
    void testSelectText() {
        component.selectText();
        verify(locator).selectText();
    }

    @Test
    void testSelectTextWithOptions() {
        component.selectText(new Locator.SelectTextOptions());
        verify(locator).selectText(any(Locator.SelectTextOptions.class));
    }

    @Test
    void testSetChecked() {
        component.setChecked(true);
        verify(locator).setChecked(true);
    }

    @Test
    void testSetCheckedWithOptions() {
        component.setChecked(true, new Locator.SetCheckedOptions());
        verify(locator).setChecked(eq(true), any(Locator.SetCheckedOptions.class));
    }

    @Test
    void testSetInputFilesPath() {
        component.setInputFiles(Paths.get("/tmp/f"));
        verify(locator).setInputFiles(Paths.get("/tmp/f"));
    }

    @Test
    void testSetInputFilesPathWithOptions() {
        component.setInputFiles(Paths.get("/tmp/f"), new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles(eq(Paths.get("/tmp/f")), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesPathArray() {
        component.setInputFiles(new Path[]{Paths.get("/tmp/f")});
        verify(locator).setInputFiles((Path[]) any());
    }

    @Test
    void testSetInputFilesPathArrayWithOptions() {
        component.setInputFiles(new Path[]{Paths.get("/tmp/f")}, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles((Path[]) any(), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesFilePayload() {
        FilePayload fp = new FilePayload("f", "text/plain", new byte[0]);
        component.setInputFiles(fp);
        verify(locator).setInputFiles(fp);
    }

    @Test
    void testSetInputFilesFilePayloadWithOptions() {
        FilePayload fp = new FilePayload("f", "text/plain", new byte[0]);
        component.setInputFiles(fp, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles(eq(fp), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testSetInputFilesFilePayloadArray() {
        component.setInputFiles(new FilePayload[]{new FilePayload("f", "text/plain", new byte[0])});
        verify(locator).setInputFiles((FilePayload[]) any());
    }

    @Test
    void testSetInputFilesFilePayloadArrayWithOptions() {
        component.setInputFiles(new FilePayload[]{new FilePayload("f", "text/plain", new byte[0])}, new Locator.SetInputFilesOptions());
        verify(locator).setInputFiles((FilePayload[]) any(), any(Locator.SetInputFilesOptions.class));
    }

    @Test
    void testTap() {
        component.tap();
        verify(locator).tap();
    }

    @Test
    void testTapWithOptions() {
        component.tap(new Locator.TapOptions());
        verify(locator).tap(any(Locator.TapOptions.class));
    }

    @Test
    void testTextContentWithOptions() {
        component.textContent(new Locator.TextContentOptions());
        verify(locator).textContent(any(Locator.TextContentOptions.class));
    }

    @Test
    void testType() {
        component.type("t");
        verify(locator).type("t");
    }

    @Test
    void testTypeWithOptions() {
        component.type("t", new Locator.TypeOptions());
        verify(locator).type(eq("t"), any(Locator.TypeOptions.class));
    }

    @Test
    void testUncheck() {
        component.uncheck();
        verify(locator).uncheck();
    }

    @Test
    void testUncheckWithOptions() {
        component.uncheck(new Locator.UncheckOptions());
        verify(locator).uncheck(any(Locator.UncheckOptions.class));
    }

    @Test
    void testWaitFor() {
        component.waitFor();
        verify(locator).waitFor();
    }

    @Test
    void testWaitForWithOptions() {
        component.waitFor(new Locator.WaitForOptions());
        verify(locator).waitFor(any(Locator.WaitForOptions.class));
    }

    @Test
    void testAnd() {
        Locator other = mock(Locator.class);
        component.and(other);
        verify(locator).and(other);
    }

    @Test
    void testAriaSnapshot() {
        component.ariaSnapshot();
        verify(locator).ariaSnapshot();
    }

    @Test
    void testAriaSnapshotWithOptions() {
        component.ariaSnapshot(new Locator.AriaSnapshotOptions());
        verify(locator).ariaSnapshot(any(Locator.AriaSnapshotOptions.class));
    }

    @Test
    void testContentFrame() {
        component.contentFrame();
        verify(locator).contentFrame();
    }

    @Test
    void testLocatorWithLocator() {
        Locator other = mock(Locator.class);
        component.locator(other);
        verify(locator).locator(other);
    }

    @Test
    void testLocatorWithLocatorAndOptions() {
        Locator other = mock(Locator.class);
        component.locator(other, new Locator.LocatorOptions());
        verify(locator).locator(eq(other), any(Locator.LocatorOptions.class));
    }

    @Test
    void testOr() {
        Locator other = mock(Locator.class);
        component.or(other);
        verify(locator).or(other);
    }

    @Test
    void testPressSequentially() {
        component.pressSequentially("text");
        verify(locator).pressSequentially("text");
    }

    @Test
    void testPressSequentiallyWithOptions() {
        component.pressSequentially("text", new Locator.PressSequentiallyOptions());
        verify(locator).pressSequentially(eq("text"), any(Locator.PressSequentiallyOptions.class));
    }
}
