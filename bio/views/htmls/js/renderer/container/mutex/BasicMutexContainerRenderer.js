function BasicMutexContainerRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.initializeTabsAndChildren = initializeTabsAndChildren;
    this.initializeTabStyle = initializeTabStyle;

    this.setTabName = setTabName;
    this.valueChanged = valueChanged;

    function initialize(anInstance) {
        var theInstance = anInstance || this;

        theInstance.tabsHolder = theInstance.getComponent("tabsHolder");
        theInstance.widget.childCount = window.parseInt(theInstance.widget.childCount);

        // Initialize tabs and children
        theInstance.initializeTabsAndChildren();
    }

    function initializeTabsAndChildren(anInstance) {
        var theInstance = anInstance || this;

        theInstance.tabs = new ArrayList();
        theInstance.children = new ArrayList();

        for (var i = 0; i < theInstance.widget.childCount; i++) {
            if (!window.TabPositions || theInstance.widget.tabPosition != TabPositions.NONE) {
                var tab = theInstance.getComponent("tab" + i);
                if (tab) {
                    tab.onclick = new Function("this.renderer.valueChanged(this)");
                    this.initializeTabStyle(tab);
                    theInstance.tabs.add(tab);
                }
            }

            var child = theInstance.getComponent("child" + i);
            theInstance.children.add(child);
        }
    }

    function initializeTabStyle(tab) {
        if (tab.tabName == this.widget.tabName) {
            tab.className = "label";
            tab.style.textDecoration = "none";
        } else {
            tab.className = "bodytext";
            tab.style.textDecoration = "underline";
        }
    }

    function setTabName(tabName, anInstance) {
        var theInstance = anInstance || this;

        // For performance
        if (theInstance.widget.tabName == tabName) {
            return;
        }

        // Update widget tabName
        theInstance.widget.tabName = tabName;

        // Update tabs
        for (var i = 0; i < theInstance.widget.childCount; i++) {
            var child = theInstance.children.get(i);

            var selected = (child.tabName == tabName);

            if (!window.TabPositions || theInstance.widget.tabPosition != TabPositions.NONE) {
                var tab = theInstance.tabs.get(i);
                theInstance.initializeTabStyle(tab);
            }

            child.style.display = (selected ? "inline" : "none");
        }
    }

    function valueChanged(aTab) {
        this.setTabName(aTab.tabName);
    }
}