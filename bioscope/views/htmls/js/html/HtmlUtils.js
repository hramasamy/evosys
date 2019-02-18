function HtmlUtilsC() {
    this.enableAllInputs = enableAllInputs;
    this.disableElement = disableElement;
    this.enableElement = enableElement;
    this.setEnabled = setEnabled;
    this.setOriginalProperty = setOriginalProperty;
    this.getOriginalProperty = getOriginalProperty;
    this.findWindow = findWindow;
    this.findWindowObject = findWindowObject;
    this._findWindowObject = _findWindowObject;
    this.encodeWithTimestamp = encodeWithTimestamp;
    this.getValue = getValue;
    this.setValue = setValue;
    this.getElementsByTagName = getElementsByTagName;

    function enableAllInputs(anEnabledFlag, aWnd) {
	var disabled = anEnabledFlag ? false : true;
	var wnd = aWnd || window;

	var elements = wnd.document.forms[0].elements;

	for (var i = 0; i < elements.length; i++) {
            var element = elements[i];

            if (disabled) {
                this.disableElement(element);
            } else {
                this.enableElement(element);
            }
	}
    }

    function disableElement(element) {
        this.setOriginalProperty(element, "disabled");
        this.setOriginalProperty(element, "readOnly");

        if (element.type == "text") {
            element.readOnly = true;
        } else {
            element.disabled = true;
        }
    }

    function setOriginalProperty(element, propName) {
        var originPropName = "origin_" + propName;
        if ((typeof element[originPropName]) == "undefined") {
            element[originPropName] = element[propName];
        }
    }

    function getOriginalProperty(element, propName) {
        return element["origin_" + propName];
    }

    function enableElement(element) {
        if (element.type == "text") {
            if (!this.getOriginalProperty(element, "readOnly")) {
                element.readOnly = false;
            }
        } else if (!this.getOriginalProperty(element, "disabled")) {
            element.disabled = false;
        }
    }

    function setEnabled(element, aFlag) {
        if (element.type == "text") {
            this.setOriginalProperty(element, "readOnly");
            element.readOnly = !aFlag;
        } else {
            this.setOriginalProperty(element, "disabled");
            element.disabled = !aFlag;
        }
    }

    function findWindow(windowName, aWnd) {
        var wnd = aWnd || window.top;

        if (wnd.name == windowName) {
            return wnd;
        }

        var frame = wnd.frames[windowName];
        if (frame) {
            return frame;
        }

        for (var i = 0; i < wnd.frames.length; i++) {
            frame = this.findWindow(windowName, wnd.frames[i]);
            if (frame) {
                return frame;
            }
        }

        return null;
    }

    function findWindowObject(objectName, aWnd) {
        var obj = this._findWindowObject(objectName, aWnd);
        if (obj == null && !aWnd) {
            if (window.parent) {
                obj = this._findWindowObject(objectName, window.parent.top);
            }

            if (obj == null && window.opener) {
                obj = this._findWindowObject(objectName, window.opener.top);
            }
        }

        return obj;
    }

    function _findWindowObject(objectName, aWnd) {
        var wnd = aWnd || window.top;

        var obj = wnd[objectName];
        if ((typeof wnd[objectName]) != "undefined") {
            return obj;
        }

        for (var i = 0; i < wnd.frames.length; i++) {
            var frame = wnd.frames[i];
            var obj = this.findWindowObject(objectName, frame);

            if (obj) {
                return obj;
            }
        }

        return null;
    }

    function encodeWithTimestamp(aUrl) {
        var index = aUrl.indexOf("&ts=");
        if (index == -1) {
            index = aUrl.length;
        }

        return aUrl.substring(0, index) + "&ts=" + new Date().getTime();
    }

    function setValue(anObj, aValue) {
        if ((typeof anObj) == "string") {
            anObj = window.document.getElementById(anObj);
        }

        if ((typeof anObj) == "undefined") {
            return;
        }

        if (anObj.type && (anObj.type.indexOf("select") == 0)) {
            for (var i = 0 ; i < anObj.options.length; i++) {
                if (anObj.options[i].value == aValue) {
                    anObj.selectedIndex = i;
                }
            }
        } else if (anObj.type && anObj.type == "radio") {
            var radios = this.getElementsByTagName("input", "radio");
            for (var i = 0 ; i < radios.length; i++) {
                var radio = radios[i];
                if (radio.name = anObj.name) {
                    radio.checked = (aValue == radio.value);
                }
            }
        } else if (anObj.type && anObj.type.indexOf("checkbox") == 0) {
            anObj.checked = eval(aValue);
        } else if ((typeof anObj.value) != "undefined") {
            anObj.value = aValue;
        } else {
            anObj.innerHTML = aValue;
        }
    }

    function getValue(anObj, propName) {
        if ((typeof anObj) == "string") {
            anObj = window.document.getElementById(anObj);
        }

        if (!anObj) {
            return null;
        }

        if (!anObj.type) {
            return anObj.innerHTML;
        }

        if (anObj.type.indexOf("select") == 0) {
            if (anObj.selectedIndex >= 0) {
                var option = anObj.options[anObj.selectedIndex];
                return propName == "text" ? option.text : option.value;
            } else {
                return "";
            }
        } else if (anObj.type == "radio") {
            var radios = this.getElementsByTagName("input", "radio");
            for (var i = 0 ; i < radios.length; i++) {
                var radio = radios[i];
                if (radio.checked) {
                    //verify that this is the correct radio button before returning its value
                    if (radio.name == anObj.name) {
                        return radio.value;
                    }
                }
            }
        } else if (anObj.type.indexOf("checkbox") == 0) {
            return anObj.checked;
        } else if ((typeof anObj.value) != "undefined") {
            return anObj.value;
        }
    }

    function getElementsByTagName(tagName, aType, aWnd) {
        var wnd = aWnd || window;

        var rawResult = wnd.document.getElementsByTagName(tagName);

        var result = new HashSet();

        for (var i = 0; i < rawResult.length; i++) {
            var element = rawResult[i];

            if (!aType || element.type == aType) {
                result.add(element);
            }
        }

        return result.toArray();
    }
}

var HtmlUtils = new HtmlUtilsC();