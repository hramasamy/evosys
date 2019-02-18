function CanvasRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.setInstanceName = setInstanceName;
    this.processReload = processReload;
    this.generateReport = generateReport;

    function initialize() {
        if (window.mapActionHandler) {
            window.mapActionHandler.canvasRenderer = this;
        }
    }

    function setInstanceName(instanceName) {
        var mapTabs = HtmlUtils.findWindowObject("mapTabs");
        if (mapTabs && mapTabs.currentMap) {
            mapTabs.currentMap.instanceName = instanceName;
            mapTabs.repaint();
        }
    }

    function processReload(param) {
        window.location.reload();
    }

    function generateReport() {
        var widgetEvent = new WidgetEvent(null, "generateReport");
        widgetEvent.set("userAction", "generateReport");
        widgetEvent.post();
    }
}