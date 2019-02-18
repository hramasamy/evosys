function TableRendererHelper(tableRenderer) {
    this.tableRenderer = tableRenderer;

    this.parseBranchData = parseBranchData;
    this.parseLeafRenderers = parseLeafRenderers;

    this.initializeHeadCells = initializeHeadCells;
    this.initializeDataCells = initializeDataCells;
    this.initializeDataCell = initializeDataCell;
    this.initializeCells = initializeCells;
    this.initializeCellCommon = initializeCellCommon;
    this.initializeHeadCell = initializeHeadCell;
    this.initializeUtilities = initializeUtilities;

    this.getSelectedRow = getSelectedRow;
    this.getLastRow = getLastRow;
    this.getRowPosition = getRowPosition;
    this.getEndRowPosition = getEndRowPosition;
    this.getStartDataCol = getStartDataCol;

    this.isRowSelected = isRowSelected;
    this.isCellSelected = isCellSelected;
    this.isCellEditable = isCellEditable;
    this.isDataRow = isDataRow;

    this.isTableDataEmpty = isTableDataEmpty;
    this.isEmptyDataCell = isEmptyDataCell;
    this.setEmptyRow = setEmptyRow;
    this.removeAllRows = removeAllRows;
    this.insertDataRow = insertDataRow;
    this.insertDataCell = insertDataCell;

    this.postNavigationEvent = postNavigationEvent;

    this.toggleSortOrder = toggleSortOrder;
    this.getUpDownGIFUrl = getUpDownGIFUrl;

    this.setCellBackgroundColor = setCellBackgroundColor;
    this.tableResized = tableResized;

    function parseBranchData(aBranchStr) {
        aBranchStr = aBranchStr.replace(/\[\]/g, "");

        var index0 = aBranchStr.indexOf("[");
        var index1 = aBranchStr.lastIndexOf("]");

        aBranchStr = aBranchStr.substring(index0 + 1, index1);
        var branchData = new Array();

        for(var i = 0;; i++) {
            index0 = aBranchStr.indexOf("[");
            index1 = aBranchStr.indexOf("]");
            if (index0 == -1 || index1 == -1 || index1 == index0 + 1) {
                break;
            }

            var colStr = aBranchStr.substring(index0 + 1, index1);
            branchData[i] = ArrayUtils.getTokens(colStr);

            aBranchStr = aBranchStr.substring(index1 + 1);
        }

        return branchData;
    }

    function parseLeafRenderers(leafRenderStrings) {
        var leafRenderers = this.parseBranchData(leafRenderStrings);

        for (var i = 0; i < leafRenderers.length; i++) {
            var leafRenderer = new TableLeafRenderer();
            var leafRendererTokens = leafRenderers[i];


            leafRenderer.tableRenderer = this.tableRenderer;
            leafRenderer.isFixed = (i < this.tableRenderer.widget.leftFixed || i >= leafRenderers.length - this.tableRenderer.widget.rightFixed);
            leafRenderer.columnIndex = parseInt(leafRendererTokens[0]);
            leafRenderer.alignment = leafRendererTokens[1];
            leafRenderer.columnWidth = parseInt(leafRendererTokens[2]);
            leafRenderer.editable = (leafRenderer.isFixed ? false : eval(leafRendererTokens[3]));
            leafRenderer.visible = eval(leafRendererTokens[4]);
            leafRenderer.isLink = eval(leafRendererTokens[5]);
            leafRenderer.dataType = eval(leafRendererTokens[6]);
            leafRenderer.numberStyle = leafRendererTokens[7];
            leafRenderer.hasTotal = eval(leafRendererTokens[8]);
            leafRenderer.editorStyles = BeanUtils.parseObject(leafRendererTokens[9], FIELD_DELIMITER_1);
            leafRenderer.columnCodes = HashMap.prototype.parseMap(leafRendererTokens[10], FIELD_DELIMITER_1);
            leafRenderer.initialize();

            leafRenderers[i] = leafRenderer;
        }

        return leafRenderers;
    }

    function initializeHeadCells() {
        this.initializeCells(this.tableRenderer.widget.tHead.rows, false);
    }

    function initializeDataCells() {

        if (this.isTableDataEmpty()) {
            return;
        }

        // Special handling for totals
        if (this.tableRenderer.widget.hasTotals && !this.isTableDataEmpty()) {
            this.getLastRow().isTotal = true;
        }

        // Other properties
        this.initializeCells(this.tableRenderer.widget.tBodies[0].rows, true);
    }

    function initializeCells(rows, isDataCell) {
        var rowCount = rows.length;
        var startCol = this.getStartDataCol();
        var dataColumnCount = this.tableRenderer.widget.dataColumnCount;
        var leafRenderers = this.tableRenderer.widget.leafRenderers;

        for (var i = 0; i < rowCount; i++) {
            var row = rows[i];

            if (!isDataCell) {
                if (row.rowIndex != rowCount - 1) {
                    continue;
                }
            }

            var cells = row.childNodes;
            for (var j = 0, k = startCol; j < dataColumnCount; j++) {
                var leafRenderer = leafRenderers[j];
                if (!leafRenderer.visible) {
                    continue;
                }

                var cell = cells[k];
                if (isDataCell) {
                    if(!this.isEmptyDataCell(cell)) {
                        this.initializeDataCell(cell, j);
                    }
                } else {
                    this.initializeHeadCell(cell, j);
                }

                k++;
            }
        }
    }

    function initializeCellCommon(cell, columnIndex) {
        cell.leafRenderer = this.tableRenderer.widget.leafRenderers[columnIndex];
        cell.renderer = this.tableRenderer;
    }

    function initializeHeadCell(cell, columnIndex) {
        this.initializeCellCommon(cell, columnIndex);

        // Disable sorting for kaleidoscope
        if (!this.tableRenderer.isKaleidoscope) {
            cell.leafRenderer.ascending = true;
            cell.onclick = new Function("this.renderer.sortTable(this)");
        }
    }

    function initializeDataCell(cell, columnIndex, anInstance) {
        var theInstance = anInstance || this;

        theInstance.initializeCellCommon(cell, columnIndex);

        var leafRenderer = cell.leafRenderer;
        var isTotalCell = cell.parentNode.isTotal;

        // Alignment
        if (leafRenderer.alignment != ColumnAlignments.DEFAULT) {
            cell.style.textAlign = leafRenderer.alignment;
        }

        // Special handling for totals
        if (isTotalCell && !leafRenderer.hasTotal) {
            cell.innerHTML = "&nbsp;";
        } else {
            // Use the leafRenderer to do other initialization
            leafRenderer.initializeDataCell(cell);
        }

        // Click and Selection
        if (!isTotalCell) {
            cell.onclick = new Function("this.renderer.cellClicked(this)");
            cell.ondblclick = new Function("this.renderer.cellDblClicked(this)");
            theInstance.tableRenderer.selectHelper.initializeDataCell(cell);
        }
    }

    function initializeUtilities(anInstance) {
        var theInstance = anInstance || this;

        var tableRenderer = theInstance.tableRenderer;

        // Calculator
        var calculatorButton = tableRenderer.getComponent("calculator");
        if (calculatorButton) {
            calculatorButton.onclick = new Function("this.renderer.calculatorClicked();");
            calculatorButton.style.display = "inline";
        }

        // Autosum
        tableRenderer.sumHelper.initialize();

        // Excel report
        var excelReportButton = tableRenderer.getComponent("excelExport");
        if (excelReportButton) {
            excelReportButton.onclick = new Function("this.renderer.excelExportClicked()");
            excelReportButton.style.display = "inline";
        }

        // Personal qube
        var personalQubeButton = tableRenderer.getComponent("personalQube");
        if (personalQubeButton) {
            personalQubeButton.onclick = new Function("this.renderer.personalQubeClicked()");
            personalQubeButton.style.display = "inline";
        }

        // Conditional formatter
        var fomatterButton = tableRenderer.getComponent("formatter");
        if (fomatterButton) {
            fomatterButton.onclick = new Function("this.renderer.formatterClicked()");
            fomatterButton.style.display = "inline";
        }
    }

    function getSelectedRow() {
        var rows = this.tableRenderer.widget.tBodies[0].rows;
        var rowCount = rows.length;

        for (var i = 0; i < rowCount; i++) {
            var row = rows[i];
            if (this.isRowSelected(row)) {
                return row;
            }
        }

        return null;
    }

    function isRowSelected(row) {
        var cells = row.childNodes;
        var cellCount = cells.length;

        for (var i = 0; i < cellCount; i++) {
            if (this.isCellSelected(cells[i])) {
                return true;
            }
        }

        return false;
    }

    function isCellSelected(cell) {
        return cell.style.backgroundColor == SELECTED_COLOR;
    }

    function isCellEditable(cell) {
        var leafRenderer = cell.leafRenderer;

        return (leafRenderer ? leafRenderer.editable : false);
    }

    function isDataRow(row) {
        if (!row) {
            return false;
        }

        if (!this.tableRenderer.widget.hasTotals) {
            return true;
        }

        var lastRow = this.getLastRow();
        return (row != lastRow || !lastRow.isTotal);
    }

    function getLastRow() {
        var dataRows = this.tableRenderer.widget.tBodies[0].rows;
        return dataRows[dataRows.length - 1];
    }

    function getRowPosition(row) {
       if (row == null) {
           return 0;
       }

       var widget = this.tableRenderer.widget;
       return widget.startRow + row.rowIndex - widget.tHead.rows.length;
    }

    function getEndRowPosition() {
        var widget = this.tableRenderer.widget;

        var endRowPosition = widget.startRow + widget.tBodies[0].rows.length + -1;
        if (widget.hasTotals) {
            endRowPosition--;
        }

        return endRowPosition;
    }

    function getStartDataCol() {
        return (this.tableRenderer.widget.leftFixedFormat > 0 ? 1 : 0);
    }

    function isTableDataEmpty() {
        var tBody = this.tableRenderer.widget.tBodies[0];
        if (tBody.rows.length == 0) {
            return true;
        }

        var row = tBody.rows[0];
        if (row.childNodes.length == 0) {
            return true;
        }

        var cell = row.childNodes[0];
        return this.isEmptyDataCell(cell);
    }

    function isEmptyDataCell(cell) {
        return cell.innerHTML == this.tableRenderer.widget.emptyTableMsg;
    }

    function setEmptyRow() {
        var widget = this.tableRenderer.widget;
        var tBody = widget.tBodies[0];

        for (var i = tBody.rows.length - 1; i >= 0; i--) {
            tBody.deleteRow(i);
        }

        var row = tBody.insertRow();
        var cell = row.insertCell();
        cell.colSpan = widget.allColumnCount;
        cell.align = "center";
        cell.innerHTML = widget.emptyTableMsg;
    }

    function removeAllRows(dataOnly) {
        var tBody = this.tableRenderer.widget.tBodies[0];
        var rowCount = tBody.rows.length;

        if (dataOnly) {
            var lastRow = this.getLastRow();
            if (lastRow && lastRow.isTotal) {
                rowCount--;
            }
        }

        for (var i = 0; i < rowCount; i++) {
            tBody.deleteRow(0);
        }
    }

    function insertDataRow(dataRowIndex) {
        var widget = this.tableRenderer.widget;
        var tBody = widget.tBodies[0];
        var row = ((typeof dataRowIndex) == "undefined" ? tBody.insertRow() : tBody.insertRow(dataRowIndex));
        var allColumnCount = widget.allColumnCount;
        var leafRenderers = widget.leafRenderers;

        for (var i = 0, k = 0; i < allColumnCount; i++) {
            var leafRenderer = leafRenderers[i];
            if (!leafRenderer || !leafRenderer.visible) {
                continue;
            }

            this.insertDataCell(row, leafRenderer);
        }

        return row;
    }

    function insertDataCell(row, leafRenderer) {
        var startDataCol = this.getStartDataCol();
        var cell = row.insertCell();

        if (cell.cellIndex < startDataCol) {
            cell.className = "numberrow";
            cell.width = this.tableRenderer.widget.numberColumnWidth;
        } else if (leafRenderer.columnWidth > 0) {
            cell.width = leafRenderer.columnWidth;
        }

        return cell;
    }

    function postNavigationEvent(startRow) {
        var widget = this.tableRenderer.widget;
        var endRow = startRow + widget.rowsInPage - 1;

        var widgetEvent = new WidgetEvent(widget, "getBulkValue");
        widgetEvent.set("startRow", startRow, "java.lang.Integer");
        widgetEvent.set("endRow", endRow, "java.lang.Integer");
        widgetEvent.post();
    }

    function toggleSortOrder(headCell) {
        var leafRenderer = headCell.leafRenderer;

        leafRenderer.ascending = !leafRenderer.ascending;

        // compose img
        var imgHTML = "<img src='" + this.getUpDownGIFUrl(leafRenderer.ascending) + "'>";

        // Update innerHTML
        headCell.innerHTML = (leafRenderer.isNumber() ? imgHTML + headCell.innerText  : headCell.innerText + imgHTML);
    }

    function getUpDownGIFUrl(isUp) {
        return window.contextPath + "/pages/images/sort" + (isUp ? "Ascending" : "Descending") + ".gif";
    }

    function setCellBackgroundColor(cell, color) {
        var cellStyle = cell.style;

        if (!cellStyle.originalBackgroundColor) {
            cellStyle.originalBackgroundColor = cellStyle.backgroundColor;
        }

        cellStyle.backgroundColor = color;
    }

    function tableResized(cell) {
        if (cell && cell.leafRenderer) {
            cell.leafRenderer.cellResized(cell);
            return;
        }

        var rows = this.tableRenderer.widget.tBodies[0].rows;
        var rowCount = rows.length;

        for (var i = 0; i < rowCount; i++) {
            var row = rows[i];
            var cells = row.childNodes;
            var cellCount = cells.length;

            for (var j = 0; j < cellCount; j++) {
                var cell = cells[j];
                var leafRenderer = cell.leafRenderer;

                if (leafRenderer) {
                    leafRenderer.cellResized(cell);
                }
            }
        }
    }
}