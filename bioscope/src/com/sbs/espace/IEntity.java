// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IEntity.java

package com.sbs.espace;

import java.util.Iterator;
import java.util.List;

// Referenced classes of package com.sbs.espace:
//            NoSuchNodeException, ValidationException, INamespace, IDataType

public interface IEntity
{

    public abstract int getContentType();

    public abstract void setValidation(boolean flag);

    public abstract boolean isValidating();

    public abstract INamespace getNamespace();

    public abstract void setDataType(IDataType idatatype);

    public abstract String getEntityType();

    public abstract IDataType getDataType();

    public abstract String getDataTypeName();

    public abstract String getName();

    public abstract void setName(String s);

    public abstract boolean isRoot();

    public abstract boolean isLeaf();

    public abstract void addChildNode(IEntity ientity)
        throws NoSuchNodeException;

    public abstract boolean removeChildNode(IEntity ientity);

    public abstract IEntity removeChildNode(String s, String as[], Object aobj[]);

    public abstract List getChildNodes(String s)
        throws NoSuchNodeException;

    public abstract Iterator getChildNodeIterator(String s)
        throws NoSuchNodeException;

    public abstract int getChildNodeCount(String s)
        throws NoSuchNodeException;

    public abstract String[] getChildrenNames();

    public abstract Object getNodeValue();

    public abstract void setNodeValue(Object obj);

    public abstract Iterator attributes();

    public abstract Object getAttributeValue(String s);

    public abstract Object setAttributeValue(String s, Object obj)
        throws ValidationException;

    public abstract void setAttributeValues(Object aobj[])
        throws ValidationException;

    public abstract void setAttributeValues(String as[], Object aobj[])
        throws ValidationException;

    public abstract Object[] getAttributeValues();

    public abstract boolean hasAttribute(String s);

    public abstract boolean hasChildNode(String s);

    public abstract Object[] select(String s)
        throws NoSuchNodeException;

    public abstract IEntity clone(boolean flag);

    public static final int SIMPLE_CONTENT = 0;
    public static final int TABULAR_CONTENT = 1;

}
