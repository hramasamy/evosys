// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleDataType.java

package com.sbs.espace;

import com.sbs.utils.AppLogger;
import java.util.*;

// Referenced classes of package com.sbs.espace:
//            DataType, ISimpleDataType, IFacet, IAttribute, 
//            ValidationException, IDataType

public class SimpleDataType extends DataType
    implements ISimpleDataType
{

    public SimpleDataType()
    {
        facets = new ArrayList();
    }

    public SimpleDataType(String name)
    {
        facets = new ArrayList();
        setName(name);
    }

    public SimpleDataType(String name, IDataType base)
    {
        facets = new ArrayList();
        setName(name);
        setBaseType(base);
    }

    public SimpleDataType(boolean primitive)
    {
        facets = new ArrayList();
        this.primitive = primitive;
    }

    public int getType()
    {
        return 0;
    }

    public boolean isPrimitive()
    {
        if(getBaseType() != null)
            return getBaseType().isPrimitive();
        else
            return primitive;
    }

    public void setBaseType(IDataType type)
    {
        if(!(type instanceof ISimpleDataType))
        {
            String errMsg = "Expected ISimpleDataType found: ".concat(String.valueOf(String.valueOf(type.getClass().getName())));
            logger.error(errMsg);
            throw new IllegalArgumentException(errMsg);
        } else
        {
            super.setBaseType(type);
            return;
        }
    }

    public void addFacet(IFacet facet)
    {
        facets.add(facet);
    }

    public Iterator facets()
    {
        return facets.iterator();
    }

    public boolean isList()
    {
        return false;
    }

    public boolean isUnion()
    {
        return false;
    }

    public boolean isArray()
    {
        return false;
    }

    public Object validate(Object val)
        throws ValidationException
    {
        Object ret = val;
        if(getBaseType() != null)
            ret = getBaseType().validate(val);
        for(Iterator itr = facets.iterator(); itr.hasNext(); ((IFacet)itr.next()).validate(val));
        return ret;
    }

    public int compare(Object v1, Object v2)
    {
        if(v1 instanceof IAttribute)
            if(!(v2 instanceof IAttribute));
        return -1;
    }

    public StringBuffer toXML()
    {
        return null;
    }

    public StringBuffer toXMLSchema()
    {
        return null;
    }

    public Map backup()
    {
        Map props = new HashMap();
        return props;
    }

    public void restore(Map map)
    {
    }

    protected static AppLogger logger;
    private boolean primitive;
    private List facets;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.espace.SimpleDataType.class);
    }
}
