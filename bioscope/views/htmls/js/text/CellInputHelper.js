function CellInputHelper(leafRenderer) {
    this.leafRenderer = leafRenderer;

    this.createInput = createInput;
    this.createSelectInput = createSelectInput;
    this.createWisePickInput = createWisePickInput;
    this.createSimpleInput = createSimpleInput;
    this.setSimpleInputStyles = setSimpleInputStyles;

    function createInput(cell) {
        if (this.leafRenderer.editorStyles.combobox) {
            return this.createSelectInput(cell);
        } else if (this.leafRenderer.editorStyles.wisepick) {
            return this.createWisePickInput(cell);
        } else {
            return this.createSimpleInput(cell);
        }
    }

    function createSelectInput(cell) {
        var selectedIndex = this.leafRenderer.getSelectedCodeIndex(cell);

        var inputNode = window.document.createElement("SELECT");

        for (var i = 0; i < this.leafRenderer.columnCodes.size(); i++) {
            var codeName = this.leafRenderer.columnCodes.keys[i];
            var codeValue = this.leafRenderer.columnCodes.values[i];

            var option = new Option(codeValue, codeName);
            if (selectedIndex == i) {
                option.selected = true;
            }
            inputNode.options.add(option);
        }

        this.setSimpleInputStyles(cell, inputNode);

        return inputNode;
    }

    function createWisePickInput(cell) {
        var spanNode = window.document.createElement("SPAN");

        var inputbox = window.document.createElement("INPUT");
        inputbox.style.position = "absolute";
        inputbox.value = this.leafRenderer.getCellValue(cell);
        var selectbox = window.document.createElement("SELECT");
        selectbox.style.position = "absolute";
        spanNode.appendChild(inputbox);
        spanNode.appendChild(selectbox);

        this.setSimpleInputStyles(cell, selectbox);

        selectbox.id = this.leafRenderer.tableRenderer.widget.id + "_" + cell.parentNode.rowIndex + "_" + cell.cellIndex + "_wisepick";
        inputbox.id = selectbox.id + "_input";

        var inputRenderer = new WisePickInputRenderer(selectbox, inputbox, cell);
        inputRenderer.initialize();

        inputbox.inputHolder = spanNode;

        return spanNode;
    }

    function createSimpleInput(cell) {
        var inputNode = window.document.createElement("INPUT");
        inputNode.type = "text";
        inputNode.value = this.leafRenderer.getCellValue(cell);

        this.setSimpleInputStyles(cell, inputNode);

        return inputNode;
    }

    function setSimpleInputStyles(cell, inputNode) {
        inputNode.className = "bodyText";
        inputNode.style.width = (cell.offsetWidth - 2);
    }
}
