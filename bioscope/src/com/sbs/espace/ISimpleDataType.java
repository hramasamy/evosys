// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ISimpleDataType.java

package com.sbs.espace;

import java.util.Date;
import java.util.Iterator;

// Referenced classes of package com.sbs.espace:
//            IDataType, IFacet, PrimitiveTypes, ValidationException, 
//            DataTypeValidator, ListType, MapType

public interface ISimpleDataType
    extends IDataType
{

    public abstract boolean isPrimitive();

    public abstract void addFacet(IFacet ifacet);

    public abstract Iterator facets();

    public abstract boolean isList();

    public abstract boolean isUnion();

    public abstract int compare(Object obj, Object obj1);

    public static final ISimpleDataType booleanType = new PrimitiveTypes() {

        public int getPrimitiveType()
        {
            return 7;
        }

        public Object validate(Object value)
            throws ValidationException
        {
            if(value == null || (value instanceof Boolean))
                return value;
            else
                return new Boolean(DataTypeValidator.validateBoolean(value));
        }

    };
    public static final ISimpleDataType intType = new PrimitiveTypes() {

        public int getPrimitiveType()
        {
            return 0;
        }

        public Object validate(Object value)
            throws ValidationException
        {
            if(value == null || (value instanceof Integer))
                return value;
            else
                return new Integer(DataTypeValidator.validateInt(value));
        }

    };
    public static final ISimpleDataType byteType = new PrimitiveTypes() {

        public int getPrimitiveType()
        {
            return 4;
        }

        public Object validate(Object value)
            throws ValidationException
        {
            if(value == null || (value instanceof Byte))
                return value;
            else
                return new Byte(DataTypeValidator.validateByte(value));
        }

    };
    public static final ISimpleDataType floatType = new PrimitiveTypes() {

        public int getPrimitiveType()
        {
            return 2;
        }

        public Object validate(Object value)
            throws ValidationException
        {
            if(value == null || (value instanceof Float))
                return value;
            else
                return new Float(DataTypeValidator.validateFloat(value));
        }

    };
    public static final ISimpleDataType longType = new PrimitiveTypes() {

        public int getPrimitiveType()
        {
            return 1;
        }

        public Object validate(Object value)
            throws ValidationException
        {
            if(value == null || (value instanceof Long))
                return value;
            else
                return new Long(DataTypeValidator.validateLong(value));
        }

    };
    public static final ISimpleDataType doubleType = new PrimitiveTypes() {

        public int getPrimitiveType()
        {
            return 3;
        }

        public Object validate(Object value)
            throws ValidationException
        {
            if(value == null || (value instanceof Double))
                return value;
            else
                return new Double(DataTypeValidator.validateDouble(value));
        }

    };
    public static final ISimpleDataType dateType = new PrimitiveTypes() {

        public int getPrimitiveType()
        {
            return 5;
        }

        public Object validate(Object value)
            throws ValidationException
        {
            if(value == null || (value instanceof Date))
                return value;
            else
                return DataTypeValidator.validateDate(value);
        }

    };
    public static final ISimpleDataType stringType = new PrimitiveTypes() {

        public int getPrimitiveType()
        {
            return 6;
        }

        public Object validate(Object value)
            throws ValidationException
        {
            if(value == null || (value instanceof String))
                return value;
            else
                return String.valueOf(value);
        }

    };
    public static final ISimpleDataType listType = new ListType() {

        public Object validate(Object value)
            throws ValidationException
        {
            return value;
        }

    };
    public static final ISimpleDataType mapType = new MapType() {

        public Object validate(Object value)
            throws ValidationException
        {
            return value;
        }

    };

}
