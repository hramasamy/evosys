// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Xml2EntityTransformer.java

package com.sbs.ds;

import com.sbs.espace.*;
import com.sbs.utils.AppLogger;
import com.sbs.utils.xml.GenericDOMParser;
import com.sbs.utils.xml.XMLParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import org.w3c.dom.Document;

// Referenced classes of package com.sbs.ds:
//            DefaultTransformer, IDataSource

public class Xml2EntityTransformer extends DefaultTransformer
{

    public Xml2EntityTransformer()
    {
    }

    public Xml2EntityTransformer(IDataSource ds)
    {
        setDataSource(ds);
    }

    public IEntity transform(IEntity e)
    {
        IEntity entity = null;
        IDataType dt = getTransformation();
        if(dt.getType() == 1)
            return transform(e, (IComplexDataType)dt);
        else
            return entity;
    }

    public void setDataSource(IDataSource ds)
    {
        this.ds = ds;
    }

    protected IEntity transform(IEntity e, IComplexDataType dt)
    {
        IEntity res = null;
        IDataType srcDT = e.getDataType();
        if(srcDT.getType() == 0)
        {
            Object value = e.getAttributeValue(srcDT.getName());
            StringBuffer stringbuffer;
            try
            {
                stringbuffer = transform(((InputStream) (new ByteArrayInputStream(String.valueOf(value).getBytes()))), dt);
            }
            catch(Exception ex)
            {
                logger.warn(String.valueOf(String.valueOf((new StringBuffer("Failed to transform entity <entityName> <DataType>.")).append(e.getName()).append(", ").append(dt.getName()))), ex);
            }
        }
        return res;
    }

    protected StringBuffer transform(InputStream io, IComplexDataType cdt)
        throws Exception
    {
        Iterator itr = cdt.attributes();
        GenericDOMParser parser = XMLParser.getDOMParser();
        Document doc = parser.parse(io);
        return null;
    }

    private static AppLogger logger;
    private IDataSource ds;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.ds.Xml2EntityTransformer.class);
    }
}
