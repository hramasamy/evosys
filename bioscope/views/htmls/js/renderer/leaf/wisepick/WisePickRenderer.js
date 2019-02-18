WisePickRenderer.prototype = new SimpleInputLeafWidgetRenderer();

function WisePickRenderer(widget) {
    this.base = SimpleInputLeafWidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.initializeSize = initializeSize;

    this.inputChanged = inputChanged;
    this.selectionChanged = selectionChanged;
    this.setError = setError;
    this.getList = getList;
    this.processGetList = processGetList;

    function initialize() {
        this.inputbox = this.getComponent("input");

        this.helper = new WisePickHelper(this);
        this.helper.initialize();

        WisePickRenderer.prototype.initialize(this);
    }

    function initializeSize() {
        if (WisePickRenderer.prototype.initializeSize(this)) {
            this.helper.initializeSize();
        }
    }

    function inputChanged() {
        RendererUtils.fireEventForOtherListeners(this.widget, "onchange");
        this.getList();
    }

    function selectionChanged() {
        this.inputbox.value = this.widget.value;

        var widgetEvent = new WidgetEvent(this.widget, "itemSelected");
        widgetEvent.set("selectedvalue", this.widget.selectedIndex, "java.lang.Integer");
        widgetEvent.post();
    }

    function setError(value) {
        this.inputbox.style.backgroundColor = (value == "true" ? "#ff08a2" : "white");
    }

    function getList() {
        var widgetEvent = new WidgetEvent(this.widget, "getList");
        if (this.inputbox.value) {
            widgetEvent.set("value", this.inputbox.value);
        }
        widgetEvent.post();
    }

    function processGetList(listStr) {
        this.helper.processGetList(listStr);
    }
}