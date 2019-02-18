// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FacetValidationException.java

package com.sbs.espace;


// Referenced classes of package com.sbs.espace:
//            ValidationException

public class FacetValidationException extends ValidationException
{

    public FacetValidationException()
    {
    }

    public FacetValidationException(String msg)
    {
        super(msg);
    }

    public FacetValidationException(String msg, Throwable t)
    {
        super(msg, t);
    }

    public FacetValidationException(Throwable t)
    {
        super(t);
    }
}
