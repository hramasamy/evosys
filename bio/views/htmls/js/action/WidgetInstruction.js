function WidgetInstruction(widgetId, parameters) {
    this.widgetId = widgetId;
    this.parameters = parameters;

    this.toString = toString;

    function toString() {
        var result = "";
        result += "widgetId=" + this.widgetId;
        result += "\nparameters={" + this.parameters + "}";

        return result;
    }
}
