// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NodeListIterator.java

package com.sbs.espace.xml;

import com.sbs.utils.string.Strings;
import java.util.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package com.sbs.espace.xml:
//            XMLEntity

public class NodeListIterator
    implements Iterator
{

    public NodeListIterator(NodeList nodes)
    {
        this(nodes, ((String) (null)));
    }

    public NodeListIterator(NodeList nodes, boolean entityIterator)
    {
        this(nodes);
        this.entityIterator = entityIterator;
    }

    public NodeListIterator(NodeList nodes, String nodeName)
    {
        this(nodes, nodeName, false);
    }

    public NodeListIterator(NodeList nodes, String nodeName, boolean entityIterator)
    {
        itr = (new ArrayList()).iterator();
        this.entityIterator = false;
        this.nodeName = nodeName;
        this.entityIterator = entityIterator;
        setNodeList(nodes);
    }

    public boolean hasNext()
    {
        return itr.hasNext();
    }

    public Object next()
    {
        if(!entityIterator)
            return (Node)itr.next();
        else
            return new XMLEntity((Node)itr.next());
    }

    public void remove()
    {
    }

    public int getNodeCount()
    {
        return nodeCount;
    }

    private void setNodeList(NodeList nodes)
    {
        if(nodes != null)
        {
            nodeCount = nodes.getLength();
            List l = new ArrayList();
            for(int i = 0; i < nodeCount; i++)
            {
                Node cNode = nodes.item(i);
                if(cNode == null || cNode.getNodeType() != 1)
                    continue;
                if(Strings.isNotEmpty(nodeName))
                {
                    if(cNode.getNodeName().equals(nodeName))
                        l.add(cNode);
                } else
                {
                    l.add(cNode);
                }
            }

            nodeCount = l.size();
            itr = l.iterator();
        }
    }

    private Iterator itr;
    private boolean entityIterator;
    private int nodeCount;
    private String nodeName;
}
