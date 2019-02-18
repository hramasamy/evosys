package com.biocis.infobus.spider;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Sanjeev
 * @version 1.0
 */

public class DefaultTransformer implements IResultTransformer {
  private Map initParam;
  private ISpider spider;

  public DefaultTransformer() {
  }

  public DefaultTransformer(Map initParam) {
    this.initParam = initParam;
  }

  public void setSpider (ISpider spider) {
    this.spider = spider;
  }

  public void setInitParam(Map param) {
    this.initParam = param;
  }

  public Map getInitParam() {
    return this.initParam;
  }

  public Object search(String query) {
    return query;
  }

  public Object transform(Object obj) {
    return obj;
  }

  public String toString() {
    return "DefaultTransformer{"+this.initParam.toString()+"}";
  }
}