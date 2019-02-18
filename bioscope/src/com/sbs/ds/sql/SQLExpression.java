// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SQLExpression.java

package com.sbs.ds.sql;

import com.sbs.ds.Expression;
import com.sbs.espace.IDataType;
import com.sbs.espace.IEntity;

public class SQLExpression extends Expression
{

    public SQLExpression()
    {
    }

    public SQLExpression(String exp)
    {
        super(exp);
    }

    public SQLExpression(IDataType dt)
    {
        this(dt, null);
    }

    public SQLExpression(IDataType dt, IEntity e)
    {
        generateExpression(dt, e);
    }

    private void generateExpression(IDataType idatatype, IEntity ientity)
    {
    }

    private StringBuffer queryClause;
    private StringBuffer fromClause;
    private StringBuffer whereClause;
    private StringBuffer endClause;
}
