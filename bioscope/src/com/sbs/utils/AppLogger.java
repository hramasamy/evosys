// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AppLogger.java

package com.sbs.utils;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

public class AppLogger
{

    protected AppLogger()
    {
    }

    protected AppLogger(Logger logger)
    {
        setLogger(logger);
    }

    protected void setLogger(Logger logger)
    {
        this.logger = logger;
    }

    public static AppLogger getLogger(String string)
    {
        return new AppLogger(Logger.getLogger(string));
    }

    public static AppLogger getLogger(Class class0)
    {
        return new AppLogger(Logger.getLogger(class0));
    }

    public static AppLogger getRootLogger()
    {
        return root;
    }

    public void debug(Object message)
    {
        logger.debug(message);
    }

    public void info(Object message)
    {
        logger.info(message);
    }

    public void warn(String message)
    {
        logger.warn(message);
    }

    public void warn(Throwable t)
    {
        logger.warn("", t);
    }

    public void warn(String message, Throwable t)
    {
        logger.warn(message, t);
    }

    public void error(String message)
    {
        logger.error(message);
    }

    public void error(Throwable t)
    {
        logger.error("", t);
    }

    public void error(String message, Throwable t)
    {
        logger.error(message, t);
    }

    public void fatal(String message)
    {
        logger.fatal(message);
    }

    public void fatal(Throwable t)
    {
        logger.fatal("", t);
    }

    public void fatal(String message, Throwable t)
    {
        logger.fatal(message, t);
    }

    static AppLogger root = new AppLogger(Logger.getRootLogger());
    Logger logger;

}
