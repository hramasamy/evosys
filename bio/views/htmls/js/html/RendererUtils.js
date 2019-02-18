function RendererUtilsC() {
    this.getChildNodeByTagName = getChildNodeByTagName;
    this.getAllChildrenByTagName = getAllChildrenByTagName;
    this.getChildNodeWithProperty = getChildNodeWithProperty;
    this.getAllChildrenByRendererClass = getAllChildrenByRendererClass;

    this.getParentNodeByTagName = getParentNodeByTagName;
    this.getParentNodeBySnippetName = getParentNodeBySnippetName;
    this.getParentNodeByTagOrSnippetName = getParentNodeByTagOrSnippetName;

    this.isComponentVisible = isComponentVisible;
    this.setBusy = setBusy;
    this.setBusyCursor = setBusyCursor;
    this.fireEventForOtherListeners = fireEventForOtherListeners;

    this.debug = debug;

    function getChildNodeByTagName(component, tagName) {
        return this.getAllChildrenByTagName(component, tagName, true).get(0);
    }

    function getAllChildrenByTagName(component, tagName, firstOneOnly) {
        var result = new ArrayList();
        var childNodes = component.childNodes;
        var childNodeCount = childNodes.length;

        for (var i = 0; i < childNodeCount; i++) {
            var childNode = childNodes[i];
            if (childNode.tagName == tagName) {
                result.add(childNode);
                if (firstOneOnly) {
                    break;
                }
            }

            var grandChildNodes = this.getAllChildrenByTagName(childNode, tagName, firstOneOnly);
            if (grandChildNodes && !grandChildNodes.isEmpty()) {
                result.addAll(grandChildNodes);
                if (firstOneOnly) {
                    break;
                }
            }
        }

        return result;
    }

    function getChildNodeWithProperty(component, propertyName) {
        var childNodes = component.childNodes;
        var childNodeCount = childNodes.length;

        for (var i = 0; i < childNodeCount; i++) {
            var childNode = childNodes[i];
            if (childNode[propertyName]) {
                return childNode;
            } else {
                var grandChildNode = this.getChildNodeWithProperty(childNode, propertyName);
                if (grandChildNode) {
                    return grandChildNode;
                }
            }
        }

        return null;
    }

    function getAllChildrenByRendererClass(component, rendererClassName) {
        var result = new ArrayList();

        var childNodes = component.childNodes;
        var childNodesLength = childNodes.length;

        for (var i = 0; i < childNodesLength; i++) {
            var childNode = childNodes[i];
            var tagName = childNode.tagName;

            // For performance
            if (!tagName || tagName == "SCRIPT"
                || tagName == "TBODY" || tagName == "THEAD"
                || tagName == "TR" || tagName == "TH" || tagName == "TD") {
                continue;
            }

            var rendererClass = childNode.rendererClass;

            if (rendererClass && (!rendererClassName || rendererClass == rendererClassName)) {
                result.add(childNode);
            }

            result.addAll(this.getAllChildrenByRendererClass(childNode, rendererClassName));
        }

        return result;
    }

    function getParentNodeByTagName(component, tagName) {
        return this.getParentNodeByTagOrSnippetName(component, tagName, true);
    }

    function getParentNodeBySnippetName(component, snippetName) {
        return this.getParentNodeByTagOrSnippetName(component, snippetName, false);
    }

    function getParentNodeByTagOrSnippetName(component, tagOrSnippetName, isByTagName) {
        var parentNode = component.parentNode;
        while (parentNode) {
            if ((isByTagName && parentNode.tagName == tagOrSnippetName) || (!isByTagName && parentNode.snippetName == tagOrSnippetName)) {
                return parentNode;
            }

            parentNode = parentNode.parentNode;
        }

        return null;
    }

    function isComponentVisible(component) {
        for (var parentNode = component; parentNode; parentNode = parentNode.parentNode) {
            if (parentNode.style && (parentNode.style.visibility == "hidden" || parentNode.style.display == "none")) {
                return false;
            }
        }

        return true;
    }

    function setBusy(busyFlag) {
        this.setBusyCursor(window.document.body, busyFlag);

        var elements = window.document.all;
        var elementCount = elements.length;

        for (var i = 0; i < elementCount; i++) {
            this.setBusyCursor(elements[i], busyFlag);
        }
    }

    function setBusyCursor(element, busyFlag) {
        if (!element.style) {
            return;
        }

        var cursor = element.style.cursor;

        if (!element.style.originalCursor) {
            element.style.originalCursor = (cursor ? cursor : "auto");
        }

        element.style.cursor = (busyFlag ? "wait" : element.style.originalCursor);
    }

    function fireEventForOtherListeners(element, eventName) {
        var oldEventHandler = element[eventName];
        if (oldEventHandler) {
            element[eventName] = new Function("void(0)");
            element.fireEvent(eventName);
            element[eventName] = oldEventHandler;
        }
    }

    function debug(info, overwrite) {
        HtmlUtils.findWindowObject("historyHandler").debug(info, overwrite);
    }
}

var RendererUtils = new RendererUtilsC();