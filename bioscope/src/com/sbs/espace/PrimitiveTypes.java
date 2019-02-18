// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleDataType.java

package com.sbs.espace;


// Referenced classes of package com.sbs.espace:
//            SimpleDataType, ValidationException

abstract class PrimitiveTypes extends SimpleDataType
{

    PrimitiveTypes()
    {
    }

    public boolean isPrimitive()
    {
        return true;
    }

    public int compare(Object v1, Object v2)
    {
        try
        {
            int i = ((Comparable)validate(v1)).compareTo(validate(v2));
            return i;
        }
        catch(ValidationException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    abstract int getPrimitiveType();

    public static final int INT_TYPE = 0;
    public static final int LONG_TYPE = 1;
    public static final int FLOAT_TYPE = 2;
    public static final int DOUBLE_TYPE = 3;
    public static final int BYTE_TYPE = 4;
    public static final int DATE_TYPE = 5;
    public static final int STRING_TYPE = 6;
    public static final int BOOLEAN_TYPE = 7;
}
