// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDatabaseObject.java

package com.sbs.db;

import java.util.Date;

public interface IDatabaseObject
{

    public abstract int getDBObjectType();

    public abstract String getName();

    public abstract String getOwner();

    public abstract Date getCreationDate();
}
