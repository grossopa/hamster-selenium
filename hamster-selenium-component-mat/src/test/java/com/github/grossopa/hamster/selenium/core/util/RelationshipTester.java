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

package com.github.grossopa.hamster.selenium.core.util;import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.opentest4j.AssertionFailedError;

import java.util.List;


/**
 * copied from Guava Testlib due to it cannot be included into this project.
 *
 * @author Jack Yin
 * @since 1.4
 */
@SuppressWarnings("all")
final class RelationshipTester<T> {
    private final Equivalence<? super T> equivalence;
    private final String relationshipName;
    private final String hashName;
    private final ItemReporter itemReporter;
    private final List<ImmutableList<T>> groups = Lists.newArrayList();

    RelationshipTester(Equivalence<? super T> equivalence, String relationshipName, String hashName,
            ItemReporter itemReporter) {
        this.equivalence = (Equivalence) Preconditions.checkNotNull(equivalence);
        this.relationshipName = (String) Preconditions.checkNotNull(relationshipName);
        this.hashName = (String) Preconditions.checkNotNull(hashName);
        this.itemReporter = (ItemReporter) Preconditions.checkNotNull(itemReporter);
    }

    public RelationshipTester<T> addRelatedGroup(Iterable<? extends T> group) {
        this.groups.add(ImmutableList.copyOf(group));
        return this;
    }

    public void test() {
        for (int groupNumber = 0; groupNumber < this.groups.size(); ++groupNumber) {
            ImmutableList<T> group = (ImmutableList) this.groups.get(groupNumber);

            for (int itemNumber = 0; itemNumber < group.size(); ++itemNumber) {
                int unrelatedGroupNumber;
                for (unrelatedGroupNumber = 0; unrelatedGroupNumber < group.size(); ++unrelatedGroupNumber) {
                    if (itemNumber != unrelatedGroupNumber) {
                        this.assertRelated(groupNumber, itemNumber, unrelatedGroupNumber);
                    }
                }

                for (unrelatedGroupNumber = 0; unrelatedGroupNumber < this.groups.size(); ++unrelatedGroupNumber) {
                    if (groupNumber != unrelatedGroupNumber) {
                        ImmutableList<T> unrelatedGroup = (ImmutableList) this.groups.get(unrelatedGroupNumber);

                        for (int unrelatedItemNumber = 0;
                             unrelatedItemNumber < unrelatedGroup.size(); ++unrelatedItemNumber) {
                            this.assertUnrelated(groupNumber, itemNumber, unrelatedGroupNumber, unrelatedItemNumber);
                        }
                    }
                }
            }
        }

    }

    private void assertRelated(int groupNumber, int itemNumber, int relatedItemNumber) {
        Item<T> itemInfo = this.getItem(groupNumber, itemNumber);
        Item<T> relatedInfo = this.getItem(groupNumber, relatedItemNumber);
        T item = itemInfo.value;
        T related = relatedInfo.value;
        this.assertWithTemplate("$ITEM must be $RELATIONSHIP to $OTHER", itemInfo, relatedInfo,
                this.equivalence.equivalent(item, related));
        int itemHash = this.equivalence.hash(item);
        int relatedHash = this.equivalence.hash(related);
        this.assertWithTemplate(
                "the $HASH (" + itemHash + ") of $ITEM must be equal to the $HASH (" + relatedHash + ") of $OTHER",
                itemInfo, relatedInfo, itemHash == relatedHash);
    }

    private void assertUnrelated(int groupNumber, int itemNumber, int unrelatedGroupNumber, int unrelatedItemNumber) {
        Item<T> itemInfo = this.getItem(groupNumber, itemNumber);
        Item<T> unrelatedInfo = this.getItem(unrelatedGroupNumber, unrelatedItemNumber);
        this.assertWithTemplate("$ITEM must not be $RELATIONSHIP to $OTHER", itemInfo, unrelatedInfo,
                !this.equivalence.equivalent(itemInfo.value, unrelatedInfo.value));
    }

    private void assertWithTemplate(String template, Item<T> item, Item<T> other,
            boolean condition) {
        if (!condition) {
            throw new AssertionFailedError(
                    template.replace("$RELATIONSHIP", this.relationshipName).replace("$HASH", this.hashName)
                            .replace("$ITEM", this.itemReporter.reportItem(item))
                            .replace("$OTHER", this.itemReporter.reportItem(other)));
        }
    }

    private Item<T> getItem(int groupNumber, int itemNumber) {
        return new Item(((ImmutableList) this.groups.get(groupNumber)).get(itemNumber), groupNumber, itemNumber);
    }

    static final class Item<T> {
        final T value;
        final int groupNumber;
        final int itemNumber;

        Item(T value, int groupNumber, int itemNumber) {
            this.value = value;
            this.groupNumber = groupNumber;
            this.itemNumber = itemNumber;
        }

        public String toString() {
            return this.value + " [group " + (this.groupNumber + 1) + ", item " + (this.itemNumber + 1) + ']';
        }
    }

    static class ItemReporter {
        ItemReporter() {
        }

        String reportItem(Item<?> item) {
            return item.toString();
        }
    }
}
