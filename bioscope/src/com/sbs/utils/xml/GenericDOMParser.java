// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenericDOMParser.java

package com.sbs.utils.xml;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ranges.DocumentRange;
import org.w3c.dom.traversal.DocumentTraversal;
import org.xml.sax.*;

// Referenced classes of package com.sbs.utils.xml:
//            BaseEntityResolver

public class GenericDOMParser
{

    public GenericDOMParser()
        throws ParserConfigurationException
    {
        if(factory == null)
            synchronized(com.sbs.utils.xml.GenericDOMParser.class)
            {
                String p = System.getProperty("javax.xml.parsers.DocumentBuilderFactory");
                if(p == null || p.trim().length() == 0)
                    System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
                factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
            }
        builder = factory.newDocumentBuilder();
        builder.setEntityResolver(entityResolver);
    }

    public boolean isNamespaceAware()
    {
        return factory.isNamespaceAware();
    }

    public void setNamespaceAware(boolean state)
    {
        factory.setNamespaceAware(state);
    }

    public boolean isValidating()
    {
        return factory.isValidating();
    }

    public void setValidating(boolean state)
    {
        factory.setValidating(state);
    }

    public boolean isCoalescing()
    {
        return factory.isCoalescing();
    }

    public void setCoalescing(boolean state)
    {
        factory.setCoalescing(state);
    }

    public boolean isExpandEntityReferences()
    {
        return factory.isExpandEntityReferences();
    }

    public void setExpandEntityReferences(boolean state)
    {
        factory.setExpandEntityReferences(state);
    }

    public boolean isIgnoringComments()
    {
        return factory.isIgnoringComments();
    }

    public void setIgnoringComments(boolean state)
    {
        factory.setIgnoringComments(state);
    }

    public boolean isIgnoringElementContentWhitespace()
    {
        return factory.isIgnoringElementContentWhitespace();
    }

    public void setIgnoringElementContentWhitespace(boolean state)
    {
        factory.setIgnoringElementContentWhitespace(state);
    }

    public Object getAttribute(String attribute)
    {
        return factory.getAttribute(attribute);
    }

    public void setAttribute(String attribute, Object value)
    {
        factory.setAttribute(attribute, value);
    }

    public void setEntityResolver(EntityResolver resolver)
    {
        builder.setEntityResolver(resolver);
    }

    public void setErrorHandler(ErrorHandler errorHandler)
    {
        builder.setErrorHandler(errorHandler);
    }

    public Document newDocument()
    {
        return builder.newDocument();
    }

    public DOMImplementation getDOMImplementation()
    {
        return builder.getDOMImplementation();
    }

    public boolean hasFeature(String moduleName, String level)
    {
        return builder.getDOMImplementation().hasFeature(moduleName, level);
    }

    public Document parse(File file)
        throws SAXException, IOException
    {
        System.out.println("parsing: ".concat(String.valueOf(String.valueOf(file.getName()))));
        InputSource input = new InputSource(new FileInputStream(file));
        return parse(input);
    }

    public Document parse(InputSource source)
        throws SAXException, IOException
    {
        return builder.parse(source);
    }

    public Document parse(InputStream input)
        throws SAXException, IOException
    {
        return parse(new InputSource(input));
    }

    public Document parse(Reader reader)
        throws SAXException, IOException
    {
        return parse(new InputSource(reader));
    }

    public Document parse(String uri)
        throws SAXException, IOException
    {
        System.out.println("parsing: ".concat(String.valueOf(String.valueOf(uri))));
        return parse(new InputSource(uri));
    }

    public Document parse(InputStream input, String systemID)
        throws SAXException, IOException
    {
        return builder.parse(input, systemID);
    }

    public DocumentTraversal getDocumentTraversalFor(Document doc)
        throws Exception
    {
        if(hasFeature("Traversal", "2.0"))
        {
            if(doc instanceof DocumentTraversal)
                return (DocumentTraversal)doc;
            else
                throw new Exception("DocumentTraversal implemenation not defined!");
        } else
        {
            throw new Exception("DocumentTraversal feature not supported by the parser!");
        }
    }

    public DocumentRange getDocumentRangeFor(Document doc)
        throws Exception
    {
        if(hasFeature("Range", "2.0"))
        {
            if(doc instanceof DocumentRange)
                return (DocumentRange)doc;
            else
                throw new Exception("DocumentRange implemenation not defined!");
        } else
        {
            throw new Exception("DocumentRange feature not supported by the parser!");
        }
    }

    public static final String XML = "XML";
    public static final String VIEWS = "Views";
    public static final String EVENTS = "Events";
    public static final String CSS = "CSS";
    public static final String TRAVERSAL = "Traversal";
    public static final String RANGE = "Range";
    public static final String HTML = "HTML";
    public static final String DOM_LEVEL_2 = "2.0";
    static DocumentBuilderFactory factory = null;
    static EntityResolver entityResolver = new BaseEntityResolver();
    private DocumentBuilder builder;
}
