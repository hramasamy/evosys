
package com.biocis.tests.infobus.spider;

import junit.framework.*;
import java.util.*;
import com.biocis.infobus.spider.*;



public class TestSpiderConfiguration extends TestCase {

  public TestSpiderConfiguration(String s) {
    super(s);
  }

  protected void setUp() {
  }

  protected void tearDown() {
  }

  public void testSetSpiderClass() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    String spiderClass1=  "STRING0";
    spiderconfiguration.setSpiderClass(spiderClass1);
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testGetSpiderName() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    String stringRet = spiderconfiguration.getSpiderName();
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testGetInitParam() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    Map mapRet = spiderconfiguration.getInitParam();
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testGetSpiderClass() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    String stringRet = spiderconfiguration.getSpiderClass();
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testSetURL() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    String url1=  "STRING0";
    spiderconfiguration.setURL(url1);
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testGetSearchParam() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    Map mapRet = spiderconfiguration.getSearchParam();
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testSetSearchParam() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    Map param1=  null  /** @todo fill in non-null value */;
    spiderconfiguration.setSearchParam(param1);
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testSetSpiderName() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    String spiderName1=  "STRING0";
    spiderconfiguration.setSpiderName(spiderName1);
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testSetInitParam() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    Map param1=  null  /** @todo fill in non-null value */;
    spiderconfiguration.setInitParam(param1);
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
  public void testGetURL() {
    SpiderConfiguration spiderconfiguration = new SpiderConfiguration();
    String stringRet = spiderconfiguration.getURL();
  /** @todo:  Insert test code here.  Use assertEquals(), for example. */
  }
}
