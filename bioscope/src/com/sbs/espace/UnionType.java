// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnionType.java

package com.sbs.espace;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.sbs.espace:
//            SimpleDataType, ISimpleDataType

public class UnionType extends SimpleDataType
{

    public UnionType()
    {
        union = new ArrayList();
    }

    public void addType(ISimpleDataType dt)
    {
        union.add(dt);
    }

    public boolean isUnion()
    {
        return true;
    }

    private List union;
}
