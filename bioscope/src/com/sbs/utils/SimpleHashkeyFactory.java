// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SimpleHashkeyFactory.java

package com.sbs.utils;


// Referenced classes of package com.sbs.utils:
//            IHashkeyFactory

public class SimpleHashkeyFactory
    implements IHashkeyFactory
{

    public SimpleHashkeyFactory()
    {
    }

    public Object createKey(Object obj)
    {
        if(obj != null)
            return obj.toString().toLowerCase();
        else
            return null;
    }
}
