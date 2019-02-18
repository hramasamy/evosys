// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IObjectHandler.java

package com.sbs.ostore;


public interface IObjectHandler
{

    public abstract void setOStoreLocation(String s);

    public abstract Object loadFromOStore(String s);

    public abstract void saveToOStore(Object obj);

    public abstract void saveAsToOStore(String s, Object obj);

    public abstract void remove(String s);
}
