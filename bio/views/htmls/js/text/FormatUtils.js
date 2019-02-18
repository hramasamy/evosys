function FormatUtilsC() {
    this.MONTH_NAMES = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

    this.groupNumber = groupNumber;
    this.padNumber = padNumber;
    this.getDecimalPlaces = getDecimalPlaces;

    function groupNumber(anNumberStr, groupingChar) {
        var divs = parseInt((anNumberStr.length - (anNumberStr.indexOf("-") == 0 ? 2 : 1))/ 3);

        for (var i = 0; i < divs; i++) {
            var index = anNumberStr.length - (i + 1) * 3 - i;
            if (index == 0 ) {
                break;
            }

            anNumberStr = anNumberStr.substring(0, index) + groupingChar + anNumberStr.substring(index);
        }

        return anNumberStr;
    }

    function padNumber(anNumber, width, rightSide) {
        var theNumberStr = new String(anNumber);

        if(theNumberStr.length == width) {
            return theNumberStr;
        }

        if(theNumberStr.length > width) {
            if (!rightSide) {
                return theNumberStr.substring(theNumberStr.length - width, theNumberStr.length);
            } else {
                return theNumberStr.substring(0, width);
            }
        }

        var theNumberLength = theNumberStr.length;

        for(var i=0; i<width - theNumberLength; i++) {
            if (!rightSide) {
                theNumberStr = "0" + theNumberStr;
            } else {
                theNumberStr = theNumberStr + "0";
            }
        }

        return theNumberStr;
    }

    function getDecimalPlaces(aNumber, aDecimalSeparater, trimTailingZero) {
        var decimalSeparater = aDecimalSeparater || ".";

        if ((typeof aNumber) == "number") {
            decimalSeparater = ".";
        }

        var numberStr = new String(aNumber);
        numberStr = StringUtils.trim(numberStr, ")");
        numberStr = StringUtils.trim(numberStr, "-");
        if (trimTailingZero) {
            numberStr = StringUtils.trim(numberStr, "0");
        }

        var index = numberStr.indexOf(decimalSeparater);
        return index == -1 ? 0 : numberStr.length - index - 1;
    }
}

var FormatUtils = new FormatUtilsC();
