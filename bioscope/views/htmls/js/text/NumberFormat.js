NumberFormat.prototype.statusIconsPath = window.contextPath + "/pages/images/status";
NumberFormat.prototype.numOfStatus = 6;

function NumberFormat(style) {
    var STATUS = "status";

    this.style = style;

    this.gs = ",";
    this.ds = ".";
    this.ni = (this.style == CURRENCY_STYLE || this.style == NEGATIVE_NUMBER_STYLE || this.style == NEGATIVE_NUMBER_RED_STYLE ? "()" : "-");

    this.preHandleNegativeString = preHandleNegativeString;
    this.getGroupingSepPattern = getGroupingSepPattern;
    this.getDecimalSepPattern = getDecimalSepPattern;

    this.applyNegativeIndicator = applyNegativeIndicator;

    this.parse = parse;
    this.format = format;

    this.MAX_REAL_DIGITS = 17;
    this.msgInvalidDataEntry = "Invalid data entry";

    function parse(aString) {
        if (!aString) {
            return 0;
        }

        if (this.style == STATUS_STYLE) {
            var imagePath = StringUtils.getHtmlParameter(aString, "src");
            var imageName = imagePath.substring(imagePath.lastIndexOf("/") + 1);

            return parseInt(imageName.substring(STATUS.length, imageName.indexOf(".")));
        }

        if (this.style == CURRENCY_STYLE && aString.indexOf("$") == 0) {
            aString = aString.substring(1);
        }

        aString = aString.replace(/ |\$/g, "");
        aString = this.preHandleNegativeString(aString);

        if (aString.match(/^\(\)$/)) {
            aString = "-" + aString.substring(1, aString.length - 1);
        }

        aString = aString.replace(this.getGroupingSepPattern(), "");
        aString = aString.replace(this.getDecimalSepPattern(), ".");

        var result = parseFloat(aString);

        if (this.style == PERCENT_STYLE) {
            result = MathUtils.divideBy10s(result, 2);
        }

        return result;
    }

    function preHandleNegativeString(aString) {
        if (!aString) {
            return aString;
        }

        if (this.ni == "()" && (aString.indexOf("(") == 0 && aString.indexOf(")") == aString.length - 1)) {
            return "-" + aString.substring(1, aString.length - 1);
        }

        return aString;
    }

    function format(aValue, decimalPlaces) {
        if ((typeof aValue) == "undefined" || aValue.length == 0) {
            return "";
        }

        if ((typeof decimalPlaces) == "undefined") {
            decimalPlaces = FormatUtils.getDecimalPlaces(aValue);
        }

        var htmlObj = null;

        if ((typeof aValue) == "object") {
            htmlObj = aValue;
            aValue = StringUtils.trim(htmlObj.value).toLowerCase();
            if (aValue == "") {
                return;
            }
            aValue  = this.parse(aValue, true);
        }

        if (isNaN(aValue)) {
            alert(this.msgInvalidDataEntry);
            return;
        }

        var result = null;

        // Status light
        if (this.style == STATUS_STYLE) {
            aValue = parseInt(aValue);
            if (aValue < 0 || aValue >= this.numOfStatus) {
                aValue = 0;
            }

            result = "<img src='" + this.statusIconsPath + "/" + STATUS + aValue + ".gif' border=0>";
        } else {
            if (this.style == PERCENT_STYLE) {
                aValue = MathUtils.multiplyFloat(aValue, 100);
            }

            var valueStr = MathUtils.convertNumberToString(aValue);

            var fields = ArrayUtils.getTokens(valueStr, ".");
            if (fields.length == 0) {
                return valueStr;
            }

            var integerStr = fields[0];
            var decimalStr = fields[1] || "";

            integerStr = FormatUtils.groupNumber(integerStr, this.gs);
            decimalStr = decimalPlaces >= 0 ? FormatUtils.padNumber(decimalStr, decimalPlaces, true) : decimalStr;
            result = (decimalStr == "" ? integerStr : integerStr + this.ds + decimalStr);

            if (this.style == PERCENT_STYLE) {
                result += "%";
            } else if (this.style == CURRENCY_STYLE) {
                if (aValue < 0) {
                    result = "-$" + result.substring(1);
                } else {
                    result = "$" + result;
                }
            }

            if (aValue < 0) {
                result = this.applyNegativeIndicator(result.substring(1));
            }
        }

        if (htmlObj != null) {
            htmlObj.value = result;
        }

        return result;
    }

    function applyNegativeIndicator(aString) {
        if (this.ni == "()") {
            aString = "(" + aString + ")";
        } else {
            aString = "-" + aString;
        }

        return aString;
    }

    function getGroupingSepPattern() {
        return new RegExp("\\" + this.gs, "g");
    }

    function getDecimalSepPattern() {
        return new RegExp("\\" + this.ds, "g");
    }
}