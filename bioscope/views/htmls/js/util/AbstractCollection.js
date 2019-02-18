function AbstractCollection(elements) {
    this.elementData = new Array();
    if (elements) {
        for (var i = 0; i < elements.length; i++) {
            this.elementData[i] = elements[i];
        }
    }

    this.isCollection = true;
    this.size = size;
    this.isEmpty = isEmpty;
    this.add = add;
    this.addAll = addAll;
    this.remove = remove;
    this.removeAll = removeAll;
    this.clear = clear;
    this.contains = contains;
    this.toArray =  toArray;
    this.toString = toString;

    function size() {
        return this.elementData.length;
    }

    function isEmpty() {
        return this.size() == 0;
    }

    function add(anElement, anInstance) {
        if ((typeof anElement) == "undefined") {
            return false;
        }

        var theInstance = anInstance || this;
        theInstance.elementData[theInstance.elementData.length] = anElement;
        return true;
    }

    function addAll(aCollection) {
        for(var i=0; i<aCollection.size(); i++) {
            this.add(aCollection.elementData[i]);
        }

        return true;
    }

    function remove(anElement) {
        var index = ArrayUtils.getElementIndex(this.elementData, anElement);
        if(index == -1) {
            return null;
        }

        var removedElement = this.elementData[index];
        this.elementData = ArrayUtils.removeElementByIndex(this.elementData, index);
        return removedElement;
    }

    function removeAll(aCollection)	{
        for(var i=0; i<aCollection.size(); i++) {
            this.remove(aCollection.elementData[i]);
        }

        return true;
    }

    function clear() {
        this.elementData = new Array();
    }

    function contains(anElement) {
        return ArrayUtils.getElementIndex(this.elementData, anElement) != -1;
    }

    function toArray() {
        return this.elementData;
    }

    function toString() {
        var result = "";

        for (var i = 0; i < this.elementData.length; i++) {
            if (i > 0) {
                result += ",";
            }
            result += this.elementData[i];
        }

        return result;
    }
}