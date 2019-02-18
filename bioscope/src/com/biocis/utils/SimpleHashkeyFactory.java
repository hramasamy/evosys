package com.biocis.utils;

import com.sbs.utils.IHashkeyFactory;





/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class SimpleHashkeyFactory implements IHashkeyFactory {
  public SimpleHashkeyFactory() {
  }

  public Object createKey(Object obj) {
    if (obj !=null) {
      return obj.toString().toLowerCase();
    }
    return null;
  }
}