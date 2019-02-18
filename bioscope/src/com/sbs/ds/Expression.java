// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Expression.java

package com.sbs.ds;


// Referenced classes of package com.sbs.ds:
//            IExpression

public class Expression
    implements IExpression
{

    public Expression()
    {
    }

    public Expression(String exp)
    {
        setExpression(exp);
    }

    public void setExpression(String exp)
    {
        this.exp = exp;
    }

    public String toString()
    {
        return exp;
    }

    private String exp;
}
