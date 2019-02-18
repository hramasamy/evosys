CommandTransporter.prototype = new MessageTransporter();

function CommandTransporter(name) {
    this.base = MessageTransporter;
    this.base(name);

    this.sendMessage = sendMessage;
    this.launchMap = launchMap;
    this.isFromWebServer = isFromWebServer;
    this.isFromAppServers = isFromAppServers;
    this.launchUrl = launchUrl;
    this.isAttach = isAttach;
    this.openPopup = openPopup;
    this.parseUrl = parseUrl;
    this.validateLaunchingUrl = validateLaunchingUrl;
    this.onLoad = onLoad;

    function onLoad() {
        // Default action
        CommandTransporter.prototype.onLoad(this);

        // Launch url actions
        if (window.report && window.report.launchingUrl) {
            this.launchUrl(window.report.launchingUrl);
        }

        // Close window if necessary
        if (this.isClosing) {
            var mapActionHandler = this.getMapActionHandler();
            if (mapActionHandler) {
                mapActionHandler.mapLocator = null;
            }
            window.top.close();
        }

        // Update history
        var historyHandler = HtmlUtils.findWindowObject("historyHandler");
        if (historyHandler) {
            historyHandler.setNotifications(HtmlUtils.getValue("notifications"));
        }
    }

    function launchMap(mapLocator, dialog) {
        // Need to locate mapTabs first
        var mapTabs = HtmlUtils.findWindowObject("mapTabs");
        if (!mapTabs) {
            return;
        }

        // Popup map window
        var mapUrl = mapTabs.getOpenMapUrl("mapLocator=" + mapLocator);
        if (!dialog || !this.openPopup(mapUrl)) {
            // Go through mapTabs
            mapTabs.openMapByLocator(mapLocator);
        }
    }

    function openPopup(url, width, height) {
        var mapActionHandler = this.getMapActionHandler();
        if (mapActionHandler.isReservedPopupAvailable()) {
            mapActionHandler.popupWindow.location.href = url;
            if (width && height) {
                mapActionHandler.popupWindow.resizeTo(width, height);
            }
            mapActionHandler.popupWindow.focus();
            mapActionHandler.popupWindow = null;
            return true;
        }

        return false;
    }

    function sendMessage(parameters) {
        var requestHTML = "";
        var requestForm = window.document.forms[0];

        if (!parameters.containsKey("userAction")) {
            parameters.put("userAction", "postCommand");
        }

        for (var i = 0; i < parameters.size(); i++) {
            var name = parameters.keys[i];
            var value = parameters.values[i];

            // Encode special characters
            if ((typeof value) == "string") {
                value = value.replace(/\"/g, "&quot;");
            }

            requestHTML += "<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\">";
        }

        if (parameters.target) {
            requestForm.target = parameters.target;
        } else {
            requestForm.target = "_self";
        }

        if (!requestForm.originalInnerHTML) {
            requestForm.originalInnerHTML = requestForm.innerHTML;
        }

        requestForm.innerHTML = requestForm.originalInnerHTML + requestHTML;
        requestForm.submit();
    }

    function parseUrl(url) {
        var urlParam = new Object();

        if (this.isFromWebServer(url)) {
            if (url.indexOf(window.contextPath) != 0) {
                url = window.contextPath + url;
            }

            urlParam.codebase = window.contextPath;
            urlParam.targetFileUrl = url.substring(window.contextPath.length);
            urlParam.bridgeFileUrl = "/pages/util/launch.jsp";
        } else {
            var index = 0;

            index = url.indexOf("/", url.indexOf("//") + 2);
            index = url.indexOf("/", index + 1);

            urlParam.codebase = url.substring(0, index);
            urlParam.targetFileUrl = url.substring(index);
            urlParam.bridgeFileUrl = "/util/launch.jsp";
        }

        return urlParam;
    }

    function isFromWebServer(url) {
        return (url.indexOf("/") == 0);
    }

    function isFromAppServers(url) {
        return (this.isFromWebServer(url) || url.indexOf(window.contextPath) != -1);
    }

    function launchUrl(launchingUrl) {
        launchingUrl = this.validateLaunchingUrl(launchingUrl);

        if (this.isAttach(launchingUrl) || (!this.openPopup(launchingUrl, 800, 600) && this.isFromAppServers(launchingUrl))) {
            // Make sure the attach property is present
            if (!this.isAttach(launchingUrl)) {
                launchingUrl += "&attach=true";
            }

            // Launch url through the hidden frame
            var clientLauncher = window.document.frames["clientLauncher"];
            if (clientLauncher) {
                clientLauncher.location.href = launchingUrl;
                return;
            }
        }
    }

    function validateLaunchingUrl(launchingUrl) {
        // A normal url
        if (!this.isFromAppServers(launchingUrl)) {
            return launchingUrl;
        }

        // No bridge exists, we only accept the bridge
        if (launchingUrl.indexOf("file=") == -1) {
            var urlParam = this.parseUrl(launchingUrl);
            launchingUrl = urlParam.codebase + urlParam.bridgeFileUrl + "?file=" + urlParam.targetFileUrl;
        }

        return launchingUrl;
    }

    function isAttach(launchingUrl) {
        return (StringUtils.getParameter(launchingUrl, "attach") == "true");
    }
}