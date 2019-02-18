function Report(actionId) {
    this.actionId = actionId;
    this.instructions = new ArrayList();

    this.addInstruction = addInstruction;
    this.toString = toString;

    function addInstruction(widgetId, parameters) {
        var instruction = new WidgetInstruction(widgetId, parameters);
        this.instructions.add(instruction);
    }

    function toString() {
        var result = "";
        result += "actionId=" + this.actionId;

        for (var i = 0; i < this.instructions.size(); i++) {
            var instruction = this.instructions.get(i);
            result += "\n\n" + instruction;
        }

        return result;
    }
}
