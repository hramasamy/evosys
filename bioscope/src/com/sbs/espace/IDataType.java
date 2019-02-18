// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataType.java

package com.sbs.espace;

import com.sbs.utils.IPersistable;

// Referenced classes of package com.sbs.espace:
//            ValidationException, ISimpleDataType, INamespace

public interface IDataType
    extends IPersistable
{

    public abstract Object getIcon();

    public abstract void setIcon(Object obj);

    public abstract int getType();

    public abstract INamespace getNamespace();

    public abstract void setNamespace(INamespace inamespace);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract IDataType getBaseType();

    public abstract void setBaseType(IDataType idatatype);

    public abstract int compare(Object obj, Object obj1);

    public abstract Object validate(Object obj)
        throws ValidationException;

    public abstract IDataType getParent();

    public abstract void setParent(IDataType idatatype);

    public abstract boolean isArray();

    public abstract StringBuffer toXML();

    public abstract StringBuffer toXMLSchema();

    public abstract boolean isPrimitive();

    public static final int SIMPLE_TYPE = 0;
    public static final int COMPLEX_TYPE = 1;
    public static final IDataType DEF_DATA_TYPE = ISimpleDataType.stringType;

}
