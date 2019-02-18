// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LogoutAction.java

package com.sbs.servlets.actions;

import com.sbs.beans.UserBean;
import com.sbs.utils.AppLogger;
import javax.servlet.http.*;
import org.apache.struts.action.*;

public class LogoutAction extends Action
{

    public LogoutAction()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        ActionForward nextPage = mapping.findForward("main");
        UserBean ub = (UserBean)request.getSession().getAttribute("valid_user");
        request.getSession().removeAttribute("valid_user");
        logger.info("Logged out user: ".concat(String.valueOf(String.valueOf(ub.getEmailID()))));
        return nextPage;
    }

    private static AppLogger logger;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.servlets.actions.LogoutAction.class);
    }
}
