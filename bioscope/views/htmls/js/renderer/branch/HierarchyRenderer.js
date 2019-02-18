function HierarchyRenderer(widget) {
    var SELECTED_COLOR = "yellow";

    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.setValue = setValue;
    this.buildTree = buildTree;
    this.updateUI = updateUI;
    this.initializeMenu = initializeMenu;
    this.keyPressed = keyPressed;
    this.nodeClicked = nodeClicked;
    this.editSelectedNode = editSelectedNode;
    this.nodeNameChanged = nodeNameChanged;
    this.setCurrent = setCurrent;
    this.validateCollapsed = validateCollapsed;
    this.getSelectedNode = getSelectedNode;
    this.addNode = addNode;
    this.removeNode = removeNode;
    this.nodeMoved = nodeMoved;

    function initialize() {
        this.widget.iconName = this.widget.iconName || "instanceNode.gif";
        this.widget.allowMultipleRoots = window.eval(this.widget.allowMultipleRoots);
        this.widget.allowAdd = window.eval(this.widget.allowAdd);
        this.widget.allowDelete = window.eval(this.widget.allowDelete);
        this.widget.allowRename = window.eval(this.widget.allowRename);
        this.widget.allowReparent = window.eval(this.widget.allowReparent);
        this.widget.allowCopy = window.eval(this.widget.allowCopy);
        this.widget.current = window.parseInt(this.widget.current);

        this.setValue(this.widget.value);
        this.initializeMenu();

        this.widget.onkeyup = new Function("this.renderer.keyPressed()");
    }

    function setValue(valueString) {
        valueString = StringUtils.trim(valueString, "[[");
        valueString = StringUtils.trim(valueString, "]]");

        this.nodesMap = new HashMap();

        var branchData = ArrayUtils.getTokens(valueString, "]" + FIELD_DELIMITER_0 + "[");
        for (var i = 0; i < branchData.length; i++) {
            var rowData = ArrayUtils.getTokens(branchData[i]);

            var key = rowData[0];
            var displayName = rowData[1];
            var ref = rowData[2];
            var position = rowData[3];

            var node = this.nodesMap.get(key);
            if (!node) {
                node = new HierarchyNode(key, displayName, ref, position, this.widget.iconName);
                this.nodesMap.put(node.key, node);
            }
        }

        this.buildTree();
        this.validateCollapsed();

        this.updateUI();
    }

    function buildTree() {
        var artificialRootNode = new HierarchyNode(null, this.widget.artificialRootName, null, -1, this.widget.iconName);

        // For now, we support single root node only
        for (var i = 0; i < this.nodesMap.size(); i++) {
            var node = this.nodesMap.values[i];
            var parentNode = node.ref ? this.nodesMap.get(node.ref) : artificialRootNode;

            if (node != this.rootNode && parentNode) {
                parentNode.addChildNode(node);
            }
        }

        // Multiple root nodes support
        if (this.widget.allowMultipleRoots) {
            this.rootNode = new HierarchyNode();
            this.rootNode.addChildNode(artificialRootNode);
            this.nodesMap.put(artificialRootNode.key, artificialRootNode);
        } else {
            this.rootNode = artificialRootNode;
        }

        this.rootNode.renderer = this;
        this.rootNode.rootNodeRefString = this.getEvalStringRef() + ".rootNode";
        this.rootNode.uiHolderName = this.widget.id;
    }

    function updateUI(pageLoading) {
        this.widget.innerHTML = this.rootNode.toHtmlString();

        this.spans = RendererUtils.getAllChildrenByTagName(this.widget, "SPAN");
        for (var i = 0; i < this.spans.size(); i++) {
            var span = this.spans.get(i);
            span.renderer = this;
            span.node = this.rootNode.findChild(span.nodePath);
            span.node.span = span;
            span.onclick = new Function("this.renderer.nodeClicked(this.node.position)");
            span.oncontextmenu = new Function("if (this.renderer.menuHelper) this.renderer.menuHelper.show(this.renderer.addRemoveMenu, null, true);return false;");

            // Selection
            if (span.node.position == this.widget.current) {
                span.style.backgroundColor = SELECTED_COLOR;
            }

            // Drag and drop
            if (this.widget.allowReparent) {
                var img = RendererUtils.getChildNodeByTagName(span, "IMG");
                img.span = span;
                img.ondragstart = new Function("this.span.renderer.dragSrcNode = this.span.node;");
                img.ondragover = new Function("window.event.returnValue = false;");
                img.ondrop = new Function("this.span.renderer.nodeMoved(this.span.node)");
            }
        }
    }

    function initializeMenu() {
        if (!this.widget.allowAdd && !this.widget.allowDelete) {
            return;
        }

        this.menuHelper = new FloatMenuHelper(this);
        this.menuHelper.initialize("addRemoveMenu");

        var tableWrapper = RendererUtils.getParentNodeByTagName(this.widget, "TABLE");
        tableWrapper.renderer = this;
        tableWrapper.onclick = new Function("this.renderer.addRemoveMenu.style.display = 'none'");
    }

    function keyPressed() {
        if (window.event.keyCode == 113) {
            // F2 pressed
            this.editSelectedNode();
        } else if (window.event.keyCode == 13) {
            var selectedNode = this.getSelectedNode();

            // Stop edit the node
            if (selectedNode && selectedNode.span) {
                var inputNode = RendererUtils.getChildNodeByTagName(selectedNode.span, "INPUT");
                if (inputNode) {
                    inputNode.blur();
                }
            }

        }
    }

    function nodeClicked(position) {
        if (this.widget.current == position) {
            // Deselect if ctrl key is pressed
            if (window.event.ctrlKey) {
                var selectedNode = this.getSelectedNode();
                span = selectedNode.span;
                span.style.backgroundColor = "white";
                this.widget.current = -2;
                this.updateUI();
                return;
            }

            // Edit the node
            this.editSelectedNode();
        } else {
            this.setCurrent(position, true);

            if (this.widget.current >= 0) {
                var widgetEvent = new WidgetEvent(this.widget, "setCurrent");
                widgetEvent.set("_current", position, "java.lang.Integer"); // Update the current cache value
                widgetEvent.set("value", position, "java.lang.Integer");
                widgetEvent.post();
            }
        }
    }

    function editSelectedNode() {
        if (!this.widget.allowRename) {
            return;
        }

        var selectedNode = this.getSelectedNode();

        // Edit the node
        if (selectedNode && selectedNode.collapsed) {
            span = selectedNode.span;
            span.innerHTML = "<input>";
            var inputNode = RendererUtils.getChildNodeByTagName(span, "INPUT");
            inputNode.span = span;
            inputNode.value = selectedNode.displayName;
            inputNode.onblur = new Function("this.span.renderer.nodeNameChanged(this)");
            inputNode.select();
            inputNode.focus();
        }
    }

    function nodeNameChanged(spanInputNode) {
        var span = spanInputNode.span;
        var node = span.node;
        var nameChanged = node.displayName != spanInputNode.value;

        node.displayName = spanInputNode.value
        span.innerHTML = node.displayName;
        span.style.backgroundColor = SELECTED_COLOR;

        if (nameChanged) {
            var widgetEvent = new WidgetEvent(this.widget, "setValueAt");
            widgetEvent.set("value", node.displayName, "com.cogencysoft.yard.qube.leaf.Leaf[java.lang.Object]");
            widgetEvent.set("position", node.position, "java.lang.Integer");
            widgetEvent.post();
        }
    }

    function setCurrent(position, noUIUpdate) {
        this.widget.current = position;

        for (var i = 0; i < this.spans.size(); i++) {
            var span = this.spans.get(i);
            var node = span.node;

            span.style.backgroundColor = (position == node.position ? SELECTED_COLOR : "white");
        }

        if (!noUIUpdate) {
            this.validateCollapsed();
            this.updateUI();
        }
    }

    function validateCollapsed() {
        var selectedNode = this.getSelectedNode();

        if (selectedNode) {
            var node = selectedNode.parentNode;
            while (node) {
                node.collapsed = false;
                node = node.parentNode;
            }
        }
    }

    function getSelectedNode() {
        for (var i = 0; i < this.nodesMap.size(); i++) {
            var node = this.nodesMap.values[i];
            if (node.position == this.widget.current) {
                return node;
            }
        }

        return null;
    }

    function addNode() {
        if (!this.widget.allowAdd) {
            window.alert("Add not allowed");
            return;
        }

        var selectedNode = this.getSelectedNode();

        var widgetEvent = new WidgetEvent(this.widget, "addRow");
        widgetEvent.set("parent", selectedNode.key + "," + selectedNode.displayName + "," + selectedNode.ref, "com.cogencysoft.deliver.widget.hierarchy.HierarchyWidgetNode[java.lang.String;java.lang.String;java.lang.String]");
        widgetEvent.set("isRoot", selectedNode.position < 0, "java.lang.Boolean");
        widgetEvent.post();

        this.addRemoveMenu.style.display = "none";
    }

    function removeNode() {
        if (!this.widget.allowDelete) {
            window.alert("Delete not allowed");
            return;
        }

        var widgetEvent = new WidgetEvent(this.widget, "deleteRow");
        widgetEvent.set("position", this.widget.current, "java.lang.Integer");
        widgetEvent.post();

        this.addRemoveMenu.style.display = "none";
    }

    function nodeMoved(targetNode) {
        var srcNode = this.dragSrcNode;
        this.dragSrcNode = null;

        if (!srcNode || srcNode == targetNode) {
            return;
        }

        var widgetEvent = new WidgetEvent(this.widget, "setParent");
        widgetEvent.set("position", srcNode.position, "java.lang.Integer");
        widgetEvent.set("parent", targetNode.key);
        widgetEvent.post();
    }
}

HierarchyNode.prototype = new TreeNode();

function HierarchyNode(key, displayName, ref, position, iconName) {
    this.base = TreeNode;
    this.base(key, null);

    this.key = key;
    this.displayName = displayName;
    this.ref = ref;
    this.position = position;
    this.iconName = iconName;

    this.getSelfHtml = getSelfHtml;
    this.getIndentationSpaces = getIndentationSpaces;
    this.updateUI = updateUI;

    function getSelfHtml() {
        return "<span nodePath='" + this.getPath() + "' style='cursor:default'>"
               + "<img src='" + window.contextPath + "/pages/images/" + this.iconName + "' width='14' height='14' border='0' align='absmiddle'>"
               + this.displayName
               + "</span>";
    }

    function getIndentationSpaces() {
        return "&nbsp;&nbsp;&nbsp;&nbsp;";
    }

    function updateUI() {
        HierarchyNode.prototype.updateUI(this);
        this.renderer.updateUI();
    }
}