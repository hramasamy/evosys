package com.biocis.infobus.spider.gigablast;

import com.biocis.infobus.spider.IResultTransformer;
import java.util.Map;
import java.util.*;
import com.biocis.infobus.spider.*;
import com.biocis.utils.xml.gigablast.*;
import java.io.*;
import com.biocis.utils.xml.*;
import org.xml.sax.*;
import java.net.*;
import com.biocis.utils.*;
import com.biocis.infobus.IDictionary;
import com.sbs.utils.AppLogger;
import com.sbs.utils.xml.GenericXMLReader;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class GigablastResultTransformer implements IResultTransformer {
  static private AppLogger logger = AppLogger.getLogger(GigablastResultTransformer.class);
  static private String categorySelQuery = "Select category_name from category where keyword like ?";

  private ISpider spider;

  public GigablastResultTransformer() {
  }

  public void setSpider(ISpider spider) {
    this.spider = spider;
  }

  public void setInitParam(Map param) {
    if (param!=null) {
      String selQ = (String)param.get(IDictionary.CATEGORY_SELECTION_QUERY);
      if (selQ!=null && selQ.length()!=0) {
        categorySelQuery = selQ;
        logger.debug("Set the category selection query: " + selQ);
      }
    }
  }

  public Map getInitParam() {
    return new HashMap();
  }

  public Object search(String query) throws SpiderException {
    GigablastContentHandler handler = new GigablastContentHandler(categorySelQuery);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    handler.setOutput(new OutputStreamWriter(baos));
    XMLReader reader =null;
    String address = this.spider.getURL()+query;
    try {

      reader = GenericXMLReader.getXMLReader();

      reader.setContentHandler(handler);
      long startTime = System.currentTimeMillis();

      InputSource is = new InputSource(new InputStreamReader(new URL(address).openStream(), "ISO-8859-1"));
      //is.setEncoding("ISO-8859-1");
      //is.setSystemId(this.spider.getURL());

      reader.parse(is);
      logger.debug("Time taken in parsing the document at " + address + " : "+(System.currentTimeMillis() - startTime) + " msec.");
    }
    catch (Exception ex) {
      throw new SpiderException("Failed to create SAXReader for document at " + address + " : " + ex.getMessage(), ex);
    }
    return new String(baos.toByteArray());
  }

  public Object transform(Object obj) {
    return obj;
  }
}
