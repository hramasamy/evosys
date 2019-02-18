// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Servlets.java

package com.sbs.utils.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// Referenced classes of package com.sbs.utils.servlets:
//            IScope

public class Servlets
    implements IScope
{

    public Servlets()
    {
    }

    public static String getApplicationBase(HttpServletRequest request)
    {
        String reqURI = request.getRequestURI();
        String appBase = reqURI.substring(0, reqURI.lastIndexOf("/"));
        return appBase;
    }

    public static String getApplicationFullPath(HttpServletRequest request)
    {
        StringBuffer buffer = new StringBuffer(request.getProtocol());
        buffer.append("//").append(request.getServerName()).append(":").append(request.getServerPort()).append(getApplicationBase(request)).append("/");
        return buffer.toString();
    }

    public static String getPhysicalPath(HttpServletRequest request)
    {
        String physicalPath = request.getSession().getServletContext().getRealPath(request.getServletPath());
        return physicalPath;
    }

    public static void setAttribute(HttpServletRequest request, String attrName, Object attrValue, short scope)
    {
        switch(scope)
        {
        case 0: // '\0'
            request.getSession().getServletContext().setAttribute(attrName, attrValue);
            break;

        case 1: // '\001'
            request.getSession().setAttribute(attrName, attrValue);
            break;

        case 2: // '\002'
            request.setAttribute(attrName, attrValue);
            break;
        }
    }

    public static Object getAttribute(HttpServletRequest request, String attrName)
    {
        return getAttribute(request, attrName, null);
    }

    public static Object getAttribute(HttpServletRequest request, String attrName, Object defaultValue)
    {
        Object value = getAttribute(request, attrName, (short)2);
        if(value == null)
        {
            value = getAttribute(request, attrName, (short)1);
            if(value == null)
                value = getAttribute(request, attrName, (short)0);
        }
        if(value == null)
            value = defaultValue;
        return value;
    }

    public static Object getAttribute(HttpServletRequest request, String attrName, Object defaultValue, short scope)
    {
        Object value = null;
        switch(scope)
        {
        case 0: // '\0'
            value = request.getSession().getServletContext().getAttribute(attrName);
            break;

        case 1: // '\001'
            value = request.getSession().getAttribute(attrName);
            break;

        case 2: // '\002'
            value = request.getAttribute(attrName);
            break;
        }
        if(value == null)
            value = defaultValue;
        return value;
    }

    public static Object getAttribute(HttpServletRequest request, String attrName, short scope)
    {
        return getAttribute(request, attrName, null, scope);
    }

    public static Object removeAttribute(HttpServletRequest request, String attrName)
    {
        Object value = removeAttribute(request, attrName, (short)2);
        if(value == null)
        {
            value = removeAttribute(request, attrName, (short)1);
            if(value == null)
                value = removeAttribute(request, attrName, (short)0);
        }
        return value;
    }

    public static Object removeAttribute(HttpServletRequest request, String attrName, short scope)
    {
        Object value = null;
        switch(scope)
        {
        case 0: // '\0'
            value = request.getSession().getServletContext().getAttribute(attrName);
            request.getSession().getServletContext().removeAttribute(attrName);
            break;

        case 1: // '\001'
            value = request.getSession().getAttribute(attrName);
            request.getSession().removeAttribute(attrName);
            break;

        case 2: // '\002'
        default:
            value = request.getAttribute(attrName);
            request.removeAttribute(attrName);
            break;
        }
        return value;
    }

    public static String getApplicationInitParam(HttpServletRequest request, String attrName)
    {
        return request.getSession().getServletContext().getInitParameter(attrName);
    }
}
