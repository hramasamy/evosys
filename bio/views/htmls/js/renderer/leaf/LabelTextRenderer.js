function LabelTextRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.setValue = setValue;

    function setValue(aValue) {
        HtmlUtils.setValue(this.widget, aValue);
    }
}