package com.biocis.infobus.spider;

import java.net.*;
import java.io.*;
import com.sbs.utils.AppLogger;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Sanjeev
 * @version 1.0
 */

public class WebSpider extends DefaultSpider {

  static AppLogger logger = AppLogger.getLogger(WebSpider.class);
  public WebSpider() {
  }

  public void setConfiguration(SpiderConfiguration config) {
    super.setConfiguration(config);
    this.setQueryTransformer(new WebQueryTransformer(this.getConfiguration()));
  }

  public Object search(String query) throws SpiderException {
    String sQuery = this.getQueryTransformer().transform(query);

    if (this.getResultTransformer()!=null) {
      try {
        return this.getResultTransformer().search(sQuery);
      }
      catch (SpiderException ex) {
        logger.warn("Failed to get result using ResultTransformer: " + ex.getMessage() + ", will try to get result using default method.", ex);
      }
    }


    try {
      long startTime = System.currentTimeMillis();
      String address = this.getURL()+sQuery;

      URL url = new URL(address);

      BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

      String line = null;
      StringBuffer sb = new StringBuffer();
      while ( (line=br.readLine())!=null) {
        sb.append(line);
      }
      br.close();
      logger.debug("Time taken in reading the url <"+address+"> " + (System.currentTimeMillis() - startTime) + " msec.");
      return sb.toString();
    }
    catch (Exception ex) {
      throw new SpiderException(ex);
    }
  }

}