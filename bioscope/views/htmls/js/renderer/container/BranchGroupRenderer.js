function BranchGroupRenderer(widget) {
    this.base = NavigatableRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.first = first;
    this.previous = previous;
    this.next = next;
    this.last = last;
    this.postNavigationEvent = postNavigationEvent;
    this.add = add;
    this.remove = remove;
    this.setCurrent = setCurrent;
    this.hasPrevious = hasPrevious;
    this.hasNext = hasNext;
    this.getPositionInfo = getPositionInfo;

    function initialize() {
        this.widget.position = parseInt(this.widget.position);

        this.initializeNavigationBar();
    }

    function setCurrent(aValue) {
        this.setPosition(StringUtils.getParameter(aValue, "position"));
    }

    function first() {
        this.postNavigationEvent(0);
    }

    function previous() {
        this.postNavigationEvent(this.widget.position - 1);
    }

    function next() {
        this.postNavigationEvent(this.widget.position + 1);
    }

    function last() {
        this.postNavigationEvent(this.widget.totalRows - 1);
    }

    function postNavigationEvent(position) {
        if (position >= 0 && position < this.widget.totalRows) {
            var widgetEvent = new WidgetEvent(this.widget, "setCurrent");
            widgetEvent.set("position", position, "java.lang.Integer");
            widgetEvent.post();
        }
    }

    function add() {
        var widgetEvent = new WidgetEvent(this.widget, "addRow");
        widgetEvent.post();
    }

    function remove() {
        var widgetEvent = new WidgetEvent(this.widget, "deleteRow");
        widgetEvent.set("position", this.widget.position, "java.lang.Integer");
        widgetEvent.post();
    }

    function hasPrevious() {
        return this.widget.position > 0;
    }

    function hasNext() {
        return this.widget.position < this.widget.totalRows - 1;
    }

    function getPositionInfo() {
        return this.widget.position + 1;
    }
}