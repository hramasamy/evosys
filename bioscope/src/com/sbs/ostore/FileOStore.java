// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileOStore.java

package com.sbs.ostore;

import com.sbs.ds.*;
import com.sbs.espace.IEntity;
import com.sbs.espace.NoSuchNodeException;
import com.sbs.espace.xml.XMLTransformer;
import com.sbs.utils.AppLogger;
import com.sbs.utils.collection.Collections;
import java.util.List;

// Referenced classes of package com.sbs.ostore:
//            OStore, IObjectHandler

class FileOStore extends OStore
{

    FileOStore()
    {
        root = System.getProperty("OStoreLocation", String.valueOf(String.valueOf(System.getProperty("user.home"))).concat("/ostore/"));
        OStore.logger.info("Initialized FileOStore at ".concat(String.valueOf(String.valueOf(root))));
    }

    public Object load(int type, String name)
    {
        String className = null;
        IObjectHandler oh = getObjectHandler(type);
        if(oh != null)
            try
            {
                Object obj = oh.loadFromOStore(name);
                return obj;
            }
            catch(Exception ex)
            {
                OStore.logger.warn(String.valueOf(String.valueOf((new StringBuffer("Error finding definition for class: ")).append(className).append(". Cause: ").append(ex.getMessage()))));
            }
        OStore.logger.warn("No object handler defined for type: ".concat(String.valueOf(String.valueOf(type))));
        return null;
    }

    public Object save(Object obj)
    {
        return null;
    }

    public Object saveAs(String name, Object obj)
    {
        return null;
    }

    public Object remove(int type, String name)
    {
        return null;
    }

    public String toString()
    {
        return "FileStore located at ".concat(String.valueOf(String.valueOf(root)));
    }

    private IObjectHandler getObjectHandler(int type)
    {
        loadMapping();
        IObjectHandler oh = null;
        oh = (IObjectHandler)objectHandlers.get(type);
        try
        {
            if(oh == null)
            {
                oh = (IObjectHandler)Class.forName(getMappingFor(type)).newInstance();
                oh.setOStoreLocation(root);
                objectHandlers.add(type, oh);
            }
        }
        catch(Exception ex)
        {
            OStore.logger.warn(String.valueOf(String.valueOf((new StringBuffer("Error loading object handler class: ")).append(getMappingFor(type)).append(". Cause: ").append(ex.getMessage()))));
        }
        return oh;
    }

    private static String getMappingFor(int type)
    {
        String className = null;
        loadMapping();
        try
        {
            Object objA[] = mapping.select(String.valueOf(String.valueOf((new StringBuffer("/OStoreMapping/mapping[@id=")).append(type).append("]"))));
            if(objA != null && objA.length > 0)
            {
                IEntity eA = (IEntity)objA[0];
                if(eA != null)
                    className = (String)eA.getAttributeValue("handlerClass");
            }
        }
        catch(NoSuchNodeException ex)
        {
            OStore.logger.info(String.valueOf(String.valueOf((new StringBuffer("Error finding mapping for object type: ")).append(type).append(". Cause: ").append(ex.getMessage()))));
        }
        return className;
    }

    private static void loadMapping()
    {
        if(mapping == null)
        {
            FileDataSource ds = new FileDataSource(String.valueOf(String.valueOf(root)).concat("mappings/mapping.xml"));
            ds.setTransformer(XMLTransformer.DEF_XML_TRANSFORMER);
            try
            {
                mapping = ds.query();
            }
            catch(DataSourceException datasourceexception) { }
            try
            {
                objectHandlers = Collections.initList(mapping.getChildNodeCount("mapping"));
            }
            catch(NoSuchNodeException nosuchnodeexception) { }
        }
    }

    private static String root;
    private static IEntity mapping;
    private static List objectHandlers;

}
