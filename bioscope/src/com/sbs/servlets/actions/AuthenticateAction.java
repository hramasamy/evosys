// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AuthenticateAction.java

package com.sbs.servlets.actions;

import com.sbs.Application;
import com.sbs.beans.UserBean;
import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.config.ForwardConfig;

public class AuthenticateAction extends Action
{

    public AuthenticateAction()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        String userName = request.getParameter("user_name");
        String pwd = request.getParameter("password");
        ActionForward nextPage = mapping.findForward("main");
        UserBean user = null;
        user = Application.authenticate(userName, pwd);
        if(user != null)
        {
            HttpSession session = request.getSession();
            session.setAttribute("valid_user", user);
            setLoginCookies(request, response, userName, pwd);
            String next = request.getParameter("origURL");
            logger.debug("the page where this page is called: ".concat(String.valueOf(String.valueOf(next))));
            if(!Strings.isEmpty(next))
                nextPage = new ActionForward(next, true);
        } else
        {
            String loginPage = mapping.findForward("login").getPath();
            String loginUrl = String.valueOf(String.valueOf(loginPage)).concat("?errorMsg=Invalid+User+Name+or+Password");
            nextPage = new ActionForward(loginUrl, false);
        }
        return nextPage;
    }

    private void setLoginCookies(HttpServletRequest req, HttpServletResponse res, String userName, String pwd)
    {
        Cookie usrNmCookie = new Cookie("user_name", userName);
        Cookie pwdCookie = new Cookie("password", pwd);
        int maxAge = 0;
        if(req.getParameter("remember") != null)
            maxAge = 0x278d00;
        usrNmCookie.setMaxAge(maxAge);
        pwdCookie.setMaxAge(maxAge);
        usrNmCookie.setPath(req.getContextPath());
        pwdCookie.setPath(req.getContextPath());
        res.addCookie(usrNmCookie);
        res.addCookie(pwdCookie);
    }

    private static AppLogger logger;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.servlets.actions.AuthenticateAction.class);
    }
}
