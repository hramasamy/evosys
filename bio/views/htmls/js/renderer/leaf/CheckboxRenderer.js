function CheckboxRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.setValue = setValue;
    this.valueChanged = valueChanged;

    function initialize() {
        this.widget.onclick = new Function("this.renderer.valueChanged()");
    }

    function setValue(aValue) {
        this.widget.checked = eval(aValue);
    }

    function valueChanged() {
        var widgetEvent = new WidgetEvent(this.widget, "valueChanged");
        widgetEvent.set("value", this.widget.checked, "java.lang.Boolean");
        widgetEvent.post();
    }
}