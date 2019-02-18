package com.biocis.infobus.spider;

import com.biocis.infobus.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class SpiderException extends InfoBusException {
  public SpiderException(){};
  public SpiderException(String msg){super(msg);}
  public SpiderException(Throwable t){super(t);}
  public SpiderException(String msg, Throwable t){super(msg,t);}
}