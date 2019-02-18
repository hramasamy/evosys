// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SbsException.java

package com.sbs.utils;


public class SbsException extends Exception
{

    public SbsException()
    {
    }

    public SbsException(String msg)
    {
        super(msg);
    }

    public SbsException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public SbsException(Throwable t)
    {
        super(t);
    }
}
