function WisePickInputRenderer(widget, inputbox, cell) {
    this.widget = widget;
    this.inputbox = inputbox;
    this.cell = cell;

    this.initialize = initialize;
    this.getList = getList;
    this.processGetList = processGetList;
    this.inputChanged = inputChanged;
    this.selectionChanged = selectionChanged;

    function initialize() {
        this.widget.renderer = this;
        this.inputbox.renderer = this;

        this.helper = new WisePickHelper(this);
        this.helper.initialize();
        this.helper.initializeSize();
    }

    function getList() {
        var leafRenderer = this.cell.leafRenderer;
        var tableRenderer = leafRenderer.tableRenderer;
        tableRenderer.currentWisePickRenderer = this;

        var widgetEvent = new WidgetEvent(tableRenderer.widget, "getList");
        widgetEvent.set("row", tableRenderer.tableHelper.getRowPosition(this.cell.parentNode), "java.lang.Integer");
        widgetEvent.set("column", leafRenderer.columnIndex, "java.lang.Integer");
        widgetEvent.post();
    }

    function processGetList(listStr) {
        this.helper.processGetList(listStr);
    }

    function inputChanged() {
        // do nothing here
    }

    function selectionChanged() {
        this.inputbox.value = this.widget.value;
    }
}
