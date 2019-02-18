// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SbsContextListenerSupport.java

package com.sbs.utils;

import com.sbs.Application;
import com.sbs.utils.memdb.client.MemDBClient;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.apache.log4j.PropertyConfigurator;

// Referenced classes of package com.sbs.utils:
//            IHashkeyFactory, AppLogger

public class SbsContextListenerSupport
{

    public SbsContextListenerSupport()
    {
        logger = null;
    }

    public void contextInitialized(ServletContextEvent sce)
    {
        ServletContext application = sce.getServletContext();
        initLogger(application);
        String hashKeyFactory = application.getInitParameter("hashKeyFactory");
        application.log(hashKeyFactory);
        IHashkeyFactory factory = null;
        try
        {
            factory = (IHashkeyFactory)Class.forName(hashKeyFactory).newInstance();
            application.setAttribute("hashKeyFactory", factory);
            logger.info("Loaded HashkeyFactory.");
            boolean useMemcache = Boolean.getBoolean(application.getInitParameter("useMemcache"));
            if(useMemcache)
            {
                String memcacheConfig = application.getInitParameter("memcacheConfig");
                MemDBClient dbClient = new MemDBClient();
                dbClient.init(memcacheConfig);
            }
            Application.setAppContext(application);
        }
        catch(Throwable ex)
        {
            logger.fatal("Failed to instanciate HashkeyFactory.", ex);
        }
    }

    public void contextDestroyed(ServletContextEvent sce)
    {
        logger.info("Going to destroy the context...");
    }

    private void initLogger(ServletContext application)
    {
        String loggerConfig = application.getInitParameter("loggerConfig");
        try
        {
            URL url = application.getResource(loggerConfig);
            if(loggerConfig != null)
                PropertyConfigurator.configure(url);
            logger = AppLogger.getLogger(getClass());
        }
        catch(MalformedURLException ex)
        {
            application.log("Error initializing logger", ex);
        }
    }

    private AppLogger logger;
}
