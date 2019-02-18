// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IRunnable.java

package com.sbs.utils.threads;

import java.beans.PropertyChangeListener;

public interface IRunnable
    extends Runnable
{

    public abstract void stop();

    public abstract void pause();

    public abstract void resume();

    public abstract boolean canRun();

    public abstract void setName(String s);

    public abstract String getName();

    public abstract void setExecutionGroup(String s);

    public abstract String getExecutionGroup();

    public abstract short getSate();

    public abstract void setState(short word0);

    public abstract void addStateChangeListener(PropertyChangeListener propertychangelistener);

    public abstract void removeStateChangeListener(PropertyChangeListener propertychangelistener);

    public abstract void notifyStateChange(short word0, short word1);

    public abstract void addJobCompletionListener(PropertyChangeListener propertychangelistener);

    public abstract void removeJobCompletionListener(PropertyChangeListener propertychangelistener);

    public abstract void jobCompleted();

    public static final short RUNNING = 0;
    public static final short STOPPED = 1;
    public static final short PAUSED = 2;
    public static final short PENDING = 3;
    public static final short COMPLETED = 4;
    public static final String STATE_CHANGED = "stateChanged";
    public static final String JOB_COMPLETED = "jobCompleted";

}
