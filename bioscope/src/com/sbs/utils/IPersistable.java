// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IPersistable.java

package com.sbs.utils;

import java.util.Map;

public interface IPersistable
{

    public abstract Map backup();

    public abstract void restore(Map map);
}
