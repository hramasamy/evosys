package com.biocis.infobus.spider;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface IQueryTransformer {
  String transform(String query);
  void setSpiderConfiguration(SpiderConfiguration config);
}