function RadioRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.getChoiceObjs = getChoiceObjs;
    this.getChoiceObjHTML = getChoiceObjHTML;
    this.setValue = setValue;
    this.setSelectedValue = setSelectedValue;
    this.valueChanged = valueChanged;

    function initialize() {
        var choiceObjs = this.getChoiceObjs();

        for (var i = 0; i < choiceObjs.size(); i++) {
            var choiceObj = choiceObjs.get(i);
            choiceObj.renderer = this;
            choiceObj.onclick = new Function("this.renderer.valueChanged(this)");
        }
    }

    function getChoiceObjs() {
        if (!this.choiceObjs || this.choiceObjs.isEmpty()) {
            this.choiceObjs = new ArrayList();

            for (var i = 0; i < this.widget.rows[0].childNodes.length; i++) {
                var choiceObj = this.widget.rows[0].childNodes[i].childNodes[0];
                if (choiceObj.name == this.widget.choiceName) {
                    this.choiceObjs.add(choiceObj);
                }
            }
        }

        return this.choiceObjs;
    }

    function getChoiceObjHTML(index, value) {
        value = StringUtils.trim(value, " ");

        if (value == "null") {
            value = "";
        }

        return "<input name='" + this.widget.choiceName + "'"
               + "type='radio'"
               + "index='" + index + "'"
               + "value='" + value + "'"
               + ">&nbsp;"
               + value;
    }

    function setValue(aValue) {
        var values = ArrayUtils.getTokens(aValue.substring(1, aValue.length - 1));
        var choiceObjs = this.getChoiceObjs();
        if (choiceObjs.isEmpty() && values.length > 0) {
            // Create the choice objs on fly
            this.widget.deleteRow(0);
            var row = this.widget.insertRow();

            for (var i = 0; i < values.length; i++) {
                var cell = row.insertCell();
                cell.innerHTML = this.getChoiceObjHTML(i, values[i]);
            }

            // Bind events
            this.initialize();
        }
    }

    function setSelectedValue(aValue) {
        HtmlUtils.setValue(this.widget.choiceName, aValue);
    }

    function valueChanged(choiceObj) {
        var widgetEvent = new WidgetEvent(this.widget, this.widget.actionName);
        widgetEvent.set("_selectedIndex", choiceObj.index, "java.lang.Integer");
        widgetEvent.set("selectedValue", choiceObj.value);

        widgetEvent.post();
    }
}