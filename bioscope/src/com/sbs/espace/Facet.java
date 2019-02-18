// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Facet.java

package com.sbs.espace;

import com.sbs.utils.AppLogger;
import com.sbs.utils.string.Strings;
import java.util.*;
import org.apache.xerces.impl.xpath.regex.RegularExpression;

// Referenced classes of package com.sbs.espace:
//            ValidationException, IFacet, FacetValidationException, ISimpleDataType, 
//            DataTypeValidator

public class Facet
    implements IFacet
{
    public static class EnumerationFacet extends Facet
    {

        public void setLimit(Object limit)
        {
            if(limit != null && Strings.isNotEmpty(String.valueOf(limit)))
            {
                Collection limitC = null;
                if(limit instanceof Collection)
                    limitC = (Collection)limit;
                else
                    limitC = Strings.collectionFromString(limit);
                this.limit = limitC;
                if(getDataType() != ISimpleDataType.stringType)
                {
                    validateLimit();
                    setLimitValidated(true);
                }
            }
        }

        protected void validateLimit()
        {
            if(getDataType() != ISimpleDataType.stringType)
            {
                Collection limitC = (Collection)limit;
                Collection limitSet = new ArrayList();
                Iterator itr = limitC.iterator();
                do
                {
                    if(!itr.hasNext())
                        break;
                    Object ne = itr.next();
                    try
                    {
                        Object next = DataTypeValidator.validate(getDataType(), ne);
                        if(!limitSet.contains(next))
                            limitSet.add(next);
                    }
                    catch(ValidationException ex)
                    {
                        logger.warn(String.valueOf(String.valueOf((new StringBuffer("The datatype specified by limit object and facet's datatype mismatch: ")).append(ne.getClass().getName()).append(", ").append(getDataType()))));
                    }
                } while(true);
                limit = limitSet;
            }
            setLimitValidated(true);
        }

        public boolean validate(Object value)
            throws FacetValidationException
        {
            if(getLimit() != null)
            {
                if(!isLimitValid())
                    validateLimit();
                Collection c = (Collection)getLimit();
                if(!c.isEmpty())
                {
                    try
                    {
                        value = DataTypeValidator.validate(getDataType(), value);
                    }
                    catch(ValidationException ex)
                    {
                        boolean flag = false;
                        return flag;
                    }
                    for(Iterator itr = c.iterator(); itr.hasNext();)
                    {
                        Object next = itr.next();
                        if(getDataType().compare(next, value) == 0)
                            return true;
                    }

                    return false;
                }
            }
            return true;
        }

        public EnumerationFacet()
        {
            super(0);
        }
    }

    public static class PatternFacet extends Facet
    {

        public boolean validate(Object value)
            throws FacetValidationException
        {
            if(getLimit() != null)
            {
                String strV = String.valueOf(value).trim();
                boolean test = ((RegularExpression)getLimit()).matches(strV);
                if(!test)
                    throw new FacetValidationException(String.valueOf(String.valueOf((new StringBuffer("Provided value does not match the pattern. Pattern: ")).append(getLimit()).append(", value: ").append(value))));
            }
            return true;
        }

        public void setLimit(Object pattern)
        {
            setLimit(new RegularExpression(String.valueOf(pattern).trim()));
        }

        public PatternFacet()
        {
            super(9);
        }
    }

    public static class MinInclusiveFacet extends Facet
    {

        public boolean validate(Object value)
            throws FacetValidationException
        {
            Class class1;
            if(value != null)
                class1 = value.getClass();
            return false;
        }

        public MinInclusiveFacet()
        {
            super(7);
        }
    }


    protected Facet(int facetID)
    {
        logger = AppLogger.getLogger((com.sbs.espace.Facet.class).getName());
        dt = ISimpleDataType.stringType;
        limitValidated = false;
        this.facetID = facetID;
    }

    public void setLimit(Object obj)
    {
        if(obj != null)
        {
            limit = obj;
            if(dt != ISimpleDataType.stringType)
            {
                limit = obj;
                validateLimit();
            }
        }
    }

    public Object getLimit()
    {
        return limit;
    }

    public ISimpleDataType getDataType()
    {
        return dt;
    }

    public void setDataType(ISimpleDataType dt)
    {
        this.dt = dt;
        if(dt == ISimpleDataType.stringType)
            limitValidated = true;
        else
            limitValidated = false;
    }

    public boolean validate(Object value)
        throws FacetValidationException
    {
        return true;
    }

    public Map backup()
    {
        Map props = new HashMap();
        return props;
    }

    public void restore(Map map)
    {
    }

    protected boolean isLimitValid()
    {
        return limitValidated;
    }

    protected void setLimitValidated(boolean flag)
    {
        limitValidated = flag;
    }

    protected void validateLimit()
    {
        try
        {
            limit = DataTypeValidator.validate(getDataType(), getLimit());
            limitValidated = true;
        }
        catch(ValidationException ex)
        {
            limit = null;
            logger.warn(String.valueOf(String.valueOf((new StringBuffer("The datatype specified by limit object and facet's datatype mismatch: ")).append(limit.getClass().getName()).append(", ").append(dt))));
        }
    }

    protected AppLogger logger;
    private int facetID;
    protected Object limit;
    private ISimpleDataType dt;
    private boolean limitValidated;
}
