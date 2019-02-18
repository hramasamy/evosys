// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Collections.java

package com.sbs.utils.collection;

import com.sbs.utils.string.Strings;
import java.util.*;

public class Collections
{

    public Collections()
    {
    }

    public static boolean isEmpty(Collection c)
    {
        return c == null || c.size() == 0;
    }

    public static boolean contains(Collection c, String value)
    {
        if(c == null || c.size() == 0 || Strings.isEmpty(value))
            return false;
        for(Iterator itr = c.iterator(); itr.hasNext();)
            if(String.valueOf(itr.next()).trim().equals(value.trim()))
                return true;

        return false;
    }

    public static boolean contains(Collection c, Object value)
    {
        if(c == null || c.size() == 0)
            return false;
        else
            return (value instanceof String) ? contains(c, (String)value) : c.contains(value);
    }

    public static Object[] toArray(Collection c)
    {
        return c.toArray();
    }

    public static List initList(int size)
    {
        List l = new ArrayList();
        for(int i = 0; i < size; i++)
            l.add(null);

        return l;
    }

    public static final Iterator EMPTY_ITERATOR = (new ArrayList()).iterator();
    public static final List EMPTY_LIST = new ArrayList(0) {

        public Iterator iterator()
        {
            return Collections.EMPTY_ITERATOR;
        }

    };

}
