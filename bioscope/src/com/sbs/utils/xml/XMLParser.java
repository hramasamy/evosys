// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMLParser.java

package com.sbs.utils.xml;


// Referenced classes of package com.sbs.utils.xml:
//            GenericDOMParser, GenericSAXParser

public class XMLParser
{

    public XMLParser()
    {
    }

    public static GenericDOMParser getDOMParser()
        throws Exception
    {
        return new GenericDOMParser();
    }

    public static GenericSAXParser getSAXParser()
        throws Exception
    {
        return new GenericSAXParser();
    }
}
