function ArrayUtilsC() {
    this.validateArray = validateArray;
    this.getTokens = getTokens;
    this.getElementIndex = getElementIndex;
    this.removeElementByIndex = removeElementByIndex;
    this.removeElements = removeElements;
    this.addElements = addElements;
    this.insertElement = insertElement;

    function validateArray(aParameter) {
        if ((typeof aParameter) == "undefined") {
            return aParameter;
        }

        var anArray = aParameter;
        if((typeof aParameter) == "string") {
            anArray = new Array();
            anArray[0] = aParameter;
        }

        return anArray;
    }

    function getTokens(aString, aDelimiter, times) {
        var index = -1;
        var tokens = new Array();
        var times = times || -1;
        var delimiter = aDelimiter || FIELD_DELIMITER_0;

        var i = 0;
        for(i = 0; (index = aString.indexOf(delimiter)) != -1; i++) {
            tokens[i] = aString.substring(0, index);

            if(index >= aString.length - 1) {
                break;
            }

            aString = aString.substring(index + delimiter.length);

            if(times != -1 && i >= times - 1) {
                i++;
                break;
            }
        }

        aString = StringUtils.trim(aString, delimiter);

        if(aString) {
            tokens[i] = aString;
        }

        return tokens;
    }

    function getElementIndex(anArray, anElement, ignoreCase) {
        for(var i=0; i<anArray.length; i++) {
            var element = anArray[i];

            if (BeanUtils.equals(element, anElement, ignoreCase)) {
                return i;
            }
        }

        return -1;
    }

    function removeElementByIndex(anArray, index) {
        var newArray = new Array();
        for(var i=0, j=0; i<anArray.length; i++) {
            if(index != i) {
                newArray[j++] = anArray[i];
            }
        }

        return newArray;
    }

    function removeElements(anArray, index1, index2) {
        var newArray = new Array();
        var length = anArray.length;

        for (var i = 0, j = 0; i < length; i++) {
            if (i < index1 || i >= index2) {
                newArray[j++] = anArray[i];
            }
        }

        return newArray;
    }

    function addElements(anArray, elements) {
        for (var i = 0; i < elements.length; i++) {
            anArray[anArray.length] = elements[i];
        }
    }

    function insertElement(elementData, position, element) {
        // Move the following element entries
        for (var i = elementData.length - 1; i >= position; i--) {
            elementData[i + 1] = elementData[i];
        }

        // Set the specified key/value pair at the position
        elementData[position] = element;
    }
}

var ArrayUtils = new ArrayUtilsC();