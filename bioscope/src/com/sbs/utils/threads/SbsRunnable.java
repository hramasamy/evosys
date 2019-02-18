// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SbsRunnable.java

package com.sbs.utils.threads;

import com.sbs.utils.AppLogger;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// Referenced classes of package com.sbs.utils.threads:
//            IRunnable

public class SbsRunnable
    implements IRunnable
{

    public SbsRunnable()
    {
        state = 3;
        proxy = new PropertyChangeSupport(this);
    }

    public SbsRunnable(String name, String executionGroupId)
    {
        this();
        setName(name);
        setExecutionGroup(executionGroupId);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setExecutionGroup(String executionGroupId)
    {
        this.executionGroupId = executionGroupId;
    }

    public String getExecutionGroup()
    {
        return executionGroupId;
    }

    public boolean canRun()
    {
        return state != 1 || state != 4;
    }

    public void stop()
    {
        if(state != 1 || state != 4)
        {
            logger.debug(String.valueOf(String.valueOf((new StringBuffer("Goint to stop <name|executionGrp>: ")).append(name).append("|").append(executionGroupId))));
            if(worker != null && worker.isAlive() && !worker.isInterrupted())
                worker.interrupt();
            setState((short)1);
        }
    }

    public void pause()
    {
        if(state == 0)
            setState((short)2);
    }

    public void resume()
    {
        if(state != 2)
            setState((short)0);
    }

    public short getSate()
    {
        return state;
    }

    public void setState(short state)
    {
        short oldValue = this.state;
        this.state = state;
        notifyStateChange(oldValue, this.state);
        synchronized(this)
        {
            notifyAll();
        }
    }

    public void addJobCompletionListener(PropertyChangeListener l)
    {
        proxy.addPropertyChangeListener("jobCompleted", l);
    }

    public void removeJobCompletionListener(PropertyChangeListener l)
    {
        proxy.removePropertyChangeListener("jobCompleted", l);
    }

    public void addStateChangeListener(PropertyChangeListener l)
    {
        proxy.addPropertyChangeListener("stateChanged", l);
    }

    public void removeStateChangeListener(PropertyChangeListener l)
    {
        proxy.removePropertyChangeListener("stateChanged", l);
    }

    public void notifyStateChange(short oldValue, short newValue)
    {
        proxy.firePropertyChange("stateChanged", oldValue, newValue);
        if(newValue == 1)
            jobCompleted();
    }

    public void jobCompleted()
    {
        if(state == 4 || state == 1)
            proxy.firePropertyChange("jobCompleted", executionGroupId, getName());
    }

    public void run()
    {
        if(getSate() != 1)
        {
            worker = Thread.currentThread();
            worker.setName(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(executionGroupId)))).append(":").append(name))));
            setState((short)0);
        }
    }

    private static AppLogger logger;
    protected short state;
    private PropertyChangeSupport proxy;
    private String executionGroupId;
    private Thread worker;
    private String name;

    static 
    {
        logger = AppLogger.getLogger(com.sbs.utils.threads.SbsRunnable.class);
    }
}
