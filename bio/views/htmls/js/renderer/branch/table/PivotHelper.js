function PivotHelper(tableRenderer) {
    this.tableRenderer = tableRenderer;

    this.deletePivotCell = deletePivotCell;
    this.getPivotCell = getPivotCell;
    this.getPivotCellIndex = getPivotCellIndex;
    this.mergeRows = mergeRows;
    this.mergeRowsByColumns = mergeRowsByColumns;
    this.mergeRowsByColumn = mergeRowsByColumn;
    this.isSiblingPivot = isSiblingPivot;
    this.isPivotCell = isPivotCell;

    this.findCellMemberRendererNode = findCellMemberRendererNode;

    function deletePivotCell(row, pivotCellIndex) {
        // we need to keep the original cells for reference
        var originalCells = row.originalCells;

        if (!originalCells) {
            originalCells = new ArrayList();

            var cells = row.childNodes;
            var cellCount = cells.length;

            for (var i = 0; i < cellCount; i++) {
                var cell = cells[i];
                originalCells.add(cell);
            }

            row.originalCells = originalCells;
        }

        // Before delete the cell, clean up the original cells to preserve the memory
        var cell = originalCells.get(pivotCellIndex);

        // Now remove it
        row.deleteCell(cell.cellIndex);
    }

    function getPivotCell(row, cellIndex) {
        var originalCells = row.originalCells;

        return (!originalCells ? row.childNodes[cellIndex] : originalCells.get(cellIndex));
    }

    function getPivotCellIndex(cell) {
        var row = cell.parentNode;
        var originalCells = row.originalCells;

        if (originalCells) {
            var originalCellCount = originalCells.size();

            for (var i = 0; i < originalCellCount; i++) {
                var originalCell = originalCells.get(i);
                if (originalCell == cell) {
                    return i;
                }
            }
        }

        return cell.cellIndex;
    }

    function mergeRows() {
        var widget = this.tableRenderer.widget;

        var startDataCol = this.tableRenderer.tableHelper.getStartDataCol();

        this.mergeRowsByColumns(startDataCol, startDataCol + widget.leftFixed - 1);
    }

    function mergeRowsByColumns(startColIndex, endColIndex) {
        var rows = this.tableRenderer.widget.tBodies[0].rows;

        for (var colIndex = startColIndex; colIndex <= endColIndex; colIndex++) {
            var startRowIndex = 0;
            var startCell = rows[0].childNodes[colIndex];

            for (var rowIndex = 0; startCell != null; rowIndex++) {
                var row = rows[rowIndex];
                var cell = (row && !row.isTotal? this.getPivotCell(row, colIndex) : null);

                if (!startCell) {
                    startCell = cell;
                }

                if (this.isSiblingPivot(startCell, cell)) {
                    continue;
                }

                // Start merge from startRow to endRow
                var endRowIndex = rowIndex - 1;
                this.mergeRowsByColumn(rows, startRowIndex, endRowIndex, startCell);

                // Continue search
                startRowIndex = endRowIndex + 1;
                startCell = cell;
            }
        }
    }

    function isSiblingPivot(startCell, cell) {
        if (startCell == cell) {
            return true;
        }

        if (!cell || cell.innerHTML != startCell.innerHTML) {
            return false;
        }

        // Check if they have the same pivot parents
        var cellIndex = this.getPivotCellIndex(cell);
        for (var i = 0; i < cellIndex; i++) {
            var startCellSibling = this.getPivotCell(startCell.parentNode, i);
            var cellSibling = this.getPivotCell(cell.parentNode, i);

            if (cellSibling && cellSibling.innerHTML) {
                if (!startCellSibling || startCellSibling.innerHTML != cellSibling.innerHTML) {
                    return false;
                }
            }
        }

        return true;
    }

    function mergeRowsByColumn(rows, startRowIndex, endRowIndex, startCell) {
        if (!this.isPivotCell(startCell) || endRowIndex <= startRowIndex) {
            return;
        }

        // Span the startCell
        startCell.rowSpan = (endRowIndex - startRowIndex + 1);
        startCell.style.verticalAlign = "top";

        // Remove the corresponding cells from the following rows
        var pivotCellIndex = this.getPivotCellIndex(startCell);
        var rows = this.tableRenderer.widget.tBodies[0].rows;

        for (var i = startRowIndex + 1; i <= endRowIndex; i++) {
            var row = rows[i];
            this.deletePivotCell(row, pivotCellIndex);
        }
    }

    function findCellMemberRendererNode(cell) {
        if ((typeof cell.memberRendererNode) == "undefined") {
            var memberRendererNode = cell.childNodes[0];

            cell.memberRendererNode = (memberRendererNode && memberRendererNode.name == "memberRenderer") ? memberRendererNode : null;
        }
        return cell.memberRendererNode;
    }

    function isPivotCell(cell) {
        return (cell && this.findCellMemberRendererNode(cell) != null ? true : false);
    }
}
