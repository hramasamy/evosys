// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultDataSource.java

package com.sbs.ds;

import com.sbs.espace.*;
import java.io.InputStream;
import java.io.OutputStream;

// Referenced classes of package com.sbs.ds:
//            IDataSource, DataSourceException, ITransformer, IDSSpec, 
//            IExpression

public class DefaultDataSource extends Entity
    implements IDataSource
{

    public DefaultDataSource()
    {
    }

    public DefaultDataSource(ITransformer transformer)
    {
        setTransformer(transformer);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getQName()
    {
        return name;
    }

    public void setTransformer(ITransformer transformer)
    {
        this.transformer = transformer;
    }

    public IDataType produces()
    {
        if(transformer == null)
            return DEF_DATA_TYPE;
        else
            return transformer.getTransformation();
    }

    public IDSSpec getSpec()
    {
        return spec;
    }

    public void setSpec(IDSSpec spec)
    {
        this.spec = spec;
    }

    public IEntity query()
        throws DataSourceException
    {
        throw new UnsupportedOperationException("Method query() not yet implemented.");
    }

    public IEntity query(IExpression query)
        throws DataSourceException
    {
        throw new UnsupportedOperationException("Method query() not yet implemented.");
    }

    public IEntity query(IExpression query, IDataType structure)
    {
        throw new UnsupportedOperationException("Method query() not yet implemented.");
    }

    public IEntity query(IExpression query, ITransformer transformer)
    {
        throw new UnsupportedOperationException("Method query() not yet implemented.");
    }

    public IEntity save(IEntity entity)
    {
        throw new UnsupportedOperationException("Method save() not yet implemented.");
    }

    public IEntity save(IEntity entity, IExpression exp)
    {
        throw new UnsupportedOperationException("Method save() not yet implemented.");
    }

    public Object execute(IExpression exp)
    {
        throw new UnsupportedOperationException("Method execute() not yet implemented.");
    }

    public Object execute(IExpression exp, IDataType structure)
    {
        throw new UnsupportedOperationException("Method execute() not yet implemented.");
    }

    public InputStream getInputStream()
        throws DataSourceException
    {
        return null;
    }

    public OutputStream getOutputStream()
        throws DataSourceException
    {
        return null;
    }

    public byte[] getContent()
        throws DataSourceException
    {
        return null;
    }

    protected ITransformer getTransformer()
    {
        return transformer;
    }

    public static final IDataType DEF_DATA_TYPE;
    private String name;
    private IDSSpec spec;
    private ITransformer transformer;

    static 
    {
        DEF_DATA_TYPE = new ArrayType(ISimpleDataType.byteType);
    }
}
