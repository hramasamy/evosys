// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NumberConstants.java

package com.sbs.utils.numbers;

import java.text.DecimalFormatSymbols;

public interface NumberConstants
{

    public static final double ACCURACY = 1.0000000000000001E-05D;
    public static final Double positiveInfinity = new Double((1.0D / 0.0D));
    public static final Double negativeInfinity = new Double((-1.0D / 0.0D));
    public static final Double nan = new Double((0.0D / 0.0D));
    public static final Double minimum = new Double(4.9406564584124654E-324D);
    public static final Double maximum = new Double(1.7976931348623157E+308D);
    public static final Double doubleZero = new Double(0.0D);
    public static final Integer integerZero = new Integer(0);
    public static final DecimalFormatSymbols symbols = new DecimalFormatSymbols();
    public static final char decimalSeparator = symbols.getDecimalSeparator();
    public static final char groupingSeparator = symbols.getGroupingSeparator();
    public static final char minusSign = symbols.getMinusSign();
    public static final char plusSign = '+';

}
