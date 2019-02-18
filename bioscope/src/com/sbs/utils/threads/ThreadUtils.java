// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThreadUtils.java

package com.sbs.utils.threads;


public class ThreadUtils
{
    public static class Semaphore
    {

        public void acquire()
            throws InterruptedException
        {
            proxy.acquire();
        }

        public void release()
        {
            proxy.release();
        }

        public void release(long l)
        {
            proxy.release(System.currentTimeMillis());
        }

        private EDU.oswego.cs.dl.util.concurrent.Semaphore proxy;

        public Semaphore()
        {
            proxy = new EDU.oswego.cs.dl.util.concurrent.Semaphore(System.currentTimeMillis());
        }
    }


    public ThreadUtils()
    {
    }
}
