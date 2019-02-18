// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NamedNodeMapIterator.java

package com.sbs.espace.xml;

import java.util.Iterator;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NamedNodeMapIterator
    implements Iterator
{

    public NamedNodeMapIterator()
    {
    }

    public NamedNodeMapIterator(NamedNodeMap nodeMap)
    {
        this.nodeMap = nodeMap;
        itemCount = this.nodeMap.getLength();
    }

    public void remove()
    {
        nodeMap.removeNamedItem(nodeMap.item(currentCount).getNodeName());
    }

    public boolean hasNext()
    {
        return currentCount < itemCount;
    }

    public Object next()
    {
        return nodeMap.item(currentCount++);
    }

    private NamedNodeMap nodeMap;
    private int itemCount;
    private int currentCount;
}
