JmsEventTransporter.prototype = new MessageTransporter();

function JmsEventTransporter(name) {
    this.base = MessageTransporter;
    this.base(name);

    this.onLoad = onLoad;
    this.processReport = processReport;

    function onLoad() {
        var result = JmsEventTransporter.prototype.onLoad(this);

        var notifications = StringUtils.trim(HtmlUtils.getValue("jmsEventNotifications"));

        if (notifications != "") {
            var historyHandler = HtmlUtils.findWindowObject("historyHandler", window.parent.top);
            if (historyHandler) {
                historyHandler.setNotifications(notifications);
            }
        }

        // Report
        var launchingUrl = window.report.launchingUrl;
        if (launchingUrl) {
            this.processReport(launchingUrl);
        }

        return result;
    }

    function processReport(launchingUrl) {
        var commandTransporter = HtmlUtils.findWindowObject("commandTransporter");
        if (commandTransporter) {
            commandTransporter.launchUrl(launchingUrl);
        }
    }
}