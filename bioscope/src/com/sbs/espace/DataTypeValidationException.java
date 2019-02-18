// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataTypeValidationException.java

package com.sbs.espace;

import com.sbs.utils.string.Strings;

// Referenced classes of package com.sbs.espace:
//            ValidationException, IDataType

public class DataTypeValidationException extends ValidationException
{

    public DataTypeValidationException()
    {
    }

    public DataTypeValidationException(Class expected, Object found)
    {
        this(expected.getName(), found);
    }

    public DataTypeValidationException(String expClName, Object found)
    {
        this(null, expClName, found);
    }

    public DataTypeValidationException(String msg, String expClName, Object found)
    {
        super(msg);
        expected = expClName;
        this.found = found;
    }

    public DataTypeValidationException(IDataType dt, Object found)
    {
        this(dt.getName(), found);
    }

    public DataTypeValidationException(String msg, IDataType dt, Object found)
    {
        this(msg, dt.getName(), found);
    }

    public String getMessage()
    {
        if(Strings.isEmpty(super.getMessage()))
            return String.valueOf(String.valueOf((new StringBuffer("Expected: ")).append(expected).append(", found: ").append(found)));
        else
            return super.getMessage();
    }

    private String expected;
    private Object found;
}
