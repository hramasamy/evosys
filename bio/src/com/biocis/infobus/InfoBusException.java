package com.biocis.infobus;

import com.sbs.utils.SbsException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class InfoBusException extends SbsException {

  public InfoBusException() {
  }

  public InfoBusException(String msg) {
    super(msg);
  }

  public InfoBusException(Throwable t) {
    super(t);
  }

  public InfoBusException(String msg, Throwable t) {
    super(msg, t);
  }
}