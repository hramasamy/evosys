// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IAttribute.java

package com.sbs.espace;


// Referenced classes of package com.sbs.espace:
//            Attribute, IDataType, INamespace

public interface IAttribute
{

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getQName();

    public abstract IDataType getDataType();

    public abstract void setType(IDataType idatatype);

    public abstract void setNamespace(INamespace inamespace);

    public abstract INamespace getNamespace();

    public abstract Object getDefaultValue();

    public abstract void setDefaultValue(Object obj);

    public static final IAttribute NAME_ATTRIBUTE = new Attribute("Name");
    public static final String DEF_ATT_NAME = "";

}
