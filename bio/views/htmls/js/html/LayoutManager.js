function LayoutManager(canvasWidth, canvasHeight) {
    this.canvasWidth = canvasWidth;
    this.canvasHeight = canvasHeight;

    var TAGS_NOT_ELIGIBLE_FOR_SIZING = ["BUTTON", "IMG"];

    this.resizeEnabled = (HtmlUtils.findWindowObject("resizeEnabled") || false);

    this.isEligibleForResizingByTag = isEligibleForResizingByTag;
    this.isEligibleForHeightChange = isEligibleForHeightChange;
    this.isEligibleForWidthChange = isEligibleForWidthChange;

    this.setSize = setSize;
    this.resize = resize;
    this.translate = translate;
    this.getPixels = getPixels;

    function isEligibleForResizingByTag(element) {
        return (ArrayUtils.getElementIndex(TAGS_NOT_ELIGIBLE_FOR_SIZING, element.tagName, true) == -1);
    }

    function isEligibleForHeightChange(element) {
        var TAGS_NOT_ELIGIBLE_FOR_HEIGHT_CHANGE = ["TR", "TD"];

        if (!this.isEligibleForResizingByTag(element)) {
            return false;
        }

        if (ArrayUtils.getElementIndex(TAGS_NOT_ELIGIBLE_FOR_HEIGHT_CHANGE, element.tagName, true) != -1) {
            return false;
        }

        return true;
    }

    function isEligibleForWidthChange(element) {
        if (!this.isEligibleForResizingByTag(element)) {
            return false;
        }

        return true;
    }

    function setSize(elementId, width, height) {
        var element = window.document.getElementById(elementId);
        if (element) {
            element.style.width = width;
            element.style.height = height;
        }
    }

    function resize() {
        if (!this.resizeEnabled) {
            return;
        }

        var viewWidth = window.document.body.clientWidth;
        var viewHeight = window.document.body.clientHeight;

        for (var i = 0; i < document.all.length; i++) {
            var element = document.all[i];

            this.translate(element, "posLeft", this.canvasWidth, viewWidth);
            this.translate(element, "posTop", this.canvasHeight, viewHeight);

            if (this.isEligibleForWidthChange(element)) {
                this.translate(element, "posWidth", this.canvasWidth, viewWidth);
            }
            if (this.isEligibleForHeightChange(element)) {
                this.translate(element, "posHeight", this.canvasHeight, viewHeight);
            }

            var renderer = element.renderer;
            if (renderer && (typeof renderer.resized) == "function") {
                renderer.resized(element);
            }
        }
    }

    function translate(element, styleAttrName, baseScale, currentScale) {
        if (!element.style[styleAttrName]) {
            return;
        }

        var originalAttrName = "original_" + styleAttrName;

        if (!element.style[originalAttrName]) {
            element.style[originalAttrName] = element.style[styleAttrName];
        }

        var originalAttrValue = this.getPixels(element.style[originalAttrName]);
        var currentAttrValue = (originalAttrValue / baseScale) * currentScale;

        if (currentAttrValue < originalAttrValue) {
            currentAttrValue = originalAttrValue;
        }

        element.style[styleAttrName] = currentAttrValue;
    }

    function getPixels(anAttr) {
        var PIXEL = "px";

        var index = new String(anAttr).indexOf(PIXEL);
        if (index != -1) {
            anAttr = anAttr.substring(0, anAttr.length - PIXEL.length);
        }

        return anAttr;
    }
}
