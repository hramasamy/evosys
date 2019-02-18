function LeafRenderer() {
    this.dataType = -1;

    this.getNumberFormat = getNumberFormat;
    this.getObjectValue = getObjectValue;
    this.isNumber = isNumber;

    function getNumberFormat() {
        var numberFormat = this.numberFormat;
        var numberStyle = this.numberStyle;

        if ((typeof numberFormat) != "undefined") {
            return numberFormat;
        }

        if (numberStyle) {
            numberFormat = new NumberFormat(numberStyle);
        } else if (this.isNumber()) {
            numberFormat = new NumberFormat(REGULAR_STYLE);
        } else {
            numberFormat = null;
        }

        this.numberFormat = numberFormat;

        return numberFormat;
    }

    function getObjectValue(element, stringValue) {
        if ((typeof element.objectValue) == "undefined") {
            var numberFormat = this.getNumberFormat();
            if (numberFormat != null) {
                if (numberFormat.style != STATUS_STYLE) {
                    stringValue = StringUtils.trim(StringUtils.trim(stringValue, "&nbsp;"));
                    element.objectValue = numberFormat.parse(stringValue);
                } else {
                    element.objectValue = parseInt(stringValue);
                }
            } else {
                element.objectValue = stringValue;
            }
        }

        return element.objectValue;

    }

    function isNumber() {
        return this.dataType == DataTypes.INTEGER
               || this.dataType == DataTypes.FLOAT
               || this.dataType == DataTypes.LONG
               || this.dataType == DataTypes.DOUBLE
               || this.dataType == DataTypes.BIG_DECIMAL
               || this.dataType == DataTypes.BIG_INTEGER;
    }
}