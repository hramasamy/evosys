ComboboxRenderer.prototype = new SimpleInputLeafWidgetRenderer();

function ComboboxRenderer(widget) {
    this.base = SimpleInputLeafWidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.clearOptions = clearOptions;
    this.setValue = setValue;
    this.setSelectedIndex = setSelectedIndex;
    this.setSelectedValue = setSelectedValue;
    this.valueChanged = valueChanged;

    function initialize() {
        ComboboxRenderer.prototype.initialize(this);

        // Add an empty option
        var oldSelectedIndex = this.widget.selectedIndex;
        this.widget.options.add(new Option("", ""), 0);
        this.widget.selectedIndex = oldSelectedIndex + 1;

        // Event binding
        this.widget.onchange = new Function("this.renderer.valueChanged()");
    }

    function clearOptions(selectObj) {
        var optionsLen = selectObj.options.length;
        for (var i = 0; i < optionsLen; i++) {
            selectObj.options.remove(0);
        }
    }

    function setValue(aValueString) {
        // Remove all
        this.clearOptions(this.widget);

        // Add options
        aValueString = StringUtils.trim(aValueString, "[");
        aValueString = StringUtils.trim(aValueString, "]");
        var values = ArrayUtils.getTokens(aValueString);
        for (var i = 0; i < values.length; i++) {
            var value = StringUtils.trim(values[i]);
            if (value == "null") {
                value = "";
            }

            var option = new Option(value, value);
            this.widget.options[this.widget.options.length] = option;
        }
    }

    function setSelectedIndex(anIndex) {
        this.widget.selectedIndex = window.parseInt(anIndex) + 1;
    }

    function setSelectedValue(aValue) {
        this.widget.value = aValue;
    }

    function valueChanged() {
        var widgetEvent = new WidgetEvent(this.widget, "itemSelected");
        widgetEvent.set("_selectedIndex", this.widget.selectedIndex - 1, "java.lang.Integer");
        widgetEvent.post();
    }
}

ComboboxRenderer.singleton = new ComboboxRenderer();
