
package com.biocis.servlets.listeners;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.sbs.utils.SbsContextListenerSupport;


/**
 *
 * @author Sanjeev K Mishra
 * @version $Revision: .1 $ $Date: 2004/12/10 $
 */

public final class BiocisContextListener implements ServletContextListener {
  private SbsContextListenerSupport support;

  public BiocisContextListener() {
       support = new SbsContextListenerSupport();
       System.out.println(support);
  }

  public void contextDestroyed(ServletContextEvent event) {
    this.support.contextDestroyed(event);
  }


  /**
   * Record the fact that this web application has been initialized.
   *
   * @param event The servlet context event
   */
  public void contextInitialized(ServletContextEvent event) {
    this.support.contextInitialized(event);
  }
}