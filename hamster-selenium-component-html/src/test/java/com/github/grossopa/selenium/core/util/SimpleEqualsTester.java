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

package com.github.grossopa.selenium.core.util;import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * copied from Guava Testlib due to it cannot be included into this project.
 *
 * @author Jack Yin
 * @since 1.4
 */

@SuppressWarnings("all")
public final class SimpleEqualsTester {
    private static final int REPETITIONS = 3;
    private final List<List<Object>> equalityGroups;
    private final RelationshipTester.ItemReporter itemReporter;

    public SimpleEqualsTester() {
        this(new RelationshipTester.ItemReporter());
    }

    SimpleEqualsTester(RelationshipTester.ItemReporter itemReporter) {
        this.equalityGroups = Lists.newArrayList();
        this.itemReporter = (RelationshipTester.ItemReporter) Preconditions.checkNotNull(itemReporter);
    }

    public SimpleEqualsTester addEqualityGroup(Object... equalityGroup) {
        Preconditions.checkNotNull(equalityGroup);
        this.equalityGroups.add(ImmutableList.copyOf(equalityGroup));
        return this;
    }

    public SimpleEqualsTester testEquals() {
        RelationshipTester<Object> delegate = new RelationshipTester(Equivalence.equals(), "Object#equals",
                "Object#hashCode", this.itemReporter);
        Iterator var2 = this.equalityGroups.iterator();

        while (var2.hasNext()) {
            List<Object> group = (List) var2.next();
            delegate.addRelatedGroup(group);
        }

        for (int run = 0; run < 3; ++run) {
            this.testItems();
            delegate.test();
        }

        return this;
    }

    private void testItems() {
        for (Object item : Iterables.concat(this.equalityGroups)) {
            assertTrue(!item.equals((Object) null), () -> item + " must not be Object#equals to null");
            assertTrue(!item.equals(NotAnInstance.EQUAL_TO_NOTHING),
                    () -> item + " must not be Object#equals to an arbitrary object of another class");
            assertEquals(item, item, () -> item + " must be Object#equals to itself");
            assertEquals(item.hashCode(), item.hashCode(),
                    () -> "the Object#hashCode of " + item + " must be consistent");
        }

    }

    private enum NotAnInstance {
        EQUAL_TO_NOTHING;

        NotAnInstance() {
        }
    }
}

