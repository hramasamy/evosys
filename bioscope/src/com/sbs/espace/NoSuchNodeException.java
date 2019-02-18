// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NoSuchNodeException.java

package com.sbs.espace;

import com.sbs.utils.SbsException;

public class NoSuchNodeException extends SbsException
{

    public NoSuchNodeException()
    {
    }

    public NoSuchNodeException(String msg)
    {
        super(msg);
    }

    public NoSuchNodeException(Throwable t)
    {
        super(t);
    }

    public NoSuchNodeException(String msg, Throwable t)
    {
        super(msg, t);
    }
}
