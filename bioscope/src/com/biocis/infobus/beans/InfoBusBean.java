package com.biocis.infobus.beans;

import java.util.*;
import com.biocis.infobus.spider.*;
import com.biocis.infobus.*;
import com.sbs.utils.AppLogger;
import com.biocis.infobus.InfoBusException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.DocumentException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class InfoBusBean implements java.io.Serializable {
  static AppLogger logger = AppLogger.getLogger(InfoBusBean.class);
  static private String path;

  private String query;
  private Object result;
  transient private InfoBus infoBus;

  private int nextHowMany = 10;
  private int currentLocation = 0;
  private boolean moreTopic = false;
  private Document resultDoc = null;
  private List topics = null;
  private List results = null;
  private Document inputDoc =null;
  private boolean html;
  private ResultHolder resultHolder;

  static private String XSLT;

  private String getXSLT() {
    if (XSLT==null) {
      XSLT = "<?xml-stylesheet type=\"text/xsl\" href=\""+ path + "/htmls/xsl/giga.xsl\"?>";
    }
    return XSLT;
  }

  public InfoBusBean() {
  }

  public void setInfoBus(InfoBus searchBus) {
    this.infoBus = searchBus;
  }

  public void setHTMLFormat(boolean html) {
    this.html = html;
  }

  public InfoBus getInfoBus() {
    if (this.infoBus==null) {
      this.infoBus = InfoBus.getInfoBus();
    }
    return this.infoBus;
  }

  public void setQuery(String query) {
    logger.debug("Setting the query to: " + query);
    this.query = query;
    reset();
  }

  private void reset() {
      currentLocation =0;
      this.resultHolder =null;
      moreTopic =false;
      topics =null;
      results =null;
      inputDoc =null;
  }

  public String getQuery() {
    return query;
  }

  public void setResultCount(int resultCount) {
        logger.info("Setting howmany: " + resultCount);
    this.nextHowMany = resultCount;
  }

  public void setStartIndex(int startIndex) {
    logger.info("Setting startIndex: " + startIndex);
    this.currentLocation = startIndex;
  }

  public void setPath(String path) {
    logger.debug("Setting the context path to " + path);
    InfoBusBean.path = path;
  }

  public Object getResult() throws InfoBusException {
    this.result = next();
    logger.debug("Result for query: " + query + "\n" + result);
    return this.result;
  }

  public Object next() throws InfoBusException {
    long startTime = System.currentTimeMillis();
    if (this.hasMoreResults()) {
      ResultHolder res = this.getResultHolder();
      StringBuffer sb = new StringBuffer();
      this.addResults(sb);

      res.setResults(sb);

      logger.debug("Time taken in performing next(): " + (System.currentTimeMillis() - startTime) + " msec.");
      String result = res.toString();
      //result.replaceAll("\n\r", "");
      return res.toString();
    }
    return "";
  }

  public Object previous() throws InfoBusException {
    this.currentLocation = this.currentLocation - this.nextHowMany;
    return next();
  }

  public Object lessCategories() throws InfoBusException {
    StringBuffer sb = new StringBuffer();
    this.moreTopic = false;
    this.addTopics(sb);
    ResultHolder holder = this.getResultHolder();
    holder.setTopics(sb);
    return holder.toString();
  }

  public Object moreCategories() throws InfoBusException {
    StringBuffer sb = new StringBuffer();
    this.moreTopic = true;
    this.addTopics(sb);
    ResultHolder holder = this.getResultHolder();
    holder.setTopics(sb);

    return holder.toString();
  }

  public boolean hasMoreResults() throws InfoBusException {
    if (this.currentLocation < this.getResultList().size()) {
      return true;
    }
    return false;
  }

  private ResultHolder getResultHolder() throws InfoBusException {
    if (this.resultHolder==null) {
      long startTime = System.currentTimeMillis();
      this.resultHolder = new ResultHolder();
      this.resultHolder.query=query;
      StringBuffer sb = new StringBuffer();
      this.addTopics(sb);
      resultHolder.setTopics(sb);
      logger.debug("Time taken in creating ResultHolder: " + (System.currentTimeMillis() - startTime) + " msec.");
    }

    return this.resultHolder;
  }

  private List getTopicList() throws InfoBusException {
    if (this.topics ==null) {
      long startTime = System.currentTimeMillis();
      this.topics = this.getInputDoc().selectNodes("//topic");
      logger.debug("Time taken in getting topics<count= " + topics.size() + "> " + (System.currentTimeMillis() - startTime) + " msec.");
    }
    return this.topics;
  }

  private List getResultList() throws InfoBusException {
    if (this.results ==null) {
      long startTime = System.currentTimeMillis();
      this.results = this.getInputDoc().selectNodes("//result");
      logger.debug("Time taken in getting results<count= " + results.size() + "> " + (System.currentTimeMillis() - startTime) + " msec.");
    }
    return this.results;
  }

  private Document getInputDoc() throws InfoBusException {
    if (this.inputDoc==null) {
      try {
        Object result = this.getInfoBus().search(this.query);
        long startTime = System.currentTimeMillis();
        inputDoc = DocumentHelper.parseText(String.valueOf(result));
        logger.debug("Time taken in creating Dom using Dom4J: " + (System.currentTimeMillis() - startTime) + " msec.");
      }
      catch (DocumentException ex) {
        throw new InfoBusException("Error parsing the search result: " + ex.getMessage(), ex);
      }
    }
    return this.inputDoc;
  }

  private void addTopics(StringBuffer buffer) throws InfoBusException {
    List topics = this.getTopicList();
    int max = this.nextHowMany;

    if (this.moreTopic) {
      max = this.topics.size();
    }

    for (int i=0; i<max && i<topics.size(); i++) {
      Element el = (Element)topics.get(i);
      buffer.append(el.asXML().replaceAll("\n",""));
    }
  }

  private void addResults(StringBuffer buffer) throws InfoBusException {
    List results = this.getResultList();
    int resSize = results.size();
    int max = this.currentLocation + this.nextHowMany;

    for (;currentLocation<resSize && currentLocation<max; currentLocation++) {
      buffer.append(((Element)results.get(currentLocation)).asXML().replaceAll("\n", ""));
    }
  }


  /**This class holds the actual result.
   *
   * <p>Title: </p>
   * <p>Description: </p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: </p>
   * @author unascribed
   * @version 1.0
   */
  class ResultHolder implements java.io.Serializable {
    StringBuffer topics = new StringBuffer();
    StringBuffer results = new StringBuffer();
    String query;

    public StringBuffer getResults() {
      return this.results;
    }

    public void setResults(StringBuffer buffer) {
      this.results = buffer;
    }

    public StringBuffer getTopics() {
      return this.topics;
    }

    public void setTopics(StringBuffer buffer) {
      this.topics = buffer;
    }

    public String toString() {
      StringBuffer res = new StringBuffer("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
      if (html) {
        res.append(getXSLT());
      }
      res.append("<response>");
      res.append("<query>");
      res.append(this.query.replaceAll(" ", "+"));
      res.append("</query>");
      res.append(topics);
      res.append(results);
      res.append("</response>");
      return res.toString();
    }
  }

}
