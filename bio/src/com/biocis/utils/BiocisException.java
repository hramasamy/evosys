package com.biocis.utils;

import com.sbs.utils.SbsException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BiocisException extends SbsException {

  public BiocisException() {
  }

  public BiocisException(String msg) {
    super(msg);
  }

  public BiocisException(String msg, Throwable t) {
    super(msg, t);
  }

  public BiocisException(Throwable t) {
    super(t);
  }
}