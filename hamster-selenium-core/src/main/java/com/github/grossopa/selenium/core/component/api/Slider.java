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

package com.github.grossopa.selenium.core.component.api;

import com.github.grossopa.selenium.core.component.WebComponent;

import java.util.List;

/**
 * Sliders allow users to make selections from a range of values.
 *
 * @param <T> the thumb type
 * @author Jack Yin
 * @since 1.7
 */
public interface Slider<T extends WebComponent> {

    /**
     * Gets the raw value.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the raw value in String.
     */
    String getValue();

    /**
     * Gets value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in Integer.
     */
    Integer getValueInteger();

    /**
     * Gets value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in Long
     */
    Long getValueLong();

    /**
     * Gets value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>59049</b></p>
     *
     * @return the value in double
     */
    Double getValueDouble();

    /**
     * Gets raw min value.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the raw min value.
     */
    String getMinValue();

    /**
     * Gets min value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in Integer.
     */
    Integer getMinValueInteger();

    /**
     * Gets min value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in Long
     */
    Long getMinValueLong();

    /**
     * Gets min value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={2}, max={8}, scale={(x) =&gt; x ** 10}, then it should return <b>1024</b></p>
     *
     * @return the min value in double
     */
    Double getMinValueDouble();

    /**
     * Gets raw max value.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the raw max value.
     */
    String getMaxValue();

    /**
     * Gets max value in Integer.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in Integer.
     */
    Integer getMaxValueInteger();

    /**
     * Gets max value in Long.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in Long
     */
    Long getMaxValueLong();

    /**
     * Gets max value in Double.
     *
     * <p>If the slider is with scale function configured, it will return the scaled value, for example, when the
     * position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then it should return <b>60466176</b></p>
     *
     * @return the max value in double
     */
    Double getMaxValueDouble();

    /**
     * Gets the first Thumb element.
     *
     * @return the first Thumb element.
     */
    T getFirstThumb();

    /**
     * Gets the all Thumb elements. they are sorted by the value in ascending order.
     *
     * @return the all Thumb elements.
     */
    List<T> getAllThumbs();

    /**
     * Is the slider vertical.
     *
     * @return true if the slider is vertical
     */
    boolean isVertical();

    /**
     * Is the slider tracker inverted.
     *
     * @return true if the slide is inverted
     */
    boolean isInverted();

    /**
     * Move the first thumb to the specified value in Integer.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param value the new integer value to set
     * @see #moveThumb(double)
     */
    void setValue(Integer value);

    /**
     * Move the target thumb to the specified value in Integer. Note the MUI thumb is always ordered by value in
     * ascending order.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param index the thumb index
     * @param value the new integer value to set
     * @see #moveThumb(double)
     */
    void setValue(int index, Integer value);

    /**
     * Move the target thumb to the specified value in Integer.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param thumb the target thumb to move
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    void setValue(T thumb, Integer value);

    /**
     * Move the first thumb by value.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param value the new long value to set
     * @see #moveThumb(double)
     */
    void setValue(Long value);

    /**
     * Move the target thumb to the specified value in long. Note the MUI thumb is always ordered by value in ascending
     * order.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param index the thumb index
     * @param value the new integer value to set
     * @see #moveThumb(double)
     */
    void setValue(int index, Long value);

    /**
     * Move the target thumb to the specified value in long.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param thumb the target thumb to move
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    void setValue(T thumb, Long value);

    /**
     * Move the first thumb to the specified value in double.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    void setValue(Double value);

    /**
     * Move the target thumb to the specified value in double. Note the MUI thumb is always ordered by value in
     * ascending order.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param index the thumb index
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    void setValue(int index, Double value);

    /**
     * Move the target thumb to the specified value in double.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified value may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately set the value as 555 as the only possible locations are 55px and 56px
     * for 550 and 560.
     * </p>
     *
     * <p>If the slider is with scale function configured, it will accept the value as scaled value, for example, when
     * when the expected position is at 50%, min={0}, max={6}, scale={(x) =&gt; x ** 10}, then the value should be
     * <b>59049</b></p>
     *
     * @param thumb the target thumb to move
     * @param value the new double value to set
     * @see #moveThumb(double)
     */
    void setValue(T thumb, Double value);


    /**
     * Moves the first thumb to the desired location in percentage.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified percentage may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately move the thumb to a position like 55.5% for value 555 as the only possible
     * locations are 55px and 56px for 550 and 560.
     * </p>
     *
     * @param percentage the percentage to move to, must between [0.0, 1.0]
     */
    void moveThumb(double percentage);

    /**
     * Moves the thumb to the desired location in percentage.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified percentage may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately move the thumb to a position like 55.5% for value 555 as the only possible
     * locations are 55px and 56px for 550 and 560.
     * </p>
     *
     * @param index the index of the thumbs
     * @param percentage the percentage to move to, must between [0.0, 1.0]
     */
    void moveThumb(int index, double percentage);

    /**
     * Moves the thumb to the desired location in percentage.
     *
     * <p>Please note that due to this action is to simulate the user web page behaviour so it is possible that the
     * specified percentage may not accurately reflect the real value, an example is that:</p>
     * <ul>
     *     <li>min value : 0</li>
     *     <li>min value : 1000</li>
     *     <li>slide width: 100px</li>
     * </ul>
     * <p>
     * so it is not possible to accurately move the thumb to a position like 55.5% for value 555 as the only possible
     * locations are 55px and 56px for 550 and 560.
     * </p>
     *
     * @param thumb the thumb component to move
     * @param percentage the percentage to move to, must between [0.0, 1.0]
     */
    @SuppressWarnings("squid:S2184")
    void moveThumb(T thumb, double percentage);

}
