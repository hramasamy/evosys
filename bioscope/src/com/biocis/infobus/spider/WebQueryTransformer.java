package com.biocis.infobus.spider;

import java.util.*;
import com.biocis.infobus.IDictionary;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class WebQueryTransformer implements IQueryTransformer {
  static public final String QUERY_SEPARATOR = IDictionary.WEB_QUERY_SEPARATOR;

  private String startQueryTag = "search?q=";
  private String endQueryTag;

  public WebQueryTransformer() {
  }

  public WebQueryTransformer(SpiderConfiguration config) {
    this.setSpiderConfiguration(config);
  }

  public void setSpiderConfiguration(SpiderConfiguration config) {
    Map params = config.getSearchParam();
    Object startTag = params.remove(IDictionary.START_WEB_QUERY_TAG);

    if (startTag!=null) {
      this.startQueryTag = String.valueOf(startTag);
    }

    StringBuffer buffer = new StringBuffer();
    Iterator itr = params.keySet().iterator();
    if (itr.hasNext()) {
      buffer.append(QUERY_SEPARATOR);
    }
    while (itr.hasNext()) {
      Object k = itr.next();
      buffer.append(k);
      buffer.append("=");
      buffer.append(params.get(k));
      if (itr.hasNext()) {
        buffer.append(QUERY_SEPARATOR);
      }
    }

    this.endQueryTag = buffer.toString();
  }

  public String transform(String query) {
    StringBuffer sb = new StringBuffer(getStartQueryTag());

    StringTokenizer st = new StringTokenizer(query, " ");

    while (st.hasMoreTokens()) {
      sb.append(st.nextToken());
      if (st.hasMoreTokens()) {
        sb.append("+");
      }
    }

    sb.append(this.endQueryTag);

    return sb.toString();
  }

  protected String getStartQueryTag() {
    return this.startQueryTag;
  }
}