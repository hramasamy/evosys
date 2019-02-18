

package com.biocis.servlets.listeners;

import java.lang.* ;
import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.sbs.utils.AppLogger;


public final class BiocisSessionListener implements ServletContextListener, HttpSessionAttributeListener, HttpSessionListener {
  private ServletContext context = null;
  static AppLogger logger = AppLogger.getLogger(BiocisSessionListener.class);

  /**
   * Record the fact that a servlet context attribute was added.
   *
   * @param event The session attribute event
   */
  public void attributeAdded(HttpSessionBindingEvent event) {
    logger.debug("attributeAdded('" + event.getSession().getId() + "', '" + event.getName() + "', '" + event.getValue() + "')");
  }


  /**
   * Record the fact that a servlet context attribute was removed.
   *
   * @param event The session attribute event
   */
  public void attributeRemoved(HttpSessionBindingEvent event) {

    logger.debug("attributeRemoved('" + event.getSession().getId() + "', '" +
        event.getName() + "', '" + event.getValue() + "')");

  }


  /**
   * Record the fact that a servlet context attribute was replaced.
   *
   * @param event The session attribute event
   */
  public void attributeReplaced(HttpSessionBindingEvent event) {

    logger.debug("attributeReplaced('" + event.getSession().getId() + "', '" +
        event.getName() + "', '" + event.getValue() + "')");

  }


  /**
   * Record the fact that this web application has been destroyed.
   *
   * @param event The servlet context event
   */
  public void contextDestroyed(ServletContextEvent event) {

    logger.debug("contextDestroyed()");
    this.context = null;

  }


  /**
   * Record the fact that this web application has been initialized.
   *
   * @param event The servlet context event
   */
  public void contextInitialized(ServletContextEvent event) {

    this.context = event.getServletContext();
    logger.debug("contextInitialized()");

  }


  /**
   * Record the fact that a session has been created.
   *
   * @param event The session event
   */
  public void sessionCreated(HttpSessionEvent event) {

    logger.debug("sessionCreated('" + event.getSession().getId() + "')");

  }


  /**
   * Record the fact that a session has been destroyed.
   *
   * @param event The session event
   */
  public void sessionDestroyed(HttpSessionEvent event) {

    logger.debug("sessionDestroyed('" + event.getSession().getId() + "')");
    String BaseDir = "/usr/local/apache/htdocs/gbrowse/databases/" ;
    File RemoveDir = new File(BaseDir + event.getSession().getId() ) ;
    File ConfDir = new File("/usr/local/apache/conf/gbrowse.conf/") ;
    logger.debug("removing directories for session ('" + event.getSession().getId() + "')" + RemoveDir) ;
    if (!deleteDir(RemoveDir))
    {
      logger.debug("Session Directory Removal failed (" + event.getSession().getId() + "')") ;
    }

    logger.debug("removing conf files for session ('" + event.getSession().getId() + "')" + ConfDir) ;
    deleteConfFiles(ConfDir, event.getSession().getId()) ;


  }


  // -------------------------------------------------------- Private Methods


  /**
   * Record the fact that a session has been destroyed.
   *
   * @param event The session event
   */
//    public void sessionDestroyed(HttpSessionEvent event) {
//
//	log("sessionDestroyed('" + event.getSession().getId() + "')");
//	String BaseDir = "/usr/local/apache/htdocs/gbrowse/databases/" ;
//	File RemoveDir = new File(BaseDir + event.getSession().getId() ) ;
//	File ConfDir = new File("/usr/local/apache/conf/gbrowse.conf/") ;
//	log ("removing directories for session ('" + event.getSession().getId() + "')" + RemoveDir) ;
//	if (!deleteDir(RemoveDir))
//	    {
//		log ("Session Directory Removal failed (" + event.getSession().getId() + "')") ;
//	    }
//
//	log ("removing conf files for session ('" + event.getSession().getId() + "')" + ConfDir) ;
//	deleteConfFiles(ConfDir, event.getSession().getId()) ;
//
//
//    }
//

// Deletes all files and subdirectories under dir.
// Returns true if all deletions were successful.
// If a deletion fails, the method stops attempting to delete and returns false.
  public static boolean deleteDir(File dir) {
    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i=0; i<children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }

    // The directory is now empty so delete it
    return dir.delete();
  }

  public static boolean deleteConfFiles(File dir, String session) {
    if (dir.isDirectory())
    {
      String[] children = dir.list();
      for (int i=0; i<children.length; i++) {
        if (children[i].indexOf(session) != -1)
        {
        File deleteFile = new File(dir, children[i]) ;
        deleteFile.delete() ;
      }

      }
    }
    return (true) ;
  }
}