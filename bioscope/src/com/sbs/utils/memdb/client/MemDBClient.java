// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemDBClient.java

package com.sbs.utils.memdb.client;

import com.danga.MemCached.SockIOPool;
import com.sbs.utils.AppLogger;
import java.util.*;

public class MemDBClient
{

    public MemDBClient()
    {
        logger = AppLogger.getLogger(com.sbs.utils.memdb.client.MemDBClient.class);
        stdPort = 9099;
        compression = true;
        compressionThreshold = 4096;
        initConnection = 5;
        minConnection = 5;
        maxConnection = 20;
        maintenanceSleep = 30;
        maxObjectSize = 40;
        threadCount = 5;
        nagle = false;
        runPerThread = 5;
    }

    public void init(Map map)
    {
    }

    public void init(String s)
    {
        logger.debug("Entering init() with following config string: ".concat(String.valueOf(String.valueOf(s))));
        String k;
        String v;
        for(StringTokenizer st = new StringTokenizer(s, ";"); st.hasMoreElements(); initialize(k, v))
        {
            StringTokenizer sub = new StringTokenizer(st.nextToken(), "=");
            k = sub.nextToken();
            v = sub.nextToken();
        }

        initSocketPool();
        logger.debug("Initialized MemDBClient with following configuration: ".concat(String.valueOf(String.valueOf(s))));
    }

    private void initSocketPool()
    {
        SockIOPool pool = SockIOPool.getInstance();
        pool.setServers(servers);
        pool.setInitConn(5);
        pool.setMinConn(5);
        pool.setMaxConn(50);
        pool.setMaintSleep(30L);
        pool.setNagle(false);
        pool.initialize();
        logger.info("SocketPool initialized.");
    }

    private void initialize(String key, String value)
    {
        if(key.equalsIgnoreCase("serverList"))
        {
            List serverList = new ArrayList();
            for(StringTokenizer kk = new StringTokenizer(String.valueOf(value), ","); kk.hasMoreElements(); serverList.add(kk.nextElement()));
            servers = new String[serverList.size()];
            serverList.toArray(servers);
        } else
        if(key.equalsIgnoreCase("port"))
            stdPort = Integer.parseInt(value);
        else
        if(key.equalsIgnoreCase("compressionThreshold"))
            compressionThreshold = Integer.parseInt(value);
        else
        if(key.equalsIgnoreCase("compression"))
            compression = Boolean.getBoolean(value);
        else
        if(key.equalsIgnoreCase("nagle"))
            nagle = Boolean.getBoolean(value);
        else
        if(key.equalsIgnoreCase("initConnection"))
            initConnection = Integer.parseInt(value);
        else
        if(key.equalsIgnoreCase("minConnection"))
            minConnection = Integer.parseInt(value);
        else
        if(key.equalsIgnoreCase("maxConnection"))
            maxConnection = Integer.parseInt(value);
        else
        if(key.equalsIgnoreCase("maintenanceSleep"))
            maintenanceSleep = Integer.parseInt(value);
        else
        if(key.equalsIgnoreCase("threadCount"))
            threadCount = Integer.parseInt(value);
        else
        if(key.equalsIgnoreCase("runPerThread"))
            runPerThread = Integer.parseInt(value);
        else
        if(key.equalsIgnoreCase("maxObjectSize"))
            maxObjectSize = 1024 * Integer.parseInt(value);
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("MemDBClient[");
        sb.append("servers=");
        sb.append(servers);
        sb.append(", ");
        sb.append("nagle=");
        sb.append(nagle);
        sb.append(", ");
        sb.append("compression=");
        sb.append(compression);
        sb.append(", ");
        sb.append("minConnection=");
        sb.append(minConnection);
        sb.append(", ");
        sb.append("maxConnection=");
        sb.append(maxConnection);
        sb.append("]");
        return sb.toString();
    }

    AppLogger logger;
    String servers[];
    int stdPort;
    boolean compression;
    int compressionThreshold;
    int initConnection;
    int minConnection;
    int maxConnection;
    int maintenanceSleep;
    int maxObjectSize;
    int threadCount;
    boolean nagle;
    int runPerThread;
}
