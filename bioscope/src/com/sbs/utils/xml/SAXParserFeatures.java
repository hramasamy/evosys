// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SAXParserFeatures.java

package com.sbs.utils.xml;


public interface SAXParserFeatures
{

    public static final String NAMESPACE_PROCESSING = "http://xml.org/sax/features/namespaces";
    public static final String NAMESPACE_PREFIX = "http://xml.org/sax/features/namespace-prefixes";
    public static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
    public static final String VALIDATION = "http://xml.org//sax//features/validation";
    public static final String PROCESS_EXTERNAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
    public static final String PROCESS_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";

}
