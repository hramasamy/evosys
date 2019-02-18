// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDefaultValue.java

package com.sbs.espace;

import com.sbs.utils.IPersistable;

public interface IDefaultValue
    extends IPersistable
{

    public abstract Object getValue();

    public abstract void setValue(Object obj);
}
