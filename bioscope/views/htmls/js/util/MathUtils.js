function MathUtilsC() {
    this.getDecimalParam = getDecimalParam;
    this.shouldCallDefault = shouldCallDefault;
    this.multiplyFloat = multiplyFloat;
    this.divideBy10s = divideBy10s;
    this.convertNumberToString = convertNumberToString;
    this.parseInt = parseInt;
    this.roundFloat = roundFloat;
    this.enHex = enHex;
    this.toHex = toHex;

    function parseInt(aString) {
        return window.parseInt(window.parseFloat(aString));
    }

    function getDecimalParam(aFloat) {
        var obj = new Object();

        var paramStr = new String(aFloat);
        var index = paramStr.indexOf(".");

        if (index == -1) {
            obj.powerOf10s = 0;
            obj.digits = this.parseInt(aFloat);
        } else {
            obj.powerOf10s = paramStr.length - 1 - index;
            obj.digits = this.parseInt(paramStr.replace(/\./, ""));
        }

        return obj;
    }

    function shouldCallDefault(float1, float2) {
            return new String(float1).toLowerCase().indexOf("e") != -1 || new String(float2).toLowerCase().indexOf("e") != -1;
    }

    function multiplyFloat(float1, float2) {
            if (this.shouldCallDefault(float1, float2)) {
                    return float1 * float2;
            }

            var param1 = this.getDecimalParam(float1);
            var param2 = this.getDecimalParam(float2);

            var digits = param1.digits * param2.digits;
            var powerOf10s = param1.powerOf10s + param2.powerOf10s;

            if (this.shouldCallDefault(digits, powerOf10s)) {
                    return float1 * float2;
            }

            return this.divideBy10s(digits, powerOf10s);
    }

    function divideBy10s(aValue, powerOf10s, doNotParseFloat) {
        var valueStr = FormatUtils.padNumber(Math.abs(aValue), powerOf10s + new String(aValue).length);
        var dotIndex = valueStr.indexOf(".");
        if (dotIndex == -1) {
            valueStr += ".";
            dotIndex = valueStr.length - 1;
        }

        var newDotIndex = dotIndex - powerOf10s;
        valueStr = valueStr.replace(/\./, "");
        valueStr = StringUtils.trim(valueStr.substring(0, newDotIndex) + "." + valueStr.substring(newDotIndex), "0");
        if (valueStr.indexOf(".") == 0) {
            valueStr = "0" + valueStr;
        }
        if (aValue < 0) {
            valueStr = "-" + valueStr;
        }

        return doNotParseFloat ? valueStr : parseFloat(valueStr);
    }

    function convertNumberToString(aNumber) {
        var result = new String(aNumber);

        var index = result.indexOf("e");

        if (index == -1) {
            return result;
        }

        var numberPart = eval(result.substring(0, index));
        var powerOf10s = eval(result.substring(index + 1));

        var decimalParam = this.getDecimalParam(numberPart);

        var digits = decimalParam.digits;
        powerOf10s -= decimalParam.powerOf10s;

        if (powerOf10s < 0) {
            result = this.divideBy10s(digits, -1 * powerOf10s, true);
        } else {
            result = FormatUtils.padNumber(digits, new String(digits).length + powerOf10s, true);
        }

        return result;
    }

    function roundFloat(aFloat) {
        if (new String(aFloat).indexOf(".") == -1) {
            return aFloat;
        }

        aFloat = Math.round(this.multiplyFloat(aFloat, 10000));
        aFloat = this.divideBy10s(aFloat, 4);

        return aFloat;
    }

    function enHex(aDigit) {
        return("0123456789ABCDEF".substring(aDigit, aDigit+1))
    }

    function toHex(n, length) {
        var result = (enHex((0xf00000 & n) >> 20) +
                enHex((0x0f0000 & n) >> 16) +
                enHex((0x00f000 & n) >> 12) +
                enHex((0x000f00 & n) >>  8) +
                enHex((0x0000f0 & n) >>  4) +
                enHex((0x00000f & n) >>  0));
        return FormatUtils.padNumber(result, length);
    }
}

var MathUtils = new MathUtilsC();