function ColumnResizeHelper(tableRenderer) {
    this.tableRenderer = tableRenderer;

    this.initialize = initialize;
    this.getResizeCursor = getResizeCursor;

    this.colMouseMove = colMouseMove;
    this.colMouseDown = colMouseDown;
    this.colMouseOut = colMouseOut;
    this.colMouseUp = colMouseUp;

    function initialize() {
        var headRows = this.tableRenderer.widget.tHead.rows;
        if (!headRows || headRows.length == 0) {
            return;
        }

        var headRow = headRows[headRows.length - 1];
        var headCells = headRow.childNodes;
        var headCellCount = headCells.length;

        for (var i = 0; i < headCellCount; i++) {
            var headCell = headCells[i];

            // Do not resize fixed columns
            var leafRenderer = headCell.leafRenderer;
            if (!leafRenderer || leafRenderer.isFixed) {
                continue;
            }

            headCell.onmousemove = new Function("this.renderer.resizeHelper.colMouseMove()");
            headCell.onmousedown = new Function("this.renderer.resizeHelper.colMouseDown()");
            headCell.onmouseout = new Function("this.renderer.resizeHelper.colMouseOut()");
            headCell.onmouseup = new Function("this.renderer.resizeHelper.colMouseUp()");
        }
    }

    function getResizeCursor() {
        return navigator.appVersion.indexOf ("MSIE 6.") != -1 ? "col-resize" : "move";
    }

    function colMouseMove() {
        var srcCell = window.event.srcElement;

        if (!this.resizingColCell) {
            srcCell.style.cursor = (window.event.offsetX >= srcCell.offsetWidth - 5 ? this.getResizeCursor() : "auto");
        } else {
            var newWidth = this.resizingColCell.style.originalWidth + (window.event.clientX - this.startMouseClientX);
            if (newWidth > 10) {
                this.resizingColCell.style.width = newWidth;
            }
        }
    }

    function colMouseDown() {
        var srcCell = window.event.srcElement;
        if (srcCell.style.cursor == this.getResizeCursor()) {
            this.resizingColCell = srcCell;
            this.resizingColCell.style.originalWidth = this.resizingColCell.offsetWidth;
            this.startMouseClientX = window.event.clientX;
            this.resizingColCell.setCapture();
        }
    }

    function colMouseOut() {
        window.event.srcElement.style.cursor = "auto";
    }

    function colMouseUp() {
        if (this.resizingColCell) {
            this.resizingColCell.style.cursor = "auto";
            this.resizingColCell.releaseCapture();
            this.resizingColCell = null;

            // Fix layout problem caused by resizing
            this.tableRenderer.resized();

            // Disable other events
            this.tableRenderer.eventDisabledUntil = new Date().getTime() + 10;
        }
    }
}