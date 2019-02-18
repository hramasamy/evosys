// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaseEntityResolver.java

package com.sbs.utils.xml;

import java.io.IOException;
import java.io.PrintStream;
import org.xml.sax.*;

public class BaseEntityResolver
    implements EntityResolver
{

    public BaseEntityResolver()
    {
    }

    public InputSource resolveEntity(String publicID, String systemID)
        throws IOException, SAXException
    {
        System.out.println(String.valueOf(String.valueOf((new StringBuffer("resolvine entity with publicID: ")).append(publicID).append(" and systemID: ").append(systemID))));
        if(systemID.equals("http://www.newInstance.com/javaxml2/copywrite.xml"))
            return new InputSource("file:///c:/xmls/javaxml2/copywrite.xml");
        if(systemID.endsWith("managexml.dtd"))
            return new InputSource("file:///xmls/dtd/managexml.dtd");
        if(systemID.endsWith("ctrlpanel.dtd"))
            return new InputSource("file:///xmls/dtd/ctrlpanel.dtd");
        if(systemID.endsWith("mgdelemdef.dtd"))
            return new InputSource("file:///xmls/dtd/mgdelemdef.dtd");
        if(systemID.endsWith("mgdeleminst.dtd"))
            return new InputSource("file:///xmls/dtd/mgdeleminst.dtd");
        if(systemID.endsWith("proxy.dtd"))
            return new InputSource("file:///xmls/dtd/proxy.dtd");
        if(systemID.endsWith("entitydefinition.dtd"))
            return new InputSource("file:///xmls/dtd/entitydefinition.dtd");
        if(systemID.endsWith("ctrlpanel.mod"))
            return new InputSource("file:///xmls/dtd/ctrlpanel.mod");
        if(systemID.endsWith("mgdelemdef.mod"))
            return new InputSource("file:///xmls/dtd/mgdelemdef.mod");
        if(systemID.endsWith("mgdeleminst.mod"))
            return new InputSource("file:///xmls/dtd/mgdeleminst.mod");
        if(systemID.endsWith("proxy.mod"))
            return new InputSource("file:///xmls/dtd/proxy.mod");
        if(systemID.endsWith("entitydefinition.mod"))
            return new InputSource("file:///xmls/dtd/entitydefinition.mod");
        if(systemID.endsWith("notif_datarecording.mod"))
        {
            return new InputSource("file:///xmls/dtd/notif_datarecording.mod");
        } else
        {
            System.out.println("entity resolved.");
            return null;
        }
    }
}
