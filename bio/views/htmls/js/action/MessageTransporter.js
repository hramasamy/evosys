function MessageTransporter(name) {
    this.name = name;

    this.onLoad = onLoad;
    this.getMapActionHandler = getMapActionHandler;

    function onLoad(anInstance) {
        var theInstance = anInstance || this;

        if (!window.report) {
            window.report = new Report();
        }

        var mapActionHandler = theInstance.getMapActionHandler();
        if (mapActionHandler) {
            mapActionHandler.processReport(window.report);
            return true;
        }

        return false;
    }

    function getMapActionHandler() {
        return HtmlUtils.findWindowObject("mapActionHandler");
    }
}