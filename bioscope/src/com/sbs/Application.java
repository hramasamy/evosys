// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Application.java

package com.sbs;

import com.sbs.beans.UserBean;
import com.sbs.utils.AppLogger;
import com.sbs.utils.SbsException;
import com.sbs.utils.string.Strings;
import com.sbs.utils.threads.IRunnable;
import com.sbs.utils.threads.ThreadPool;
import com.sbs.utils.threads.ThreadUtils;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

public final class Application
{
    static class ExecutionSupport
        implements PropertyChangeListener
    {

        public void execute(IRunnable runnable)
            throws InterruptedException
        {
            runnable.addJobCompletionListener(this);
            group.add(runnable);
            Application.logger.debug(String.valueOf(String.valueOf((new StringBuffer("Execution group current queue size: ")).append(executionGroupId).append(": ").append(group.size()))));
            Application.getThreadPool().execute(runnable);
        }

        public void stopExecution()
        {
            Application.logger.debug(String.valueOf(String.valueOf((new StringBuffer("Goint to stop execution group current queue size: ")).append(executionGroupId).append(": ").append(group.size()))));
            IRunnable r;
            for(Iterator itr = group.iterator(); itr.hasNext(); r.stop())
            {
                r = (IRunnable)itr.next();
                r.removeJobCompletionListener(this);
            }

            Application.executionGroups.remove(executionGroupId);
        }

        public void propertyChange(PropertyChangeEvent event)
        {
            Object eventSource = event.getSource();
            String evName = event.getPropertyName();
            Object grpId = event.getNewValue();
            Object jobName = event.getOldValue();
            Application.logger.debug("Received PropertyChangeEvent: ".concat(String.valueOf(String.valueOf(event))));
            if(executionGroupId == grpId && evName.equals("jobCompleted"))
            {
                Application.logger.debug("Job completed for: ".concat(String.valueOf(String.valueOf(jobName))));
                group.remove(eventSource);
                Application.logger.debug(String.valueOf(String.valueOf((new StringBuffer("Execution group current queue size: ")).append(executionGroupId).append(": ").append(group.size()))));
                if(group.size() == 0)
                    Application.executionGroups.remove(executionGroupId);
                Application.logger.debug("Total execution groups: ".concat(String.valueOf(String.valueOf(Application.executionGroups.keySet().size()))));
            }
        }

        private String executionGroupId;
        private List group;

        public ExecutionSupport(String executionGrpId)
        {
            group = new ArrayList();
            executionGroupId = executionGrpId;
        }
    }


    public Application()
    {
    }

    public static String getHostName()
    {
        return String.valueOf(context.getAttribute("hostname"));
    }

    public static int getPort()
    {
        return Integer.parseInt(String.valueOf(context.getAttribute("hostname")));
    }

    public static String getContextName()
    {
        return context.getServletContextName();
    }

    public static void setAppContext(ServletContext sc)
    {
        if(tobeSet)
        {
            context = sc;
            tobeSet = false;
        }
    }

    public static ServletContext getAppContext()
    {
        return context;
    }

    public static Connection getDefaultDBConnection()
        throws SbsException
    {
        return getDatabaseConnectorFor("biocis");
    }

    public static Connection getDatabaseConnectorFor(String dsName)
        throws SbsException
    {
        Connection c = null;
        try
        {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/".concat(String.valueOf(String.valueOf(dsName))));
            Connection connection = ds.getConnection();
            return connection;
        }
        catch(Throwable t)
        {
            throw new SbsException("Error getting database connection for : ".concat(String.valueOf(String.valueOf(dsName))), t);
        }
    }

    private static ThreadPool getThreadPool()
    {
        if(threadPool == null)
            synchronized(com.sbs.Application.class)
            {
                if(threadPool == null)
                {
                    threadPool = ThreadPool.getThreadPool();
                    if(context != null)
                    {
                        String maxPoolSize = context.getInitParameter("maxThreadPoolSize");
                        if(Strings.isNotEmpty(maxPoolSize))
                            threadPool.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
                    }
                }
            }
        return ThreadPool.getThreadPool();
    }

    public static UserBean authenticate(String userName, String pwd)
    {
        Connection c = null;
        Statement stmt = null;
        try
        {
            c = getDefaultDBConnection();
            stmt = c.createStatement();
            UserBean ub = UserBean.search(stmt, userName, pwd);
            UserBean userbean = ub;
            return userbean;
        }
        catch(Exception ex)
        {
            logger.warn("Failed to authenticate the user: ".concat(String.valueOf(String.valueOf(ex.getMessage()))));
        }
        finally
        {
            try
            {
                if(stmt != null)
                    stmt.close();
                if(c != null)
                    c.close();
            }
            catch(SQLException sqlexception) { }
        }
        return null;
    }

    public static void execute(IRunnable runnable, String executionGroupId)
        throws InterruptedException
    {
        ExecutionSupport exSupport = getExecutionSupport(executionGroupId);
        exSupport.execute(runnable);
    }

    public static void execute(Runnable runnable)
        throws InterruptedException
    {
        getThreadPool().execute(runnable);
    }

    public static void stopExecution(String executionGroupId)
    {
        ExecutionSupport es = (ExecutionSupport)executionGroups.remove(executionGroupId);
        if(es != null)
        {
            logger.debug("Stopping the exection for group: ".concat(String.valueOf(String.valueOf(executionGroupId))));
            es.stopExecution();
        }
    }

    private static ExecutionSupport getExecutionSupport(String exGroupId)
    {
        ExecutionSupport exSupport = (ExecutionSupport)executionGroups.get(exGroupId);
        if(exSupport == null)
        {
            com.sbs.utils.threads.ThreadUtils.Semaphore semaphore = new com.sbs.utils.threads.ThreadUtils.Semaphore();
            try
            {
                semaphore.acquire();
                exSupport = (ExecutionSupport)executionGroups.get(exGroupId);
                if(exSupport == null)
                    exSupport = new ExecutionSupport(exGroupId);
                executionGroups.put(exGroupId, exSupport);
                semaphore.release();
            }
            catch(InterruptedException ex)
            {
                exSupport = (ExecutionSupport)executionGroups.get(exGroupId);
                if(exSupport == null)
                    exSupport = new ExecutionSupport(exGroupId);
                executionGroups.put(exGroupId, exSupport);
            }
        }
        return exSupport;
    }

    private static ThreadPool threadPool;
    private static AppLogger logger;
    private static boolean tobeSet = true;
    private static final String JDBC_CONTEXT = "java:comp/env/jdbc/";
    static ServletContext context;
    private static Map executionGroups = new HashMap();

    static 
    {
        logger = AppLogger.getLogger(com.sbs.Application.class);
    }



}
