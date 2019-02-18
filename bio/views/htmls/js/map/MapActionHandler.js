function MapActionHandler(name, mapLocator) {
    this.name = name;
    this.mapLocator = mapLocator;
    this.waitMessage = "Please wait and try again...";

    this.eventQueue = new Queue();

    this.initializeRenders = initializeRenders;
    this.prepareReservedPopup = prepareReservedPopup;
    this.isReservedPopupAvailable = isReservedPopupAvailable;
    this.postEvent = postEvent;
    this.processReport = processReport;

    this.finalize = finalize;
    this.getApplicationWindow = getApplicationWindow;
    this.getApplicationHandler = getApplicationHandler;

    function initializeRenders() {
        window.document.body.style.cursor = "wait";

        var elements = window.document.all;
        var elementCount = elements.length;

        for (var i = 0; i < elementCount; i++) {
            var element = elements[i];

            if (!element.id && element.name) {
                element.id = element.name;
            }

            if (element.rendererClass) {
                element.renderer = new window[element.rendererClass](element);
                if ((typeof element.renderer.initialize) == "function") {
                    element.renderer.initialize();
                }
            }
        }

        window.document.body.style.cursor = "default";
    }

    function prepareReservedPopup() {
        if (this.isReservedPopupAvailable()) {
            return;
        }

        var applicationHandler = this.getApplicationHandler();

        if (!this.dialog) {
            if (this.isReservedPopupAvailable(applicationHandler.popupWindow)) {
                this.popupWindow = applicationHandler.popupWindow;
                return;
            }
        }

        this.popupWindow = window.open(window.contextPath + "/pages/util/reservedpopup.html", "_blank", "width=200,height=20");
        if (!this.dialog) {
            applicationHandler.popupWindow = this.popupWindow;
        }
    }

    function postEvent(event) {
        // Prepare a blank window for possible launching actions
        this.prepareReservedPopup();

        // Locate the command transporter
        var commandTransporter = HtmlUtils.findWindowObject("commandTransporter");
        if (!commandTransporter) {
            alert(this.waitMessage);
            return;
        }

        // Cache the event if necessary
        if (this.isProcessingEvent) {
            this.eventQueue.enqueue(event);
            return;
        }

        // Disable user inputs
        RendererUtils.setBusy(true);

        // Post the event
        var paramsMap = (event.isMap ? event : event.toMap());
        paramsMap.put("mapLocator", this.mapLocator);

        commandTransporter.sendMessage(paramsMap);
    }

    function processReport(report) {
        // Process response, note in some cases the report object might be null
        for (var i = 0; report && i < report.instructions.size(); i++) {
            var instruction = report.instructions.get(i);

            var widget = window.document.getElementById(instruction.widgetId);

            if (widget && widget.renderer) {
                widget.renderer.actionId = report.actionId;
                widget.renderer.processInstructions(instruction.parameters);
                widget.renderer.actionId = null;
            }
        }

        // Process the queued events
        this.isProcessingEvent = false;
        if (!this.eventQueue.isEmpty()) {
            this.postEvent(this.eventQueue.dequeue());
        }

        // Enable all inputs
        if (!report || report.actionId != "jmsEvent") {
            RendererUtils.setBusy(false);
        }
    }

    function isReservedPopupAvailable(aPopupWindow) {
        var popupWindow = aPopupWindow || this.popupWindow;
        return (popupWindow && !popupWindow.closed && popupWindow.location.href.search(/reservedpopup\.html$/) != -1 ? true : false);
    }

    function finalize() {
        // Close those reserved popups
        if (this.isReservedPopupAvailable()) {
            if (this.dialog) {
                this.popupWindow.close();
            } else {
                // Reserve this to applicationHandler to be shared by siblings
                var applicationHandler = this.getApplicationHandler();
                applicationHandler.popupWindow = this.popupWindow;
            }
        }

        // Close the map or logout
        if (this.dialog && this.mapLocator != null) {
            var applicationHandler = this.getApplicationHandler();
            if (applicationHandler) {
                var mapTabs = applicationHandler.getMapTabs();
                mapTabs.closeMap(this.mapLocator);
            }
        }
    }

    function getApplicationWindow() {
        if (!this.dialog) {
            return window.top;
        }

        for (var openerWindow = window.top.opener; openerWindow && openerWindow.top.opener;) {
            openerWindow = openerWindow.top.opener;
        }

        var appWindow = (openerWindow ? openerWindow.top : null);
        if (appWindow != null && HtmlUtils.findWindowObject("applicationHandler", appWindow) == null) {
            appWindow = null;
        }

        return appWindow;
    }

    function getApplicationHandler() {
        var appWindow = this.getApplicationWindow();
        return (appWindow ? HtmlUtils.findWindowObject("applicationHandler", appWindow) : null);
    }
}