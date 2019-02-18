KaleidoscopeRenderer.prototype = new TableRenderer();

function KaleidoscopeRenderer(widget) {
    this.base = TableRenderer;
    this.base(widget);

    this.tableHelperClass = KaleidoscopeRendererHelper;
    this.isKaleidoscope = true;

    this.initialize = initialize;
    this.updateTable = updateTable;
    this.toggleDisplayMenu = toggleDisplayMenu;
    this.cellClicked = cellClicked;
    this.pivotDrilled = pivotDrilled;
    this.graphClicked = graphClicked;
    this.pieChartClicked = pieChartClicked;
    this.pivotEditorClicked = pivotEditorClicked;
    this.pivotPropertyEditorClicked = pivotPropertyEditorClicked;
    this.pivotDrillThroughClicked = pivotDrillThroughClicked;
    this.addOrRemoveFavorite = addOrRemoveFavorite;
    this.sendSetPivotName = sendSetPivotName;
    this.setFavorites = setFavorites;
    this.setCurrentFavorite = setCurrentFavorite;

    function initialize() {
        // Super class process
        KaleidoscopeRenderer.prototype.initialize(this);

        // Spanning headers
        this.tableHelper.initializeSpanningHeaders();
    }

    function updateTable(branchData) {
        this.tableHelper.removeAllRows();
        KaleidoscopeRenderer.prototype.updateTable(branchData, this);
    }

    function pivotDrilled(cell, actionIdPrefix, isUp) {
        var widgetEvent = new WidgetEvent(this.widget, actionIdPrefix + (isUp ? "Up" : "Down"));
        if (actionIdPrefix == "pivotColumnDrill") {
            var memberRenderer = cell.memberRenderer;

            widgetEvent.set("rowIndex", memberRenderer.rowIndex);
            widgetEvent.set("colIndex", memberRenderer.columnIndex);
        } else {
            widgetEvent.set("rowIndex", this.tableHelper.getRowPosition(cell.parentNode));
            widgetEvent.set("colIndex", cell.leafRenderer.columnIndex);
        }

        widgetEvent.post();

        window.event.returnValue = false;
    }

    function toggleDisplayMenu(cell) {
        this.pivotMenu.style.display = "none";
        this.drillThroughMenu.style.display = "none";

        this.menuHelper.show(this.pivotHelper.isPivotCell(cell) ? this.pivotMenu : this.drillThroughMenu, cell);
    }

    function cellClicked(cell) {
        if (this.pivotMenu.style.display != "none" || this.drillThroughMenu.style.display != "none") {
            this.pivotMenu.style.display = "none";
            this.drillThroughMenu.style.display = "none";
            return;
        }

        KaleidoscopeRenderer.prototype.cellClicked(cell, this);
    }

    function graphClicked() {new WidgetEvent(this.widget, "graphMapRequested").post();}
    function pieChartClicked() {new WidgetEvent(this.widget, "pieMapRequested").post();}
    function pivotEditorClicked() {new WidgetEvent(this.widget, "pivotEditorRequested").post();}
    function pivotPropertyEditorClicked() {new WidgetEvent(this.widget, "pivotPropertyEditorRequested").post();}

    function pivotDrillThroughClicked() {
        var srcCell = this.drillThroughMenu.srcCell;

        var widgetEvent = new WidgetEvent(this.widget, "pivotDrillThrough");
        widgetEvent.set("row", this.tableHelper.getRowPosition(srcCell.parentNode), "java.lang.Integer");
        widgetEvent.set("column", srcCell.leafRenderer.columnIndex, "java.lang.Integer");
        widgetEvent.post();
    }

    function sendSetPivotName(pivotName) {
        var widgetEvent = new WidgetEvent(this.widget, "setPivotName");
        widgetEvent.set("pivotName", pivotName);
        widgetEvent.post();
    }

    function addOrRemoveFavorite(actionName) {
        var favorites = this.widget.favorites;

        var widgetEvent = new WidgetEvent(this.widget, actionName);
        widgetEvent.set("pivotName", favorites.pivotName);
        widgetEvent.set("pivotOwner", favorites.pivotOwner);
        widgetEvent.post();
    }

    function setFavorites(favoritesString) {
        this.widget.favorites = BeanUtils.parseObject(favoritesString);
        this.tableHelper.updateFavoritesUI();
    }

    function setCurrentFavorite(currentFavoriteString) {
        var favoriteParams = BeanUtils.parseObject(currentFavoriteString);

        var favorites = this.widget.favorites;
        favorites.pivotName = favoriteParams.pivotName;
        favorites.pivotOwner = favoriteParams.pivotOwner;
    }
}