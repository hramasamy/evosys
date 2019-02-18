
package com.biocis.servlets ;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.sbs.utils.*;
import com.sbs.utils.servlets.*;
import com.sbs.utils.xml.*;
import org.w3c.dom.*;
import java.net.URL;
import com.sbs.ds.FileDataSource;
import java.io.StringReader;
import com.sbs.utils.string.Strings;
import com.sbs.ds.IDataSource;
import com.sbs.ds.*;
import com.biocis.beans.SearchBean;
import com.sbs.espace.DataTypeValidator;
import com.sbs.Application;


public class EnhancerRefreshServlet extends HttpServlet implements IServletConstants {
    static private AppLogger logger = AppLogger.getLogger(EnhancerRefreshServlet.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        logger.debug("doGet called...");

        if (!ifStopped(request, response)) {
            String configDirectory = this.getConfigDir(request);

            try {
                servXml(request, response, configDirectory);
            }
            catch (Exception ex) {
                logger.warn("Caught exception while serving doGet. Cause: " + ex.getMessage());
                throw new ServletException(ex);
            }
        }
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }


    /*****************************************************************************************************************
     * Private methods
     *****************************************************************************************************************/

    private void servXml(HttpServletRequest request, HttpServletResponse response, String configDirectory)
            throws IOException, Exception
    {
        String sessionId = request.getSession().getId();
        logger.debug("servXml called from host:" + request.getRemoteHost() + ", requestURI: " + request.getRequestURI());

        List chList = (List)Servlets.getAttribute(request, IServletConstants.CHROMOSOME_LIST, Servlets.SESSION);

        String organismName = checkError(request, response, configDirectory, sessionId, chList);
	
	System.out.println(" Got after Check Error " + chList) ;

        String summary = getSummaryInfo(request, organismName, chList, configDirectory);

	System.out.println(" Got after Summary Info " + chList) ;

        byte[] result = transform(request, summary);

	System.out.println(" Got after transform " + chList) ;

        request.setAttribute("result", new String(result));

        this.forwardToView(request, response);
    }

    private String getSummaryInfo(HttpServletRequest request) throws IOException {
        String configDir = Servlets.getApplicationInitParam(request, IServletConstants.GBROWSER_LOCATION);
        List chList = (List)Servlets.getAttribute(request, IServletConstants.CHROMOSOME_LIST, Servlets.SESSION);
        String organism = (String)Servlets.getAttribute(request, IServletConstants.ORGANISM, Servlets.SESSION);
	System.out.println(" Came herein get SummaryInfo") ;
        return getSummaryInfo(request, organism, chList, configDir).toString();
    }

    private String getSummaryInfo(HttpServletRequest request, String organism, List chList, String configDir)
    throws IOException
    {
        StringBuffer summary = (StringBuffer)Servlets.getAttribute(request, SUMMARY, Servlets.SESSION);
	System.out.println(" Came herein overloaded SummaryInfo") ;
        if (summary==null) {
            summary = new StringBuffer(SUMMARY_START_TAG);
            summary.append("<search>");
            summary.append(Servlets.getAttribute(request, SEARCH_INFO, Servlets.SESSION));
            summary.append("</search>");
            logger.debug("Created the summary Info: " + summary.toString());
        }

        StringBuffer baseNameB = new StringBuffer(configDir).append(request.getSession().getId()).append("/");
        String rootDir = baseNameB.toString();

        List exclude = (List)Servlets.getAttribute(request, DONE_LIST, new ArrayList(), Servlets.SESSION);

        Iterator itr = chList.iterator();

	System.out.println(" Came herein overloaded SummaryInfo Chlist iternator ") ;
        while (itr.hasNext()) {
            String chName = (String)itr.next();
            if (!exclude.contains(chName)) { //do only if it not already done.
                File resFile = new File(rootDir+chName +"_"+"summary.xml");
                if (resFile.exists()) {//summary file exist process it
                    BufferedReader reader = new BufferedReader(new FileReader(resFile));
                    String line =null;
                    while ( (line=reader.readLine())!=null) {
                        summary.append(line);
                    }
                    exclude.add(chName);
                }
            }
        }

        if (exclude.size()==chList.size()) { //All chromosmes are done
            cleanup(request);
        }
        else {
            Servlets.setAttribute(request, SUMMARY, summary, Servlets.SESSION);
            Servlets.setAttribute(request, DONE_LIST, exclude, Servlets.SESSION);
        }

        String summaryStr = summary.toString()+"</summary>";
	System.out.println(" Came herein overloaded SummaryInfo Chlist iternator " + summaryStr) ;
        return summaryStr;
    }

    private boolean ifStopped(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String ifStopped = request.getParameter(IServletConstants.STOP_SEARCH);

        if (Strings.isNotEmpty(ifStopped)) {
            boolean stopVal = DataTypeValidator.getBoolean(ifStopped);
            logger.debug("<stopVal> Got a request to stop the search from " + ifStopped + ", " + stopVal  + ", " + request.getRemoteHost());

            if (stopVal) {
                Application.stopExecution(request.getSession().getId());
            }

            request.setAttribute(IServletConstants.STOP_SEARCH, "1");

            try {
                request.setAttribute("result", new String(this.transform(request, this.getSummaryInfo(request))));
            }
            catch (Exception ex) {
                throw new ServletException(ex);
            }

            this.cleanup(request);
            this.forwardToView(request, response);
            return stopVal;
        }
        return false;
    }

    private String checkError(HttpServletRequest request, HttpServletResponse response, String configDirectory, String sessionId, List chList) throws DataSourceException, IOException, ServletException {
        boolean errorFound = false;
        StringBuffer error = new StringBuffer();

        if (chList==null) {
            if (new File (configDirectory + sessionId + "/summary.html").exists()) {
                IDataSource ids = new FileDataSource(configDirectory + sessionId + "/summary.html");
                error.append(new String(ids.getContent()));
            }
            else {
                logger.debug("Chromosome list null, forwading the appropriate page!");
                error.append("<br><h2>Please specify the search criteria before using this page.</h2>");
            }
            errorFound = true;
        }

        String organismName = ((SearchBean)Servlets.getAttribute(request, IServletConstants.SEARCH_BEAN, Servlets.SESSION)).getOrganism();

        if (Strings.isEmpty(organismName)) {
            organismName = (String)Servlets.getAttribute(request, IServletConstants.ORGANISM);

            if (Strings.isEmpty(organismName)) {
                errorFound = true;
                error.append("<br><h2>Please specify the search criteria before using this page.</h2>");
            }
        }

        if (errorFound) {
            request.setAttribute("result", error.toString());
            request.setAttribute(COMPLETED, "1");
            this.forwardToView(request, response);
        }

        return organismName;
    }

    private void forwardToView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jspFileName = this.getInitParameter("jsp");
        logger.debug("Forwarding the request to " + jspFileName);
        RequestDispatcher rd = this.getServletContext().getRequestDispatcher(jspFileName);
        rd.forward(request,response);
    }

    private byte[] transform(HttpServletRequest request, String srcDomContent)
    throws Exception
    {
        String sessionId = request.getSession().getId();
        Document srcDom = XMLParser.getDOMParser().parse(new StringReader(srcDomContent));
        return this.transform(request, this.getConfigDir(request), sessionId, srcDom);
    }

    private byte[] transform(HttpServletRequest request, String configDirectory, String sessionId, Document srcDom)
    throws Exception
    {
        String httpCodebase = Servlets.getApplicationInitParam(request, IServletConstants.HTTP_CODEBASE);

        Map params = getXSLTParams(request);

        Document xsltDom = getXSLTDom(request, httpCodebase);

        byte[] result = XMLUtils.transform(srcDom, xsltDom, false, params);

        if (request.getAttribute(IServletConstants.COMPLETED)!=null) { //Write the final result to summary.html
            try {
                IDataSource ds = new FileDataSource(configDirectory + sessionId + "/summary.html");
                OutputStream os = ds.getOutputStream();
                os.write(result);
                os.flush();
                os.close();
            }
            catch (Exception ex) {
                logger.warn("Error saving the final summary result: " + ex.getMessage(), ex);
            }
        }
        return result;
    }

    private Map getXSLTParams(HttpServletRequest request) {
        Map params = (Map)Servlets.getAttribute(request, IServletConstants.XSLT_PARAMS, Servlets.SESSION);

        if (params==null) {
            String httpCodebase = Servlets.getApplicationInitParam(request, IServletConstants.HTTP_CODEBASE);
            SearchBean bean = (SearchBean)Servlets.getAttribute(request, IServletConstants.SEARCH_BEAN, Servlets.SESSION);

            params = new HashMap();
            params.put("codebase", httpCodebase);
            params.put("noOfSites", bean.getNoOfSites()+"");
            params.put("noOfBases", bean.getNoOfSites()+"");
            params.put("logicalConstraints", bean.getCondition());
            params.put("geneConstraints", bean.getOrderOfSites());
            params.put("orientation", bean.getGeneOrientation());

            String appBase = Servlets.getApplicationBase(request);
            String detailServlet = appBase;
            if (appBase.endsWith("/")) {
                detailServlet = appBase.substring(0, appBase.lastIndexOf("/"));
            }
            detailServlet += this.getInitParameter("detail");

            params.put("detail", detailServlet);
            Servlets.setAttribute(request, IServletConstants.XSLT_PARAMS, params, Servlets.SESSION);
            logger.debug("Setup Summary XSLT param completed.");
        }

        return params;
    }

    private Document getXSLTDom(HttpServletRequest request, String httpCodebase) throws Exception {
        Document xsltDom = (Document)Servlets.getAttribute(request, ENHANCER_XSLT, Servlets.APPLICATION);
        if (xsltDom==null) {
            String xslt = this.getInitParameter("xslt");
            if (httpCodebase.endsWith("/")) {
                xslt = httpCodebase.substring(0, httpCodebase.lastIndexOf("/"))+xslt;
            }
            else {
                xslt = httpCodebase+xslt;
            }

            xsltDom = XMLParser.getDOMParser().parse(new FileDataSource(xslt).getInputStream());
            Servlets.setAttribute(request, ENHANCER_XSLT, xsltDom, Servlets.APPLICATION);
            logger.debug("Setup SummaryXSLT completed");
        }
        return xsltDom;
    }

    private void cleanup(HttpServletRequest request) {
        Servlets.removeAttribute(request, SUMMARY, Servlets.SESSION);
        Servlets.removeAttribute(request, DONE_LIST, Servlets.SESSION);
        Servlets.removeAttribute(request, CHROMOSOME_LIST, Servlets.SESSION);
        Servlets.removeAttribute(request, IServletConstants.XSLT_PARAMS, Servlets.SESSION);
        request.setAttribute(COMPLETED, "1");//to indicate that search is complete
    }

    private String getConfigDir(HttpServletRequest request) {
        return Servlets.getApplicationInitParam(request, IServletConstants.GBROWSER_LOCATION);
    }
}