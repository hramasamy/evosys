// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ListType.java

package com.sbs.espace;


// Referenced classes of package com.sbs.espace:
//            SimpleDataType, ISimpleDataType

public class ListType extends SimpleDataType
{

    public ListType()
    {
    }

    public ListType(String name)
    {
        super(name);
    }

    public ListType(String name, ISimpleDataType base)
    {
        super(name, base);
    }

    public boolean isList()
    {
        return true;
    }
}
