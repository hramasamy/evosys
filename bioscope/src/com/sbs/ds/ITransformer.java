// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ITransformer.java

package com.sbs.ds;

import com.sbs.espace.IDataType;
import com.sbs.espace.IEntity;
import java.io.InputStream;

public interface ITransformer
{

    public abstract IDataType getTransformation();

    public abstract void setTransformation(IDataType idatatype);

    public abstract IEntity transform(IEntity ientity)
        throws Exception;

    public abstract IEntity transform(Object obj)
        throws Exception;

    public abstract IEntity transform(InputStream inputstream)
        throws Exception;

    public abstract void setParent(ITransformer itransformer);

    public abstract ITransformer getParent();
}
