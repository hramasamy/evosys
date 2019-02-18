// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OStore.java

package com.sbs.ostore;

import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;

// Referenced classes of package com.sbs.ostore:
//            FileOStore

public abstract class OStore
{

    public OStore()
    {
    }

    public static OStore getOStore()
    {
        if(ostore == null)
            synchronized(com.sbs.ostore.OStore.class)
            {
                if(ostore == null)
                {
                    String ostoreImpl = System.getProperty("OStoreImpl");
                    if(Strings.isNotEmpty(ostoreImpl))
                        try
                        {
                            ostore = (OStore)Class.forName(ostoreImpl).newInstance();
                        }
                        catch(Exception ex)
                        {
                            logger.fatal("Error initializing XStore: ".concat(String.valueOf(String.valueOf(ex.getMessage()))), ex);
                        }
                    else
                        ostore = new FileOStore();
                }
            }
        return ostore;
    }

    public abstract Object load(int i, String s);

    public abstract Object save(Object obj);

    public abstract Object saveAs(String s, Object obj);

    public abstract Object remove(int i, String s);

    protected static AppLogger logger;
    private static OStore ostore;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.ostore.OStore.class);
    }
}
