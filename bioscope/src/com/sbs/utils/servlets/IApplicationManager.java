// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IApplicationManager.java

package com.sbs.utils.servlets;

import java.util.List;
import java.util.Map;

// Referenced classes of package com.sbs.utils.servlets:
//            IScope

public interface IApplicationManager
    extends IScope
{

    public abstract List listCurrentSessions();

    public abstract Map getAllAttributes();

    public abstract Map getAllAttributes(short word0);

    public abstract void removeAllAttributes(short word0);

    public abstract void removeAttribute(String s, short word0);

    public abstract void removeSession(String s);

    public abstract void lockUser(String s);
}
