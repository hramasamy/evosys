// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UserBean.java

package com.sbs.beans;

import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;
import java.io.Serializable;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class UserBean
    implements Serializable
{

    public UserBean()
    {
        notifyChange = true;
        enabled = true;
        lastModified = System.currentTimeMillis();
        createdOn = lastModified;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String pwd)
    {
        password = pwd;
    }

    public String getFirstName()
    {
        return fName;
    }

    public void setFirstName(String fName)
    {
        this.fName = fName;
    }

    public String getLastName()
    {
        return lName;
    }

    public void setLastName(String lName)
    {
        this.lName = lName;
    }

    public String getNickName()
    {
        return nName;
    }

    public void setNickName(String nName)
    {
        this.nName = nName;
    }

    public String getExtenalID()
    {
        return externalID;
    }

    public void setExternalID(String extID)
    {
        externalID = extID;
    }

    public String getEmailID()
    {
        return emailID;
    }

    public void setEmailID(String email)
    {
        emailID = email;
        externalID = email;
        name = email;
    }

    public long geLastModified()
    {
        return lastModified;
    }

    public void setLastModified(long lastModified)
    {
        this.lastModified = lastModified;
    }

    public long getCreatedOn()
    {
        return createdOn;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean flag)
    {
        enabled = flag;
    }

    public String getHowFound()
    {
        return howFound;
    }

    public void setHowFound(String howFound)
    {
        this.howFound = howFound;
    }

    public String getIndustry()
    {
        return industry;
    }

    public void setIndustry(String industry)
    {
        this.industry = industry;
    }

    public boolean isNotifyProgress()
    {
        return notifyChange;
    }

    public void setNotifyProgress(boolean flag)
    {
        notifyChange = flag;
    }

    public boolean doesExist(Statement stmt)
    {
        ResultSet rs = null;
        try
        {
            rs = stmt.executeQuery("select * from users where external_id = ".concat(String.valueOf(String.valueOf(Strings.getStringForDBInsert(externalID)))));
            if(rs.next())
            {
                boolean flag = true;
                return flag;
            }
        }
        catch(SQLException sqlexception) { }
        finally
        {
            if(rs != null)
                try
                {
                    rs.close();
                }
                catch(SQLException sqlexception1) { }
        }
        return false;
    }

    public void save(Statement stmt, boolean newRecord)
        throws Exception
    {
        StringBuffer query = null;
        if(newRecord)
        {
            query = new StringBuffer("insert into users (external_id, passwd, last_nm, first_nm, nick_nm, pri_email, ind_short_nm, how_found, notify_progress, create_dt, last_modified) values(");
            query.append(Strings.getStringForDBInsert(externalID));
            query.append(",");
            query.append(Strings.getStringForDBInsert(password));
            query.append(",");
            query.append(Strings.getStringForDBInsert(lName));
            query.append(",");
            query.append(Strings.getStringForDBInsert(fName));
            query.append(",");
            query.append(Strings.getStringForDBInsert(nName));
            query.append(",");
            query.append(Strings.getStringForDBInsert(emailID));
            query.append(",");
            query.append(Strings.getStringForDBInsert(industry));
            query.append(",");
            query.append(Strings.getStringForDBInsert(howFound));
            query.append(",");
            query.append(Strings.getStringForDBInsert(notifyChange));
            query.append(",");
            query.append(createdOn);
            query.append(",");
            query.append(lastModified);
            query.append(")");
        } else
        {
            query = new StringBuffer("update users ");
            query.append("set external_id = ");
            query.append(Strings.getStringForDBInsert(externalID));
            query.append(",");
            query.append("passwd = ");
            query.append(Strings.getStringForDBInsert(password));
            query.append(",");
            query.append("last_nm = ");
            query.append(Strings.getStringForDBInsert(lName));
            query.append(",");
            query.append("first_nm = ");
            query.append(Strings.getStringForDBInsert(fName));
            query.append(",");
            query.append("nick_nm = ");
            query.append(Strings.getStringForDBInsert(nName));
            query.append(",");
            query.append("pri_email = ");
            query.append(Strings.getStringForDBInsert(emailID));
            query.append(",");
            query.append("ind_short_nm = ");
            query.append(Strings.getStringForDBInsert(industry));
            query.append(",");
            query.append("how_found = ");
            query.append(Strings.getStringForDBInsert(howFound));
            query.append(",");
            query.append("notify_progress = ");
            query.append(Strings.getStringForDBInsert(notifyChange));
            query.append(",");
            query.append("last_modified = ");
            query.append(System.currentTimeMillis());
            query.append(" where id = ".concat(String.valueOf(String.valueOf(userID))));
        }
        String qStr = query.toString();
        logger.debug("Going to execute: ".concat(String.valueOf(String.valueOf(qStr))));
        stmt.execute(qStr);
    }

    public void loadAllFields(Statement stmt)
        throws Exception
    {
        String query = "select * from users where id = ".concat(String.valueOf(String.valueOf(userID)));
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next())
        {
            setEmailID(rs.getString("external_id"));
            setFirstName(rs.getString("first_nm"));
            setLastName(rs.getString("last_nm"));
            setNickName(rs.getString("nick_nm"));
            setExternalID(rs.getString("external_id"));
            setIndustry(rs.getString("ind_short_nm"));
            setHowFound(rs.getString("how_found"));
            setNotifyProgress(rs.getString("notify_progress").equalsIgnoreCase("t"));
            setPassword(rs.getString("passwd"));
            setEnabled(rs.getString("enabled").equalsIgnoreCase("t"));
            createdOn = rs.getLong("create_dt");
            setLastModified(rs.getLong("last_modified"));
        }
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("UserBean[Name: ");
        sb.append(name);
        sb.append(", ");
        sb.append("Password: ");
        sb.append(password);
        sb.append(",");
        sb.append("FirstName: ");
        sb.append(fName);
        sb.append(",");
        sb.append("LastName: ");
        sb.append(lName);
        sb.append(",");
        sb.append("NickName: ");
        sb.append(nName);
        sb.append(",");
        sb.append("Email: ");
        sb.append(emailID);
        sb.append(",");
        sb.append("ExernalID: ");
        sb.append(externalID);
        sb.append(",");
        sb.append("Industry: ");
        sb.append(industry);
        sb.append(",");
        sb.append("HowFound: ");
        sb.append(howFound);
        sb.append(",");
        sb.append("NotifyProgress: ");
        sb.append(notifyChange);
        sb.append(",");
        sb.append("Enabled: ");
        sb.append(enabled);
        sb.append(",");
        sb.append("CreatedOn: ");
        sb.append(new Date(createdOn));
        sb.append(",");
        sb.append("LastModified: ");
        sb.append(new Date(lastModified));
        sb.append("]");
        return sb.toString();
    }

    public static List getAll()
    {
        return new ArrayList();
    }

    public static UserBean search(Statement stmt, String externalID, String pwd)
        throws Exception
    {
        String query = String.valueOf(String.valueOf((new StringBuffer("select * from users where external_id = ")).append(Strings.getStringForDBInsert(externalID)).append(" and passwd = ").append(Strings.getStringForDBInsert(pwd))));
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next())
        {
            UserBean ub = new UserBean();
            ub.setName(externalID);
            ub.userID = rs.getInt(rs.findColumn("id"));
            return ub;
        }
        if(rs != null)
            try
            {
                rs.close();
            }
            catch(SQLException sqlexception) { }
        return null;
    }

    static AppLogger logger;
    private String name;
    private String password;
    private String fName;
    private String lName;
    private String nName;
    private boolean notifyChange;
    private String industry;
    private String howFound;
    private String externalID;
    private String emailID;
    private boolean enabled;
    private long lastModified;
    private long createdOn;
    private int userID;
    private static final String INSERT_STMT = "insert into users (external_id, passwd, last_nm, first_nm, nick_nm, pri_email, ind_short_nm, how_found, notify_progress, create_dt, last_modified) values(";

    static 
    {
        logger = AppLogger.getLogger(com.sbs.beans.UserBean.class);
    }
}
