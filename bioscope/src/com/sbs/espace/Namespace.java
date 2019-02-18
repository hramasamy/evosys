// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Namespace.java

package com.sbs.espace;

import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.sbs.espace:
//            INamespace

public class Namespace
    implements INamespace
{

    public Namespace(String value)
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

    private String value;
}
