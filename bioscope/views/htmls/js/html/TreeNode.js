function TreeNode(name, parentNode) {
    this.name = name;
    this.parentNode = parentNode;

    this.expandImageURI = window.contextPath + "/htmls/images/menu_expand.gif";
    this.collapseImageURI = window.contextPath + "/htmls/images/menu_collapse.gif";
    this.collapsed = true;

    this.isRoot = isRoot;
    this.isLeaf = isLeaf;
    this.getPath = getPath;
    this.getRoot = getRoot;
    this.getChildren = getChildren;
    this.findChild = findChild;
    this.addChildNode = addChildNode;
    this.expand = expand;
    this.collapse = collapse;
    this.updateUI = updateUI;
    this.getExpandCollapseHtml = getExpandCollapseHtml;
    this.getCurrentNodeRefString = getCurrentNodeRefString;
    this.toHtmlString = toHtmlString;
    this.getIndentationSpaces = getIndentationSpaces;
    this.toString = toString;

    function isRoot() {
        return this.parentNode == null;
    }

    function isLeaf() {
        return this.getChildren().isEmpty();
    }

    function getPath() {
        var result = "";

        for(var childNode = this; childNode && !childNode.isRoot(); childNode = childNode.parentNode) {
            result = "/" + childNode.name + result;
        }

        return result.substring(1);
    }

    function getRoot() {
        var node = this;
        while (!node.isRoot()) {
            node = node.parentNode;
        }

        return node;
    }

    function getChildren() {
        return new ArrayList(this.childNodesMap ? this.childNodesMap.values : null);
    }

    function findChild(aPath) {
        if (this.getPath() == aPath) {
            return this;
        }

        var children = this.getChildren();

        for (var i = 0; i < children.size(); i++) {
            var child = children.get(i);
            var result = child.findChild(aPath);
            if (result) {
                return result;
            }
        }

        return null;
    }

    function addChildNode(childNode, anInstance) {
        var theInstance = anInstance || this;

        if (!theInstance.childNodesMap) {
            theInstance.childNodesMap = new HashMap();
        }

        childNode.parentNode = theInstance;
        theInstance.childNodesMap.put(childNode.name, childNode);
    }

    function expand() {
        this.collapsed = false;
        this.getRoot().updateUI();
    }

    function collapse() {
        this.collapsed = true;
        this.getRoot().updateUI();
    }

    function updateUI(anInstance) {
        var theInstance = anInstance || this;
		
        var element = window.document.getElementById(theInstance.getRoot().uiHolderName);
        if (!element) {
            alert("No tree hodler defined in the page");
        } else {
            element.innerHTML = theInstance.toHtmlString();
        }
    }

    function getExpandCollapseHtml(anInstance) {
        var theInstance = anInstance || this;

        var commandImageURI = theInstance.collapsed ? theInstance.expandImageURI : theInstance.collapseImageURI;
        var commandMethod = theInstance.collapsed ? "expand()" : "collapse()";

        return "<img src='" + commandImageURI + "'"
               + " style='cursor:hand'"
               + " onclick=\"" + theInstance.getCurrentNodeRefString() + "." + commandMethod + "\""
               + ">";
    }

    function getCurrentNodeRefString() {
        return this.getRoot().rootNodeRefString + ".findChild('" + this.getPath() + "')";
    }

    function toHtmlString(aLevel) {
        // Generate the hyper link if no children
        if (this.isLeaf()) {
            return this.getIndentationSpaces() + this.getSelfHtml()
        }

        // Process non-action menu items
        var level = aLevel || 0;
        var result = "";

        if (!this.isRoot()) {
            var result = this.getExpandCollapseHtml() + " " + this.getSelfHtml();

            if (this.collapsed) {
                return result;
            }
        }

        // Process contained menu items
        var children = this.getChildren();
        for (var i = 0; i < children.size(); i++) {
            var child = children.get(i);
            if (i > 0 || !this.isRoot()) {
                // Add the block intent
                result += "<br>";
                for (var j = 0; j < level; j++) {
                    result += this.getIndentationSpaces();
                }
            }

            result += child.toHtmlString(level + 1);
        }

        return result;
    }

    function getIndentationSpaces() {
        return "&nbsp;&nbsp;";
    }

    function toString() {
        return this.getPath();
    }
}