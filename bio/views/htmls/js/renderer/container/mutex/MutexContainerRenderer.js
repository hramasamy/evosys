MutexContainerRenderer.prototype = new BasicMutexContainerRenderer();

function MutexContainerRenderer(widget) {
    this.base = BasicMutexContainerRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.initializeSize = initializeSize;
    this.valueChanged = valueChanged;
    this.setTabName = setTabName;
    this.getTabNames = getTabNames;
    this.setVisible = setVisible;
    this.setEnabled = setEnabled;
    this.findTabNamesOrIndexesParam = findTabNamesOrIndexesParam;
    this.enableTab = enableTab
    this.findNextEnabledTab = findNextEnabledTab;
    this.findEnabledTab = findEnabledTab;

    function initialize() {
        MutexContainerRenderer.prototype.initialize(this);

        this.widget.widgetWidth = window.parseInt(this.widget.widgetWidth);
        this.widget.widgetHeight = window.parseInt(this.widget.widgetHeight);

        // Size of the client area
        this.initializeSize();
    }

    function initializeSize() {
        for (var i = 0; i < this.widget.childCount; i++) {
            var clientArea = this.getComponent("clientArea" + i);
            if (clientArea.sizeValidated || !RendererUtils.isComponentVisible(clientArea)) {
                continue;
            }

            // Client area size
            if (this.widget.tabPosition == TabPositions.TOP || this.widget.tabPosition == TabPositions.BOTTOM) {
                clientArea.style.posHeight = this.widget.widgetHeight - this.tabsHolder.offsetHeight - 2;
            } else if (this.widget.tabPosition == TabPositions.LEFT || this.widget.tabPosition == TabPositions.RIGHT) {
                clientArea.style.posWidth = this.widget.widgetWidth - this.tabsHolder.offsetWidth - 2;
            }

            // Restrict child group sizes
            var immediateChild = clientArea.childNodes[0];
            if (immediateChild && immediateChild.borderedgroup == "true") {
                immediateChild.style.posWidth = clientArea.style.posWidth;
                immediateChild.style.posHeight = clientArea.style.posHeight;
            }

            // Set the sized flag
            clientArea.sizeValidated = true;

            // Take care of the children mutexes too
            var childWidgets = RendererUtils.getAllChildrenByRendererClass(clientArea);
            for (var i = 0; i < childWidgets.size(); i++) {
                var renderer = childWidgets.get(i).renderer;
                if (renderer && (typeof renderer.initializeSize) == "function") {
                    renderer.initializeSize();
                }
            }
        }
    }

    function setVisible(visibleStr) {
        var tabName = StringUtils.getParameter(visibleStr, "tabName");
        this.setTabName(tabName);
    }

    function setEnabled(parametersStr) {
        var tabNamesObj = this.findTabNamesOrIndexesParam(parametersStr, "tabName");
        if (tabNamesObj == null) {
            var tabIndexesObj = this.findTabNamesOrIndexesParam(parametersStr, "tabNumber");
            if (tabIndexesObj != null) {
                tabNamesObj = new Array();
                for (var i = 0; i < tabIndexesObj.length; i++) {
                    var index = parseInt(tabIndexesObj[i]);
                    tabNamesObj[i] = this.tabs.get(index).tabName;
                }
            }
        }

        var enabled = eval(StringUtils.getParameter(parametersStr, "enabled"));

        if (tabNamesObj == null) {
            enabled = eval(parametersStr);
        }

        // Populate the right tab names
        var tabNames = null;
        if (tabNamesObj == null) {
            tabNames = this.getTabNames();
        } else {
            tabNames = new ArrayList();
            for (var i = 0; i < tabNamesObj.length; i++) {
                tabNames.add(tabNamesObj[i]);
            }
        }

        // Enable tabs
        for (var i = 0; i < tabNames.size(); i++) {
            this.enableTab(tabNames.get(i), enabled);
        }
    }

    function findTabNamesOrIndexesParam(parametersStr, paramName) {
        var result = StringUtils.getParameter(parametersStr, paramName);
        if (result != null) {
            if (parametersStr.indexOf("]") != -1) {
                result = StringUtils.getParameter(parametersStr, paramName, ["]"]).substring(1);
                result = ArrayUtils.getTokens(result);
            }
        }

        if ((typeof result) == "string") {
            result = new Array(result);
        }

        return result;
    }

    function enableTab(tabName, enabled) {
        for (var i = 0; i < this.tabs.size(); i++) {
            var tab = this.tabs.get(i);
            if (tab.tabName == tabName) {
                tab.isEnabled = enabled;
                tab.style.display = (enabled ? "inline" : "none");

                var nextEnabledTab = this.findNextEnabledTab(i);
                this.setTabName(nextEnabledTab != null ? nextEnabledTab.tabName : null);

                break;
            }
        }
    }

    function findNextEnabledTab(currentTabIndex) {
        var enabledTab = this.findEnabledTab(currentTabIndex, this.tabs.size());
        if (enabledTab == null) {
            enabledTab = this.findEnabledTab(0, currentTabIndex);
        }

        return enabledTab;
    }

    function findEnabledTab(startIndex, endIndex) {
        for (var i = startIndex; i < endIndex; i++) {
            var tab = this.tabs.get(i);

            if (tab.isEnabled != false) {
                return tab;
            }
        }

        return null;
    }

    function getTabNames() {
        var tabNames = new ArrayList();
        for (var i = 0; i < this.tabs.size(); i++) {
            tabNames.add(this.tabs.get(i).tabName);
        }

        return tabNames;
    }

    function setTabName(tabName) {
        if (tabName && this.widget.tabName != tabName && this.getTabNames().contains(tabName)) {
            MutexContainerRenderer.prototype.setTabName(tabName, this);
            this.initializeSize();
        }
    }

    function valueChanged(tab) {
        var tabName = tab.tabName;

        if (this.widget.tabName == tabName) {
            return;
        }

        this.setTabName(tabName);

        // Send event
        var widgetEvent = new WidgetEvent(this.widget, "setVisible");
        widgetEvent.set("_tabName", tabName);
        widgetEvent.post();
    }
}