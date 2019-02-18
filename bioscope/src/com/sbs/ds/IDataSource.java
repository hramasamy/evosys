// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataSource.java

package com.sbs.ds;

import com.sbs.espace.IDataType;
import com.sbs.espace.IEntity;
import java.io.InputStream;
import java.io.OutputStream;

// Referenced classes of package com.sbs.ds:
//            DataSourceException, IDSSpec, ITransformer, IExpression

public interface IDataSource
{

    public abstract IDataType produces();

    public abstract IDSSpec getSpec();

    public abstract void setSpec(IDSSpec idsspec);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract String getQName();

    public abstract void setTransformer(ITransformer itransformer);

    public abstract IEntity query()
        throws DataSourceException;

    public abstract IEntity query(IExpression iexpression)
        throws DataSourceException;

    public abstract IEntity query(IExpression iexpression, IDataType idatatype);

    public abstract IEntity query(IExpression iexpression, ITransformer itransformer);

    public abstract IEntity save(IEntity ientity);

    public abstract IEntity save(IEntity ientity, IExpression iexpression);

    public abstract Object execute(IExpression iexpression);

    public abstract Object execute(IExpression iexpression, IDataType idatatype);

    public abstract InputStream getInputStream()
        throws DataSourceException;

    public abstract OutputStream getOutputStream()
        throws DataSourceException;

    public abstract byte[] getContent()
        throws DataSourceException;
}
