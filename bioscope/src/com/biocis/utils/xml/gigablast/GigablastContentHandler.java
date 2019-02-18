package com.biocis.utils.xml.gigablast;

import com.megginson.sax.XMLWriter;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import com.biocis.infobus.*;
import java.sql.Connection;
import com.biocis.*;
import java.sql.PreparedStatement;
import java.sql.*;
import com.sbs.utils.AppLogger;
import com.sbs.Application;
import com.biocis.infobus.IDictionary;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class GigablastContentHandler extends XMLFilterImpl {
  static private AppLogger logger = AppLogger.getLogger(GigablastContentHandler.class);
  private PreparedStatement statement;

  private int currentID = 1;

  private boolean inTopic = false;
  private boolean category = false;

  private String categoryName;

  private XMLWriter writer;

  public GigablastContentHandler() {
    this.writer = new XMLWriter();
  }

  public GigablastContentHandler(String catSelQ) {
    this.writer = new XMLWriter();
    try {
      Connection dbConnection = Application.getDatabaseConnectorFor(IDictionary.CATEGORY_DS_NAME);
      statement = dbConnection.prepareStatement(catSelQ);
      logger.debug("Created the database statement for " + catSelQ);
    }
    catch (Exception ex) {
      logger.error("Error getting database connection for Category, the dsName: " + IDictionary.CATEGORY_DS_NAME, ex);
    }
  }

  public void setXMLWriter(XMLWriter writer) {
    this.writer = writer;
  }

  public void setParent(XMLReader xMLReader) {
    this.writer.setParent(xMLReader);
  }

  public void setOutput(Writer w) {
    this.writer.setOutput(w);
  }

  public XMLReader getParent() {
    return writer.getParent();
  }

  public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes atts) throws SAXException {
    if (qualifiedName.equalsIgnoreCase(IDictionary.TOPIC)) {
      inTopic = true;
    }
    if (inTopic && qualifiedName.equalsIgnoreCase(IDictionary.NAME)) {
      category = true;
    }

    this.writer.startElement(namespaceURI, localName, qualifiedName, atts);
    if (qualifiedName.equalsIgnoreCase(IDictionary.RESULT)) {
      this.writer.startElement(namespaceURI, IDictionary.RESULT_ID, IDictionary.RESULT_ID, IDictionary.EMPTY_ATTR_IMPL);
      this.writer.characters(this.getID());
      this.writer.endElement(namespaceURI, IDictionary.RESULT_ID, IDictionary.RESULT_ID);
    }
  }

  public void endElement(String namespaceURI, String localName, String qualifiedName) throws SAXException {
    if (qualifiedName.equalsIgnoreCase(IDictionary.TOPIC)) {
      this.writer.startElement(namespaceURI, IDictionary.URL, IDictionary.URL, IDictionary.EMPTY_ATTR_IMPL);

      this.writer.characters(this.getURL());
      this.writer.endElement(namespaceURI, IDictionary.URL, IDictionary.URL);

      this.writer.startElement(namespaceURI, IDictionary.ICON_LOCATION, IDictionary.ICON_LOCATION, IDictionary.EMPTY_ATTR_IMPL);

      this.writer.characters(getIconLocation());
      this.writer.endElement(namespaceURI, IDictionary.ICON_LOCATION, IDictionary.ICON_LOCATION);

      inTopic = false;
    }
    if (inTopic && qualifiedName.equalsIgnoreCase(IDictionary.NAME)) {
      category = false;
    }

    this.writer.endElement(namespaceURI, localName, qualifiedName);
  }

  public void setDocumentLocator(Locator l) {
    this.writer.setDocumentLocator(l);
  }
  public void startDocument() throws org.xml.sax.SAXException {
    this.writer.startDocument();
  }
  public void endDocument() throws org.xml.sax.SAXException {
    this.writer.endDocument();
  }
  public void startPrefixMapping(String str1, String str2) throws org.xml.sax.SAXException {
    this.writer.startPrefixMapping(str1, str2);
  }
  public void endPrefixMapping(String str1) throws org.xml.sax.SAXException {
    this.writer.endPrefixMapping(str1);
  }
  public void characters(char[] cArr, int i, int j) throws org.xml.sax.SAXException {
    if (category) {
      this.categoryName = new String(cArr, i, j);
    }
    this.writer.characters(cArr, i, j);
  }
  public void ignorableWhitespace(char[] cArr, int i, int j) throws org.xml.sax.SAXException {
    this.writer.ignorableWhitespace(cArr, i, j);
  }
  public void processingInstruction(String str1, String str2) throws org.xml.sax.SAXException {
    this.writer.processingInstruction(str1, str2);
  }
  public void skippedEntity(String e) throws org.xml.sax.SAXException {
    this.writer.skippedEntity(e);
  }

  private String getURL() {
    return this.categoryName + "/url";
  }

  private String getIconLocation() {
    String iconLoc = "misc";
    try {
      statement.setString(1, this.categoryName);
      ResultSet rs = statement.executeQuery();
      while (rs.next()) {
        iconLoc = rs.getString(1);
        if (iconLoc==null || iconLoc.length()==0) {
          logger.warn("No icon location defined for category: " + this.categoryName);
          iconLoc = "misc";
          break;
        }
      }
      rs.close();
    }
    catch (Throwable t) {
      logger.error("Error creating query statement.", t);
    }
    return iconLoc;
  }

  private String getID() {
    return ""+this.currentID++;
  }
}