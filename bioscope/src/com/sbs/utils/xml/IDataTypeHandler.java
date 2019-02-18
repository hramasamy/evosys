// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDataTypeHandler.java

package com.sbs.utils.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public interface IDataTypeHandler
{

    public abstract boolean canHandle(String s);

    public abstract Node toNode(Document document, Object obj, String s)
        throws Exception;

    public abstract Node toNode(Document document, Object obj)
        throws Exception;

    public abstract Object fromNode(Node node, Object obj, String s)
        throws Exception;
}
