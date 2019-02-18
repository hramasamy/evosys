ChartRenderer.prototype = new WidgetRenderer();

function ChartRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.findValidAttrName = findValidAttrName;
    this.setTooltips = setTooltips;
    this.setValue = setValue;
    this.clicked = clicked;
    this.onMouseOut = onMouseOut;
    this.onMouseMove = onMouseMove;
    this.onContextMenu = onContextMenu;
    this.hideFloatMenu = hideFloatMenu;
    this.hideTooltips = hideTooltips;
    this.postChartEvent = postChartEvent;
    this.excelExportClicked = excelExportClicked;
    this.imageExportClicked = imageExportClicked;
    this.propertiesClicked = propertiesClicked;

    function initialize() {
        this.tooltipsHolder = this.getComponent("tooltips");

        this.widget.onclick = new Function("this.renderer.clicked()");
        this.widget.onmousemove = new Function("this.renderer.onMouseMove()");
        this.widget.onmouseout = new Function("this.renderer.onMouseOut()");
        this.widget.oncontextmenu = new Function("this.renderer.onContextMenu();return false;");

        this.menuHelper = new FloatMenuHelper(this);
        this.menuHelper.initialize("chartMenu");
    }

    function findValidAttrName(name) {
        return name != "border" && ChartRenderer.prototype.findValidAttrName(name, this);
    }

    function setTooltips(tooltips) {
        var paddings = (10 - tooltips.length) / 2;
        for (var i = 0; i < paddings; i++) {
            tooltips = "&nbsp;" + tooltips + "&nbsp;";
        }

        this.tooltipsHolder.innerHTML = tooltips;
        this.tooltipsHolder.style.posLeft = this.lastPosition.x;
        this.tooltipsHolder.style.posTop = this.lastPosition.y;
        this.tooltipsHolder.style.display = "inline";
    }

    function setValue(aValue) {
        this.widget.src = HtmlUtils.encodeWithTimestamp(this.widget.src);
    }

    function clicked() {
        window.clearTimeout(this.tooltipsRequestTimer);

        this.hideFloatMenu();
        this.hideTooltips();
        this.postChartEvent("ITEM_SELECTED");
    }

    function onMouseOut() {
        window.clearTimeout(this.tooltipsRequestTimer);

        if (this.chartMenu.lastDisplayTime && new Date().getTime() - this.chartMenu.lastDisplayTime > 10) {
            this.chartMenu.style.display = "none";
        }
    }

    function onMouseMove() {
        this.hideTooltips();

        var REQUEST_INTERVAL = 2200;

        this.lastPosition = new Object();
        this.lastPosition.x = window.event.offsetX;
        this.lastPosition.y = window.event.offsetY;

        // The event will be fired if the mouse stays for 2000ms
        window.clearTimeout(this.tooltipsRequestTimer);
        this.tooltipsRequestTimer = window.setTimeout("window.document.getElementById('" + this.widget.id + "').renderer.postChartEvent('tooltipsRequested'," + window.event.offsetX + "," + window.event.offsetY + ")", REQUEST_INTERVAL);
    }

    function onContextMenu() {
        this.hideTooltips();

        window.clearTimeout(this.tooltipsRequestTimer);

        this.menuHelper.show(this.chartMenu, null, true);
        this.chartMenu.lastDisplayTime = new Date().getTime();
    }

    function hideFloatMenu() {
        this.chartMenu.style.display = "none";
    }

    function hideTooltips() {
        this.tooltipsHolder.style.display = "none";
    }

    function postChartEvent(actionId, x, y) {
        var widgetEvent = new WidgetEvent(this.widget, actionId);
        widgetEvent.set("x", ((typeof x) != "undefined" ? x : window.event.offsetX), "java.lang.Integer");
        widgetEvent.set("y", ((typeof y) != "undefined" ? y : window.event.offsetY), "java.lang.Integer");
        widgetEvent.post();
    }

    function excelExportClicked() {new WidgetEvent(this.widget, "excelExport").post();}
    function propertiesClicked() {new WidgetEvent(this.widget, "propertiesRequested").post();}

    function imageExportClicked() {
        var imgUrl = HtmlUtils.encodeWithTimestamp(this.widget.src);
        window.open(imgUrl);
    }
}