// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IComplexDataType.java

package com.sbs.espace;

import java.util.Iterator;

// Referenced classes of package com.sbs.espace:
//            IDataType, ValidationException, IAttribute

public interface IComplexDataType
    extends IDataType
{

    public abstract IAttribute setAttribute(IAttribute iattribute);

    public abstract IAttribute getAttribute(String s);

    public abstract void addAttribute(IAttribute iattribute);

    public abstract IAttribute removeAttribute(String s);

    public abstract String[] getAttributeNames();

    public abstract Iterator attributes();

    public abstract int hasAttribute(String s);

    public abstract void renameAttribute(String s, String s1);

    public abstract void insertAttribute(String s, IAttribute iattribute, int i);

    public abstract void insertAttribute(int i, IAttribute iattribute);

    public abstract int getAttributeCount();

    public abstract void addChild(IDataType idatatype);

    public abstract IDataType getChild(String s);

    public abstract IDataType removeChild(String s);

    public abstract boolean isRoot();

    public abstract boolean isLeaf();

    public abstract int getChildCount();

    public abstract Iterator children();

    public abstract String[] getChildNames();

    public abstract boolean hasChild(String s);

    public abstract Object validate(String s, Object obj)
        throws ValidationException;

    public abstract boolean validate()
        throws ValidationException;

    public abstract Object[] validate(String as[], Object aobj[])
        throws ValidationException;

    public abstract Object[] validate(Object aobj[])
        throws ValidationException;
}
