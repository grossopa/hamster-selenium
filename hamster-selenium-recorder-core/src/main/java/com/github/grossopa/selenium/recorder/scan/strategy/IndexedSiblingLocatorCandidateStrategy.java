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
package com.github.grossopa.selenium.recorder.scan.strategy;

import com.github.grossopa.selenium.recorder.model.LocatorCandidate;
import com.github.grossopa.selenium.recorder.model.LocatorContext;
import com.github.grossopa.selenium.recorder.model.LocatorContext.AncestorInfo;
import com.github.grossopa.selenium.recorder.model.LocatorType;

import java.util.List;
import java.util.Map;

/**
 * A strategy that builds a unique locator candidate from the sibling index using an XPath positional predicate.
 * When multiple siblings of the same tag name exist under the same parent and the element has no {@code id},
 * {@code name} or custom attribute, this strategy generates a precise indexed locator to distinguish the target
 * element from its siblings.
 *
 * <p>Unlike {@link SiblingListLocatorCandidateStrategy} which produces a list locator matching all siblings, this
 * strategy produces a single-element locator that targets exactly one element at the given position.</p>
 *
 * <p>Examples of generated locators:
 * <ul>
 * <li>With ancestor id: {@code //*[@id='formSection']/child::button[2]}</li>
 * <li>With ancestor test id: {@code //*[@data-testid='actions']/child::button[1]}</li>
 * <li>Without ancestor: {@code //button[3]}</li>
 * </ul>
 *
 * @author Jack Yin
 * @since 1.17
 * @see LocatorCandidate#PRIORITY_INDEXED_SIBLING
 * @see LocatorContext#getSiblingIndex()
 */
public class IndexedSiblingLocatorCandidateStrategy implements LocatorCandidateStrategy {

    @Override
    public List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text) {
        return List.of();
    }

    @Override
    public List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text,
            LocatorContext context) {
        if (context.getSiblingCount() <= 1) {
            return List.of();
        }

        int siblingIndex = context.getSiblingIndex();
        if (siblingIndex <= 0) {
            return List.of();
        }

        AncestorInfo ancestor = context.getAncestor();
        String elementXpath = "*[local-name()='" + tagName + "'][" + siblingIndex + "]";

        String xpathValue;
        String description;
        if (ancestor != null && ancestor.id() != null) {
            xpathValue = "//*[@id='" + ancestor.id() + "']/child::" + elementXpath;
            description = "by indexed sibling #" + siblingIndex + " under #" + ancestor.id();
        } else if (ancestor != null && ancestor.testId() != null) {
            xpathValue = "//*[@data-testid='" + ancestor.testId() + "']/child::" + elementXpath;
            description = "by indexed sibling #" + siblingIndex + " under data-testid \"" + ancestor.testId() + "\"";
        } else {
            xpathValue = "//" + elementXpath;
            description = "by indexed sibling #" + siblingIndex + " " + tagName;
        }

        return List.of(new LocatorCandidate(LocatorType.XPATH, xpathValue,
                LocatorCandidate.PRIORITY_INDEXED_SIBLING, description));
    }
}
