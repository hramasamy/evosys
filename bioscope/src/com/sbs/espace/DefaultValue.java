// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultValue.java

package com.sbs.espace;

import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.sbs.espace:
//            IDefaultValue

public class DefaultValue
    implements IDefaultValue
{

    public DefaultValue()
    {
    }

    public DefaultValue(Object value)
    {
        this.value = value;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public Map backup()
    {
        Map myProperties = new HashMap();
        myProperties.put("class", getClass().getName());
        myProperties.put("value", value);
        return myProperties;
    }

    public void restore(Map map)
    {
    }

    private Object value;
}
