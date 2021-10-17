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

package com.github.grossopa.selenium.component.mui.v4.exception;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Throws when the target index is not found during pagination.
 *
 * @author Jack Yin
 * @since 1.7
 */
public class PaginationNotFoundException extends RuntimeException {

    private final int targetIndex;
    private final List<Integer> scannedIndices;

    /**
     * Constructs an instance with the target index to find and actual scanned indices list.
     *
     * @param targetIndex the target index tried to find but actually not found
     * @param scannedIndices the scanned indices list
     */
    public PaginationNotFoundException(int targetIndex, List<Integer> scannedIndices) {
        super(String.format("Tried to find index %d but not found, scanned indices are %s", targetIndex,
                StringUtils.join(scannedIndices, ",")));

        this.targetIndex = targetIndex;
        this.scannedIndices = ImmutableList.copyOf(scannedIndices);
    }

    /**
     * Gets the target index to find
     *
     * @return the target index to find
     */
    public int getTargetIndex() {
        return targetIndex;
    }

    /**
     * Gets the scanned index list
     *
     * @return the scanned index list
     */
    public List<Integer> getScannedIndices() {
        return scannedIndices;
    }
}
