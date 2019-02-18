TextBoxRenderer.prototype = new SimpleInputLeafWidgetRenderer();

function TextBoxRenderer(widget) {
    this.base = SimpleInputLeafWidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.onFocus = onFocus;
    this.onBlur = onBlur;
    this.getObjectValue = getObjectValue;

    function initialize() {
        TextBoxRenderer.prototype.initialize(this);

        if ((typeof this.widget.dataType) != "undefined") {
            this.leafRenderer = new LeafRenderer();
            this.leafRenderer.dataType = window.parseInt(this.widget.dataType);
            this.leafRenderer.numberStyle = this.widget.numberStyle;
        }

        this.widget.encrypt = eval(this.widget.encrypt);
        this.widget.onfocus = new Function("this.renderer.onFocus()");
        this.widget.onblur = new Function("this.renderer.onBlur()");
    }

    function onFocus() {
        this.widget.value = this.getObjectValue();
    }

    function onBlur() {
        var objectValue = this.widget.value;
        var stringValue = objectValue;

        this.widget.value = (this.leafRenderer && this.leafRenderer.isNumber()? this.leafRenderer.getNumberFormat().format(objectValue) : stringValue);

        if (this.widget.objectValue != objectValue) {
            this.widget.objectValue = objectValue;

            // Inform other listeners
            this.widget.fireEvent("onchange");

            var widgetEvent = new WidgetEvent(this.widget, "valueChanged");
            if (this.widget.encrypt) {
                widgetEvent.set("value", objectValue, "_WebDeliverServices.encrypt(" + objectValue + ")");
            } else {
                widgetEvent.set("_value", objectValue);
            }
            widgetEvent.post();
        }
    }

    function getObjectValue() {
        if ((typeof this.widget.objectValue) == "undefined") {
            this.widget.objectValue = this.leafRenderer ? this.leafRenderer.getObjectValue(this.widget, this.widget.value) : this.widget.value;
        }

        return this.widget.objectValue;
    }
}