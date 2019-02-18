function WidgetEvent(widget, actionId) {
    this.widget = (typeof widget) == "string" ? window.document.getElementById(widget) : widget;
    this.actionId = actionId;
    this.parameters = new HashMap();
    this.parameterTypes = new HashMap();

    this.set = set;
    this.get = get;
    this.toMap = toMap;
    this.post = post;

    function set(name, value, aType) {
        this.parameters.put(name, value);

        var type = (typeof aType) != "undefined" ? aType : "java.lang.String";
        this.parameterTypes.put(name, type);
    }

    function get(name) {
        return this.parameters.get(name);
    }

    function toMap() {
        var result = new HashMap(this.parameters.keys, this.parameters.values);
        result.put("parameterNames", new String(this.parameters.keys));
        result.put("parameterTypes", new String(this.parameterTypes.values));

        result.put("widgetId", this.widget ? this.widget.id : null);
        result.put("actionId", this.actionId);

        if (this.target) {
            result.target = this.target;
        }

        return result;
    }

    function post() {
        var mapActionHandler = HtmlUtils.findWindowObject("mapActionHandler");
        if (mapActionHandler) {
            mapActionHandler.postEvent(this);
        }
    }
}