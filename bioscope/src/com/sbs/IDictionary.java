// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IDictionary.java

package com.sbs;

import org.xml.sax.helpers.AttributesImpl;

public interface IDictionary
{

    public static final String CLASS_NAME = "class";
    public static final String INFO_BUS_BEAN = "info_bus_bean";
    public static final String INFO_BUS = "info_bus";
    public static final String HASH_KEY_FACTORY = "hashKeyFactory";
    public static final String SPIDER_CONFIG = "spider_config";
    public static final String SPIDERS = "spiders";
    public static final String MEM_SERVER = "memcacheConfig";
    public static final String WEB_QUERY_SEPARATOR = "&";
    public static final String START_WEB_QUERY_TAG = "start_query_tag";
    public static final String LOGGER_CONFIG = "loggerConfig";
    public static final String SERVER_LIST = "serverList";
    public static final String PORT = "port";
    public static final String COMPRESSION = "compression";
    public static final String COMPRESSION_THRESHOLD = "compressionThreshold";
    public static final String INIT_CONNECTION = "initConnection";
    public static final String MIN_CONNECTION = "minConnection";
    public static final String MAX_CONNECTION = "maxConnection";
    public static final String THREAD_COUNT = "threadCount";
    public static final String NAGLE = "nagle";
    public static final String MAINTENANCE_SLEEP = "maintenanceSleep";
    public static final String RUN_PER_THREAD = "runPerThread";
    public static final String MAX_OBJECT_SIZE = "maxObjectSize";
    public static final String TOPIC = "topic";
    public static final String RESULT = "result";
    public static final String RESULT_ID = "id";
    public static final String URL = "url";
    public static final String ICON_LOCATION = "icon-location";
    public static final String NAME = "name";
    public static final String USE_MEM_CACHE = "useMemcache";
    public static final AttributesImpl EMPTY_ATTR_IMPL = new AttributesImpl();
    public static final String HOST_NAME = "hostname";
    public static final String HOST_PORT = "port";
    public static final String SEARCH_START_INDEX = "s";
    public static final String SEARCH_COUNT = "n";
    public static final String QUERY = "q";
    public static final String CATEGORY_DS_NAME = "category";
    public static final String CATEGORY_SELECTION_QUERY = "category_selection_query";
    public static final String VALID_USER = "valid_user";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";
    public static final String REMEMBER = "remember";
    public static final String MAX_THREAD_POOL_SIZE = "maxThreadPoolSize";

}
