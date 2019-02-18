// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ValidationException.java

package com.sbs.espace;

import com.sbs.utils.SbsException;

public class ValidationException extends SbsException
{

    public ValidationException()
    {
    }

    public ValidationException(String msg)
    {
        super(msg);
    }

    public ValidationException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public ValidationException(Throwable t)
    {
        super(t);
    }
}
