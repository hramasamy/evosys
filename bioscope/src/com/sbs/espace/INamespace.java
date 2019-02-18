// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   INamespace.java

package com.sbs.espace;

import com.sbs.utils.IPersistable;

// Referenced classes of package com.sbs.espace:
//            Namespace

public interface INamespace
    extends IPersistable
{

    public static final INamespace DEF_NAMESPACE = new Namespace("com.sbs");

}
