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

public interface ISpider {
  void setConfiguration(SpiderConfiguration c);
  SpiderConfiguration getConfiguration();
  String getName();
  String getURL();
  Object search(String query) throws SpiderException;
  IQueryTransformer getQueryTransformer();
  IResultTransformer getResultTransformer();
}