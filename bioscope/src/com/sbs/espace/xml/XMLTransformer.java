// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMLTransformer.java

package com.sbs.espace.xml;

import com.sbs.ds.DefaultTransformer;
import com.sbs.espace.*;
import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;
import com.sbs.utils.xml.*;
import java.io.InputStream;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

// Referenced classes of package com.sbs.espace.xml:
//            XMLEntity

public class XMLTransformer extends DefaultTransformer
{

    public XMLTransformer()
    {
    }

    public IEntity transform(IEntity e)
        throws Exception
    {
        throw new UnsupportedOperationException("Method transform() not yet implemented.");
    }

    public IEntity transform(Object obj)
        throws Exception
    {
        if(obj instanceof IEntity)
        {
            return transform((IEntity)obj);
        } else
        {
            if(!(obj instanceof String));
            return null;
        }
    }

    public IEntity transform(InputStream in)
        throws Exception
    {
        return new XMLEntity(XMLParser.getDOMParser().parse(in));
    }

    public static Document convert(IEntity entity)
        throws Exception
    {
        if(entity.getEntityType().equals("xml"))
        {
            return ((XMLEntity)entity).getDocument();
        } else
        {
            DocumentBuilder builder = DocumentBuilderPool.createNSAware();
            Document dom = builder.newDocument();
            handleDataType(dom, dom, entity);
            return dom;
        }
    }

    private static Element createEntityNode(Document dom, IEntity entity, boolean includeDTDef)
    {
        Element entityNode = dom.createElementNS("com.sbs.espace", "entity");
        entityNode.setAttribute("class", entity.getClass().getName());
        if(includeDTDef)
        {
            Element dtEl = dom.createElement("data-type");
            dtEl.setAttribute("class", entity.getDataType().getClass().getName());
            Element dtNmEl = dom.createElement("name");
            dtNmEl.appendChild(dom.createTextNode(entity.getDataType().getName()));
            dtEl.appendChild(dtNmEl);
            entityNode.appendChild(dtEl);
        }
        entityNode.setAttribute("data-type-name", entity.getDataType().getName());
        if(!entity.getName().equals(entity.getDataType().getName()))
        {
            Element instName = dom.createElement("instance-name");
            instName.appendChild(dom.createTextNode(entity.getName()));
            entityNode.appendChild(instName);
        }
        return entityNode;
    }

    private static void handleDataType(Document dom, Node parent, IEntity entity)
    {
        IDataType dt = entity.getDataType();
        if(dt != null)
            if(dt.getType() == 1)
                handleComplexType(dom, parent, entity);
            else
                handleSimpleType(dom, parent, entity);
    }

    private static void handleSimpleType(Document dom, Node parent, IEntity entity)
    {
        ISimpleDataType dt = (ISimpleDataType)entity.getDataType();
        Object nv = entity.getNodeValue();
        if(nv == null)
            return;
        Element currentNode = createEntityNode(dom, entity, false);
        Element nodeValue = dom.createElement("value");
        nodeValue.setAttribute("type", nv.getClass().getName());
        if(dt.isPrimitive())
        {
            if(nv != null && Strings.isNotEmpty(String.valueOf(nv)))
            {
                Node valueNode = dom.createTextNode(String.valueOf(nv));
                nodeValue.appendChild(valueNode);
            }
        } else
        if((dt instanceof ListType) && (nv instanceof List))
        {
            IDataType base = dt.getBaseType();
            List nl = (List)nv;
            Object item;
            for(Iterator itr = nl.iterator(); itr.hasNext(); appendItem(dom, nodeValue, base, item))
                item = itr.next();

        } else
        if(dt instanceof MapType)
            if(!(nv instanceof Map));
        currentNode.appendChild(nodeValue);
        parent.appendChild(currentNode);
    }

    private static void appendItem(Document dom, Node parent, IDataType dt, Object item)
    {
        Element nodeValue = dom.createElement("item");
        nodeValue.setAttribute("type", item.getClass().getName());
        if(dt.isPrimitive())
        {
            if(item != null && Strings.isNotEmpty(String.valueOf(item)))
            {
                Node valueNode = dom.createTextNode(String.valueOf(item));
                nodeValue.appendChild(valueNode);
            }
        } else
        if((dt instanceof ListType) && (item instanceof List))
        {
            List l = (List)item;
            for(Iterator itr = l.iterator(); itr.hasNext(); appendItem(dom, ((Node) (nodeValue)), ((IDataType) (ISimpleDataType.stringType)), itr.next()));
        } else
        if((dt instanceof MapType) && (item instanceof Map))
        {
            Map map = (Map)item;
            Iterator itr = map.keySet().iterator();
            do
            {
                if(!itr.hasNext())
                    break;
                Object key = itr.next();
                Object val = map.get(key);
                if(val != null)
                {
                    Element kNode = dom.createElement("key");
                    kNode.setAttribute("name", String.valueOf(key));
                    Element vlNode = dom.createElement("value");
                    vlNode.setAttribute("type", val.getClass().getName());
                    Node txtNode = dom.createTextNode(String.valueOf(val));
                    vlNode.appendChild(txtNode);
                    kNode.appendChild(vlNode);
                    nodeValue.appendChild(kNode);
                }
            } while(true);
        }
        parent.appendChild(nodeValue);
    }

    private static void handleComplexType(Document dom, Node parent, IEntity entity)
    {
        Element currentNode = null;
        if(parent.getNodeType() == 9)
            currentNode = createEntityNode(dom, entity, true);
        else
            currentNode = createEntityNode(dom, entity, false);
        IComplexDataType cdt = (IComplexDataType)entity.getDataType();
        Iterator itr = cdt.attributes();
        do
        {
            if(!itr.hasNext())
                break;
            IAttribute attr = (IAttribute)itr.next();
            String attrName = attr.getName();
            String attrVal = String.valueOf(entity.getAttributeValue(attrName));
            if(Strings.isNotEmpty(attrVal))
            {
                Element attrNode = dom.createElement("attribute");
                attrNode.setAttribute("name", attrName);
                attrNode.setAttribute("type", attr.getDataType().getClass().getName());
                setAttributeValue(dom, attrNode, (ISimpleDataType)attr.getDataType(), entity.getAttributeValue(attrName));
                currentNode.appendChild(attrNode);
            }
        } while(true);
        String chNames[] = entity.getChildrenNames();
        for(int i = 0; i < chNames.length; i++)
            try
            {
                IEntity chEntity;
                for(Iterator chItr = entity.getChildNodeIterator(chNames[i]); chItr.hasNext(); handleDataType(dom, currentNode, chEntity))
                    chEntity = (IEntity)chItr.next();

            }
            catch(NoSuchNodeException nosuchnodeexception) { }

        parent.appendChild(currentNode);
    }

    private static void setAttributeValue(Document dom, Node attrNode, ISimpleDataType sdt, Object value)
    {
        Element vE = dom.createElement("value");
        if(!(sdt instanceof ListType) && !(sdt instanceof ArrayType))
            vE.appendChild(dom.createTextNode(String.valueOf(value)));
        attrNode.appendChild(vE);
    }

    private static AppLogger logger;
    public static final XMLTransformer DEF_XML_TRANSFORMER = new XMLTransformer();

    static 
    {
        logger = AppLogger.getLogger(com.sbs.espace.xml.XMLTransformer.class);
    }
}
