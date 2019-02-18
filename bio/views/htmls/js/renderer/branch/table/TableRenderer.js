var SELECTED_COLOR = "#ffff00";
var ROW_COLOR0 = "white";
var ROW_COLOR1 = "#eeeeee";

function TableRenderer(widget) {
    this.base = NavigatableRenderer;
    this.base(widget);

    this.lastSelectedCell = null;
    this.lastEditingCell = null;

    this.initialize = initialize;
    this.hasPrevious = hasPrevious;
    this.hasNext = hasNext;
    this.getPositionInfo = getPositionInfo;

    this.first = first;
    this.previous = previous;
    this.next = next;
    this.last = last;

    this.add = add;
    this.remove = remove;

    this.cellClicked = cellClicked;
    this.cellDblClicked = cellDblClicked;
    this.linkClicked = linkClicked;
    this.selectCell = selectCell;
    this.editCell = editCell;
    this.stopEditingCell = stopEditingCell;

    this.setValue = setValue;
    this.setBulkValue = setBulkValue;
    this.setSelectedRow = setSelectedRow;
    this.setTotals = setTotals;
    this.processGetList = processGetList;

    this.updateTable = updateTable;
    this.updateRow = updateRow;

    this.sortTable = sortTable;

    this.calculatorClicked = calculatorClicked;
    this.excelExportClicked = excelExportClicked;
    this.formatterClicked = formatterClicked;
    this.personalQubeClicked = personalQubeClicked;

    this.tableSelected = tableSelected;
    this.resized = resized;

    function initialize(anInstance) {
        var theInstance = anInstance || this;

        var tableHelperClass = (theInstance.tableHelperClass || TableRendererHelper);
        theInstance.tableHelper = new tableHelperClass(theInstance);
        theInstance.pivotHelper = new PivotHelper(theInstance);
        theInstance.menuHelper = new FloatMenuHelper(theInstance);
        theInstance.sumHelper = new SumHelper(theInstance);
        theInstance.selectHelper = new SelectHelper(theInstance);
        theInstance.resizeHelper = new ColumnResizeHelper(theInstance);

        var widget = theInstance.widget;
        widget.favorites = BeanUtils.parseObject(widget.favorites);
        widget.hasTotals = eval(widget.hasTotals || false);
        widget.leftFixedFormat = parseInt(widget.leftFixedFormat || 0);
        widget.leftFixed = parseInt(widget.leftFixed);
        widget.rightFixed = parseInt(widget.rightFixed);
        widget.emptyTableMsg = widget.emptyTableMsg || "Empty";
        widget.startRow = parseInt(widget.startRow || "0");
        widget.rowsInPage = parseInt(widget.rowsInPage || "0");
        widget.numberColumnWidth = parseInt(widget.numberColumnWidth);
        widget.dataColumnCount = parseInt(widget.dataColumnCount);
        widget.allColumnCount = parseInt(widget.allColumnCount);

        // Initialize leaf renderers
        widget.leafRenderers = theInstance.tableHelper.parseLeafRenderers(widget.leafRenderers);

        // Initialize the navigation bar
        theInstance.initializeNavigationBar();

        // Initialize the table body cells
        theInstance.tableHelper.initializeHeadCells();

        // Initialize the data table cells
        theInstance.tableHelper.initializeDataCells();

        // Initialize the utilities
        theInstance.tableHelper.initializeUtilities();

        // Render the pivot table
        theInstance.pivotHelper.mergeRows();

        // Initialize column resizer
        theInstance.resizeHelper.initialize();
    }

    function hasPrevious() {
        return this.widget.startRow > 0;
    }

    function hasNext() {
        return this.tableHelper.getEndRowPosition() < this.widget.totalRows - 1;
    }

    function getPositionInfo() {
        var position = this.widget.position;

        if ((typeof position) == "number"
            && position >= this.widget.startRow
            && position <= this.tableHelper.getEndRowPosition()) {
            return position + 1;
        } else {
            var startPosition = this.widget.startRow + 1;
            var endPosition = this.tableHelper.getEndRowPosition() + 1;

            if (endPosition < startPosition) {
                endPosition = startPosition;
            }

            return startPosition + "-" + endPosition;
        }
    }

    function previous() {
        this.tableHelper.postNavigationEvent(this.widget.startRow - this.widget.rowsInPage);
    }

    function first() {
        this.tableHelper.postNavigationEvent(0);
    }

    function next() {
        this.tableHelper.postNavigationEvent(this.widget.startRow + this.widget.rowsInPage);
    }

    function last() {
        this.tableHelper.postNavigationEvent(this.widget.rowsInPage * parseInt((this.widget.totalRows - 1)/this.widget.rowsInPage));
    }

    function add() {
        var widgetEvent = new WidgetEvent(this.widget, "addRow");
        widgetEvent.set("row", this.tableHelper.getRowPosition(this.tableHelper.getSelectedRow()), "java.lang.Integer");
        widgetEvent.post();
    }

    function remove() {
        if (this.tableHelper.getSelectedRow() == null) {
            alert("No row selected in the current page");
            return;
        }

        var widgetEvent = new WidgetEvent(this.widget, "removeRow");
        widgetEvent.set("row", this.tableHelper.getRowPosition(this.tableHelper.getSelectedRow()), "java.lang.Integer");
        widgetEvent.post();
    }

    function cellClicked(selectedCell, anInstance) {
        theInstance = anInstance || this;

        // Do not proceed if already selected
        if (theInstance.tableHelper.isCellSelected(selectedCell)) {
            return;
        }

        // Select cell
        theInstance.selectCell(selectedCell);

        // Send event
        var widgetEvent = new WidgetEvent(theInstance.widget, "tableClicked");
        widgetEvent.set("row", theInstance.tableHelper.getRowPosition(selectedCell.parentNode), "java.lang.Integer");
        widgetEvent.set("column", selectedCell.leafRenderer.columnIndex, "java.lang.Integer");
        widgetEvent.set("fireDynamic", "true", "java.lang.Boolean");
        widgetEvent.post();
    }

    function cellDblClicked(selectedCell, anInstance) {
        theInstance = anInstance || this;

        // Do not proceed if already selected
        if (theInstance.tableHelper.isCellEditable(selectedCell) && TableLeafRenderer.singleton.getCellInputNode(selectedCell) == null) {
            theInstance.editCell(selectedCell);
        }
    }

    function linkClicked(cell) {
        window.event.returnValue = false;

        var widgetEvent = new WidgetEvent(this.widget, "setLinkClicked");
        widgetEvent.set("row", this.tableHelper.getRowPosition(cell.parentNode), "java.lang.Integer");
        widgetEvent.set("column", cell.leafRenderer.columnIndex, "java.lang.Integer");
        widgetEvent.post();
    }

    function selectCell(selectedCell) {
        this.tableHelper.setCellBackgroundColor(selectedCell, SELECTED_COLOR);
        if (selectedCell == this.lastSelectedCell) {
            return;
        }

        if (this.lastSelectedCell) {
            this.tableHelper.setCellBackgroundColor(this.lastSelectedCell, this.lastSelectedCell.style.originalBackgroundColor);
        }

        this.lastSelectedCell = selectedCell;

        if (this.lastEditingCell) {
            this.stopEditingCell(this.lastEditingCell);
        }
    }

    function editCell(cell) {
        var leafRenderer = cell.leafRenderer;

        if (leafRenderer && leafRenderer.editCell(cell)) {
            var cellInputNode = leafRenderer.getCellInputNode(cell);
            cellInputNode.tableRenderer = this;
            cellInputNode.cell = cell;
            cellInputNode.onblur = new Function("this.tableRenderer.stopEditingCell(this.cell)");
            this.lastEditingCell = cell;
        }
    }

    function stopEditingCell(cell) {
        var leafRenderer = cell.leafRenderer;

        if (leafRenderer && leafRenderer.stopEditingCell(cell)) {
            var cellValue = leafRenderer.getCellValue(cell);
            this.lastEditingCell = null;

            var widgetEvent = new WidgetEvent(this.widget, "setValueAt");
            widgetEvent.set("row", this.tableHelper.getRowPosition(cell.parentNode), "java.lang.Integer");
            widgetEvent.set("column", leafRenderer.columnIndex, "java.lang.Integer");
            widgetEvent.set("value", cellValue, "_getAsLeaf(" + leafRenderer.columnIndex + ":java.lang.Integer;" + cellValue + ")");
            widgetEvent.post();
        }
    }

    function setValue(aValue) {
        this.setTotalRows(StringUtils.getParameter(aValue, "totalRows"));

        if (this.widget.totalRows == 0) {
            this.tableHelper.setEmptyRow();
        }
    }

    function setBulkValue(aValue) {
        var index = aValue.indexOf("=");
        if (index == -1) {
            return;
        }

        var valueStr = aValue.substring(index + 1, aValue.length - 1);

        // Parse the rows data
        var branchData = this.tableHelper.parseBranchData(valueStr);

        // Parse the start row
        index = valueStr.lastIndexOf("=");
        this.widget.startRow = parseInt(valueStr.substring(index + 1));

        // Update the table UI
        this.updateTable(branchData);

        // Update rowCountStatus
        this.updateRowCountStatus();
    }

    function setTotals(aTotalsStr) {
        var totalsData = this.tableHelper.parseBranchData(aTotalsStr);

        if (this.widget.hasTotals && totalsData[0]) {
            if (this.tableHelper.isTableDataEmpty()) {
                this.tableHelper.removeAllRows();
            }

            // Insert totals row when necessary
            var totalsRow = this.tableHelper.getLastRow();
            if (!totalsRow || !totalsRow.isTotal) {
                totalsRow = this.tableHelper.insertDataRow();
                totalsRow.isTotal = true;
            }

            this.updateRow(totalsRow, totalsData[0]);
        }
    }

    function processGetList(aValueString) {
        if (this.currentWisePickRenderer) {
            this.currentWisePickRenderer.processGetList(aValueString);
        }
    }

    function setSelectedRow(aValue) {
        var position = parseInt(StringUtils.getParameter(aValue, "row"));
        var rowIndex = position - this.widget.startRow;

        // Handle add/removeRow
        if (this.actionId == "addRow" || this.actionId == "removeRow" || rowIndex >= this.widget.rowsInPage) {
            this.setPosition(position);
            this.tableHelper.postNavigationEvent(position - this.widget.rowsInPage + 1);
            return;
        }

        // Normal selection process
        var row = this.widget.tBodies[0].rows[rowIndex];
        var startCol = this.tableHelper.getStartDataCol();
        if (!this.tableHelper.isRowSelected(row)) {
            this.selectCell(row.childNodes[startCol]);
        }

        this.setPosition(position);
    }

    function updateTable(branchData, anInstance) {
        var theInstance = anInstance || this;

        // Insert an empty
        if (branchData.length == 0) {
            theInstance.tableHelper.setEmptyRow();
            return;
        }

        // Delete the empty row
        theInstance.tableHelper.removeAllRows(true);

        var dataRows = theInstance.widget.tBodies[0].rows;
        var branchDataCount = branchData.length;
        var rowsInPage = theInstance.widget.rowsInPage;

        for (var i = 0; i < branchDataCount && i < rowsInPage; i++) {
            var row = theInstance.tableHelper.insertDataRow(i);
            theInstance.updateRow(row, branchData[i]);
        }

        // Delete those unused rows
        var tBody = theInstance.widget.tBodies[0];
        for (var i = dataRows.length -1; i >= branchDataCount; i--) {
            if (theInstance.tableHelper.isDataRow(dataRows[i])) {
                tBody.deleteRow(i);
            }
        }

        // Update the navigation
        theInstance.updateNavigation();

        // Merge rows if necessary
        theInstance.pivotHelper.mergeRows();
    }

    function updateRow(row, rowData) {
        var startDataCol = this.tableHelper.getStartDataCol();
        var isTotalRow = !this.tableHelper.isDataRow(row);

        if (startDataCol > 0 && !isTotalRow) {
            var cell = row.childNodes[0];
            cell.className = "numberrow";
            cell.innerHTML = this.leftFixedFormat == 2 ? this.tableHelper.getRowPosition(row) + 1 : "&nbsp;";
        }

	// Temporary, needs to have a better solution. E.g. take care of setTableColumn() method
        if (rowData.length > this.widget.dataColumnCount) {
            this.getCanvasRenderer().processReload(true);
            return;
        }

        var allColumnCount = this.widget.allColumnCount;
        var leafRenderers = this.widget.leafRenderers;
        var cells = row.childNodes;
        for (var i = 0, k = startDataCol; i < allColumnCount; i++) {
            var leafRenderer = leafRenderers[i];
            if (!leafRenderer || !leafRenderer.visible) {
                continue;
            }

            var cell = cells[k];
            if (!cell) {
                cell = this.tableHelper.insertDataCell(row, leafRenderer);
            }

            cell.innerHTML = (rowData[i] || "&nbsp;");

            if (!isTotalRow) {
                if (!leafRenderer.isFixed) {
                    cell.style.backgroundColor = (row.rowIndex % 2 == 0 ? ROW_COLOR1 : ROW_COLOR0);
                    cell.style.originalBackgroundColor = cell.style.backgroundColor;

                    if (k == startDataCol && this.tableHelper.getRowPosition(row) == this.widget.position) {
                        if (!this.tableHelper.isRowSelected(row)) {
                            cell.style.backgroundColor = SELECTED_COLOR;
                        }
                    }
                }
            } else {
                cell.className = "total";
            }

            this.tableHelper.initializeDataCell(cell, i);

            k++;
        }
    }

    function sortTable(headCell) {
        // Check if the event dispatching is enabled or not
        if (this.eventDisabledUntil && new Date().getTime() < this.eventDisabledUntil) {
            return;
        }

        this.tableHelper.toggleSortOrder(headCell);

        // Send sort table event
        var leafRenderer = headCell.leafRenderer;
        var widgetEvent = new WidgetEvent(this.widget, "sortTable");
        widgetEvent.set("column", leafRenderer.columnIndex, "java.lang.Integer");
        widgetEvent.set("ascending", leafRenderer.ascending, "java.lang.Boolean");
        widgetEvent.post();
    }

    function calculatorClicked() {
        alert("Feature not available yet");
    }

    function excelExportClicked() {new WidgetEvent(this.widget, "excelExport").post();}
    function formatterClicked() {new WidgetEvent(this.widget, "conditionalFormatterRequested").post();}

    function personalQubeClicked() {
        var widgetEvent = new WidgetEvent(this.widget, "savePersonalQube");
        widgetEvent.targetWindowName = "TablePersonalQube";
        widgetEvent.post();
    }

    function tableSelected() {
        var selectedRows = this.selectHelper.processTableSelected();
        if (selectedRows.length == 0) {
            return;
        }

        // Auto sum
        this.sumHelper.processTableSelected();

        // Post selection event
        var widgetEvent = new WidgetEvent(this.widget, "tableMultiSelect");
        widgetEvent.set("row", selectedRows, "java.lang.reflect.Array[int]");
        widgetEvent.set("fireDynamic", "true", "java.lang.Boolean");
        widgetEvent.post();
    }

    function resized(cell) {
        this.tableHelper.tableResized(cell);
    }
}