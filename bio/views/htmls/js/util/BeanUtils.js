function BeanUtilsC() {
    this.parseObject = parseObject;
    this.equals = equals;

    function parseObject(objectString, aDelimiter) {
        var delimiter = aDelimiter || FIELD_DELIMITER_0;

        var result = new Object();
        result.keys = new Array();

        // Remove the enclosing characters
        var matches = objectString.match(/^{(.*)}$/);
        if (matches && matches[1]) {
            objectString = matches[1];
        }

        // Preprocess the nested array
        for (var index0 = -1, index1 = -1;;) {
            index0 = objectString.indexOf("[", index0 + 1);
            index1 = objectString.indexOf("]", index0);
            if (index0 == -1 || index1 == -1 || index1 == index0 + 1) {
                break;
            }

            var arrayString = objectString.substring(index0 + 1, index1);
            arrayString = StringUtils.replace(arrayString, FIELD_DELIMITER_0, FIELD_DELIMITER_2);
            arrayString = StringUtils.replace(arrayString, FIELD_DELIMITER_1, FIELD_DELIMITER_2);

            objectString = objectString.substring(0, index0 + 1) + arrayString + objectString.substring(index1);
            index0 = index1;
            index1 = -1;
        }

        // Now process the tokens
        var tokens = ArrayUtils.getTokens(objectString, delimiter);
        for (var i = 0; i < tokens.length; i++) {
            var token = tokens[i];

            var fieldTokens = ArrayUtils.getTokens(token, "=");

            var key = fieldTokens[0];
            var value = fieldTokens[1];

            if (value) {
                var matches = value.match(/^\[(.*)\]$/);
                if (matches) {
                    value = matches[1] ? ArrayUtils.getTokens(matches[1], FIELD_DELIMITER_2) : new Array(0);
                }
            }

            result[key] = value;
            result.keys[i] = key;
        }

        return result;
    }

    function equals(obj1, obj2, ignoreCase) {
        if (obj1 == obj2) {
            return true;
        }

        if ((obj1 && !obj2) || (!obj1 && obj2)) {
            return false;
        }

        if((typeof obj1) == "string" && (typeof obj2) == "string") {
            if (ignoreCase && obj1.toLowerCase() == obj2.toLowerCase()) {
                return true;
            }
        } else if ((typeof obj1) == "object" && (typeof obj2) == "object") {
            if ((typeof obj1.equals) == "function") {
                return obj1.equals(obj2);
            }
        }

        return false;
    }
}

var BeanUtils = new BeanUtilsC();
