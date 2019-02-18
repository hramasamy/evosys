package com.biocis.infobus.spider;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface IResultTransformer {
  void setSpider(ISpider spider);
  void setInitParam(Map param);
  Map getInitParam();
  Object search(String query) throws SpiderException;
  Object transform(Object obj);
}

