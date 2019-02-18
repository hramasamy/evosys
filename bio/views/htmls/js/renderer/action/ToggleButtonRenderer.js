function ToggleButtonRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.setSelected = setSelected;
    this.buttonPushed = buttonPushed;

    function initialize() {
        this.widget.renderer = this;
        this.widget.onclick = new Function("this.renderer.buttonPushed()");

        this.setSelected(this.widget.selected);
    }

    function setSelected(aFlag) {
        var selected = eval(aFlag);
        this.widget.selected = selected;

        this.widget.style.backgroundColor = (selected ? "#b7b7b7" : "#ffffea");
        this.widget.style.borderStyle = (selected ? "inset" : "outset");
        this.widget.style.backgroundRepeat = "no-repeat";
        this.widget.style.backgroundImage = "url(" + (selected ? this.widget.redImage : this.widget.greenImage) + ")";
    }

    function buttonPushed() {
        var widgetEvent = new WidgetEvent(this.widget, "buttonPushed");
        widgetEvent.set("selected", !this.widget.selected);
        widgetEvent.post();
    }
}