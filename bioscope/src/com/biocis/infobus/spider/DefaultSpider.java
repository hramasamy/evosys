package com.biocis.infobus.spider;

import com.sbs.utils.AppLogger;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Sanjeev
 * @version 1.0
 */

public class DefaultSpider implements ISpider {
  static AppLogger logger = AppLogger.getLogger(DefaultSpider.class);

  protected SpiderConfiguration config;
  protected IQueryTransformer qTransformer;
  private IResultTransformer resTransformer;

  public DefaultSpider() {
  }

  public void setConfiguration(SpiderConfiguration c) {
    this.config = c;
    if (c.getResultTransformerClassName()!=null) {
      try {
        this.resTransformer = (IResultTransformer)Class.forName(c.getResultTransformerClassName()).newInstance();
        this.resTransformer.setInitParam(c.getTransformerParam());
        this.resTransformer.setSpider(this);
      }
      catch (Exception ex) {
        logger.warn("Failed to initialize ResultTransformer: " + ex.getMessage(), ex);
      }
    }
  }

  public SpiderConfiguration getConfiguration() {
    return this.config;
  }

  public String getName() {
    return this.config.getSpiderName();
  }

  public String getURL() {
    return this.config.getURL();
  }

  public Object search(String query) throws SpiderException {
    return null;
  }

  public IQueryTransformer getQueryTransformer() {
    return this.qTransformer;
  }


  public IResultTransformer getResultTransformer() {
    return this.resTransformer;
  }

  protected void setQueryTransformer(IQueryTransformer qTransformer) {
    this.qTransformer = qTransformer;
  }

  protected void setResultTransformer(IResultTransformer t) {
    this.resTransformer =t;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(this.getClass().getName());
    sb.append("{");
    sb.append(config);
    sb.append(",");
    sb.append(this.qTransformer);
    sb.append(",");
    sb.append(this.resTransformer);
    sb.append("}");
    return sb.toString();
  }
}