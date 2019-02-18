// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PredefinedDataTypes.java

package com.sbs.espace.predef;

import com.sbs.espace.*;

public class PredefinedDataTypes
{

    public PredefinedDataTypes()
    {
    }

    public static final ISimpleDataType PHONE;
    public static final ISimpleDataType SSN;
    public static final ISimpleDataType ZIP;
    public static final ISimpleDataType EMAIL;
    public static final ISimpleDataType NET_PROTOCOL;

    static 
    {
        PHONE = new SimpleDataType("Phone", ISimpleDataType.stringType);
        SSN = new SimpleDataType("SSN", ISimpleDataType.stringType);
        ZIP = new SimpleDataType("Zip", ISimpleDataType.stringType);
        EMAIL = new SimpleDataType("Email", ISimpleDataType.stringType);
        NET_PROTOCOL = new SimpleDataType("NetworkProtocol", ISimpleDataType.stringType);
        IFacet phonePattern = new com.sbs.espace.Facet.PatternFacet();
        phonePattern.setLimit("^\\(?(\\d{3})\\)?[-| ]?(\\d{3})[-| ]?(\\d{4})$");
        PHONE.addFacet(phonePattern);
        IFacet ssnPattern = new com.sbs.espace.Facet.PatternFacet();
        ssnPattern.setLimit("^(\\d{3})[-| ]?(\\d{2})[-| ]?(\\d{4})$");
        SSN.addFacet(ssnPattern);
        IFacet zipPattern = new com.sbs.espace.Facet.PatternFacet();
        zipPattern.setLimit("^\\d{5}(-\\d{4})?$");
        ZIP.addFacet(zipPattern);
        IFacet protoEnum = new com.sbs.espace.Facet.EnumerationFacet();
        protoEnum.setLimit("file,http,ftp");
        NET_PROTOCOL.addFacet(protoEnum);
        IFacet emailFacet = new com.sbs.espace.Facet.PatternFacet();
        emailFacet.setLimit("^[a-z][\\w\\_\\.]*\\@[a-z\\d\\_]*\\.[a-z]{2,4}$");
        EMAIL.addFacet(emailFacet);
    }
}
