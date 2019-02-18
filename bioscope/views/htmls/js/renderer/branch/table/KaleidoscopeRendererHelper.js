KaleidoscopeRendererHelper.prototype = new TableRendererHelper();

function KaleidoscopeRendererHelper(kaleidoscopeRenderer) {
    this.base = TableRendererHelper;
    this.base(kaleidoscopeRenderer);

    this.initializeSpanningHeaders = initializeSpanningHeaders;
    this.initializeDataCell = initializeDataCell;
    this.getDrillImageHTML = getDrillImageHTML;
    this.findAndCreateMemberRenderer = findAndCreateMemberRenderer;
    this.updatePivotDrillImages = updatePivotDrillImages;
    this.initializeUtilities = initializeUtilities;
    this.updateFavoritesUI = updateFavoritesUI;
    this.toggleDisplayFavorites = toggleDisplayFavorites;

    function initializeSpanningHeaders() {
        var rows = this.tableRenderer.widget.tHead.rows;
        var rowCount = rows.length;

        for (var i = 0; i < rowCount - 1; i++) {
            var row = rows[i];
            var cells = row.childNodes;
            var cellCount = cells.length;

            for (var j = 0; j < cellCount; j++) {
                var cell = cells[j];

                var memberRenderer = this.findAndCreateMemberRenderer(cell, "pivotColumnDrill");
                if (memberRenderer != null) {
                    memberRenderer.rowIndex = i;
                    memberRenderer.columnIndex = this.getStartDataCol() + j;
                }
            }
        }
    }

    function initializeDataCell(cell, columnIndex) {
        KaleidoscopeRendererHelper.prototype.initializeDataCell(cell, columnIndex, this);

        var memberRenderer = this.findAndCreateMemberRenderer(cell, "pivotRowDrill");

        cell.oncontextmenu = new Function("this.renderer.toggleDisplayMenu(this);return false;");
    }

    function findAndCreateMemberRenderer(cell, drillActionIdPrefix) {
        var memberRendererNode = this.tableRenderer.pivotHelper.findCellMemberRendererNode(cell);

        if (!memberRendererNode) {
            return null;
        }

        // Build member renderer
        var memberRenderer = new Object();
        var attributes = ArrayUtils.getTokens(memberRendererNode.value, ";");
        memberRenderer.drillDownEnabled = eval(attributes[0]);
        memberRenderer.drillUpEnabled = eval(attributes[1]);
        cell.tableRenderer = this.tableRenderer;
        cell.memberRenderer = memberRenderer;

        this.updatePivotDrillImages(cell, drillActionIdPrefix);

        return memberRenderer;
    }

    function updatePivotDrillImages(cell, drillActionIdPrefix) {
        if (!cell.memberRenderer) {
            return;
        }

        var innerHTML = "";
        if (cell.memberRenderer.drillUpEnabled) {
            innerHTML += this.getDrillImageHTML(drillActionIdPrefix, true);
        }
        if (cell.memberRenderer.drillDownEnabled) {
            innerHTML += this.getDrillImageHTML(drillActionIdPrefix, false);
        }
        innerHTML += cell.innerText;

        cell.innerHTML = innerHTML;
    }

    function getDrillImageHTML(drillActionIdPrefix, isUp) {
        var imgName = (isUp ? "sortAscending.gif" : "sortDescending.gif");

        return "<img src=\"" + this.getUpDownGIFUrl(isUp) + "\" onclick=\"this.parentNode.tableRenderer.pivotDrilled(this.parentNode, '" + drillActionIdPrefix + "'," + isUp + ")\">";
    }

    function initializeUtilities() {
        // Super class process
        KaleidoscopeRendererHelper.prototype.initializeUtilities(this);

        // Pivot Menu
        this.tableRenderer.menuHelper.initialize("pivotMenu");
        this.tableRenderer.menuHelper.initialize("drillThroughMenu");

        // Graph
        var graphButton = this.tableRenderer.getComponent("graph");
        if (graphButton) {
            graphButton.onclick = new Function("this.renderer.graphClicked();");
            graphButton.style.display = "inline";
        }

        // Pie chart
        var pieChartButton = this.tableRenderer.getComponent("pieChart");
        if (pieChartButton) {
            pieChartButton.onclick = new Function("this.renderer.pieChartClicked();");
            pieChartButton.style.display = "inline";
        }

        // Favorites
        this.tableRenderer.pivotNameHolder = this.tableRenderer.getComponent("pivotName");

        this.tableRenderer.menuHelper.initialize("favoritesListMenu");

        this.tableRenderer.favoritesListButton = this.tableRenderer.getComponent("favoritesList");
        this.tableRenderer.favoritesListButton.onclick = new Function("this.renderer.tableHelper.toggleDisplayFavorites()");

        this.tableRenderer.addFavoriteButton = this.tableRenderer.getComponent("addFavorite");
        this.tableRenderer.addFavoriteButton.onclick = new Function("this.renderer.addOrRemoveFavorite('addFavorite')");

        this.tableRenderer.removeFavoriteButton = this.tableRenderer.getComponent("removeFavorite");
        this.tableRenderer.removeFavoriteButton.onclick = new Function("this.renderer.addOrRemoveFavorite('removeFavorite')");

        this.updateFavoritesUI();
    }

    function toggleDisplayFavorites() {
        var favoritesMenu = this.tableRenderer.favoritesListMenu;

        var favoritesList = this.tableRenderer.widget.favorites.favorites;
        var isVisible = (favoritesList.length > 0 && favoritesMenu.style.display == "none");

        favoritesMenu.style.display = (isVisible ? "inline" : "none");
    }

    function updateFavoritesUI() {
        var favoritesList = this.tableRenderer.widget.favorites.favorites;

        this.tableRenderer.addFavoriteButton.style.display = (favoritesList.length == 0 ? "inline" : "none");
        this.tableRenderer.removeFavoriteButton.style.display = (favoritesList.length == 0 ? "none" : "inline");

        // Favorites list menu
        var favoritesMenu = this.tableRenderer.favoritesListMenu;
        for (var i = 0; i < favoritesMenu.rows.length; i++) {
            favoritesMenu.deleteRow(0);
        }

        for (var i = 0; i < favoritesList.length; i++) {
            var favorite = favoritesList[i];

            var row = favoritesMenu.insertRow();
            var cell = row.insertCell();

            cell.menu = favoritesMenu;
            cell.innerText = favorite;
            cell.onclick = new Function("this.menu.renderer.sendSetPivotName('" + favorite + "');this.menu.style.display='none';");
        }
        favoritesMenu.style.display = "none";
    }
}