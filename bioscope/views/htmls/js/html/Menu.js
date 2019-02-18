Menu.prototype = new TreeNode();

function Menu(name, parentNode, aMapClass) {
    this.base = TreeNode;
    this.base(name, parentNode);

    this.uiHolderName = "menu";
    this.rootNodeRefString = "window.menu";
    this.mapClass = aMapClass || HashMap;
    this.childNodesMap = new this.mapClass();
    
    this.expandImageURI = window.contextPath + "/htmls/images/menu_expand.gif";
    this.collapseImageURI = window.contextPath + "/htmls/images/menu_collapse.gif";
   
    this.retrieveName = retrieveName;
    this.addChildNode = addChildNode;
    this.addStringChildNode = addStringChildNode;
    this.getMapName = getMapName;
    this.getSelfHtml = getSelfHtml;
    this.getEventHandlerName = getEventHandlerName;

    function retrieveName(aPath) {
        var index = aPath.indexOf("/");
        return index == -1 ? aPath : aPath.substring(0, index);
    }

    function addChildNode(childNode) {
        if ((typeof childNode) == "string") {
            this.addStringChildNode(childNode);
        } else {
            Menu.prototype.addChildNode(childNode, this);
        }
    }

    function addStringChildNode(aPath) {
        if (aPath.indexOf("/") == 0) {
            aPath = aPath.substring(1);
        }

        var childName = this.retrieveName(aPath);
        var childNode = this.childNodesMap.get(childName);
        if (!childNode) {
            childNode = new Menu(childName, this, this.mapClass);
            this.addChildNode(childNode);
        }

        if (aPath != childName) {
            var subPath = aPath.substring(childName.length);
            childNode.addChildNode(subPath);
        }

        return childNode;
    }

    function getMapName() {
        return this.mapName || this.getPath();
    }

    function getSelfHtml() {
        if (this.isLeaf()) {
            return "<a nodePath='" + this.getPath() + "' href=\"javascript:" + this.getEventHandlerName() + "('" + this.getMapName() + "')" + ";void(0)\">"
                   + this.name
                   + "</a>";
        } else {
            return this.name;
        }
    }

    function getEventHandlerName() {
        var eventHandlerName = this.eventHandlerName;
        if (eventHandlerName) {
            return eventHandlerName;
        }

        if (this.parentNode) {
            return this.parentNode.getEventHandlerName();
        }

        return null;
    }
}