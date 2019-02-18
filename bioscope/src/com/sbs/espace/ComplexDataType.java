// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ComplexDataType.java

package com.sbs.espace;

import com.sbs.utils.string.Strings;
import java.util.*;

// Referenced classes of package com.sbs.espace:
//            DataType, IAttribute, IDataType, ValidationException, 
//            IComplexDataType

public class ComplexDataType extends DataType
    implements IComplexDataType
{

    public ComplexDataType()
    {
        attrList = new ArrayList();
        children = new ArrayList();
    }

    public ComplexDataType(String name)
    {
        attrList = new ArrayList();
        children = new ArrayList();
        setName(name);
    }

    public int getType()
    {
        return 1;
    }

    public void renameAttribute(String oldName, String newName)
    {
        int position = getPosition(oldName);
        if(position != -1)
            ((IAttribute)attrList.get(position)).setName(newName);
    }

    public void insertAttribute(int position, IAttribute attr)
    {
        if(position < 0 || position >= attrList.size())
            attrList.add(attr);
        else
            attrList.add(position, attr);
    }

    public void insertAttribute(String name, IAttribute attr, int where)
    {
        int position = getPosition(name);
        if(where == 1)
            position++;
        insertAttribute(position, attr);
    }

    public IAttribute setAttribute(IAttribute attr)
    {
        if(attr == null)
            return attr;
        IAttribute retAttr = null;
        int position = getPosition(attr.getName());
        if(position != -1)
            retAttr = (IAttribute)attrList.set(position, attr);
        else
            attrList.add(attr);
        return retAttr;
    }

    public IAttribute getAttribute(String name)
    {
        int position = getPosition(name);
        if(position == -1)
            return null;
        else
            return (IAttribute)attrList.get(position);
    }

    public void addAttribute(IAttribute att)
    {
        if(att != null)
            attrList.add(att);
    }

    public IAttribute removeAttribute(String attName)
    {
        return (IAttribute)attrList.remove(getPosition(attName));
    }

    public Iterator attributes()
    {
        return attrList.iterator();
    }

    public String[] getAttributeNames()
    {
        String names[] = new String[getAttributeCount()];
        Iterator itr = attrList.iterator();
        for(int i = 0; itr.hasNext(); i++)
            names[i] = ((IAttribute)itr.next()).getName();

        return names;
    }

    public int hasAttribute(String name)
    {
        return getPosition(name);
    }

    public int getAttributeCount()
    {
        return attrList.size();
    }

    public void addChild(IDataType dt)
    {
        if(dt != null)
        {
            children.add(dt);
            dt.setParent(this);
        }
    }

    public IDataType getChild(String name)
    {
        int pos = getChildPosition(name);
        if(pos != -1)
            return (IDataType)children.get(pos);
        else
            return null;
    }

    public IDataType removeChild(String name)
    {
        int pos = getChildPosition(name);
        if(pos != -1)
        {
            IDataType child = (IDataType)children.remove(pos);
            child.setParent(null);
            return child;
        } else
        {
            return null;
        }
    }

    public boolean isRoot()
    {
        return getParent() == null;
    }

    public boolean isLeaf()
    {
        return children.size() == 0;
    }

    public Iterator children()
    {
        return children.iterator();
    }

    public boolean hasChild(String name)
    {
        if(Strings.isNotEmpty(name))
        {
            for(int i = 0; i < children.size(); i++)
                if(((IDataType)children.get(i)).getName().equals(name.trim()))
                    return true;

        }
        return false;
    }

    public int getChildCount()
    {
        return children.size();
    }

    public String[] getChildNames()
    {
        String names[] = new String[getChildCount()];
        Iterator itr = children.iterator();
        for(int i = 0; itr.hasNext(); i++)
            names[i] = ((IDataType)itr.next()).getName();

        return names;
    }

    public Object validate(Object value)
        throws ValidationException
    {
        if(value == null)
            return null;
        if(value instanceof Object[])
        {
            Object arr[] = (Object[])value;
            Object validVal[] = new Object[arr.length];
            Iterator itr = attributes();
            StringBuffer validationError = null;
            boolean failed = false;
            for(int i = 0; itr.hasNext() && i < arr.length; i++)
            {
                IAttribute att = (IAttribute)itr.next();
                try
                {
                    validVal[i] = att.getDataType().validate(arr[i]);
                    continue;
                }
                catch(ValidationException ex)
                {
                    if(!failed)
                        validationError = new StringBuffer(ex.getMessage());
                    else
                        validationError.append(ex.getMessage());
                }
                validationError.append("\n");
            }

            if(failed)
                throw new ValidationException(validationError.toString());
            else
                return ((Object) (validVal));
        } else
        {
            if(getAttributeCount() != 1);
            return value;
        }
    }

    public Object validate(String attName, Object value)
        throws ValidationException
    {
        if(getPosition(attName) != -1)
            return getAttribute(attName).getDataType().validate(value);
        else
            return null;
    }

    public boolean validate()
        throws ValidationException
    {
        return false;
    }

    public Object[] validate(String attNames[], Object values[])
        throws ValidationException
    {
        return null;
    }

    public Object[] validate(Object values[])
        throws ValidationException
    {
        return null;
    }

    public int compare(Object source, Object target)
    {
        return 0;
    }

    public Map backup()
    {
        Map props = new HashMap();
        return props;
    }

    public void restore(Map map)
    {
    }

    private int getPosition(String name)
    {
        int position = -1;
        int i = 0;
        do
        {
            if(i >= attrList.size())
                break;
            IAttribute attr = (IAttribute)attrList.get(i);
            if(name.equals(attr.getName()))
            {
                position = i;
                break;
            }
            i++;
        } while(true);
        return position;
    }

    private int getChildPosition(String name)
    {
        int position = -1;
        int i = 0;
        do
        {
            if(i >= children.size())
                break;
            IDataType dt = (IDataType)children.get(i);
            if(name.equals(dt.getName()))
            {
                position = i;
                break;
            }
            i++;
        } while(true);
        return position;
    }

    private List attrList;
    private List children;
}
