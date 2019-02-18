// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Numbers.java

package com.sbs.utils.numbers;

import com.sbs.utils.string.Strings;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.Format;

// Referenced classes of package com.sbs.utils.numbers:
//            NumberConstants

public class Numbers
    implements NumberConstants
{

    public Numbers()
    {
    }

    public static Double add(Double firstOperand, Double secondOperand)
    {
        return new Double(firstOperand.doubleValue() + secondOperand.doubleValue());
    }

    public static double asDecimalPrecision(double value, double precision)
    {
        int multiplier = (int)Math.pow(10D, precision);
        return Math.rint(value * (double)multiplier) / (double)multiplier;
    }

    public static double asDouble(String value)
    {
        return Strings.isEmpty(value) ? 0.0D : Double.parseDouble(value);
    }

    public static int asInt(String value)
    {
        return Strings.isEmpty(value) ? 0 : Integer.parseInt(value);
    }

    public static double asPercent(double value)
    {
        return setPrecision(value, 1);
    }

    public static String asStringWithPrecision(double value, int precision)
    {
        if(precision == 0)
            return Integer.toString((int)value);
        double newValue = setPrecision(value, precision);
        String stringValue = "".concat(String.valueOf(String.valueOf(newValue)));
        int addedZeros = precision - (stringValue.length() - stringValue.indexOf(".") - 1);
        for(int j = 0; j < addedZeros; j++)
            stringValue = String.valueOf(String.valueOf(stringValue)).concat("0");

        return stringValue;
    }

    public static String asStringWithPrecision(Double value, int precision)
    {
        return asStringWithPrecision(value.doubleValue(), precision);
    }

    public static int compareTo(double first, double second)
    {
        if(first == second)
            return 0;
        else
            return first <= second ? -1 : 1;
    }

    public static int compareTo(int first, int second)
    {
        if(first == second)
            return 0;
        else
            return first <= second ? -1 : 1;
    }

    public static int compareTo(Double first, Double second)
    {
        return compareTo(first.doubleValue(), second.doubleValue());
    }

    public static Double divide(Double numerator, Double denominator)
    {
        return new Double(numerator.doubleValue() / denominator.doubleValue());
    }

    public static boolean equals(double first, double second)
    {
        return equals(first, second, 1.0000000000000001E-05D);
    }

    public static boolean equals(double first, double second, double accuracy)
    {
        return Math.abs(first - second) <= accuracy;
    }

    public static boolean equals(String first, String second)
    {
        return isDouble(first) && isDouble(second) && equals(asDouble(first), asDouble(second));
    }

    public static boolean equals(BigInteger firstBigInt, int intValue)
    {
        BigInteger otherBigInt = new BigInteger("".concat(String.valueOf(String.valueOf(intValue))));
        return equals(firstBigInt, otherBigInt);
    }

    public static boolean equals(BigInteger first, BigInteger second)
    {
        return noneNull(first, second) && first.compareTo(second) == 0;
    }

    public static String format(Number number, String pattern)
    {
        DecimalFormat format = new DecimalFormat();
        String patternToUse = pattern;
        if(Strings.isEmpty(pattern))
            patternToUse = "###.############################";
        format.applyPattern(patternToUse);
        return format.format(number);
    }

    public static String formatAsCurrency(double number)
    {
        return formatAsCurrency(((Number) (new Double(number))));
    }

    public static String formatAsCurrency(Number number)
    {
        return number.doubleValue() != -1D ? format(number, "0.00") : "";
    }

    public static boolean greaterThan(BigInteger firstBigInt, int intValue)
    {
        BigInteger otherBigInt = new BigInteger("".concat(String.valueOf(String.valueOf(intValue))));
        return greaterThan(firstBigInt, otherBigInt);
    }

    private static boolean greaterThan(BigInteger first, BigInteger second)
    {
        return noneNull(first, second) && first.compareTo(second) > 0;
    }

    private static boolean greaterThanOrEqualTo(BigInteger first, BigInteger second)
    {
        return noneNull(first, second) && first.compareTo(second) >= 0;
    }

    public static boolean isBetween(double value, double low, double high)
    {
        return low <= value && value <= high;
    }

    public static boolean isBetween(int value, int low, int high)
    {
        return isBetween(value, low, high);
    }

    public static boolean isDouble(String subject)
    {
        if(subject == null)
            return false;
        else
            return Strings.asDouble(subject) != null;
    }

    public static boolean isEven(int subject)
    {
        return subject % 2 == 0;
    }

    public static boolean isInteger(String subject)
    {
        try
        {
            asInt(subject);
            boolean flag = true;
            return flag;
        }
        catch(NumberFormatException e)
        {
            boolean flag1 = false;
            return flag1;
        }
    }

    public static boolean isNumeric(char character)
    {
        return Character.isDigit(character) || NumberConstants.decimalSeparator == character || NumberConstants.groupingSeparator == character || '+' == character || NumberConstants.minusSign == character;
    }

    public static boolean isNumeric(String subject)
    {
        boolean result = true;
        for(int i = 0; result && i < subject.length(); i++)
            result &= isNumeric(subject.charAt(i));

        return result && isDouble(subject);
    }

    public static boolean isReallyCloseToZero(double value)
    {
        double verySmall = 1.0000000000000001E-05D;
        return value == (double)0 || value > (double)0 && value < verySmall || value < (double)0 && value > -verySmall;
    }

    public static boolean isWholeNumber(double value)
    {
        double tolerance = 0.001D;
        double wholeNumber = Math.rint(value);
        return Math.abs(value - wholeNumber) < tolerance;
    }

    public static boolean lessThan(BigInteger firstBigInt, BigInteger otherBigInt)
    {
        if(firstBigInt == null || otherBigInt == null)
            return false;
        else
            return firstBigInt.compareTo(otherBigInt) < 0;
    }

    public static boolean lessThanOrEqualTo(BigInteger firstBigInt, int intValue)
    {
        BigInteger otherBigInt = new BigInteger("".concat(String.valueOf(String.valueOf(intValue))));
        return lessThanOrEqualTo(firstBigInt, otherBigInt);
    }

    public static boolean lessThanOrEqualTo(BigInteger firstBigInt, BigInteger otherBigInt)
    {
        if(firstBigInt == null || otherBigInt == null)
            return false;
        else
            return firstBigInt.compareTo(otherBigInt) <= 0;
    }

    public static Double multiply(Double firstOperand, Double secondOperand)
    {
        return new Double(firstOperand.doubleValue() * secondOperand.doubleValue());
    }

    public static Double multiply(Double firstOperand, Integer secondOperand)
    {
        return new Double(firstOperand.doubleValue() * (double)secondOperand.intValue());
    }

    private static boolean noneNull(BigInteger first, BigInteger second)
    {
        return first != null && second != null;
    }

    public static double setPrecision(double value, int precision)
    {
        int multiplier = 1;
        for(int i = 0; i < precision; i++)
            multiplier *= 10;

        double newValue = value * (double)multiplier;
        double rounded = Math.round(newValue);
        newValue = rounded / (double)multiplier;
        return newValue;
    }

    public static Double setPrecision(Double value, int precision)
    {
        if(value == null)
            return null;
        else
            return new Double(setPrecision(value.doubleValue(), precision));
    }

    public static Double subtract(Double firstOperand, Double secondOperand)
    {
        return new Double(firstOperand.doubleValue() - secondOperand.doubleValue());
    }

    public static Integer subtract(Integer firstOperand, Integer secondOperand)
    {
        return new Integer(firstOperand.intValue() - secondOperand.intValue());
    }
}
