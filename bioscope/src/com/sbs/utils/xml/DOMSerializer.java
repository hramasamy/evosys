// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DOMSerializer.java

package com.sbs.utils.xml;

import com.sbs.utils.string.Strings;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.xml.serialize.BaseMarkupSerializer;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

public class DOMSerializer
{

    public DOMSerializer()
    {
    }

    public static void serialize(Document doc, String fileName)
        throws Exception
    {
        File fl = (new File(fileName)).getParentFile();
        if(Strings.isNotEmpty(fl.getName()) && !fl.exists())
            fl.mkdirs();
        OutputFormat format = new OutputFormat(doc);
        format.setPreserveEmptyAttributes(true);
        synchronized(serializer)
        {
            serializer.setOutputFormat(format);
            serializer.setOutputByteStream(new FileOutputStream(fileName));
            serializer.asDOMSerializer().serialize(doc);
            try
            {
                serializer.reset();
            }
            catch(Exception ex)
            {
                throw ex;
            }
        }
    }

    public static InputStream getInputStream(Document doc)
        throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        synchronized(serializer)
        {
            try
            {
                serializer.setOutputByteStream(baos);
                serializer.asDOMSerializer().serialize(doc);
                serializer.reset();
            }
            catch(Exception ex)
            {
                if(ex instanceof IllegalStateException)
                {
                    serializer = new XMLSerializer();
                } else
                {
                    serializer = new XMLSerializer();
                    throw ex;
                }
            }
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static String convertToString(Document doc)
        throws Exception
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(256);
        synchronized(serializer)
        {
            try
            {
                serializer.setOutputByteStream(baos);
                serializer.asDOMSerializer().serialize(doc);
                serializer.reset();
            }
            catch(Exception ex)
            {
                serializer = new XMLSerializer();
                throw ex;
            }
        }
        byte bArray[] = baos.toByteArray();
        return new String(bArray);
    }

    static XMLSerializer serializer = new XMLSerializer();

}
