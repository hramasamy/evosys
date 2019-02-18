// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMLUtils.java

package com.sbs.utils.xml;

import com.sbs.utils.AppLogger;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.xpath.XPathAPI;
import org.jfor.jfor.converter.Converter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.tidy.Tidy;
import org.xml.sax.*;

// Referenced classes of package com.sbs.utils.xml:
//            XMLParser, GenericDOMParser, DOMSerializer

public class XMLUtils
{
    public static class DocumentProp
    {

        public boolean isHTML()
        {
            return isHTML;
        }

        public Document getDocument()
        {
            return document;
        }

        private boolean isHTML;
        private Document document;

        public DocumentProp(boolean isHTML, Document document)
        {
            this.isHTML = isHTML;
            this.document = document;
        }
    }

    public static class StringPrintWriter extends PrintWriter
    {

        public String getString()
        {
            flush();
            return ((StringWriter)super.out).toString();
        }

        public StringPrintWriter()
        {
            super(new StringWriter());
        }
    }


    public XMLUtils()
    {
    }

    public static byte[] transform(Reader srcReader, Reader xsltReader, boolean runFop)
        throws Exception
    {
        return transform(srcReader, xsltReader, runFop, null);
    }

    public static byte[] transform(Reader srcReader, Reader xsltReader, boolean runFop, Map params)
        throws Exception
    {
        return transform(XMLParser.getDOMParser().parse(srcReader), XMLParser.getDOMParser().parse(xsltReader), runFop);
    }

    public static byte[] transform(InputStream source, InputStream xslt, boolean runFop)
        throws Exception
    {
        return transform(source, xslt, runFop, null);
    }

    public static byte[] transform(InputStream source, InputStream xslt, boolean runFop, Map params)
        throws Exception
    {
        return transform(XMLParser.getDOMParser().parse(source), XMLParser.getDOMParser().parse(xslt), runFop);
    }

    public static byte[] transform(File source, File xslt, boolean runFop)
        throws Exception
    {
        return transform(source, xslt, runFop, null);
    }

    public static byte[] transform(File source, File xslt, boolean runFop, Map params)
        throws Exception
    {
        return transform(((InputStream) (new FileInputStream(source))), ((InputStream) (new FileInputStream(xslt))), runFop, params);
    }

    public static byte[] transform(URL source, URL xslt, boolean runFop)
        throws Exception
    {
        return transform(source, xslt, runFop, null);
    }

    public static byte[] transform(URL source, URL xslt, boolean runFop, Map params)
        throws Exception
    {
        return transform(loadDocument(source.getFile()).getDocument(), loadDocument(xslt.getFile()).getDocument(), runFop, params);
    }

    public static byte[] transform(Document src, Document xslt, boolean runFop)
        throws Exception
    {
        return transform(src, xslt, runFop, null);
    }

    public static byte[] transform(Document src, Document xslt, boolean runFop, Map params)
        throws Exception
    {
        byte xsltResult[] = applyXSLT(src, xslt, params);
        if(runFop)
        {
            InputSource foXmlInput = new InputSource(new ByteArrayInputStream(xsltResult));
            Writer foOut = new StringWriter();
            new Converter(foXmlInput, foOut, Converter.createConverterOption());
        }
        return xsltResult;
    }

    private static byte[] applyXSLT(Document xmlDOM, Document xslDOM, Map params)
        throws IOException, SAXException, ParserConfigurationException, FileNotFoundException, TransformerConfigurationException, TransformerException
    {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        if(tFactory.getFeature("http://javax.xml.transform.dom.DOMSource/feature") && tFactory.getFeature("http://javax.xml.transform.dom.DOMResult/feature"))
        {
            DOMSource xslDomSource = new DOMSource(xslDOM);
            xslDomSource.setSystemId(xslDOM.getNamespaceURI());
            Transformer transformer = tFactory.newTransformer(xslDomSource);
            if(params != null)
            {
                Object k;
                for(Iterator keys = params.keySet().iterator(); keys.hasNext(); transformer.setParameter(String.valueOf(k).trim(), params.get(k)))
                    k = keys.next();

            }
            DOMSource xmlDomSource = new DOMSource(xmlDOM);
            xmlDomSource.setSystemId(xmlDOM.getNamespaceURI());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            transformer.transform(xmlDomSource, new StreamResult(baos));
            return baos.toByteArray();
        } else
        {
            throw new SAXNotSupportedException("DOM node processing not supported!");
        }
    }

    private static String applyXPath(Document doc, String xpath)
        throws Exception
    {
        if(xpath != null && xpath.length() > 0)
        {
            Transformer serializer = TransformerFactory.newInstance().newTransformer();
            serializer.setOutputProperty("omit-xml-declaration", "yes");
            NodeIterator nl = XPathAPI.selectNodeIterator(doc, xpath);
            StringWriter writer = new StringWriter();
            Node n;
            while((n = nl.nextNode()) != null) 
            {
                if(isTextNode(n))
                {
                    for(Node nn = n.getNextSibling(); isTextNode(nn); nn = nn.getNextSibling())
                        writer.write(nn.getNodeValue());

                } else
                {
                    serializer.transform(new DOMSource(n), new StreamResult(writer));
                }
                writer.write("\n");
            }
            writer.flush();
            return writer.getBuffer().toString();
        } else
        {
            logger.info("Bad input args: ".concat(String.valueOf(String.valueOf(xpath))));
            return null;
        }
    }

    private static boolean isTextNode(Node n)
    {
        if(n == null)
        {
            return false;
        } else
        {
            short nodeType = n.getNodeType();
            return nodeType == 4 || nodeType == 3;
        }
    }

    private static DocumentProp loadDocument(String url)
        throws Exception
    {
        Document document = null;
        boolean isHTML = false;
        URLConnection connection = (new URL(url)).openConnection();
        if(connection.getContentType() != null && connection.getContentType().equals("text/html") || url.endsWith(".html") || url.endsWith(".htm") || url.endsWith(".HTML") || url.endsWith(".HTM") || url.toUpperCase().startsWith("HTTP"))
        {
            document = loadDocumentTidy(connection.getInputStream());
            isHTML = true;
        } else
        {
            document = loadDocumentJAXP(connection.getInputStream());
            isHTML = false;
        }
        return new DocumentProp(isHTML, document);
    }

    private static Document loadDocumentTidy(InputStream in)
        throws Exception
    {
        Tidy tidy = new Tidy();
        //tidy.setCharEncoding(3);
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.setOnlyErrors(true);
        StringPrintWriter err = new StringPrintWriter();
        tidy.setErrout(err);
        Document document = tidy.parseDOM(in, null);
        String errors = err.getString();
        if(errors.equals(""));
        document.normalize();
        return document;
    }

    private static Document loadDocumentJAXP(InputStream in)
        throws Exception
    {
        Document document = XMLParser.getDOMParser().parse(in);
        document.normalize();
        return document;
    }

    /**
     * @deprecated Method parse is deprecated
     */

    private static String parse(DocumentProp dp, String xpath, String xsl)
        throws Exception
    {
        boolean isHTML = dp.isHTML();
        Document doc = dp.getDocument();
        String xpathResult = "";
        if(xpath != null && xpath.length() > 0)
            xpathResult = applyXPath(doc, xpath);
        else
            xpathResult = DOMSerializer.convertToString(doc);
        if(xsl == null || xsl.length() == 0)
            return xpathResult;
        ByteArrayInputStream bais = new ByteArrayInputStream(xpathResult.getBytes());
        Document xpathDOM;
        if(isHTML)
            xpathDOM = loadDocumentTidy(bais);
        else
            xpathDOM = loadDocumentJAXP(bais);
        Document xslDOM = loadDocument(xsl).getDocument();
        String xsltResult = new String(applyXSLT(xpathDOM, xslDOM, null));
        return xsltResult;
    }

    private static String parse(String url, String xpath, String xsl)
        throws Exception
    {
        DocumentProp dp = loadDocument(normalizeUrl(url));
        return parse(dp, xpath, xsl);
    }

    private static String normalizeUrl(String url)
        throws MalformedURLException
    {
        return url;
    }

    public static void main(String args[])
    {
        try
        {
            XMLUtils parser = new XMLUtils();
            String result = parse("http://www.yahoo.com", "/html/body/center/table[4]/tr/td[1]/center[2]/table", "c:\\jboss-3.0.3\\xsl\\hyperlink.xsl");
            logger.debug(result);
        }
        catch(Exception e)
        {
            logger.info(e.getMessage());
        }
    }

    private static AppLogger logger;

    static 
    {
        logger = AppLogger.getLogger(javax.xml.transform.Transformer.class);
    }
}
