function SearchFormRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.registerChildEventsMonitor = registerChildEventsMonitor;
    this.criteriaChanged = criteriaChanged;
    this.search = search;
    this.showSearchCriteria = showSearchCriteria;
    this.showSearchOptionWidgets = showSearchOptionWidgets;

    this.getFilterName = getFilterName;
    this.setSearchText = setSearchText;

    function initialize() {
        this.criteriaIndicatorImg = this.getComponent("criteriaIndicator");

        this.searchOptionWidgets = this.getComponent("searchOptionWidgets");
        this.searchCriteriaHolder = this.getComponent("searchCriteriaHolder");
        this.searchCriteriaFilterName = this.getComponent("searchCriteriaFilterName");
        this.searchCriteriaExpression = this.getComponent("searchCriteriaExpression");

        this.favorites = this.getComponent("favorites");
        this.searchButton = this.getComponent("searchButton");
        this.filterBuilder = this.getComponent("filterBuilder");

        this.searchCriteriaHolder.onclick = new Function("this.renderer.showSearchOptionWidgets()");

        this.registerChildEventsMonitor();

        if (this.widget.searchText) {
            HtmlUtils.setValue(this.searchCriteriaExpression, this.widget.searchText);
            this.showSearchCriteria();
        }
    }

    function registerChildEventsMonitor() {
        var childWidgets = RendererUtils.getAllChildrenByRendererClass(this.widget);
        for (var i = 0; i < childWidgets.size(); i++) {
            var childWidget = childWidgets.get(i);
            childWidget.attachEvent("onchange", new Function(this.getEvalStringRef() + ".criteriaChanged()"));
        }
    }

    function criteriaChanged() {
        this.criteriaIndicatorImg.src = window.contextPath + "/pages/images/status_red.gif";
    }

    function search(favorites) {
        var searchEvent = new WidgetEvent(this.widget, "search");

        if (favorites) {
            if (this.getFilterName() != "") {
                searchEvent.set("filterName", favorites.value);
                this.showSearchCriteria();
                searchEvent.post();
            }
        } else {
            searchEvent.post();
        }

        // Set the green indicator line
        this.criteriaIndicatorImg.src = window.contextPath + "/pages/images/status_green.gif";
    }

    function getFilterName() {
        return StringUtils.trim(this.favorites.value.replace(/&nbsp;/g, " "));
    }

    function setSearchText(searchText) {
        this.widget.searchText = StringUtils.getParameter(searchText, "searchText");

        HtmlUtils.setValue(this.searchCriteriaExpression, this.widget.searchText);
    }

    function showSearchCriteria() {
        var filterName = this.getFilterName();
        if (filterName != "") {
            HtmlUtils.setValue(this.searchCriteriaFilterName, ": " + filterName);
        }

        this.searchOptionWidgets.style.display = "none";
        this.searchCriteriaHolder.style.display = "inline";
    }

    function showSearchOptionWidgets() {
        this.searchOptionWidgets.style.display = "inline";

        // Initialize the size (could be hidden)
        var allChildren = RendererUtils.getAllChildrenByRendererClass(this.widget);
        for (var i = 0; i < allChildren.size(); i++) {
            var renderer = allChildren.get(i).renderer;
            if (renderer.initializeSize) {
                renderer.initializeSize();
            }
        }

        this.searchCriteriaHolder.style.display = "none";
    }
}
