// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataTypeHandler.java

package com.sbs.ostore.file;

import com.sbs.ostore.IObjectHandler;

public class DataTypeHandler
    implements IObjectHandler
{

    public DataTypeHandler()
    {
    }

    public void setOStoreLocation(String location)
    {
        rootDir = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(location)))).append("datatypes").append(System.getProperty("file.separator"))));
    }

    public Object loadFromOStore(String name)
    {
        throw new UnsupportedOperationException("Method loadFromOStore() not yet implemented.");
    }

    public void saveToOStore(Object obj)
    {
        throw new UnsupportedOperationException("Method saveToOStore() not yet implemented.");
    }

    public void saveAsToOStore(String name, Object obj)
    {
        throw new UnsupportedOperationException("Method saveAsToOStore() not yet implemented.");
    }

    public void remove(String name)
    {
        throw new UnsupportedOperationException("Method remove() not yet implemented.");
    }

    private String rootDir;
}
