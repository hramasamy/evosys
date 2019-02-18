// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PatternValidationException.java

package com.sbs.espace;

import com.sbs.utils.string.Strings;

// Referenced classes of package com.sbs.espace:
//            FacetValidationException

public class PatternValidationException extends FacetValidationException
{

    public PatternValidationException()
    {
    }

    public PatternValidationException(String msg)
    {
        super(msg);
    }

    public PatternValidationException(String pattern, String value)
    {
        this.pattern = pattern;
        found = value;
    }

    public PatternValidationException(String pattern, Object value)
    {
        this.pattern = pattern;
        found = value;
    }

    public String getMessage()
    {
        String msg = super.getMessage();
        if(Strings.isEmpty(msg))
            return String.valueOf(String.valueOf((new StringBuffer("Unsatisfied pattern: ")).append(pattern).append(", value: ").append(found)));
        else
            return msg;
    }

    private String pattern;
    private Object found;
}
