// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuthenticationException.java

package com.sbs.utils;


// Referenced classes of package com.sbs.utils:
//            SbsException

public class AuthenticationException extends SbsException
{

    public AuthenticationException()
    {
    }

    public AuthenticationException(String msg)
    {
        super(msg);
    }

    public AuthenticationException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public AuthenticationException(Throwable t)
    {
        super(t);
    }
}
