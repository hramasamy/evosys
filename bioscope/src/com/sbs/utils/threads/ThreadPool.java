// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThreadPool.java

package com.sbs.utils.threads;

import EDU.oswego.cs.dl.util.concurrent.PooledExecutor;
import java.util.List;

public class ThreadPool
{

    public static ThreadPool getThreadPool()
    {
        if(pool == null)
            synchronized(com.sbs.utils.threads.ThreadPool.class)
            {
                if(pool == null)
                    pool = new ThreadPool(10);
            }
        return pool;
    }

    private ThreadPool(int count)
    {
        executor = new PooledExecutor(count);
    }

    public int getMaximumPoolSize()
    {
        return executor.getMaximumPoolSize();
    }

    public void setMaximumPoolSize(int maxSize)
    {
        executor.setMaximumPoolSize(maxSize);
    }

    public int getMinimumPoolSize()
    {
        return executor.getMinimumPoolSize();
    }

    public void setMinimumPoolSize(int minSize)
    {
        executor.setMinimumPoolSize(minSize);
    }

    public int getPoolSize()
    {
        return executor.getPoolSize();
    }

    public void interruptAll()
    {
        executor.interruptAll();
    }

    public void shutdownAfterProcessingCurrentlyQueuedTasks()
    {
        executor.shutdownAfterProcessingCurrentlyQueuedTasks();
    }

    public List drain()
    {
        return executor.drain();
    }

    public synchronized long getKeepAliveTime()
    {
        return executor.getKeepAliveTime();
    }

    public synchronized void setKeepAliveTime(long time)
    {
        executor.setKeepAliveTime(time);
    }

    public synchronized void runWhenBlocked()
    {
        executor.runWhenBlocked();
    }

    public synchronized void waitWhenBlocked()
    {
        executor.waitWhenBlocked();
    }

    public synchronized void discardWhenBlocked()
    {
        executor.discardWhenBlocked();
    }

    public synchronized void discardOldestWhenBlocked()
    {
        executor.discardOldestWhenBlocked();
    }

    public void execute(Runnable runnable)
        throws InterruptedException
    {
        executor.execute(runnable);
    }

    private static ThreadPool pool;
    private PooledExecutor executor;
    public static final int DEFAULT_MAXIMUMPOOLSIZE = 0x7fffffff;
    public static final int DEFAULT_MINIMUMPOOLSIZE = 1;
    public static final long DEFAULT_KEEPALIVETIME = 60000L;
}
