// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AccessControlFilter.java

package com.sbs.servlets.filters;

import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;
import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.*;
import javax.servlet.http.*;

public class AccessControlFilter
    implements Filter
{

    public AccessControlFilter()
    {
        config = null;
    }

    public void init(FilterConfig config)
        throws ServletException
    {
        this.config = config;
        loginPage = config.getInitParameter("loginPage");
        if(Strings.isEmpty(loginPage))
        {
            throw new ServletException("loginPage init param missing");
        } else
        {
            logger.info("Deployed access control successfuly.");
            return;
        }
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws ServletException, IOException
    {
        HttpServletRequest httpReq = (HttpServletRequest)req;
        HttpServletResponse httpResp = (HttpServletResponse)resp;
        if(!isAuthenticated(httpReq))
        {
            String forwardURI = getForwardURI(httpReq);
            ServletContext sc = config.getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(forwardURI);
            if(rd == null)
                httpResp.sendError(500, "Login page doesn't exist");
            logger.debug("User not authenticated, forwarding request to login page: ".concat(String.valueOf(String.valueOf(httpReq.getRequestURI()))));
            rd.forward(req, resp);
            return;
        } else
        {
            chain.doFilter(req, resp);
            return;
        }
    }

    public void destroy()
    {
        config = null;
    }

    private String getForwardURI(HttpServletRequest req)
    {
        StringBuffer uri = new StringBuffer(loginPage);
        uri.append("?errorMsg=Please+log+in+first&origURL=").append(URLEncoder.encode(getContextRelativeURI(req)));
        return uri.toString();
    }

    private boolean isAuthenticated(HttpServletRequest req)
    {
        boolean authenticated = false;
        HttpSession session = req.getSession();
        if(session.getAttribute("valid_user") != null)
            authenticated = true;
        return authenticated;
    }

    private String getContextRelativeURI(HttpServletRequest req)
    {
        int ctxPathLength = req.getContextPath().length();
        String reqURI = req.getRequestURI();
        StringBuffer uri = new StringBuffer(reqURI.substring(ctxPathLength));
        String query = req.getQueryString();
        if(Strings.isNotEmpty(query))
            uri.append("?").append(query);
        return uri.toString();
    }

    private static AppLogger logger;
    private FilterConfig config;
    private String loginPage;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.servlets.filters.AccessControlFilter.class);
    }
}
