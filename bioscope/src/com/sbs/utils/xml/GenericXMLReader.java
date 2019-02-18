// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenericXMLReader.java

package com.sbs.utils.xml;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class GenericXMLReader
{

    public GenericXMLReader()
    {
    }

    public static XMLReader getXMLReader()
        throws SAXException
    {
        XMLReader xmlreader1;
        try
        {
            XMLReader reader = XMLReaderFactory.createXMLReader();
            XMLReader xmlreader = reader;
            return xmlreader;
        }
        catch(SAXException ex)
        {
            xmlreader1 = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        }
        return xmlreader1;
    }

    static final String saxDriverClass = "org.apache.xerces.parsers.SAXParser";
}
