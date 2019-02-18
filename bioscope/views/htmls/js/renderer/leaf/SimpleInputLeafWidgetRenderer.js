function SimpleInputLeafWidgetRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.setDefaultBorderStyles = setDefaultBorderStyles;
    this.setDefaultBorderStyle = setDefaultBorderStyle;
    this.initializeSize = initializeSize;
    this.initializeLink = initializeLink;

    function initialize(anInstance) {
        var theInstance = anInstance || this;

        // Set default border styles
        theInstance.setDefaultBorderStyles();

        // Adjust the length
        theInstance.initializeSize();

        // Special hanlding for link
        theInstance.initializeLink();
    }

    function setDefaultBorderStyles() {
        this.setDefaultBorderStyle("left");
        this.setDefaultBorderStyle("top");
        this.setDefaultBorderStyle("right");
        this.setDefaultBorderStyle("bottom");
    }

    function setDefaultBorderStyle(borderName) {
        borderName = borderName.charAt(0).toUpperCase() + borderName.substring(1);

        this.widget.style["border" + borderName + "Color"] = "#afb7d1";
        this.widget.style["border" + borderName + "Width"] = 1;
        this.widget.style["border" + borderName + "Style"] = "solid";
    }

    function initializeSize(anInstance) {
        var theInstance = anInstance || this;

        if (!RendererUtils.isComponentVisible(theInstance.widget) || theInstance.widget.sizeValidated) {
            return false;
        }

        theInstance.widget.widgetWidth = RendererUtils.getParentNodeByTagName(theInstance.widget, "TABLE").widgetWidth;

        // Calculate the width adjustment
        if (!theInstance.widget.widgetWidth) {
            return false;
        }

        // Widget siblings width
        theInstance.widget.siblingsWidth = 0;
        var nextSibling = theInstance.widget.nextSibling;
        while (nextSibling) {
            if ((!nextSibling.style || nextSibling.style.display != "none") && (nextSibling.width || (nextSibling.style && nextSibling.style.posWidth))) {
                theInstance.widget.siblingsWidth += (nextSibling.width || nextSibling.style.posWidth);
            }

            nextSibling = nextSibling.nextSibling;
        }

        // Now adjust the width
        theInstance.widget.style.posWidth = theInstance.widget.widgetWidth - RendererUtils.getParentNodeByTagName(theInstance.widget, "TD").offsetLeft - theInstance.widget.siblingsWidth;

        // Set the flag
        theInstance.widget.sizeValidated = true;

        return true;
    }

    function initializeLink() {
        this.widget.isLink = (this.widget.isLink == "true");
        if (!this.widget.isLink) {
            return;
        }

        var linkHolder = this.getComponent("link");
        // Position the link image
        if (linkHolder.style.display == "none") {
            linkHolder.style.posLeft = this.widget.widgetWidth - this.widget.siblingsWidth - linkHolder.style.posWidth;
        }
        linkHolder.onclick = new Function("new WidgetEvent(this.renderer.widget, 'setLinkClicked').post()");
        linkHolder.style.display = "inline";
    }
}