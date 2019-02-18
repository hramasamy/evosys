package com.biocis.infobus;

import java.util.*;
import com.biocis.infobus.spider.*;
import com.biocis.utils.*;
import com.sbs.utils.IHashkeyFactory;
import com.sbs.utils.AppLogger;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class InfoBus {
  static private AppLogger logger = AppLogger.getLogger(InfoBus.class);

  private Map spiderMap;
  private IHashkeyFactory hashkeyFactory;
  private Collection spiderSet;
  private boolean useMemcache;

  static private InfoBus infoBus;

  static public InfoBus getInfoBus() {
    if (infoBus==null) {
      synchronized (InfoBus.class) {
        if (infoBus==null) {
          infoBus = new InfoBus();
        }
      }
    }
    return infoBus;
  }

  private InfoBus() {
  }

  public void init(Map params) {
  }

  public void setSpiders(Map spiders) {
    this.spiderMap = spiders;
    this.spiderSet = this.spiderMap.values();
  }

  public void setUseMemcache(boolean flag) {
    this.useMemcache = flag;
  }

  public void setHashkeyFactory(IHashkeyFactory factory) {
    this.hashkeyFactory = factory;
  }

  public IHashkeyFactory getHashkeyFactory() {
    return this.hashkeyFactory;
  }

  public Map getSpiders() {
    return this.spiderMap;
  }

  public Object search(String query) throws InfoBusException {
    logger.debug("Searching: " + query);

    long startTime = System.currentTimeMillis();

    String queryKey = String.valueOf(this.hashkeyFactory.createKey(query));
    logger.debug("Searching: HashKey: " + queryKey);

    Iterator itr = spiderSet.iterator();
    StringBuffer sb = new StringBuffer();

    while (itr.hasNext()) {
     try {
       sb.append(((ISpider)itr.next()).search(queryKey));
     }
     catch (SpiderException ex) {
       logger.warn("Exception while searching <" + queryKey + "> " + ex.getMessage(), ex);
     }
    }

    String result = sb.toString();

    logger.info("Total time taken in search < " + queryKey +" > " + (System.currentTimeMillis() - startTime) + " msec.");

    return result;
  }

  public String toString() {
    return this.spiderMap.toString();
  }
}