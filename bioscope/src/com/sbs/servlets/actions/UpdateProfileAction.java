// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpdateProfileAction.java

package com.sbs.servlets.actions;

import com.sbs.Application;
import com.sbs.beans.UserBean;
import com.sbs.utils.AppLogger;
import java.sql.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.config.ForwardConfig;

public class UpdateProfileAction extends Action
{

    public UpdateProfileAction()
    {
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    {
        UserBean ub = (UserBean)request.getSession().getAttribute("valid_user");
        ActionForward nextPage = mapping.findForward("updateProfile");
        Connection c = null;
        Statement stmt = null;
        try
        {
            c = Application.getDatabaseConnectorFor("biocis");
            stmt = c.createStatement();
            ub.loadAllFields(stmt);
            String regPage = mapping.findForward("userReg").getPath();
            StringBuffer sb = new StringBuffer(regPage);
            sb.append("?email=");
            sb.append(ub.getEmailID());
            sb.append("&");
            sb.append("passwd=");
            sb.append(ub.getPassword());
            sb.append("&");
            sb.append("lName=");
            sb.append(ub.getLastName());
            sb.append("&");
            sb.append("fName=");
            sb.append(ub.getFirstName());
            sb.append("&");
            sb.append("nName=");
            sb.append(ub.getNickName());
            sb.append("&");
            sb.append("industry=");
            sb.append(ub.getIndustry());
            sb.append("&");
            sb.append("howFound=");
            sb.append(ub.getHowFound());
            if(ub.isNotifyProgress())
            {
                sb.append("&");
                sb.append("notifyProgress");
                sb.append("on");
                sb.append("&");
            }
            nextPage = new ActionForward(sb.toString(), false);
        }
        catch(Exception ex)
        {
            logger.error("User registration failed: ".concat(String.valueOf(String.valueOf(ex.getMessage()))), ex);
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
