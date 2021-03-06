
package com.biocis.servlets;

import java.lang.* ;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.biocis.utils.* ;
import com.sbs.utils.AppLogger;
import com.sbs.utils.servlets.Servlets;
import com.sbs.Application;
import com.sbs.utils.string.Strings;
import com.biocis.beans.EnhancerSearchBean;
import com.biocis.beans.SearchBean;
import com.sbs.espace.DataTypeValidator;
import com.sbs.IDictionary;
import com.sbs.beans.UserBean;



public class SuperEnhancerServlet extends HttpServlet implements IServletConstants {
    static private AppLogger logger = AppLogger.getLogger(SuperEnhancerServlet.class);

    //    ResourceBundle rb = ResourceBundle.getBundle("LocalStrings");

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        Application.stopExecution(request.getSession().getId()); //Stopping previous search in this session

        Servlets.removeAttribute(request, COMPLETED, Servlets.SESSION);

        //Servlets.removeAttribute(request, IServletConstants.STOP_SEARCH, Servlets.REQUEST);

        String organism = request.getParameter(IServletConstants.ORGANISM);
        logger.debug("doGet called from host:" + request.getRemoteHost() + ", requestURI: " + request.getRequestURI() + ", for organism: " + organism);
        if (Strings.isEmpty(organism)) {
            logger.info("Can't proceed with request, no organism specified, forwarding the request to enhancerRefresh.");
            RequestDispatcher rd = request.getRequestDispatcher("enhancerRefresh");
            rd.forward(request, response);
        }

        String configDirectory = Servlets.getApplicationInitParam(request, GBROWSER_LOCATION);
        List l = getChromosomeList(configDirectory + request.getParameter(IServletConstants.ORGANISM), request, response.getWriter());

        Servlets.setAttribute(request, IServletConstants.ORGANISM, organism, Servlets.SESSION);

        String physicalPath = request.getSession().getServletContext().getRealPath(request.getServletPath());

        PrintWriter out = response.getWriter () ;

        setConfigFiles (request, response, out) ;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }

    public void setConfigFiles (HttpServletRequest request, HttpServletResponse response, PrintWriter out) throws IOException, ServletException
    {
        String configDirectory = Servlets.getApplicationInitParam(request, GBROWSER_LOCATION);
        logger.debug("configDirectory is: " + configDirectory);

        SearchBean searchBean = this.createSearchBean(request);

        String organism = request.getParameter("organism");

        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(3600) ;
        if(!session.isNew()) {
            sessionDestroyed(request) ;
        }

        String sessionId = session.getId();
        String configFileDirectory = configDirectory + sessionId;

        logger.debug("configFileDirectory is: " + configFileDirectory);

        String organismListFile = configDirectory + organism ;

        logger.debug("organismListFile is: " + organismListFile);

        boolean cache = searchBean.getUseCache();

        String doCache = cache==true?"1":"0";

        String replaceString =null;

        File orgFile = new File(organismListFile + ".info");
        logger.debug("OrgFile name is: " + orgFile.getAbsolutePath());

        StringBuffer errorBuffer = new StringBuffer();

        if (orgFile.exists()) {
            FileReader fr = new FileReader (orgFile);
            BufferedReader in = new BufferedReader (fr);
            replaceString = in.readLine();
            fr.close() ;
        }
        else {
            throw new ServletException("OrganismListFile not found at location: " + orgFile.getAbsolutePath());
        }

        List chList = getChromosomeList (organismListFile, request, out) ;

        writeConfigFile(request, configDirectory, searchBean);

        cleanup(configFileDirectory, chList);

        logger.debug("Going to check the grammer...");

        SuperEnhancerCheck.check(configFileDirectory + "/config.txt", "", session.getId(), "");

        if (new File (configFileDirectory +  "/config.err").exists()) {
            displayError(errorBuffer, request, response, out);
        }
        else {
            logger.debug("Grammer OK");
        }

        SuperEnhancerSystem eSystem = new SuperEnhancerSystem (configFileDirectory, chList, session.getId(), doCache, replaceString, organism);

        try {
            logger.debug("Calling Application to execute: " + eSystem);
            Application.execute(eSystem, sessionId);
        }
        catch (InterruptedException ex) {
            logger.error(ex);
            throw new ServletException(ex);
        }

        logger.debug("Going to forward the request to: enhancerRefresh");
        response.sendRedirect("enhancerRefresh");
    }

    private void cleanup(String configFileDirectory, List chList) {
        File errFile = new File( configFileDirectory + "/config.err") ;

        if (errFile.exists()) {
            errFile.delete() ;
        }

        for (int i=0; i<chList.size(); i++) {
            File removeFile = new File (configFileDirectory + "/" + (String)chList.get(i) + "/" + (String)chList.get(i) + ".done");
            if (removeFile.exists()) {
                removeFile.delete() ;
            }

            removeFile = new File( configFileDirectory + "/" + (String)chList.get(i) + "/" + (String)chList.get(i) + ".nores");
            if (removeFile.exists()) {
                removeFile.delete() ;
            }
        }
    }

    private void displayError(StringBuffer errorBuffer, HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        errorBuffer.append("<br>");
        errorBuffer.append("You have errors in the logical expression<br>") ;
        errorBuffer.append("make sure you have nested them in parenthesis properly <br>") ;
        errorBuffer.append("Some examples are <br>") ;
        errorBuffer.append("(1A & 1B) will search for presence of atleast 1 of A and B <br>") ;
        errorBuffer.append("(1A & (1B | 1C)) will search for presence of atleast 1 of A with B or C <br>") ;
        errorBuffer.append("(1A & (1B & 1C)) will search for presence of atleast 1 of A and B and C <br>") ;
        this.displayErrorMsg(errorBuffer.toString(), request, response, out);
        return ;
    }

    private SearchBean createSearchBean(HttpServletRequest request) {
        SearchBean searchBean = (SearchBean)Servlets.getAttribute(request, IServletConstants.SEARCH_BEAN, new EnhancerSearchBean(), Servlets.SESSION);

        searchBean.setName(request.getParameter(SearchBean.SEARCH_NAME));
        searchBean.setOrganism(request.getParameter(SearchBean.ORGANISM));

        String value = request.getParameter(SearchBean.NO_OF_SITES);
        if (Strings.isNotEmpty(value)) {
            searchBean.setNoOfSites(Integer.parseInt(value));
        }

        searchBean.setOverlapSites(request.getParameter(SearchBean.OVERLAP_SITES));

        value = request.getParameter(SearchBean.WIDTH);
        if (Strings.isNotEmpty(value)) {
            searchBean.setWidth(Integer.parseInt(value));
        }

        value = request.getParameter(SearchBean.MIN);
        if (Strings.isNotEmpty(value)) {
            searchBean.setMin(Integer.parseInt(value));
        }

        value = request.getParameter(SearchBean.MAX);
        if (Strings.isNotEmpty(value)) {
            searchBean.setMax(Integer.parseInt(value));
        }

        searchBean.setCondition(request.getParameter(SearchBean.CONDITION));
        searchBean.setOrderOfSites(request.getParameter(SearchBean.ORDER_OF_SITES));

        searchBean.setUseCache(DataTypeValidator.getBoolean(request.getParameter(SearchBean.USE_CACHE)));

        value = request.getParameter(SearchBean.WIDTH_TO_INCLUDE);
        if (Strings.isNotEmpty(value)) {
            searchBean.setWidthToInclude(Integer.parseInt(value));
        }

        searchBean.setGeneNames(request.getParameter(SearchBean.GENE_NAMES));
        searchBean.setGeneOrientation(request.getParameter(SearchBean.GENE_ORIENTATION));
        searchBean.setAnyGene(DataTypeValidator.getBoolean(request.getParameter(SearchBean.ANY_GENE)));

        fillMotifTags(request, searchBean);

        Servlets.setAttribute(request, IServletConstants.SEARCH_BEAN, searchBean, Servlets.SESSION);

        return searchBean;
    }

    private void fillMotifTags(HttpServletRequest request, SearchBean searchBean) {
        searchBean.clearTags();
        for (int i = 'A'; i<='J'; i++) {
            char c = (char)i;
            String tag = c+"";

            String name = request.getParameter("name"+c);
            String pattern = request.getParameter(tag);
            if (Strings.isNotEmpty(name) && Strings.isNotEmpty(pattern)) {
                String overlap = request.getParameter(c+"O");
                Map map = new HashMap();
                map.put(SearchBean.TAG, tag);
                map.put(SearchBean.TAG_NAME, name);
                map.put(SearchBean.TAG_PATTERH, pattern.toUpperCase());
                map.put(SearchBean.TAG_OVERLAP, overlap);
                searchBean.addTag(map);
            }
        }
    }

    private void displayErrorMsg(String msg, HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        request.setAttribute("errorMsg", msg);
        try {
            RequestDispatcher rd = request.getRequestDispatcher("/views/jsps/errorpage.jsp");
            rd.forward(request, response);
        }
        catch (Exception ex) {
            out.println ("<html>") ;
            out.println("<body bgcolor=\"white\">");
            out.println("<head>");

            String title = "Enhancer Error Page" ;

            out.println("<title>" + title + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(msg);
            out.println("</body>") ;
            out.println("</html>") ;
        }
    }


    private void writeConfigFile (HttpServletRequest request, String configDir, SearchBean sb) throws IOException
    {
        String searchName = sb.getName();
        logger.debug("The name of search is: " + searchName);

        StringBuffer buffer = new StringBuffer();
        List tags = sb.getTags();
        fillExpressions(buffer, tags);
        fillOverlaps(buffer, tags);
        fillMotifNames(buffer, tags);

        buffer.append("<organism>\n").append("name=").append(sb.getOrganism()).append("\n").append("</organism>\n");
        buffer.append("<min>\n").append("name=").append(sb.getMin()==0?"":sb.getMin()+"").append("\n").append("</min>\n");
        buffer.append("<max>\n").append("name=").append(sb.getMax()==0?"":sb.getMax()+"").append("\n").append("</max>\n");
        buffer.append("<directory>\n").append("name=").append(configDir).append("\n").append("</directory>\n");
        buffer.append("<sites>\n").append("number=").append(sb.getNoOfSites()).append("\n").append("</sites>\n");
        buffer.append("<constraint>\n").append("width=").append(sb.getWidth()).append("\n").append("</constraint>\n");
        buffer.append("<boolean>\n").append("condition=").append(sb.getCondition()).append("\n").append("</boolean>\n");
        buffer.append("<display>\n").append("width=").append(sb.getWidthToInclude()).append("\n").append("</display>\n");
        buffer.append("<direction>\n").append("value=").append(sb.getGeneOrientation()).append("\n").append("</direction>\n");
        buffer.append("<nooverlap>\n").append("list=").append(sb.getOverlapSites()).append("\n").append("</nooverlap>\n");
        buffer.append("<genenames>\n").append("list=").append(sb.getGeneNames()).append("\n").append("</genenames>\n");
        buffer.append("<anygene>\n").append("value=").append(sb.getAnyGene()==true?1:0).append("\n").append("</anygene>\n");
        String orderOfSites = sb.getOrderOfSites();
        if (orderOfSites!=null && orderOfSites.trim().length()!=0) {
            orderOfSites = orderOfSites.replaceAll("<", "&lt;");
            orderOfSites = orderOfSites.replaceAll(">", "&gt;");
        }
	
	//        buffer.append("<precedences>\n").append("value=").append(sb.getOrderOfSites()).append("\n").append("</precedences>\n");  
        buffer.append("<precedences>\n").append("value=").append(orderOfSites).append("\n").append("</precedences>\n");

        // String userName = ((UserBean)Servlets.getAttribute(request, IDictionary.VALID_USER)).getName();
	String userName = "biocis@biocis.net" ;

        /**@todo Remove the substring once native code starts supporting @ symbol */
        userName = userName.substring(0, userName.indexOf('@'));

        buffer.append("<save>\n").append("user=").append(userName).append("\n").append("name=").append(sb.getName()).append("\n</save>\n");

        Servlets.setAttribute(request, IServletConstants.SEARCH_INFO, buffer, Servlets.SESSION);

        File file = new File (configDir+"/"+request.getSession().getId()+"/");

        if (!file.exists() || !file.canRead()) {
            file.mkdirs();
        }

        PrintWriter br = new PrintWriter(new FileWriter(file+"/config.txt"));
        br.print(buffer.toString()) ;
        br.flush();
        br.close();
    }

    private void fillExpressions(final StringBuffer sb, final List tags) {
        sb.append("<expressions>\n");
        for (int i=0; i<tags.size(); i++) {
            Map map = (Map)tags.get(i);
            sb.append(map.get(SearchBean.TAG)).append("=").append(map.get(SearchBean.TAG_PATTERH)).append("\n");
        }
        sb.append("</expressions>\n");
    }

    private void fillOverlaps(final StringBuffer sb, final List tags) {
        sb.append("<overlap>\n");
        for (int i=0; i<tags.size(); i++) {
            Map map = (Map)tags.get(i);
            sb.append(map.get(SearchBean.TAG)).append("=");
            String overlap = (String)map.get(SearchBean.TAG_OVERLAP);
            if (Strings.isNotEmpty(overlap)) {
                sb.append(1);
            }
            else {
                sb.append(0);
            }
            sb.append("\n");
        }
        sb.append("</overlap>\n");
    }

    private void fillMotifNames(final StringBuffer sb, final List tags) {
        sb.append("<motifnames>\n");
        for (int i=0; i<tags.size(); i++) {
            Map map = (Map)tags.get(i);
            sb.append(map.get(SearchBean.TAG)).append("=").append(map.get(SearchBean.TAG_NAME)).append("\n");
        }
        sb.append("</motifnames>\n");
    }

    public List getChromosomeList (String filename, HttpServletRequest request, PrintWriter out)
    {
        List chList = (List)Servlets.getAttribute(request, CHROMOSOME_LIST, Servlets.SESSION);
        if (chList==null) {
            chList = new ArrayList();
            File file = new File (filename) ;

            if (!file.exists() || !file.canRead())
            {
                out.println ("EnhancerExeute.java : Error in ReadChromosomeLists Module <p>") ;
                return chList;
            }
            try
            {
                FileReader fr = new FileReader (file) ;
                BufferedReader in = new BufferedReader (fr) ;
                String line ;
                try
                {
                    while ((line = in.readLine()) != null)
                    {
                        if (Strings.isNotEmpty(line)) {
                            chList.add(line) ;
                        }
                    }
                    fr.close() ;
                }
                catch (IOException e)
                {
                    logger.error(e);
                    out.println ("SuperEnhancerServlet.java : IO error occurred " + file + "<p>") ;
                }
            }
            catch (FileNotFoundException e)
            {
                logger.error(e);
                out.println ("SuperEnhancerServlet.java : File not found " + file + "<p>") ;
            }
            Servlets.setAttribute(request, CHROMOSOME_LIST, chList, Servlets.SESSION);
        }
        return chList;
    }

    public void sessionDestroyed(HttpServletRequest request) {

        String BaseDir = Servlets.getApplicationInitParam(request, GBROWSER_LOCATION);
        String id = request.getSession().getId();
        File RemoveDir = new File(BaseDir + id);
        File ConfDir = new File("/usr/local/apache/conf/gbrowse.conf/") ;
        if (!deleteDir(RemoveDir))
        {
            System.out.println ("Deleting " + RemoveDir + "......") ;
            //		log ("Session Directory Removal failed (" + ses.getId() + "')") ;
        }

        deleteConfFiles(ConfDir, id) ;
    }


    // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns false.
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    private static boolean deleteConfFiles(File dir, String session) {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                if (children[i].indexOf(session) != -1)
                {
                File deleteFile = new File(dir, children[i]) ;
                deleteFile.delete() ;
            }

            }
        }
        return (true) ;
    }

    public static void main(String[] args) {
//        for (int i='A'; i<='J'; i++) {
//            char c = (char)i;
//            System.out.println(c);
//        }
        String name ="sanjeev@manage.com";
        name = name.substring(0, name.indexOf('@'));
        System.out.println(name);
    }
}