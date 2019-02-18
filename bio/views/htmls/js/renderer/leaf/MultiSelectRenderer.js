function MultiSelectRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.toggleChoicesDisplay = toggleChoicesDisplay;
    this.showChoices = showChoices;
    this.hideChoices = hideChoices;
    this.isChoicesHidden = isChoicesHidden;
    this.updateValue = updateValue;

    this.getSelectedIndexes = getSelectedIndexes;
    this.getSelectedValues = getSelectedValues;
    this.getSelectedIndexesOrValues = getSelectedIndexesOrValues;

    function initialize() {
        // Set the onclick to have the equivalent behavior as onmouseenter
        this.widget.onclick = new Function("this.renderer.toggleChoicesDisplay()");
        //this.widget.onmouseenter = new Function("this.renderer.showChoices()");

        // Initialize the choices holder
        this.choicesHolder = this.getComponent("choices");

        // Initialize the choices
        var selectedIndexes = new HashSet(window.eval(this.widget.selectedIndexes));
        this.choices = new ArrayList();
        for (var i = 0; i < this.widget.choiceCount; i++) {
            var choice = this.getComponent("choice" + i);

            choice.checked = selectedIndexes.contains(i);
            choice.onclick = new Function("this.renderer.updateValue()");

            this.choices.add(choice);
        }

        // Update the value
        this.updateValue();

        // Change the z-index of the parent nodes
        this.propagateZIndex(this.choicesHolder);
    }

    function showChoices() {
        this.choicesHolder.style.display = "inline";
    }

    function hideChoices() {
        this.choicesHolder.style.display = "none";

        // Send event
        var multiSelectEvent = new WidgetEvent(this.widget, "itemSelected");
        multiSelectEvent.set("selectedIndexes", this.getSelectedIndexes(), "java.util.Vector[java.lang.Integer]");
        multiSelectEvent.set("selectedvalues", this.getSelectedValues(), "java.util.Vector");
        multiSelectEvent.post();
    }

    function isChoicesHidden() {
        return this.choicesHolder.style.display == "none";
    }

    function toggleChoicesDisplay() {
        if (this.isChoicesHidden()) {
            this.showChoices();
        } else {
            this.hideChoices();
        }
    }

    function updateValue() {
        var selectedValue = this.getSelectedValues();

        var originalValue = HtmlUtils.getValue(this.widget);
        var preservedValue = originalValue.substring(0, originalValue.indexOf(">") + 1);

        HtmlUtils.setValue(this.widget, preservedValue + selectedValue);
    }

    function getSelectedIndexes() {
        return this.getSelectedIndexesOrValues(false);
    }

    function getSelectedValues() {
        return this.getSelectedIndexesOrValues(true);
    }

    function getSelectedIndexesOrValues(isValue) {
        var result = "";

        for (var i = 0; i < this.choices.elementData.length; i++) {
            var choice = this.choices.elementData[i];

            if (choice.checked) {
                result += (result.length > 0 ? "," : "") + (isValue ? choice.value : i);
            }
        }

        return result;
    }
}
