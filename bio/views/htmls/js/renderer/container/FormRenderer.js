function FormRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.processPopup = processPopup;

    function processPopup(valueString) {
        var confirmed = window.confirm(valueString);
        var widgetEvent = new WidgetEvent(this.widget, "answerPopup");
        widgetEvent.set("value", confirmed ? 0 : 1, "java.lang.Integer");
        widgetEvent.post();
    }
}