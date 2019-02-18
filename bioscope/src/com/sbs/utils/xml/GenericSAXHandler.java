// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenericSAXHandler.java

package com.sbs.utils.xml;

import java.io.PrintStream;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GenericSAXHandler extends DefaultHandler
{

    public GenericSAXHandler()
    {
    }

    public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attr)
        throws SAXException
    {
        System.out.println("StartElement: ".concat(String.valueOf(String.valueOf(qualifiedName))));
    }

    public void endElement(String namespaceURI, String localName, String qualifiedName)
        throws SAXException
    {
        System.out.println("EndElement: ".concat(String.valueOf(String.valueOf(qualifiedName))));
    }
}
