// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataSourceException.java

package com.sbs.ds;

import com.sbs.utils.SbsException;

public class DataSourceException extends SbsException
{

    public DataSourceException()
    {
    }

    public DataSourceException(String msg)
    {
        super(msg);
    }

    public DataSourceException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public DataSourceException(Throwable t)
    {
        super(t);
    }
}
