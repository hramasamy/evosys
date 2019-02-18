package com.biocis.infobus.spider;

import java.util.*;
import java.beans.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class SpiderConfiguration implements java.io.Serializable {
  Map searchParam;
  Map initParam;
  Map transformerParam;
  String resultTransformerClassName;

  String url;
  String spiderName;
  String spiderClass;


  public SpiderConfiguration() {
  }

  public void setSpiderName(String spiderName) {
    this.spiderName = spiderName;
  }

  public String getSpiderName() {
    return this.spiderName;
  }

  public void setSpiderClass(String spiderClass) {
    this.spiderClass = spiderClass;
  }

  public String getSpiderClass() {
    return this.spiderClass;
  }

    public void setURL(String url) {
    this.url = url;
  }

  public String getURL() {
    return this.url;
  }

  public Map getInitParam() {
    return this.initParam;
  }

  public void setInitParam(Map param) {
    this.initParam = param;
  }

  public void setTransformerParam(Map param) {
    this.transformerParam =param;
  }

  public Map getTransformerParam() {
    return this.transformerParam;
  }

  public Map getSearchParam() {
    return searchParam;
  }

  public void setSearchParam(Map param) {
    this.searchParam = param;
  }

  public String getResultTransformerClassName() {
    return resultTransformerClassName;
  }

  public void setResultTransformerClassName(String className) {
    resultTransformerClassName = className;
  }

  public String toString() {
    return "spiderName: " + this.getSpiderName() + "\n"+ "spiderClass: " + this.getSpiderClass()+"\n" + "URL: " + this.getURL()+ "\n" + searchParam.toString() + "\n" +
        "transformer: " + this.transformerParam;
  }


  static final String SPIDER_NAME = "Gigablast";
  static final String QUERY = "java web services";
  static final String SPIDER_CONFIG = "c:/apache/Tomcat 5.0/webapps/BugTrack/conf/spiders/configuration.xml";

  public static void main(String[] args) {
    Map configs = new HashMap();


    SpiderConfiguration c = new SpiderConfiguration();
    c.setSpiderName("Google");
    c.setSpiderClass("com.google.spider");
    Map param = new HashMap();
    param.put("xyz", "abc");
    c.setSearchParam(param);
    c.setURL("www.google.com");

    configs.put(c.getSpiderName(), c);

    SpiderConfiguration c1 = new SpiderConfiguration();
    c1.setSpiderName("gigablast");
    c1.setSpiderClass("com.gigablast.spider");
    Map param2 = new HashMap();
    param2.put("raw", "8");
    c1.setSearchParam(param2);
    c1.setURL("www.gigablast.com");
    configs.put(c1.getSpiderName(), c1);
    try {
//      XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("c:/xfer/spid.xml")));
//
//      e.writeObject(configs);
//      e.close();

        XMLDecoder d = new XMLDecoder(new FileInputStream(SPIDER_CONFIG));

        Object obj = d.readObject();
        d.close();
        Map allConfigs = (Map)obj;
        SpiderConfiguration config = (SpiderConfiguration)allConfigs.get(SPIDER_NAME);
        System.out.println(config);
      }

    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}