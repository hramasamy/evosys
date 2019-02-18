// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileDataSource.java

package com.sbs.ds;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

// Referenced classes of package com.sbs.ds:
//            DataSourceException

interface IProtocolHandler
{

    public abstract InputStream getInputStream(String s)
        throws DataSourceException;

    public abstract OutputStream getOutputStream(String s)
        throws DataSourceException;

    public abstract byte[] getContent(String s)
        throws DataSourceException;

    public static final int FILE = 0;
    public static final int HTTP = 1;
    public static final int FTP = 2;
    public static final int MAIL = 3;
    public static final IProtocolHandler FILE_HANLDER = new IProtocolHandler() {

        public InputStream getInputStream(String path)
            throws DataSourceException
        {
            try
            {
                FileInputStream fileinputstream = new FileInputStream(path);
                return fileinputstream;
            }
            catch(FileNotFoundException ex)
            {
                throw new DataSourceException(ex);
            }
        }

        public OutputStream getOutputStream(String path)
            throws DataSourceException
        {
            try
            {
                FileOutputStream fileoutputstream = new FileOutputStream(path);
                return fileoutputstream;
            }
            catch(FileNotFoundException ex)
            {
                throw new DataSourceException(ex);
            }
        }

        public byte[] getContent(String path)
            throws DataSourceException
        {
            try
            {
                InputStream fis = new FileInputStream(path);
                byte bytes[] = new byte[fis.available()];
                fis.read(bytes);
                byte abyte0[] = bytes;
                return abyte0;
            }
            catch(IOException ex)
            {
                throw new DataSourceException(ex);
            }
        }

    };
    public static final IProtocolHandler DEFAULT_HANLDER = new IProtocolHandler() {

        public InputStream getInputStream(String path)
            throws DataSourceException
        {
            try
            {
                InputStream inputstream = (new URL(path)).openStream();
                return inputstream;
            }
            catch(Exception ex)
            {
                throw new DataSourceException(ex);
            }
        }

        public OutputStream getOutputStream(String path)
            throws DataSourceException
        {
            try
            {
                OutputStream outputstream = (new URL(path)).openConnection().getOutputStream();
                return outputstream;
            }
            catch(Exception ex)
            {
                throw new DataSourceException(ex);
            }
        }

        public byte[] getContent(String path)
            throws DataSourceException
        {
            try
            {
                InputStream fis = (new URL(path)).openStream();
                byte bytes[] = new byte[fis.available()];
                fis.read(bytes);
                byte abyte0[] = bytes;
                return abyte0;
            }
            catch(IOException ex)
            {
                throw new DataSourceException(ex);
            }
        }

    };

}
