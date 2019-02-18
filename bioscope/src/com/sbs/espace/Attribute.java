// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Attribute.java

package com.sbs.espace;

import com.sbs.utils.IPersistable;
import com.sbs.utils.string.Strings;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.sbs.espace:
//            INamespace, IAttribute, IDataType

public class Attribute
    implements IAttribute, IPersistable
{

    public Attribute()
    {
        this("");
    }

    public Attribute(String name)
    {
        this(name, IDataType.DEF_DATA_TYPE);
    }

    public Attribute(String name, IDataType dt)
    {
        namespace = INamespace.DEF_NAMESPACE;
        setName(name);
        setType(dt);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getQName()
    {
        if(namespace == null);
        return getName();
    }

    public IDataType getDataType()
    {
        return dt;
    }

    public void setType(IDataType type)
    {
        dt = type;
    }

    public Object getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(Object value)
    {
        defaultValue = value;
    }

    public INamespace getNamespace()
    {
        return namespace;
    }

    public void setNamespace(INamespace ns)
    {
        namespace = ns;
    }

    public Map backup()
    {
        Map myProperties = new HashMap();
        myProperties.put("class", getClass().getName());
        myProperties.put("name", getName());
        myProperties.put("namespace", getNamespace().backup());
        if(getDefaultValue() != null)
            myProperties.put("defaultValue", getDefaultValue());
        if(getDataType() != null)
            myProperties.put("dataType", getDataType().backup());
        return myProperties;
    }

    public void restore(Map properties)
    {
        if(properties != null)
        {
            String name = (String)properties.get("name");
            if(Strings.isNotEmpty(name))
                setName(name);
            Map nspace = (Map)properties.get("namespace");
            INamespace inamespace;
            if(nspace != null)
                try
                {
                    inamespace = (INamespace)Class.forName((String)nspace.get("class")).newInstance();
                }
                catch(Exception exception) { }
        }
    }

    private String name;
    private IDataType dt;
    private INamespace namespace;
    private Object defaultValue;
}
