// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ValidationResult.java

package com.sbs.espace;


public class ValidationResult
{

    public ValidationResult(Object result)
    {
        success = true;
        validObject = result;
    }

    public ValidationResult(String msg)
    {
        success = true;
        failureCause = msg;
    }

    public boolean isValid()
    {
        return success;
    }

    public String getFailureCause()
    {
        return failureCause;
    }

    private boolean success;
    private Object validObject;
    private String failureCause;
}
