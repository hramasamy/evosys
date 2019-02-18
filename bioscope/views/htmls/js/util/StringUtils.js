function StringUtilsC() {
    this.getSubstring = getSubstring;
    this.getParameter = getParameter;
    this.getHtmlParameter = getHtmlParameter;
    this.trim = trim;
    this.replace = replace;

    function getSubstring(aString, flag1, flag2) {
        var index1 = aString.indexOf(flag1);
        if (index1 == -1) {
            return null;
        }

        var index2 = aString.indexOf(flag2, index1 + flag1.length);
        if (index2 == -1) {
            index2 = aString.length;
        }

        return aString.substring(index1 + flag1.length, index2);
    }

    function getParameter(aString, paramName, aDelimiters, tailToTrim) {
        // Locate index0
        var index0 = aString.indexOf(paramName + "=");
        if (index0 == -1) {
            return null;
        }
        index0 += paramName.length + 1;

        // Locate index1
        var index1 = -1;
        var delimiters = aDelimiters || [FIELD_DELIMITER_0, ",", "]", "}", "?", "&"];

        for (var i = 0; i < delimiters.length && index1 == -1; i++) {
            index1 = aString.indexOf(delimiters[i], index0);
        }

        if (index1 == -1) {
            index1 = aString.length;
        }

        var result = aString.substring(index0, index1);
        if (tailToTrim) {
            result = this.trim(result, tailToTrim);
        }
        return result;
    }

    function getHtmlParameter(aString, paramName) {
        return this.getParameter(aString, paramName, [" ", ">"], "\"");
    }

    function trim(aString, tail, aTrimLeadingFlag, aTrimTrailingFlag) {
        var trimLeadingFlag = (typeof(aTrimLeadingFlag) == "undefined" ? true : aTrimLeadingFlag);
        var trimTrailingFlag = (typeof(aTrimTrailingFlag) == "undefined" ? true : aTrimTrailingFlag);

        if(aString.length == 0) {
            return "";
        }

        if (!tail) {
            return new String(aString).replace(/^\s*|\s*$/g, "");
        }

        var index = -1;

        while(trimLeadingFlag && aString.indexOf(tail) == 0) {
            if(aString == tail) {
                return "";
            }

            aString = aString.substring(tail.length);
        }

        while(trimTrailingFlag && (index = aString.lastIndexOf(tail)) == aString.length - tail.length && index != -1) {
            aString = aString.substring(0, index);
        }

        return aString;
    }

    function replace(aString, oldPart, newPart) {
        var regExp = new RegExp(oldPart, "g");
        return aString.replace(regExp, newPart);
    }
}

var StringUtils = new StringUtilsC();