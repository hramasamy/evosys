// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BeanLoader.java

package com.sbs.beans;

import com.sbs.ds.*;
import com.sbs.espace.*;
import com.sbs.espace.xml.XMLTransformer;
import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;
import com.sbs.utils.xml.DOMSerializer;
import java.beans.*;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.*;
import org.w3c.dom.Document;

public class BeanLoader
{

    private BeanLoader()
    {
    }

    public static Object load(String xmlFileName)
        throws DataSourceException
    {
        Object bean = null;
        try
        {
            IDataSource ds = new FileDataSource(xmlFileName);
            ds.setTransformer(XMLTransformer.DEF_XML_TRANSFORMER);
            IEntity entity = ds.query();
            System.out.println(DOMSerializer.convertToString(XMLTransformer.convert(entity)));
            String beanClassNm = (String)entity.getAttributeValue("data-type-name");
            Class clazz = Class.forName(String.valueOf(beanClassNm));
            bean = clazz.newInstance();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor pdArr[] = info.getPropertyDescriptors();
            List children = entity.getChildNodes("entity");
            String selection = "/entity/entity[@data-type-name='%1']";
            String propValSelection = "value";
            for(int i = 0; i < pdArr.length; i++)
            {
                String propName = pdArr[i].getName();
                Method m = pdArr[i].getWriteMethod();
                if(m != null)
                {
                    String replaced = selection.replaceFirst("%1", propName);
                    Object selected[] = entity.select(replaced);
                    if(selected.length > 0)
                    {
                        IEntity propEntity = (IEntity)selected[0];
                        selected = propEntity.select(propValSelection);
                        if(selected.length > 0)
                            try
                            {
                                Object propVal = null;
                                Class paramType = m.getParameterTypes()[0];
                                List interfaces = Arrays.asList(paramType.getInterfaces());
                                IEntity valEntity = (IEntity)selected[0];
                                if(interfaces.contains(java.util.Collection.class))
                                    propVal = extractCollection(valEntity);
                                else
                                if(!interfaces.contains(java.util.Map.class))
                                {
                                    propVal = valEntity.getNodeValue();
                                    propVal = DataTypeValidator.validate(paramType, propVal);
                                }
                                m.invoke(bean, new Object[] {
                                    propVal
                                });
                            }
                            catch(Exception ex)
                            {
                                logger.warn(String.valueOf(String.valueOf((new StringBuffer("Unable to load the property for the bean ")).append(beanClassNm).append(". Root cause: ").append(ex.getMessage()))));
                            }
                    }
                }
            }

        }
        catch(Exception ex)
        {
            throw new DataSourceException(ex);
        }
        return bean;
    }

    private static Object extractCollection(IEntity entity)
    {
        List value = new ArrayList();
        try
        {
            Iterator itr = entity.getChildNodeIterator("item");
            do
            {
                if(!itr.hasNext())
                    break;
                Object item = extractItem((IEntity)itr.next());
                if(item != null)
                    value.add(item);
            } while(true);
        }
        catch(NoSuchNodeException nosuchnodeexception) { }
        return value;
    }

    private static Object extractItem(IEntity entity)
    {
        Object item = null;
        String itemClassNm = (String)entity.getAttributeValue("type");
        if(Strings.isNotEmpty(itemClassNm))
            try
            {
                Class itemClass = Class.forName(itemClassNm);
                List interfaces = Arrays.asList(itemClass.getInterfaces());
                if(!interfaces.contains(java.util.Collection.class))
                    if(interfaces.contains(java.util.Map.class))
                    {
                        Iterator itr = entity.getChildNodeIterator("key");
                        do
                        {
                            if(!itr.hasNext())
                                break;
                            IEntity key = (IEntity)itr.next();
                            String keyName = (String)key.getAttributeValue("name");
                            List valueNodes = key.getChildNodes("value");
                            if(valueNodes.size() > 0)
                            {
                                IEntity valEntity = (IEntity)valueNodes.get(0);
                                item = extractItem(valEntity);
                            }
                        } while(true);
                    } else
                    {
                        item = entity.getNodeValue();
                        item = DataTypeValidator.validate(itemClass, item);
                    }
            }
            catch(Exception exception) { }
        return item;
    }

    public static void save(Object bean, String fileName)
        throws DataSourceException
    {
        try
        {
            File fl = new File(fileName);
            BeanInfo info = Introspector.getBeanInfo(bean.getClass());
            IComplexDataType cdt = new ComplexDataType(bean.getClass().getName());
            IEntity entity = new Entity(cdt);
            PropertyDescriptor descArr[] = info.getPropertyDescriptors();
            for(int i = 0; i < descArr.length; i++)
            {
                PropertyDescriptor pd = descArr[i];
                String propName = pd.getName();
                Class propType = pd.getPropertyType();
                Method m = pd.getReadMethod();
                if(m == null || propName.equalsIgnoreCase("class"))
                    continue;
                try
                {
                    Object value = m.invoke(bean, new Object[0]);
                    IDataType dt = getType(propName, propType);
                    if(!dt.isPrimitive())
                        if((dt instanceof ListType) && (value instanceof Collection))
                        {
                            List contains = new ArrayList((Collection)value);
                            if(contains.size() > 0)
                            {
                                IDataType chDt = getType("", contains.get(0).getClass());
                                dt.setBaseType(chDt);
                            }
                        } else
                        if(dt instanceof MapType)
                            if(!(value instanceof Map));
                    cdt.addChild(dt);
                    IEntity e = new Entity(dt, value);
                    entity.addChildNode(e);
                }
                catch(Exception ex)
                {
                    logger.warn(String.valueOf(String.valueOf((new StringBuffer("Ignoring the property: ")).append(propName).append(". Cause: ").append(ex.getMessage()))), ex);
                }
            }

            Document dom = XMLTransformer.convert(entity);
            DOMSerializer.serialize(dom, fileName);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new DataSourceException(ex);
        }
    }

    private static void handleType(IComplexDataType cdt, Object bean, IEntity entity, Method m, String propName, Class propType)
    {
        IDataType dt = new SimpleDataType(propName, ISimpleDataType.stringType);
        if(propType.equals(Integer.TYPE) || propType.equals(java.lang.Integer.class))
            dt = new SimpleDataType(propName, ISimpleDataType.intType);
        else
        if(propType.equals(Long.TYPE) || propType.equals(java.lang.Long.class))
            dt = new SimpleDataType(propName, ISimpleDataType.longType);
        else
        if(propType.equals(Double.TYPE) || propType.equals(java.lang.Double.class))
            dt = new SimpleDataType(propName, ISimpleDataType.doubleType);
        else
        if(propType.equals(Float.TYPE) || propType.equals(java.lang.Float.class))
            dt = new SimpleDataType(propName, ISimpleDataType.floatType);
        else
        if(propType.equals(Boolean.TYPE) || propType.equals(java.lang.Boolean.class))
            dt = new SimpleDataType(propName, ISimpleDataType.booleanType);
        else
        if(propType.equals(Byte.TYPE) || propType.equals(java.lang.Byte.class))
            dt = new SimpleDataType(propName, ISimpleDataType.byteType);
        else
        if(propType.equals(java.util.Date.class))
            dt = new SimpleDataType(propName, ISimpleDataType.dateType);
        if(propType.equals(java.util.Collection.class))
            dt = new ListType(propName);
        else
        if(propType.equals(java.util.Map.class))
            dt = new MapType(propName);
        List interfaces = Arrays.asList(propType.getInterfaces());
        if(interfaces.contains(java.util.Collection.class))
            dt = new SimpleDataType(propName);
        cdt.addChild(dt);
        try
        {
            Object value = m.invoke(bean, new Object[0]);
            IEntity e = new Entity(dt, value);
            entity.addChildNode(e);
        }
        catch(Exception ex)
        {
            logger.warn(String.valueOf(String.valueOf((new StringBuffer("Ignoring the property: ")).append(propName).append(". Cause: ").append(ex.getMessage()))), ex);
        }
    }

    private static IDataType getType(String propName, Class propType)
    {
        IDataType dt = new SimpleDataType(propName, ISimpleDataType.stringType);
        if(propType.equals(Integer.TYPE) || propType.equals(java.lang.Integer.class))
            return new SimpleDataType(propName, ISimpleDataType.intType);
        if(propType.equals(Long.TYPE) || propType.equals(java.lang.Long.class))
            return new SimpleDataType(propName, ISimpleDataType.longType);
        if(propType.equals(Double.TYPE) || propType.equals(java.lang.Double.class))
            return new SimpleDataType(propName, ISimpleDataType.doubleType);
        if(propType.equals(Float.TYPE) || propType.equals(java.lang.Float.class))
            return new SimpleDataType(propName, ISimpleDataType.floatType);
        if(propType.equals(Boolean.TYPE) || propType.equals(java.lang.Boolean.class))
            return new SimpleDataType(propName, ISimpleDataType.booleanType);
        if(propType.equals(Byte.TYPE) || propType.equals(java.lang.Byte.class))
            return new SimpleDataType(propName, ISimpleDataType.byteType);
        if(propType.equals(java.util.Date.class))
            return new SimpleDataType(propName, ISimpleDataType.dateType);
        if(propType.equals(java.lang.String.class))
            return dt;
        List interfaces = Arrays.asList(propType.getInterfaces());
        if(interfaces.contains(java.util.Collection.class))
            return dt = new ListType(propName);
        if(interfaces.contains(java.util.Map.class))
            return dt = new MapType(propName);
        else
            return dt;
    }

    private static AppLogger logger;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.beans.BeanLoader.class);
    }
}
