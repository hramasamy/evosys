// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultTransformer.java

package com.sbs.ds;

import com.sbs.espace.IDataType;
import com.sbs.espace.IEntity;
import java.io.InputStream;

// Referenced classes of package com.sbs.ds:
//            ITransformer

public class DefaultTransformer
    implements ITransformer
{

    public DefaultTransformer()
    {
    }

    public DefaultTransformer(IDataType dt)
    {
        setTransformation(dt);
    }

    public DefaultTransformer(ITransformer t)
    {
        setParent(t);
    }

    public IDataType getTransformation()
    {
        throw new UnsupportedOperationException("Method getTransformation() not yet implemented.");
    }

    public void setTransformation(IDataType dt)
    {
        throw new UnsupportedOperationException("Method setTransformation() not yet implemented.");
    }

    public IEntity transform(Object obj)
        throws Exception
    {
        if(obj instanceof IEntity)
            return transform((IEntity)obj);
        else
            return null;
    }

    public IEntity transform(InputStream in)
        throws Exception
    {
        throw new UnsupportedOperationException("Method setTransformation() not yet implemented.");
    }

    public IEntity transform(IEntity e)
        throws Exception
    {
        throw new UnsupportedOperationException("Method transform() not yet implemented.");
    }

    public void setParent(ITransformer t)
    {
        parent = t;
    }

    public ITransformer getParent()
    {
        return parent;
    }

    private IDataType dt;
    private ITransformer parent;
}
