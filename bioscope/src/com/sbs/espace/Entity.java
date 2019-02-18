// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Entity.java

package com.sbs.espace;

import com.sbs.utils.collection.Collections;
import com.sbs.utils.string.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.sbs.espace:
//            IComplexDataType, NoSuchNodeException, IEntity, ValidationException, 
//            IDataType, INamespace, IAttribute

public class Entity
    implements IEntity
{

    public Entity()
    {
        validating = true;
        isComplex = false;
        attributeValues = new ArrayList();
    }

    public Entity(IDataType dt)
    {
        validating = true;
        isComplex = false;
        setDataType(dt);
    }

    public Entity(IDataType dt, Object nodeValue)
    {
        this(dt);
        setNodeValue(nodeValue);
    }

    public void setValidation(boolean flag)
    {
        validating = flag;
    }

    public boolean isValidating()
    {
        return validating;
    }

    public int getContentType()
    {
        return 0;
    }

    public INamespace getNamespace()
    {
        if(getDataType() != null)
            return getDataType().getNamespace();
        else
            return INamespace.DEF_NAMESPACE;
    }

    public String getEntityType()
    {
        return "base";
    }

    public IDataType getDataType()
    {
        return dataType;
    }

    public void setDataType(IDataType dt)
    {
        dataType = dt;
        if(dataType != null)
        {
            if(dataType.getType() == 1)
            {
                int count = ((IComplexDataType)dataType).getAttributeCount();
                attributeValues = new ArrayList(count);
                isComplex = true;
            }
        } else
        {
            attributeValues = new ArrayList();
        }
    }

    public String getDataTypeName()
    {
        if(dataType != null)
            return dataType.getName();
        else
            return null;
    }

    public String getName()
    {
        if(Strings.isNotEmpty(instanceName))
            return instanceName;
        else
            return getDataTypeName();
    }

    public void setName(String instanceName)
    {
        this.instanceName = instanceName;
    }

    public boolean isRoot()
    {
        if(getDataType() != null && getDataType().getType() == 1)
            return ((IComplexDataType)getDataType()).isRoot();
        else
            return false;
    }

    public boolean isLeaf()
    {
        if(dataType != null && isComplexType())
            return ((IComplexDataType)dataType).isLeaf();
        else
            return true;
    }

    public boolean hasAttribute(String attrName)
    {
        return getDataType() != null && isComplexType() && ((IComplexDataType)dataType).hasAttribute(attrName) != -1;
    }

    public boolean hasChildNode(String chName)
    {
        return getDataType() != null && isComplex && ((IComplexDataType)dataType).hasChild(chName) && children.get(chName) != null;
    }

    public void addChildNode(IEntity node)
        throws NoSuchNodeException
    {
        if(!hasChild(node.getDataTypeName()))
            children.put(node.getDataTypeName(), new ArrayList());
        ((List)children.get(node.getDataTypeName())).add(node);
    }

    public boolean removeChildNode(IEntity node)
    {
        try
        {
            if(hasChild(node.getDataType().getName()))
            {
                boolean flag = ((List)children.get(node.getDataTypeName())).remove(node);
                return flag;
            }
        }
        catch(NoSuchNodeException nosuchnodeexception) { }
        return false;
    }

    public IEntity removeChildNode(String childName, String attr[], Object val[])
    {
        try
        {
            Iterator itr = getChildNodeIterator(childName);
            IEntity current =null;
            boolean found;
            do
            {
                do
                {
                    if(!itr.hasNext())
                        break;
                    current = (IEntity)itr.next();
                } while(current.getDataType().getType() != 1);
                IComplexDataType cdt = (IComplexDataType)current.getDataType();
                found = true;
                int i = 0;
                do
                {
                    if(i >= attr.length || i >= val.length)
                        break;
                    if(cdt.getAttribute(attr[i]).getDataType().compare(current.getAttributeValue(attr[i]), val[i]) != 0)
                    {
                        found = false;
                        break;
                    }
                    i++;
                } while(true);
            } while(!found);
            IEntity ientity = current;
            return ientity;
        }
        catch(NoSuchNodeException nosuchnodeexception) { }
        return null;
    }

    public List getChildNodes(String childName)
        throws NoSuchNodeException
    {
        if(hasChild(childName))
            return (List)children.get(childName);
        else
            return Collections.EMPTY_LIST;
    }

    public Iterator getChildNodeIterator(String childName)
        throws NoSuchNodeException
    {
        return getChildNodes(childName).iterator();
    }

    public int getChildNodeCount(String childName)
        throws NoSuchNodeException
    {
        if(hasChild(childName))
            return ((List)children.get(childName)).size();
        else
            return -1;
    }

    public String[] getChildrenNames()
    {
        if(getDataType() != null && isComplexType())
            return ((IComplexDataType)dataType).getChildNames();
        else
            return null;
    }

    public boolean hasChild(String name)
        throws NoSuchNodeException
    {
        if(getDataType() != null && isComplexType() && ((IComplexDataType)dataType).hasChild(name))
            return getChildMap().containsKey(name);
        else
            throw new NoSuchNodeException("No such node exists, please check the datatype: ".concat(String.valueOf(String.valueOf(dataType))) != null ? "null" : dataType.getName());
    }

    public boolean hasChildren()
    {
        return !getChildMap().isEmpty();
    }

    public Iterator getIteratorForChildNode(String string)
    {
        return null;
    }

    public void addAttributeValue(String s, Object obj)
    {
    }

    public Iterator attributes()
    {
        return getInstanceData().iterator();
    }

    public Object getAttributeValue(String att)
    {
        if(isComplexType())
        {
            int attPosition = ((IComplexDataType)getDataType()).hasAttribute(att);
            if(attPosition != -1 && getInstanceData().size() > attPosition)
                return attributeValues.get(attPosition);
        }
        return null;
    }

    public Object setAttributeValue(String attName, Object val)
        throws ValidationException
    {
        Object validVal = val;
        if(isComplexType())
        {
            IComplexDataType cdt = (IComplexDataType)getDataType();
            int position = cdt.hasAttribute(attName);
            if(position != -1)
            {
                if(isValidating())
                    validVal = cdt.validate(attName, val);
                List d = getInstanceData();
                if(d.size() <= position)
                    d.add(position, validVal);
                else
                    validVal = getInstanceData().set(position, validVal);
            }
        } else
        if(getDataType().getName().equals(attName))
        {
            if(isValidating())
                validVal = getDataType().validate(val);
            getInstanceData().add(0, validVal);
        }
        return validVal;
    }

    public void setAttributeValues(Object value[])
        throws ValidationException
    {
        Object validVal[] = value;
        if(isValidating())
            validVal = (Object[])getDataType().validate(((Object) (value)));
        List l = getInstanceData();
        for(int i = 0; i < validVal.length; i++)
            l.add(i, validVal[i]);

    }

    public void setAttributeValues(String attNames[], Object val[])
        throws ValidationException
    {
        for(int i = 0; i < attNames.length && i < val.length; i++)
            setAttributeValue(attNames[i], val[i]);

    }

    public Object[] getAttributeValues()
    {
        return Collections.toArray(attributeValues);
    }

    public Object[] select(String expression)
        throws NoSuchNodeException
    {
        return null;
    }

    public void setNodeValue(Object nodeValue)
    {
        this.nodeValue = nodeValue;
    }

    public Object getNodeValue()
    {
        return nodeValue;
    }

    public IEntity clone(boolean deep)
    {
        IEntity cloned = new Entity(getDataType());
        cloned.setName(new String(getName()));
        List cData = new ArrayList(attributeValues.size());
        java.util.Collections.copy(attributeValues, cData);
        try
        {
            cloned.setAttributeValues(Collections.toArray(cData));
        }
        catch(ValidationException validationexception) { }
        if(!deep);
        return cloned;
    }

    protected Map getChildMap()
    {
        if(children == null)
            children = new HashMap();
        return children;
    }

    protected List getInstanceData()
    {
        if(attributeValues == null)
            if(isComplexType())
                attributeValues = new ArrayList(((IComplexDataType)getDataType()).getAttributeCount());
            else
                attributeValues = new ArrayList();
        return attributeValues;
    }

    protected IAttribute getAttribute(String name)
    {
        if(isComplexType())
            return ((IComplexDataType)dataType).getAttribute(name);
        else
            return null;
    }

    protected boolean isComplexType()
    {
        return isComplex;
    }

    protected Object nodeValue;
    private IDataType dataType;
    private String instanceName;
    private IEntity parent;
    private Map children;
    private List attributeValues;
    private boolean validating;
    private boolean isComplex;
}
