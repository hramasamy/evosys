// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataType.java

package com.sbs.espace;

import com.sbs.utils.AppLogger;
import java.util.Map;

// Referenced classes of package com.sbs.espace:
//            IDataType, ValidationException, INamespace

public abstract class DataType
    implements IDataType
{

    public DataType()
    {
    }

    public DataType(String name)
    {
        setName(name);
    }

    public DataType(String name, IDataType base)
    {
        setName(name);
        setBaseType(base);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Object getIcon()
    {
        return icon;
    }

    public void setIcon(Object icon)
    {
        this.icon = icon;
    }

    public boolean isPrimitive()
    {
        return false;
    }

    public IDataType getBaseType()
    {
        return base;
    }

    public void setBaseType(IDataType type)
    {
        base = type;
    }

    public Object validate(Object value)
        throws ValidationException
    {
        return null;
    }

    public INamespace getNamespace()
    {
        return namespace;
    }

    public void setNamespace(INamespace namespace)
    {
        this.namespace = namespace;
    }

    public IDataType getParent()
    {
        return parent;
    }

    public void setParent(IDataType dt)
    {
        parent = dt;
    }

    public boolean isArray()
    {
        return false;
    }

    public StringBuffer toXML()
    {
        return null;
    }

    public StringBuffer toXMLSchema()
    {
        return null;
    }

    public abstract int compare(Object obj, Object obj1);

    public abstract int getType();

    public abstract void restore(Map map);

    public abstract Map backup();

    protected static AppLogger logger;
    private String name;
    private IDataType base;
    private INamespace namespace;
    private IDataType parent;
    private Object icon;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.espace.DataType.class);
    }
}
