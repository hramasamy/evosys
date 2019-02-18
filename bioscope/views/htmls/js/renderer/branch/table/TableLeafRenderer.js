TableLeafRenderer.prototype = new LeafRenderer();

function TableLeafRenderer() {
    this.base = LeafRenderer;
    this.base();

    this.initialize = initialize;

    this.editCell = editCell;
    this.stopEditingCell = stopEditingCell;

    this.initializeDataCell = initializeDataCell;
    this.positionLinkHolder = positionLinkHolder;
    this.updateDataCellStyle = updateDataCellStyle;
    this.getSelectedCodeIndex = getSelectedCodeIndex;
    this.checkCellTypeAndDisplay = checkCellTypeAndDisplay;
    this.getCellValue = getCellValue;
    this.getCellInputNode = getCellInputNode;
    this.validateLinkHTML = validateLinkHTML;

    this.cellResized = cellResized;

    function initialize() {
        this.inputHelper = new CellInputHelper(this);
    }

    function initializeDataCell(cell) {
        // If this is link, set the shape of the cursor
        if (!cell.parentNode.isTotal) {
            // For codes
            this.checkCellTypeAndDisplay(cell);

            if (this.isLink) {
                var linkHTML = "<img src='" + window.contextPath + "/pages/images/link.gif'"
                             + " style='position:absolute;cursor:hand'"
                             + " onclick='this.cell.leafRenderer.tableRenderer.linkClicked(this.cell)'>";
                cell.innerHTML += linkHTML;

                this.positionLinkHolder(cell);
            }
        }

        // Update cell styles
        this.updateDataCellStyle(cell);
    }

    function positionLinkHolder(cell) {
        var linkHolder = RendererUtils.getChildNodeByTagName(cell, "IMG");
        if (!this.isLink || !linkHolder) {
            return;
        }

        linkHolder.cell = cell;
        cell.linkHolder = linkHolder;

        var parentNode = this.tableRenderer.widget.parentNode;
        linkHolder.style.posLeft = cell.offsetLeft + cell.offsetWidth - 7 - parentNode.scrollLeft;
        linkHolder.style.posTop = cell.offsetTop - parentNode.scrollTop;
    }

    function checkCellTypeAndDisplay(cell) {
        if (this.dataType == DataTypes.BOOLEAN) {
            cell.objectValue = ("true" == cell.innerText);
            cell.innerHTML = "<input type='checkbox' " + (cell.objectValue ? "checked" : "") + " " + (this.editable ? "" : "disabled") + " onclick='this.parentNode.objectValue=this.checked'>";
            return;
        }

        if (this.isFixed) {
            return;
        }

        if (this.editorStyles.combobox) {
            var selectedCodeIndex = this.getSelectedCodeIndex(cell);
            cell.innerText = this.columnCodes.values[selectedCodeIndex] || "";
        }
    }

    function updateDataCellStyle(cell) {
        // Left/Right fixed
        if (this.isFixed && !cell.parentNode.isTotal) {
            cell.style.backgroundColor = ROW_COLOR1;
            cell.style.border = "1 outset";
            cell.isFixed = true;

            // Kaleidoscope
            if (this.columnIndex < this.tableRenderer.widget.leftFixed) {
                cell.noWrap = true;
            }
        }

        // Number format
        var numberFormat = this.getNumberFormat();
        if (numberFormat == null)  {
            return;
        }

        // Special handling for status
        var cellValue = this.getCellValue(cell);

        if (numberFormat.style == STATUS_STYLE) {
            cell.style.textAlign = "center";

            if (!isNaN(cellValue)) {
                cell.innerHTML = numberFormat.format(cellValue);
            }
        } else {
            cell.style.textAlign = "right";

            if (cellValue < 0) {
                HtmlUtils.setOriginalProperty(cell.style, "color");
                cell.style.color = "red";
            } else {
                cell.style.color = HtmlUtils.getOriginalProperty(cell.style, "color");
            }
        }
    }

    function getSelectedCodeIndex(cell) {
        var cellValue = this.getCellValue(cell);
        var columnCodeCount = this.columnCodes.size();
        var keys = this.columnCodes.keys;
        var values = this.columnCodes.values;

        for (var i = 0; i < columnCodeCount; i++) {
            var codeName = keys[i];
            var codeValue = values[i];

            if (cellValue == codeName) {
                return i;
            }
        }

        return -1;
    }

    function editCell(cell) {
        if (this.getCellInputNode(cell) != null) {
            return false;
        }

        // Create the cellInputNode;
        var cellInputNode = this.inputHelper.createInput(cell);

        // Insert the input node
        cell.innerText = "";

        // Not sure why insertBefore(cellInputNode, cell.linkHolder) does not work
        cell.insertBefore(cellInputNode);
        this.validateLinkHTML(cell);

        // Fix resize problem
        this.tableRenderer.resized();

        return true;
    }

    function stopEditingCell(cell) {
        var cellInputNode = this.getCellInputNode(cell);
        if (cellInputNode == null) {
            return false;
        }

        var originalCellValue = cell.objectValue;
        cell.objectValue = HtmlUtils.getValue(cellInputNode);

        // Remove the inputNode
        cell.removeChild(cellInputNode.inputHolder || cellInputNode);

        var numberFormat = this.getNumberFormat();
        cell.innerText = (numberFormat != null ? numberFormat.format(cell.objectValue) : cell.objectValue);
        this.validateLinkHTML(cell);

        // Check for codes
        this.checkCellTypeAndDisplay(cell);

        // update the cell style
        this.updateDataCellStyle(cell);

        // Fix resize problem
        this.tableRenderer.resized();

        // Send event if value changed
        return (originalCellValue != cell.objectValue);
    }

    function getCellInputNode(cell) {
        var inputNode = RendererUtils.getChildNodeByTagName(cell, "INPUT")
              || RendererUtils.getChildNodeByTagName(cell, "SELECT");

        return (inputNode && inputNode.type != "hidden" ? inputNode : null);
    }

    function getCellValue(cell) {
        return this.getObjectValue(cell, cell.innerText);
    }

    function validateLinkHTML(cell) {
        if (cell.linkHolder && cell.innerHTML.indexOf(cell.linkHolder.outerHTML) == -1) {
            cell.innerHTML += cell.linkHolder.outerHTML;
        }
    }

    function cellResized(cell) {
        this.positionLinkHolder(cell);
    }
}

TableLeafRenderer.singleton = new TableLeafRenderer();