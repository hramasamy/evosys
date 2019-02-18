package com.biocis.infobus.memdb.client;

import java.util.*;
import com.biocis.infobus.*;
import com.danga.MemCached.*;
import com.biocis.utils.*;
import com.sbs.utils.AppLogger;
import com.danga.MemCached.SockIOPool;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MemDBClient {
  AppLogger logger = AppLogger.getLogger(MemDBClient.class);

  String[] servers;
  int stdPort =9099;
  boolean compression =true;
  int compressionThreshold =4096;
  int initConnection =5;
  int minConnection =5;
  int maxConnection =20;
  int maintenanceSleep = 30;
  int maxObjectSize = 40;
  int threadCount =5;
  boolean nagle =false;
  int runPerThread =5;

  public MemDBClient() {
  }

  public void init(Map param) {
  }
  /**
   * @param s initialization string: server_list=10.2.3.4:9099,10.2.3.10:8088;port=9099;compression=true;compression_threshold=4096;init_connection=5;min_connection=5;max_connection=20;maintenance_sleep=30;nagle=false;thread_count=5;run_per_thread=5;max_object_size=40
   */
  public void init(String s) {
    logger.debug("Entering init() with following config string: " + s );
    StringTokenizer st = new StringTokenizer(s, ";");
    while (st.hasMoreElements()) {
      StringTokenizer sub = new StringTokenizer(st.nextToken(), "=");
      String k = sub.nextToken();
      String v = sub.nextToken();
      this.initialize(k,v);
    }

    initSocketPool();
    logger.debug("Initialized MemDBClient with following configuration: " + s);
  }

  private void initSocketPool() {
    SockIOPool pool = SockIOPool.getInstance();
    pool.setServers(this.servers);

    pool.setInitConn(5);
    pool.setMinConn(5);
    pool.setMaxConn(50);
    pool.setMaintSleep(30);

    pool.setNagle(false);
    pool.initialize();
    logger.info("SocketPool initialized.");
  }

  private void initialize(String key, String value) {
    if (key.equalsIgnoreCase(IDictionary.SERVER_LIST)) {
      List serverList = new ArrayList();
      StringTokenizer kk = new StringTokenizer(String.valueOf(value), ",");
      while (kk.hasMoreElements()) {
        serverList.add(kk.nextElement());
      }
      this.servers = new String[serverList.size()];
      serverList.toArray(this.servers);
    }
    else if (key.equalsIgnoreCase(IDictionary.PORT)) {
      this.stdPort = Integer.parseInt(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.COMPRESSION_THRESHOLD)) {
      this.compressionThreshold = Integer.parseInt(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.COMPRESSION)) {
      this.compression = Boolean.getBoolean(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.NAGLE)) {
      this.nagle = Boolean.getBoolean(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.INIT_CONNECTION)) {
      this.initConnection = Integer.parseInt(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.MIN_CONNECTION)) {
      this.minConnection = Integer.parseInt(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.MAX_CONNECTION)) {
      this.maxConnection = Integer.parseInt(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.MAINTENANCE_SLEEP)) {
      this.maintenanceSleep = Integer.parseInt(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.THREAD_COUNT)) {
      this.threadCount = Integer.parseInt(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.RUN_PER_THREAD)) {
      this.runPerThread = Integer.parseInt(value);
    }
    else if (key.equalsIgnoreCase(IDictionary.MAX_OBJECT_SIZE)) {
      this.maxObjectSize = 1024*Integer.parseInt(value);
    }
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("MemDBClient[");
    sb.append("servers=");sb.append(this.servers);sb.append(", ");
    sb.append("nagle=");sb.append(this.nagle);sb.append(", ");
    sb.append("compression=");sb.append(this.compression);sb.append(", ");
    sb.append("minConnection=");sb.append(this.minConnection);sb.append(", ");
    sb.append("maxConnection=");sb.append(this.maxConnection);
    sb.append("]");
    return sb.toString();
  }
}