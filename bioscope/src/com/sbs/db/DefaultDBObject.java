// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefaultDBObject.java

package com.sbs.db;

import java.util.Date;

// Referenced classes of package com.sbs.db:
//            IDatabaseObject

public class DefaultDBObject
    implements IDatabaseObject
{

    public DefaultDBObject()
    {
        this(0);
    }

    public DefaultDBObject(int dbObjectType)
    {
        this.dbObjectType = dbObjectType;
    }

    public int getDBObjectType()
    {
        return dbObjectType;
    }

    public String getName()
    {
        return name;
    }

    public String getOwner()
    {
        return owner;
    }

    public Date getCreationDate()
    {
        return createdOn;
    }

    private int dbObjectType;
    private String owner;
    private String name;
    private Date createdOn;
}
