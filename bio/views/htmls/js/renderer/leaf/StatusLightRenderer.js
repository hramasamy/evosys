function StatusLightRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.setValue = setValue;

    function setValue(value) {
        value = parseInt(value);
        if (value <= 0 || value > 6) {
            value = 0;
        }

        this.widget.src = this.widget.imgPath + "/status" + value + ".gif";
    }
}