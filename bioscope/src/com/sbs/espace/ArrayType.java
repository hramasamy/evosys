// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArrayType.java

package com.sbs.espace;

import java.util.Map;

// Referenced classes of package com.sbs.espace:
//            DataType, IDataType

public class ArrayType extends DataType
{

    public ArrayType()
    {
    }

    public ArrayType(IDataType base)
    {
        setBaseType(base);
    }

    public int compare(Object target, Object source)
    {
        throw new UnsupportedOperationException("Method compare() not yet implemented.");
    }

    public int getType()
    {
        if(getBaseType() != null)
            return getBaseType().getType();
        else
            return 0;
    }

    public boolean isPrimitive()
    {
        if(getBaseType() != null)
            return getBaseType().isPrimitive();
        else
            return true;
    }

    public Map backup()
    {
        return null;
    }

    public void restore(Map map)
    {
    }
}
