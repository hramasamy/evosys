// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IFacet.java

package com.sbs.espace;

import com.sbs.utils.IPersistable;

// Referenced classes of package com.sbs.espace:
//            FacetValidationException, ISimpleDataType

public interface IFacet
    extends IPersistable
{

    public abstract void setDataType(ISimpleDataType isimpledatatype);

    public abstract void setLimit(Object obj);

    public abstract Object getLimit();

    public abstract boolean validate(Object obj)
        throws FacetValidationException;

    public static final int ENUMERATION = 0;
    public static final int FRACTION_DIGITS = 1;
    public static final int LENGTH = 2;
    public static final int MAX_EXCLUSIVE = 3;
    public static final int MAX_INCLUSIVE = 4;
    public static final int MAX_LENGTH = 5;
    public static final int MIN_EXCLUSIVE = 6;
    public static final int MIN_INCLUSIVE = 7;
    public static final int MIN_LENGTH = 8;
    public static final int PATTERN = 9;
    public static final int TOTAL_DIGITS = 10;
    public static final int WHITE_SPACE = 11;

}
