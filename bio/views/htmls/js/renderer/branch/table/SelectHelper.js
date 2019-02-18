function SelectHelper(tableRenderer) {
    this.tableRenderer = tableRenderer;

    this.initializeDataCell = initializeDataCell;
    this.selectionStarted = selectionStarted;
    this.onMouseOver = onMouseOver;
    this.onMouseUp = onMouseUp;
    this.getSelectedCells = getSelectedCells;
    this.getSelectedNumberCells = getSelectedNumberCells;
    this.processTableSelected = processTableSelected;
    this.markSelection = markSelection;

    function initializeDataCell(cell) {
        cell.onselectstart = new Function("this.renderer.selectHelper.selectionStarted(this); return false;");
        cell.onmouseover = new Function("this.renderer.selectHelper.onMouseOver(this)");
        cell.onmouseup = new Function("this.renderer.selectHelper.onMouseUp(this)");
    }

    function selectionStarted(cell) {
        if (!this.selectedArea) {
            this.selectedArea = new Object();
        }

        this.selectedArea.startCell = cell;
        this.selectionInProcess = true;

        this.onMouseOver(cell);
    }

    function onMouseOver(cell, selectionDone) {
        if (this.selectionInProcess && (selectionDone || !cell.selected)) {
            cell.selected = true;
            this.selectedArea.endCell = cell;
            this.markSelection();
        }
    }

    function onMouseUp(cell) {
        if (this.selectionInProcess) {
            this.selectionDone = true;
            this.onMouseOver(cell, true);
            this.tableRenderer.tableSelected();
        }
    }

    function processTableSelected() {
        var selectedRowPositions = new Array();

        // Find selected row positions and mark selected cells unselected
        var startRowPosition = this.tableRenderer.tableHelper.getRowPosition(this.selectedArea.startCell.parentNode);
        var endRowPosition = this.tableRenderer.tableHelper.getRowPosition(this.selectedArea.endCell.parentNode);

        // Fill all rows betwen
        for (var i = startRowPosition; i <= endRowPosition; i++) {
            selectedRowPositions[i - startRowPosition] = i;
        }

        // Reset the flags
        this.selectionInProcess = false;
        this.selectionDone = false;

        return selectedRowPositions;
    }

    function markSelection() {
        var rows = this.tableRenderer.widget.rows;
        var pivotHelper = this.tableRenderer.pivotHelper;

        var startRowIndex = this.selectedArea.startCell.parentNode.rowIndex;
        var endRowIndex = this.selectedArea.endCell.parentNode.rowIndex;
        var startColIndex = pivotHelper.getPivotCellIndex(this.selectedArea.startCell);
        var endColIndex = pivotHelper.getPivotCellIndex(this.selectedArea.endCell);

        var rowCount = rows.length;

        for (var i = 0; i < rowCount; i++) {
            var row = rows[i];
            var cells = row.childNodes;
            var cellCount = cells.length;

            for (var j = 0; j < cellCount; j++) {
                var cell = cells[j];

                var cellIndex = pivotHelper.getPivotCellIndex(cell);
                var cellColor = (i >= startRowIndex && i <= endRowIndex && cellIndex >= startColIndex && cellIndex <= endColIndex ? SELECTED_COLOR : cell.style.originalBackgroundColor);

                if (cellColor != cell.style.backgroundColor) {
                    this.tableRenderer.tableHelper.setCellBackgroundColor(cell, cellColor);
                }

                // Reset the selected mark of the cell
                if (this.selectionDone) {
                    cell.selected = false;
                }
            }
        }
    }

    function getSelectedCells() {
        var result = new Array();

        var rows = this.tableRenderer.widget.tBodies[0].rows;
        var rowCount = rows.length;

        for (var i = 0; i < rowCount; i++) {
            var row = rows[i];
            var cells = row.childNodes;
            var cellCount = cells.length;

            for (var j = 0; j < cellCount; j++) {
                cell = cells[j];
                if (cell.style.backgroundColor == SELECTED_COLOR) {
                    result[result.length] = cell;
                }
            }
        }

        return result;
    }

    function getSelectedNumberCells() {
        var result = new Array();

        var selectedCells = this.getSelectedCells();
        var selectedCellCount = selectedCells.length;

        for (var i = 0; i < selectedCellCount; i++) {
            var cell = selectedCells[i];

            if (cell.leafRenderer.isNumber()) {
                result[result.length] = cell;
            }
        }

        return result;
    }
}