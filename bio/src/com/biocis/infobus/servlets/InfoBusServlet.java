package com.biocis.infobus.servlets;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import javax.servlet.http.*;
import com.biocis.infobus.*;
import com.biocis.infobus.beans.*;
import com.biocis.utils.*;
import java.util.*;
import com.biocis.*;
import com.sbs.utils.AppLogger;
import com.biocis.infobus.IDictionary;
import com.sbs.Application;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class InfoBusServlet extends HttpServlet {
  private AppLogger logger = AppLogger.getLogger(InfoBusServlet.class);

  public InfoBusServlet() {
  }

  public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    HttpSession session = req.getSession();
    InfoBusBean bean = (InfoBusBean)session.getAttribute(IDictionary.INFO_BUS_BEAN);
    if (bean==null) {
      logger.debug("InfoBusBean not found going to create a new one...");
      bean = new InfoBusBean();
      bean.setInfoBus((InfoBus)req.getAttribute(IDictionary.INFO_BUS));
      session.setAttribute(IDictionary.INFO_BUS_BEAN, bean);

      String protocol = req.getProtocol().substring(0,req.getProtocol().indexOf("/"));
      bean.setPath(protocol+"://"+req.getHeader("Host")+req.getContextPath());
    }

    if (getServletContext().getAttribute(IDictionary.HOST_NAME)!=null) {
      getServletContext().setAttribute(IDictionary.HOST_NAME, req.getServerName());
      getServletContext().setAttribute(IDictionary.PORT, new Integer(req.getServerPort()));
    }

    Map params = req.getParameterMap();
    if (params.containsKey(IDictionary.QUERY)) {
      Object q = params.get(IDictionary.QUERY);
      String query = null;
      if (q instanceof String[]) {
        String[] strArr = (String[])q;
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<strArr.length; i++) {
          sb.append(strArr[i]);
          if (i+1<strArr.length) {
            sb.append("+");
          }
        }
        query = sb.toString();
      }

      logger.debug("going to search: " + query);
      if (!query.equalsIgnoreCase(bean.getQuery())) {
        logger.debug("current query<"+ query + "> is different from beans <"+bean.getQuery()+">");
        bean.setQuery(query);
      }

      if (params.containsKey("html")) {
//        String path = "http://"+Application.getHostName()+":"+Application.getPort()+"/"+ Application.getContextName();
//        bean.setPath(path);
        bean.setHTMLFormat(true);
      }

      if (params.containsKey(IDictionary.SEARCH_COUNT)) {
        logger.debug("Found Search_count param: " + ((String[])params.get(IDictionary.SEARCH_COUNT))[0]);
        bean.setResultCount(Integer.parseInt(((String[])params.get(IDictionary.SEARCH_COUNT))[0]));
      }
      if (params.containsKey(IDictionary.SEARCH_START_INDEX)) {
        logger.debug("Found Search_start_index param: " + ((String[])params.get(IDictionary.SEARCH_START_INDEX))[0]);
        bean.setStartIndex(Integer.parseInt(((String[])params.get(IDictionary.SEARCH_START_INDEX))[0]));
      }

      //this.getServletContext().getRequestDispatcher("/jsps/infoBus.jsp").forward(req,res);

      try {

        String result = (String)bean.getResult();
        res.getWriter().write(result);
        res.flushBuffer();
      }
      catch (InfoBusException ex) {
        throw new ServletException("Error searching: " + query, ex);
      }
    }
//    Iterator itr = params.keySet().iterator();
//    while (itr.hasNext()) {
//      Object k = itr.next();
//      logger.debug("key: " + k + "\tvalue: " + params.get(k));
//    }
//
//    req.getRequestDispatcher("/jsps/infoBus.jsp").forward(req, res);
  }
}