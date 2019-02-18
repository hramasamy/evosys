// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileSpec.java

package com.sbs.ds;

import com.sbs.espace.*;
import com.sbs.espace.predef.PredefinedDataTypes;

// Referenced classes of package com.sbs.ds:
//            DSSpec

public class FileSpec extends DSSpec
{

    private FileSpec()
    {
        super("FileSpec");
        addAttribute(NAME);
        addAttribute(LOCATION);
        addAttribute(PROTOCOL);
    }

    private static final IAttribute NAME;
    private static final IAttribute LOCATION;
    private static final IAttribute PROTOCOL;
    public static final FileSpec FILE_SPEC = new FileSpec();

    static 
    {
        NAME = new Attribute("Name", ISimpleDataType.stringType);
        LOCATION = new Attribute("Location", ISimpleDataType.stringType);
        PROTOCOL = new Attribute("Protocol", PredefinedDataTypes.NET_PROTOCOL);
        PROTOCOL.setDefaultValue("file");
    }
}
