// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataTypeValidator.java

package com.sbs.espace;

import com.sbs.utils.date.DateFormat;
import com.sbs.utils.string.Strings;
import java.lang.reflect.Constructor;
import java.text.ParseException;
import java.util.Date;

// Referenced classes of package com.sbs.espace:
//            ValidationException, PrimitiveTypes, DataTypeValidationException, ISimpleDataType

public class DataTypeValidator
{

    public DataTypeValidator()
    {
    }

    public static Object validate(Class c, Object value)
        throws ValidationException
    {
        Object validated = null;
        Class valCalss = value.getClass();
        try
        {
            if(c.isPrimitive())
            {
                String strVal = String.valueOf(value);
                if(Strings.isEmpty(strVal))
                {
                    Object obj1 = null;
                    return obj1;
                }
                if(c.equals(Boolean.TYPE))
                {
                    Boolean boolean1 = new Boolean(strVal);
                    return boolean1;
                }
                if(c.equals(Short.TYPE))
                {
                    Short short1 = new Short(strVal);
                    return short1;
                }
                if(c.equals(Byte.TYPE))
                {
                    Byte byte1 = new Byte(strVal);
                    return byte1;
                }
                if(c.equals(Integer.TYPE))
                {
                    Integer integer = new Integer(strVal);
                    return integer;
                }
                if(c.equals(Long.TYPE))
                {
                    Long long1 = new Long(strVal);
                    return long1;
                }
                if(c.equals(Float.TYPE))
                {
                    Float float1 = new Float(strVal);
                    return float1;
                }
                if(c.equals(Double.TYPE))
                {
                    Double double1 = new Double(strVal);
                    return double1;
                }
            } else
            {
                Constructor constructor = c.getConstructor(new Class[] {
                    valCalss
                });
                validated = constructor.newInstance(new Object[] {
                    value
                });
            }
            Object obj = validated;
            return obj;
        }
        catch(Exception ex)
        {
            throw new ValidationException(String.valueOf(String.valueOf((new StringBuffer("Can't convert the value to a requested class.")).append(value).append(", ").append(c))), ex);
        }
    }

    public static Object validate(ISimpleDataType dt, Object value)
        throws ValidationException
    {
        if(value == null)
            return value;
        Object validated = null;
        if(dt instanceof PrimitiveTypes)
            return validate((PrimitiveTypes)dt, value);
        else
            return validated;
    }

    public static Object validate(PrimitiveTypes pt, Object value)
        throws ValidationException
    {
        Object validated = null;
        int type = pt.getPrimitiveType();
        switch(type)
        {
        case 7: // '\007'
            validated = new Boolean(validateBoolean(value));
            break;

        case 0: // '\0'
            int i = validateInt(value);
            validated = new Integer(i);
            break;

        case 4: // '\004'
            byte b = validateByte(value);
            validated = new Byte(b);
            break;

        case 1: // '\001'
            long l = validateLong(value);
            validated = new Long(l);
            break;

        case 2: // '\002'
            float f = validateFloat(value);
            validated = new Float(f);
            break;

        case 3: // '\003'
            double d = validateDouble(value);
            validated = new Double(d);
            break;

        case 5: // '\005'
            Date dt = validateDate(value);
            validated = dt;
            break;

        case 6: // '\006'
            validated = value;
            break;
        }
        return validated;
    }

    public static boolean validateBoolean(Object value)
        throws DataTypeValidationException
    {
        boolean validated = isBoolean(value);
        if(!validated)
            throw new DataTypeValidationException("Boolean", value);
        else
            return validated;
    }

    public static long validateLong(Object value)
        throws DataTypeValidationException
    {
        try
        {
            long l = (long)parseDouble(value);
            return l;
        }
        catch(Exception ex)
        {
            throw new DataTypeValidationException("Long", value);
        }
    }

    public static byte validateByte(Object value)
        throws DataTypeValidationException
    {
        try
        {
            byte byte0 = (byte)(int)parseDouble(value);
            return byte0;
        }
        catch(Exception ex)
        {
            throw new DataTypeValidationException("Byte", value);
        }
    }

    public static int validateInt(Object value)
        throws DataTypeValidationException
    {
        try
        {
            int i = (int)parseDouble(value);
            return i;
        }
        catch(Exception ex)
        {
            throw new DataTypeValidationException("Integer", value);
        }
    }

    public static double validateDouble(Object value)
        throws DataTypeValidationException
    {
        try
        {
            double d = parseDouble(value);
            return d;
        }
        catch(Exception ex)
        {
            throw new DataTypeValidationException("Double", value);
        }
    }

    public static float validateFloat(Object value)
        throws DataTypeValidationException
    {
        try
        {
            float f = (float)parseDouble(value);
            return f;
        }
        catch(Exception ex)
        {
            throw new DataTypeValidationException("Float", value);
        }
    }

    public static Date validateDate(Object value)
        throws DataTypeValidationException
    {
        if(value == null || (value instanceof Date))
            return (Date)value;
        if(value instanceof Long)
            return new Date(((Long)value).longValue());
        try
        {
            Date date = DateFormat.parseDate(String.valueOf(value));
            return date;
        }
        catch(ParseException ex) { }
        try
        {
            Date date1 = new Date(validateLong(value));
            return date1;
        }
        catch(DataTypeValidationException datatypevalidationexception)
        {
            throw new DataTypeValidationException("Date", value);
        }
    }

    public static boolean isBoolean(Object value)
    {
        if(value instanceof Boolean)
            return true;
        if(value instanceof Number)
        {
            int v = ((Number)value).intValue();
            return v == 1 || v == 0;
        } else
        {
            return isBoolean(String.valueOf(value));
        }
    }

    private static boolean isBoolean(String value)
    {
        if(Strings.isEmpty(value))
            return false;
        value = value.trim();
        return value.equalsIgnoreCase("t") || value.equalsIgnoreCase("f") || value.equalsIgnoreCase("0") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
    }

    public static byte getByte(Object val)
    {
        return (byte)(int)getDouble(val);
    }

    public static int getInt(Object val)
    {
        return (int)getDouble(val);
    }

    public static long getLong(Object val)
    {
        return (long)getDouble(val);
    }

    public static float getFloat(Object val)
    {
        return (float)getDouble(val);
    }

    public static double getDouble(Object val)
    {
        try
        {
            double d = parseDouble(val);
            return d;
        }
        catch(Exception ex)
        {
            double d1 = 0.0D;
            return d1;
        }
    }

    public static boolean getBoolean(Object val)
    {
        boolean bool = false;
        if(val == null)
            return false;
        if(val instanceof Boolean)
            return ((Boolean)val).booleanValue();
        if(val instanceof Number)
        {
            int v = ((Number)val).intValue();
            return v == 1;
        }
        String str = String.valueOf(val).trim();
        if(Strings.isNotEmpty(str) && (str.equalsIgnoreCase("t") || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("1") || str.equalsIgnoreCase("on")))
            return true;
        else
            return bool;
    }

    public static Date getDate(Object val)
    {
        try
        {
            Date date = validateDate(val);
            return date;
        }
        catch(DataTypeValidationException ex)
        {
            Date date1 = null;
            return date1;
        }
    }

    private static double parseDouble(Object value)
    {
        if(value instanceof Number)
            return ((Number)value).doubleValue();
        if(value instanceof Date)
            return (double)((Date)value).getTime();
        else
            return Double.parseDouble(String.valueOf(value));
    }
}
