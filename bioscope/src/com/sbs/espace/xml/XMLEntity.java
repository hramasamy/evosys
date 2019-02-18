// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   XMLEntity.java

package com.sbs.espace.xml;

import com.sbs.espace.*;
import com.sbs.espace.Entity;
import com.sbs.utils.AppLogger;
import com.sbs.utils.collection.Collections;
import com.sbs.utils.string.Strings;
import java.util.*;
import javax.xml.transform.TransformerException;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.*;

// Referenced classes of package com.sbs.espace.xml:
//            NodeListIterator, NamedNodeMapIterator

public class XMLEntity extends Entity
{

    public XMLEntity()
    {
    }

    public XMLEntity(IDataType dt)
    {
        this(dt, null);
    }

    public XMLEntity(Node node)
    {
        this(null, node);
    }

    public XMLEntity(IDataType dt, Node node)
    {
        super(dt);
        setNode(node);
    }

    public XMLEntity(Document dom)
    {
        setDocument(dom);
    }

    public Document getDocument()
    {
        return dom;
    }

    public String getEntityType()
    {
        return "xml";
    }

    public void setNode(Node node)
    {
        if(node != null)
        {
            this.node = node;
            this.node.normalize();
            if(getNode().getNodeType() == 1)
                isElement = true;
            dom = node.getOwnerDocument();
        }
    }

    public void setDocument(Document dom)
    {
        if(dom != null)
            setNode(dom.getDocumentElement());
    }

    public int getContentType()
    {
        return 0;
    }

    public INamespace getNamespace()
    {
        return null;
    }

    public boolean isRoot()
    {
        return getNode() != null && node == dom.getDocumentElement();
    }

    public boolean isLeaf()
    {
        return getNode() != null && getNode().getNodeType() != 1;
    }

    public boolean hasAttribute(String attrName)
    {
        if(getDataType() != null)
        {
            if(isComplexType() && ((IComplexDataType)getDataType()).hasAttribute(attrName) != -1)
                return true;
        } else
        if(getNode() != null && isElement)
            return ((Element)node).hasAttribute(attrName);
        return false;
    }

    public boolean hasChildNode(String chName)
    {
        if(getNode() != null)
        {
            NodeList nodes = getNode().getChildNodes();
            int nodeCount = nodes.getLength();
            List nodeList = new ArrayList();
            for(int i = 0; i < nodeCount; i++)
            {
                Node cNode = nodes.item(i);
                if(cNode != null && cNode.getNodeType() == 1 && Strings.isNotEmpty(chName) && cNode.getNodeName().equals(chName))
                    return true;
            }

        }
        return false;
    }

    public List getChildNodes(String chName)
    {
        List nodeList = new ArrayList();
        if(getNode() != null)
        {
            NodeList nodes = getNode().getChildNodes();
            int nodeCount = nodes.getLength();
            for(int i = 0; i < nodeCount; i++)
            {
                Node cNode = nodes.item(i);
                if(cNode != null && cNode.getNodeType() == 1 && Strings.isNotEmpty(chName) && cNode.getNodeName().equals(chName))
                    nodeList.add(new XMLEntity(cNode));
            }

        }
        return nodeList;
    }

    public void addChildNode(IEntity node)
        throws NoSuchNodeException
    {
        throw new UnsupportedOperationException("Method addChildNode() not yet implemented.");
    }

    public boolean removeChildNode(IEntity node)
    {
        throw new UnsupportedOperationException("Method removeChildNode() not yet implemented.");
    }

    public IEntity removeChildNode(String childName, String attr[], Object val[])
    {
        Element element;
        if(getNode() != null && isElement)
            element = (Element)node;
        return null;
    }

    public Iterator getChildNodeIterator(String childName)
        throws NoSuchNodeException
    {
        if(getDataType() != null)
            hasChild(childName);
        if(getNode() != null)
            return new NodeListIterator(node.getChildNodes(), childName, true);
        else
            return Collections.EMPTY_ITERATOR;
    }

    public int getChildNodeCount(String childName)
        throws NoSuchNodeException
    {
        if(getDataType() != null)
            hasChild(childName);
        if(getNode() != null)
            return (new NodeListIterator(node.getChildNodes(), childName)).getNodeCount();
        else
            return -1;
    }

    public String[] getChildrenNames()
    {
        if(getDataType() != null && isComplexType())
            return ((IComplexDataType)getDataType()).getChildNames();
        if(getNode() != null && isElement)
        {
            NodeList nl = node.getChildNodes();
            List nameL = new ArrayList();
            Iterator itr = new NodeListIterator(nl);
            do
            {
                if(!itr.hasNext())
                    break;
                Node n = (Node)itr.next();
                if(!nameL.contains(n.getNodeName()))
                    nameL.add(n.getNodeName());
            } while(true);
            String cNames[] = new String[nameL.size()];
            return (String[])nameL.toArray(cNames);
        } else
        {
            return null;
        }
    }

    public Iterator attributes()
    {
        if(dom != null)
            return new NamedNodeMapIterator(getNode().getAttributes());
        else
            return Collections.EMPTY_ITERATOR;
    }

    public Object getAttributeValue(String attName)
    {
        Object attValue = null;
        if(getDataType() != null)
            if(!isComplexType());
        if(getNode() != null && isElement)
        {
            attValue = ((Element)getNode()).getAttribute(attName);
            if(getDataType() != null && getDataType().getType() == 1)
            {
                try
                {
                    Object obj = ((IComplexDataType)getDataType()).validate(attName, attValue);
                    return obj;
                }
                catch(ValidationException ex)
                {
                    logger.warn(ex.getMessage());
                }
                Object obj1 = attValue;
                return obj1;
            }
        }
        return attValue;
    }

    public Object setAttributeValue(String attName, Object value)
        throws ValidationException
    {
        Object oldV = null;
        if(getDataType() != null)
            if(!isComplexType());
        if(getNode() != null && isElement)
        {
            Element e = (Element)node;
            oldV = e.getAttribute(attName);
            e.setAttribute(attName, String.valueOf(value));
        }
        return oldV;
    }

    public void setAttributeValues(Object value[])
        throws ValidationException
    {
        throw new UnsupportedOperationException("Method setAttributeValues() not yet implemented.");
    }

    public void setAttributeValues(String names[], Object values[])
        throws ValidationException
    {
        for(int i = 0; i < names.length && i < values.length; i++)
            setAttributeValue(names[i], values[i]);

    }

    public Object[] getAttributeValues()
    {
        throw new UnsupportedOperationException("Method getNodeValue() not yet implemented.");
    }

    public Object getNodeValue()
    {
        if(super.nodeValue == null && getNode() != null)
        {
            StringBuffer sb = new StringBuffer();
            NodeList nl = node.getChildNodes();
            int length = nl.getLength();
            for(int i = 0; i < length; i++)
            {
                Node nd = nl.item(i);
                if(nd.getNodeType() == 3)
                {
                    nd.normalize();
                    sb.append(nd.getNodeValue());
                }
            }

            super.nodeValue = sb.toString().trim();
        }
        return super.nodeValue;
    }

    public Object[] select(String expression)
        throws NoSuchNodeException
    {
        Object retValue[] = null;
        if(getNode() != null)
            try
            {
                NodeListIterator itr = new NodeListIterator(XPathAPI.selectNodeList(getNode(), expression), true);
                retValue = new Object[itr.getNodeCount()];
                for(int i = 0; itr.hasNext(); i++)
                    retValue[i] = (Entity)itr.next();

            }
            catch(TransformerException ex)
            {
                throw new NoSuchNodeException(String.valueOf(String.valueOf((new StringBuffer("Error running expression: ")).append(expression).append(". Cause: ").append(ex.getCause()))));
            }
        return retValue;
    }

    public IEntity clone(boolean deep)
    {
        XMLEntity cloned = new XMLEntity(getDataType());
        cloned.setName(new String(getName()));
        if(getNode() != null)
        {
            Node n = node.cloneNode(deep);
            cloned.setNode(n);
        }
        return cloned;
    }

    private Node getNode()
    {
        if(node == null && dom != null)
            node = dom.getDocumentElement();
        return node;
    }

    private boolean isTextNode(Node n)
    {
        short s = n.getNodeType();
        return s == 8 || s == 3 || s == 4 || s == 6 || s == 7 || s == 12;
    }

    private static AppLogger logger;
    private Document dom;
    private Node node;
    private boolean isElement;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.espace.xml.XMLEntity.class);
    }
}
