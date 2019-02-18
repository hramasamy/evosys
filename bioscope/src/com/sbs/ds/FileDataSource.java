// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileDataSource.java

package com.sbs.ds;

import com.sbs.espace.IEntity;
import com.sbs.utils.string.Strings;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

// Referenced classes of package com.sbs.ds:
//            DefaultDataSource, DataSourceException, FileSpec, IProtocolHandler, 
//            ITransformer

public class FileDataSource extends DefaultDataSource
{

    public FileDataSource()
    {
        name = "";
        location = "";
        protocol = "";
        setSpec(FileSpec.FILE_SPEC);
    }

    public FileDataSource(String name)
    {
        this.name = "";
        location = "";
        protocol = "";
        setName(name);
    }

    public String getQName()
    {
        return getFullPath();
    }

    public InputStream getInputStream()
        throws DataSourceException
    {
        return getProtocolHandler().getInputStream(getFullPath());
    }

    public OutputStream getOutputStream()
        throws DataSourceException
    {
        return getProtocolHandler().getOutputStream(getFullPath());
    }

    public void setName(String name)
    {
        this.name = convertToUniformSeparator(name);
        setProtocol(this.name);
    }

    public String getName()
    {
        return name;
    }

    public void setProtocol(String protocol)
    {
        String proto = protocol.toLowerCase();
        if(!proto.startsWith("http") && !proto.startsWith("ftp"))
        {
            this.protocol = "file";
            handler = IProtocolHandler.FILE_HANLDER;
        } else
        {
            handler = IProtocolHandler.DEFAULT_HANLDER;
        }
    }

    public String getProtocol()
    {
        return protocol;
    }

    public void setLocation(String location)
    {
        this.location = convertToUniformSeparator(location);
        if(!this.location.endsWith("/"))
            this.location = String.valueOf(String.valueOf(this.location)).concat("/");
    }

    public String getLocation()
    {
        return location;
    }

    public String getFullPath()
    {
        if(Strings.isNotEmpty(location))
        {
            if(!location.startsWith(getProtocol()) && Strings.isNotEmpty(protocol) && protocol != "file")
                location = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getProtocol())))).append("://").append(getLocation())));
            if(getName().indexOf(location) != -1)
                return name;
            else
                return String.valueOf(location) + String.valueOf(getName());
        } else
        {
            return getName();
        }
    }

    public byte[] getContent()
        throws DataSourceException
    {
        return getProtocolHandler().getContent(getFullPath());
    }

    public IEntity query()
        throws DataSourceException
    {
        if(getTransformer() != null)
            try
            {
                IEntity ientity = getTransformer().transform(getInputStream());
                return ientity;
            }
            catch(Exception ex)
            {
                throw new DataSourceException(ex);
            }
        else
            return null;
    }

    private URL getURL()
        throws DataSourceException
    {
        if(url == null)
            try
            {
                url = new URL(getFullPath());
            }
            catch(MalformedURLException ex)
            {
                throw new DataSourceException(ex);
            }
        return url;
    }

    private IProtocolHandler getProtocolHandler()
    {
        if(handler == null)
            if(protocol.equalsIgnoreCase("file"))
                handler = IProtocolHandler.FILE_HANLDER;
            else
                handler = IProtocolHandler.DEFAULT_HANLDER;
        return handler;
    }

    private String convertToUniformSeparator(String path)
    {
        StringBuffer sb = new StringBuffer(path);
        return path.replace('\\', '/');
    }

    public static final String FILE_PROTOCOL = "file";
    public static final String HTTP_PROTOCOL = "http";
    public static final String FTP_PROTOCOL = "ftp";
    private String name;
    private String location;
    private String protocol;
    private URL url;
    private IProtocolHandler handler;
}
