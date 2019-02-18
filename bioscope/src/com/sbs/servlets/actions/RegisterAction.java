// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RegisterAction.java

package com.sbs.servlets.actions;

import com.sbs.Application;
import com.sbs.beans.UserBean;
import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;
import java.sql.*;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.config.ForwardConfig;

public class RegisterAction extends Action
{

    public RegisterAction()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        UserBean ub = (UserBean)request.getSession().getAttribute("valid_user");
        boolean newReg = false;
        if(ub == null)
        {
            ub = new UserBean();
            newReg = true;
        }
        ub.setEmailID(request.getParameter("email"));
        ub.setPassword(request.getParameter("passwd"));
        ub.setLastName(request.getParameter("lName"));
        ub.setFirstName(request.getParameter("fName"));
        String nName = request.getParameter("nName");
        if(Strings.isEmpty(nName))
            nName = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(request.getParameter("lName"))))).append(", ").append(request.getParameter("fName"))));
        ub.setNickName(nName);
        ub.setIndustry(request.getParameter("industry").trim());
        ub.setHowFound(request.getParameter("howFound"));
        String notifyProg = request.getParameter("notifyProgress");
        if(!"on".equalsIgnoreCase(notifyProg))
            ub.setNotifyProgress(false);
        logger.debug("Created the UserBean from the request: ".concat(String.valueOf(String.valueOf(ub))));
        ActionForward nextPage = mapping.findForward("login");
        Connection c = null;
        Statement stmt = null;
        try
        {
            c = Application.getDatabaseConnectorFor("biocis");
            stmt = c.createStatement();
            if(newReg && ub.doesExist(stmt))
            {
                String regPage = mapping.findForward("userReg").getPath();
                String regUrl = String.valueOf(String.valueOf(regPage)).concat("?errorMsg=Email already exists please choose another email. If you have forgotten the password please visit forgot password page.");
                nextPage = new ActionForward(regUrl, false);
                ActionForward actionforward = nextPage;
                return actionforward;
            }
            ub.save(stmt, newReg);
            logger.debug("User update successful.");
        }
        catch(Exception ex)
        {
            logger.error("User update failed: ".concat(String.valueOf(String.valueOf(ex.getMessage()))), ex);
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                if(c != null)
                    c.close();
            }
            catch(SQLException sqlexception) { }
        }
        return nextPage;
    }

    private static AppLogger logger;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.servlets.actions.RegisterAction.class);
    }
}
