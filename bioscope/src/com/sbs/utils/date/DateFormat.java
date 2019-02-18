// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DateFormat.java

package com.sbs.utils.date;

import com.sbs.utils.string.Strings;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DateFormat
{

    public DateFormat()
    {
    }

    public static Date parseDate(Object val)
        throws ParseException
    {
        if(val == null || Strings.isEmpty(String.valueOf(val)))
            return null;
        ParseException pe = null;
        String strDt = String.valueOf(val);
        Date dt = null;
        Iterator itr;
        try
        {
            Date date = DEF_DT_FORMAT.parse(strDt);
            return date;
        }
        catch(Exception ex)
        {
            itr = dfList.iterator();
        }
        do
        {
            if(!itr.hasNext())
                break;
            try
            {
                dt = ((SimpleDateFormat)itr.next()).parse(strDt);
                if(dt != null)
                {
                    Date date1 = dt;
                    return date1;
                }
            }
            catch(ParseException ex1)
            {
                pe = ex1;
            }
        } while(true);
        if(dt == null)
            throw pe;
        else
            return dt;
    }

    private static SimpleDateFormat DEF_DT_FORMAT = new SimpleDateFormat();
    static List dfList;

    static 
    {
        dfList = new ArrayList();
        dfList.add(new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"));
        dfList.add(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"));
        dfList.add(new SimpleDateFormat("MM-dd-yyyy"));
        dfList.add(new SimpleDateFormat("MM/dd/yyyy"));
        dfList.add(new SimpleDateFormat("MM/dd/yy"));
        dfList.add(new SimpleDateFormat("MM-dd-yy"));
    }
}
