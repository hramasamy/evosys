// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultXMLFilter.java

package com.sbs.utils.xml;

import com.megginson.sax.XMLWriter;
import java.io.IOException;
import org.xml.sax.*;
import org.xml.sax.helpers.XMLFilterImpl;

public class DefaultXMLFilter
    implements XMLFilter
{

    public DefaultXMLFilter()
    {
        writer = new XMLWriter();
    }

    public void setParent(XMLReader parent)
    {
        writer.setParent(parent);
    }

    public XMLReader getParent()
    {
        return writer.getParent();
    }

    public boolean getFeature(String f)
        throws SAXNotSupportedException, SAXNotRecognizedException
    {
        return writer.getFeature(f);
    }

    public void setFeature(String f, boolean flag)
        throws SAXNotSupportedException, SAXNotRecognizedException
    {
        writer.setFeature(f, flag);
    }

    public Object getProperty(String p)
        throws SAXNotSupportedException, SAXNotRecognizedException
    {
        return writer.getProperty(p);
    }

    public void setProperty(String p, Object v)
        throws SAXNotSupportedException, SAXNotRecognizedException
    {
        writer.setProperty(p, v);
    }

    public void setEntityResolver(EntityResolver er)
    {
        writer.setEntityResolver(er);
    }

    public EntityResolver getEntityResolver()
    {
        return writer.getEntityResolver();
    }

    public void setDTDHandler(DTDHandler dh)
    {
        writer.setDTDHandler(dh);
    }

    public DTDHandler getDTDHandler()
    {
        return writer.getDTDHandler();
    }

    public void setContentHandler(ContentHandler ch)
    {
        writer.setContentHandler(ch);
    }

    public ContentHandler getContentHandler()
    {
        return writer.getContentHandler();
    }

    public void setErrorHandler(ErrorHandler eh)
    {
        setErrorHandler(eh);
    }

    public ErrorHandler getErrorHandler()
    {
        return writer.getErrorHandler();
    }

    public void parse(InputSource is)
        throws SAXException, IOException
    {
        writer.parse(is);
    }

    public void parse(String s)
        throws SAXException, IOException
    {
        writer.parse(s);
    }

    private XMLWriter writer;
}
