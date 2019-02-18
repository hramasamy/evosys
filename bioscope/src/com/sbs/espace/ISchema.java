// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISchema.java

package com.sbs.espace;

import java.util.Iterator;

public interface ISchema
{

    public abstract String getNamespace();

    public abstract void setNamespace(String s);

    public abstract String getName();

    public abstract String getParentName();

    public abstract ISchema getParent();

    public abstract void setParent(ISchema ischema);

    public abstract boolean isRoot();

    public abstract boolean isLeaf();

    public abstract void addChildNode(ISchema ischema);

    public abstract void removeChildNode(ISchema ischema);

    public abstract Iterator getChildNodes();

    public abstract boolean hasAttributes();

    public abstract boolean hasAttribute(String s);

    public abstract boolean hasChild(String s);

    public abstract boolean hasChildren();

    public abstract Iterator getIteratorForChildNode(String s);

    public abstract Object removeAttribute(String s);

    public abstract Iterator attributes();
}
