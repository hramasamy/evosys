function ColorRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.clicked = clicked;
    this.colorChosen = colorChosen;

    function initialize() {
        this.widget.onclick = new Function("this.renderer.clicked()");
    }

    function clicked() {
        if (this.colorChooserWnd && !this.colorChooserWnd.closed) {
            return;
        }

        var colorChooserURL = HtmlUtils.encodeWithTimestamp(this.widget.colorChooserURL);

        var windowName = "";
        var features = "width=600,height=350";

        this.colorChooserWnd = window.open(colorChooserURL, windowName, features);
        this.colorChooserWnd.listener = this;
    }

    function colorChosen(red, green, blue) {
        var widgetEvent = new WidgetEvent(this.widget, "dataChanged");
        widgetEvent.set("value", red + "," + green + "," + blue, "java.awt.Color[int;int;int]");
        widgetEvent.post();
    }
}
