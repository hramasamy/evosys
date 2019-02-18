DateRenderer.prototype = new SimpleInputLeafWidgetRenderer();

function DateRenderer(widget) {
    this.base = SimpleInputLeafWidgetRenderer;
    this.base(widget);

    this.initialize = initialize;
    this.dateSelected = dateSelected;
    this.openDateDialog = openDateDialog;

    function initialize() {
        DateRenderer.prototype.initialize(this);

        this.widget.onchange = new Function("this.renderer.dateSelected()");

        this.calendar = this.getComponent("calendar");
        this.calendar.onclick = new Function("this.renderer.openDateDialog()");
    }

    function openDateDialog() {
        window.open(this.widget.calendarPath + "?dateListener=" + this.widget.id, "calendar", "height=220,width=250,status=no,toolbar=no,menubar=no,location=no");
    }

    function dateSelected(year, month, day) {
        // Must from a manual change
        if (arguments.length == 0) {
            var date = new Date(this.widget.value);
            // A simple workaround for day light saving time shift
            date.setTime(date.getTime() + 2 * 3600 * 1000);
            year = date.getYear();
            month = date.getMonth();
            day = date.getDate();
        } else {
        }

        var date = new Date();
        date.setYear(year);
        date.setMonth(month);
        date.setDate(day);

        var widgetEvent = new WidgetEvent(this.widget, "dateChanged");
        widgetEvent.set("_value", date.getTime(), "java.util.Date[long]");
        widgetEvent.post();
    }
}
