// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenericSAXParser.java

package com.sbs.utils.xml;

import java.io.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

public class GenericSAXParser
{

    public GenericSAXParser()
        throws Exception
    {
        try
        {
            parser = factory.newSAXParser();
        }
        catch(Exception e)
        {
            try
            {
                parser = defaultSAXParser();
            }
            catch(Exception ex)
            {
                throw new Exception("Error while creating parser: ".concat(String.valueOf(String.valueOf(e.getMessage()))), ex);
            }
        }
    }

    private static SAXParser defaultSAXParser()
        throws ParserConfigurationException, SAXException
    {
        return SAXParserFactory.newInstance().newSAXParser();
    }

    public void setFilter(XMLFilter filter)
        throws SAXException
    {
        XMLReader reader = parser.getXMLReader();
        filter.setParent(reader);
        reader = filter;
    }

    public void setValidating(boolean state)
    {
        factory.setValidating(state);
    }

    public void setNamespaceAware(boolean state)
    {
        factory.setNamespaceAware(state);
    }

    public XMLReader getXMLReader()
        throws Exception
    {
        return parser.getXMLReader();
    }

    public Object getProperty(String propertyURI)
        throws Exception
    {
        return parser.getProperty(propertyURI);
    }

    public void setProperty(String propertyURI, Object value)
        throws Exception
    {
        parser.setProperty(propertyURI, value);
    }

    public boolean getFeature(String featureURI)
    {
        try
        {
            boolean flag = factory.getFeature(featureURI);
            return flag;
        }
        catch(Exception exception)
        {
            return false;
        }
    }

    public void setFeature(String featureURI, boolean state)
        throws Exception
    {
        try
        {
            factory.setFeature(featureURI, state);
        }
        catch(SAXNotSupportedException nse)
        {
            throw new Exception("The factory does not support the feature: ".concat(String.valueOf(String.valueOf(featureURI))));
        }
        catch(SAXNotRecognizedException nre)
        {
            throw new Exception("The factory does not recognize the feature: ".concat(String.valueOf(String.valueOf(featureURI))));
        }
        catch(ParserConfigurationException pce)
        {
            throw new Exception("Configuration error: ".concat(String.valueOf(String.valueOf(pce.getMessage()))));
        }
    }

    public void parse(File file)
        throws Exception
    {
        parser.getXMLReader().parse(new InputSource(new FileInputStream(file)));
    }

    public void parse(File file, DefaultHandler handler)
        throws Exception
    {
        parser.parse(file, handler);
    }

    public void parse(String uri, DefaultHandler handler)
        throws Exception
    {
        parser.parse(uri, handler);
    }

    public void parse(InputSource source, DefaultHandler handler)
        throws Exception
    {
        parser.parse(source, handler);
    }

    public void parse(InputStream input, DefaultHandler handler)
        throws Exception
    {
        parser.parse(input, handler);
    }

    public void parse(InputStream input, DefaultHandler handler, String systemID)
        throws Exception
    {
        parser.parse(input, handler, systemID);
    }

    static SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser;

}
