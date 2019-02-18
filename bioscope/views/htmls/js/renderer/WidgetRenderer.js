function WidgetRenderer(widget) {
    this.widget = widget;
    this.aliases = new HashMap();

    this.getComponent = getComponent;
    this.getParentRenderer = getParentRenderer;
    this.getCanvasRenderer = getCanvasRenderer;
    this.processInstructions = processInstructions;
    this.findValidAttrName = findValidAttrName;
    this.setEnabled = setEnabled;
    this.propagateZIndex = propagateZIndex;
    this.getEvalStringRef = getEvalStringRef;
    this.toString = toString;

    function getComponent(componentId) {
        var component = window.document.getElementById(this.widget.id + "_" + componentId);
        if (component) {
            component.renderer = this;
        }
        return component;
    }

    function getParentRenderer(snippetName) {
        var parentNode = RendererUtils.getParentNodeBySnippetName(this.widget, snippetName);
        return (parentNode ? parentNode.renderer : null);
    }

    function getCanvasRenderer() {
        return this.getParentRenderer("canvas");
    }

    function processInstructions(parameters) {
        for (var i = 0; i < parameters.size(); i++) {
            var name = parameters.keys[i];
            var value = parameters.values[i];

            var nameWithFirstCapital = name.charAt(0).toUpperCase() + name.substring(1);

            var processFuncName = "process" + nameWithFirstCapital;
            if ((typeof this[processFuncName]) == "function") {
                this[processFuncName](value);
                continue;
            }

            var funcName = "set" + nameWithFirstCapital;
            if ((typeof this[funcName]) == "function") {
                this[funcName](value);
            } else {
                if (name == "value") {
                    HtmlUtils.setValue(this.widget, value);
                    RendererUtils.fireEventForOtherListeners(this.widget, "onchange");
                } else {
                    var attrName = this.findValidAttrName(name);
                    if (attrName) {
                        this.widget[attrName] = value;
                    }
                }
            }
        }
    }

    function findValidAttrName(name, anInstance) {
        var theInstance = anInstance || this;

        if (theInstance.aliases.containsKey(name)) {
            return theInstance.aliases.get(name);
        } else if (name == "type" || (typeof theInstance.widget[name]) == "undefined") {
            return null;
        } else {
            return name;
        }
    }

    function propagateZIndex(aComponent) {
        var component = aComponent || this.widget;

        if (!WidgetRenderer.singleton.currentZIndex) {
            WidgetRenderer.singleton.currentZIndex = 100;
        }
        var zIndex = WidgetRenderer.singleton.currentZIndex--;

        for (var parentNode = component; parentNode; parentNode = parentNode.parentNode) {
            if (parentNode.style && parentNode.style.zIndex <= zIndex) {
                parentNode.style.zIndex = zIndex + 1;
            }
        }
    }

    function setEnabled(aFlag) {
        HtmlUtils.setEnabled(this.widget, eval(aFlag));
    }

    function getEvalStringRef() {
        return "window.document.getElementById('" + this.widget.id + "').renderer";
    }

    function toString() {
        return "WidgetRenderer"
    }
}

WidgetRenderer.singleton = new WidgetRenderer();