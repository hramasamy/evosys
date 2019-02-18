function NavigatableRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.updateRowCountStatus = updateRowCountStatus;
    this.updateNavigation = updateNavigation;
    this.setVisible = setVisible;

    this.initializeNavigationBar = initializeNavigationBar;

    this.setPosition = setPosition;
    this.setTotalRows = setTotalRows;

    function initializeNavigationBar() {
        this.firstButton = this.getComponent("first");
        this.previousButton = this.getComponent("previous");
        this.nextButton = this.getComponent("next");
        this.lastButton = this.getComponent("last");
        this.addButton = this.getComponent("add");
        this.removeButton = this.getComponent("remove");
        this.rowCountStatus = this.getComponent("rowCountStatus");

        this.firstButton.onclick = new Function("this.renderer.first()");
        this.previousButton.onclick = new Function("this.renderer.previous()");
        this.nextButton.onclick = new Function("this.renderer.next()");
        this.lastButton.onclick = new Function("this.renderer.last()");
        this.addButton.onclick = new Function("this.renderer.add()");
        this.removeButton.onclick = new Function("this.renderer.remove()");

        this.widget.totalRows = parseInt(this.widget.totalRows || 0);
        this.widget.allowAdd = eval(this.widget.allowAdd);
        this.widget.allowDelete = eval(this.widget.allowDelete);

        this.updateNavigation();
        this.updateRowCountStatus();
    }

    function setPosition(aValue) {
        this.widget.position = parseInt(aValue);

        this.updateRowCountStatus();
    }

    function setTotalRows(aValue) {
        this.widget.totalRows = parseInt(aValue);

        if (this.widget.totalRows == 0) {
            this.widget.position = 0;
        }

        this.updateRowCountStatus();
    }

    function updateNavigation() {
        this.setVisible(this.firstButton, this.hasPrevious());
        this.setVisible(this.previousButton, this.hasPrevious());
        this.setVisible(this.nextButton, this.hasNext());
        this.setVisible(this.lastButton, this.hasNext());
        this.setVisible(this.addButton, this.widget.allowAdd);
        this.setVisible(this.removeButton, this.widget.allowDelete);
    }

    function setVisible(element, isVisible) {
        element.style.display = (isVisible ? "inline" : "none");
    }

    function updateRowCountStatus() {
        var statusHtml = this.getPositionInfo() + " of " + this.widget.totalRows + " ";

        if (this.widget.totalRows == 0) {
            statusHtml = "&nbsp;";
        }

        HtmlUtils.setValue(this.rowCountStatus, statusHtml);
        this.updateNavigation();
    }
}