// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DocumentBuilderPool.java

package com.sbs.utils.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DocumentBuilderPool
{

    public DocumentBuilderPool()
    {
        this(false);
    }

    public DocumentBuilderPool(boolean validating)
    {
        _documentBuilderFactory = DocumentBuilderFactory.newInstance();
        _documentBuilderFactory.setValidating(validating);
    }

    protected void expire(Object obj)
    {
    }

    protected boolean validate(Object o)
    {
        return true;
    }

    public static DocumentBuilder create()
        throws Exception
    {
        return create(false);
    }

    public static DocumentBuilder createNSAware()
        throws Exception
    {
        return createNSAware(false);
    }

    public static DocumentBuilder create(boolean validating)
        throws Exception
    {
        if(validating)
            return _validatingDocBuilderFactory.newDocumentBuilder();
        else
            return _documentBuilderFactory.newDocumentBuilder();
    }

    public static DocumentBuilder createNSAware(boolean validating)
        throws Exception
    {
        if(validating)
            return _nsAwareValidatingDocBuilderFactory.newDocumentBuilder();
        else
            return _nsAwareDocBuilderFactory.newDocumentBuilder();
    }

    static DocumentBuilderFactory _documentBuilderFactory = DocumentBuilderFactory.newInstance();
    static DocumentBuilderFactory _nsAwareDocBuilderFactory;
    static DocumentBuilderFactory _validatingDocBuilderFactory;
    static DocumentBuilderFactory _nsAwareValidatingDocBuilderFactory;

    static 
    {
        _nsAwareDocBuilderFactory = DocumentBuilderFactory.newInstance();
        _nsAwareDocBuilderFactory.setNamespaceAware(true);
        _validatingDocBuilderFactory = DocumentBuilderFactory.newInstance();
        _validatingDocBuilderFactory.setValidating(true);
        _nsAwareValidatingDocBuilderFactory = DocumentBuilderFactory.newInstance();
        _nsAwareValidatingDocBuilderFactory.setValidating(true);
        _nsAwareValidatingDocBuilderFactory.setNamespaceAware(true);
    }
}
