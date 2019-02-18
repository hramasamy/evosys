PullDownTextBoxRenderer.prototype = new SimpleInputLeafWidgetRenderer();

function PullDownTextBoxRenderer(widget) {
    this.base = SimpleInputLeafWidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.toggleTextAreaDisplay = toggleTextAreaDisplay;
    this.clicked = clicked;
    this.showTextArea = showTextArea;
    this.hideTextArea = hideTextArea;
    this.isTextAreaHidden = isTextAreaHidden;
    this.keyPressedInTextArea = keyPressedInTextArea;
    this.valueChanged = valueChanged;

    function initialize() {
        PullDownTextBoxRenderer.prototype.initialize(this);

        // Set the onclick
        if (!this.widget.onclick) {
            this.widget.onclick = new Function("this.renderer.clicked()");
        }
        this.widget.onchange = new Function("this.renderer.valueChanged()");

        // Initialize the pen
        this.pen = this.getComponent("pen");
        this.pen.onclick = new Function("this.renderer.toggleTextAreaDisplay()");

        // Initialize the textArea
        this.textArea = this.getComponent("textArea");
        this.textArea.style.width = this.widget.style.width;
        this.textArea.onkeyup = new Function("this.renderer.keyPressedInTextArea()");

        // Change the z-index of the parent nodes
        this.propagateZIndex(this.textArea);
    }

    function clicked() {
        this.hideTextArea();
    }

    function showTextArea() {
        this.textArea.value = this.widget.value;
        this.textArea.style.display = "inline";
    }

    function hideTextArea() {
        this.textArea.style.display = "none";
        this.valueChanged();
    }

    function isTextAreaHidden() {
        return this.textArea.style.display == "none";
    }

    function toggleTextAreaDisplay() {
        if (this.isTextAreaHidden()) {
            this.showTextArea();
        } else {
            this.hideTextArea();
        }
    }

    function keyPressedInTextArea() {
        this.widget.value = this.textArea.value.replace(/\r\n/g, " ");
    }

    function valueChanged() {
        if (this.isTextAreaHidden()) {
            var widgetEvent = new WidgetEvent(this.widget, "valueChanged");
            widgetEvent.set("value", this.widget.value);
            widgetEvent.post();
        }
    }
}
