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
import com.github.grossopa.selenium.recorder.model.LocatorType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.grossopa.selenium.core.locator.By2.xpathBuilder;

/**
 * A strategy that builds locator candidates from the customized key attributes (e.g. {@code data-testid}) configured
 * in the {@link com.github.grossopa.selenium.recorder.config.RecorderConfig}.
 *
 * <p>This strategy skips the {@code id} and {@code name} attributes as they are handled by
 * {@link IdLocatorCandidateStrategy} and {@link NameLocatorCandidateStrategy} respectively.</p>
 *
 * <p>The xpath is built via {@link com.github.grossopa.selenium.core.locator.By2#xpathBuilder()} which handles quote
 * escaping automatically through
 * {@link com.github.grossopa.selenium.core.util.SeleniumUtils#enrichQuote(String)}.</p>
 *
 * @author Jack Yin
 * @since 1.15
 * @see LocatorCandidate#PRIORITY_CUSTOM_ATTRIBUTE
 */
public class CustomAttributeLocatorCandidateStrategy implements LocatorCandidateStrategy {

    private final List<String> keyAttributes;

    /**
     * Constructs an instance with the key attribute names to evaluate.
     *
     * @param keyAttributes the key attribute names from the configuration, must not be null
     */
    public CustomAttributeLocatorCandidateStrategy(List<String> keyAttributes) {
        this.keyAttributes = List.copyOf(keyAttributes);
    }

    @Override
    public List<LocatorCandidate> toCandidates(int index, String tagName, Map<String, String> attributes, String text) {
        List<LocatorCandidate> candidates = new ArrayList<>();
        for (String attrName : keyAttributes) {
            if ("id".equals(attrName) || "name".equals(attrName)) {
                continue;
            }
            String value = attributes.get(attrName);
            if (StringUtils.isNotBlank(value) && !value.contains("\n") && !value.contains("\r")) {
                String xpathValue = xpathBuilder().anywhere().attr(attrName).exact(value).xpathString();
                candidates.add(new LocatorCandidate(LocatorType.XPATH, xpathValue,
                        LocatorCandidate.PRIORITY_CUSTOM_ATTRIBUTE,
                        "by attribute " + attrName + "=\"" + value + "\""));
            }
        }
        return candidates;
    }
}
