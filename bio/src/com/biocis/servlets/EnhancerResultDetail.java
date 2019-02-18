package com.biocis.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.sbs.utils.servlets.Servlets;
import org.w3c.dom.Document;
import com.sbs.ds.FileDataSource;
import com.sbs.utils.xml.XMLParser;
import javax.servlet.ServletException;
import java.io.File;
import com.sbs.utils.xml.XMLUtils;
import java.util.Map;
import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.*;
import com.sbs.utils.AppLogger;
import com.sbs.ds.IDataSource;
import javax.servlet.RequestDispatcher;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class EnhancerResultDetail extends HttpServlet implements IServletConstants {
    static AppLogger logger = AppLogger.getLogger(EnhancerResultDetail.class);

    public EnhancerResultDetail() {
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String httpCodebase = Servlets.getApplicationInitParam(request, HTTP_CODEBASE);
        String resBaseDir = Servlets.getApplicationInitParam(request, this.GBROWSER_LOCATION);
        String chromosome = request.getParameter("chromosome");
        String sessionID = request.getSession().getId();

        String detailDir = resBaseDir + sessionID+  "/" + chromosome + "/";

        String finalResFlName = detailDir + chromosome +".html";
        File fl = new File(finalResFlName);
        byte[] res = null;
        try {
            if (!fl.exists()) {
                Map params = new HashMap();
                params.put("codebase", httpCodebase);

                String resFileName = detailDir + "index.xml";
                Document srcDom = XMLParser.getDOMParser().parse(new FileDataSource(resFileName).getInputStream());
                srcDom.normalize();
                Document xsltDom = this.getXSLT(request, httpCodebase);
                xsltDom.normalize();
                res = XMLUtils.transform(srcDom, xsltDom, false, params);
                try {
                    IDataSource ds = new FileDataSource(finalResFlName);
                    ds.getOutputStream().write(res);
                }
                catch (IOException ex) {
                    logger.warn("Error saving final result file: " + fl.getAbsolutePath(), ex);
                }
            }
            else {
                IDataSource ds = new FileDataSource(finalResFlName);
                res = ds.getContent();
            }
            request.setAttribute("result", new String(res));
            request.setAttribute("chromosome", chromosome);
            RequestDispatcher rd = this.getServletContext().getRequestDispatcher(this.getInitParameter("jsp"));
            rd.forward(request, response);
        }
        catch (Exception ex) {
             throw new ServletException(ex);
        }
    }

    private Document getXSLT(HttpServletRequest request, String httpCodebase) throws Exception {
        Document xsltDom = (Document)Servlets.getAttribute(request, ENHANCER_DETAIL_XSLT, Servlets.APPLICATION);
        if (xsltDom==null) {
            String xslt = this.getInitParameter("xslt");
            if (httpCodebase.endsWith("/")) {
                xslt = httpCodebase.substring(0, httpCodebase.lastIndexOf("/"))+xslt;
            }
            else {
                xslt = httpCodebase+xslt;
            }

            xsltDom = XMLParser.getDOMParser().parse(new FileDataSource(xslt).getInputStream());
            Servlets.setAttribute(request, this.ENHANCER_DETAIL_XSLT, xsltDom, Servlets.SESSION);
        }
        return xsltDom;
    }
}