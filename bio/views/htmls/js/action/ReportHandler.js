function ReportHandler() {
    this.getReportMenu = getReportMenu;

    this.setReportMenuVisible = setReportMenuVisible;

    this.generateReport = generateReport;

    function getReportMenu() {
        return window.document.getElementById("reportMenu");
    }

    function setReportMenuVisible(visible) {
        var reportMenu = this.getReportMenu();
        reportMenu.style.display = (visible ? "inline" : "none");
    }

    function generateReport(reportType) {
        var mapActionHandler = HtmlUtils.findWindowObject("mapActionHandler");
        if (mapActionHandler && mapActionHandler.canvasRenderer) {
            mapActionHandler.canvasRenderer.generateReport();
        }
    }
}
