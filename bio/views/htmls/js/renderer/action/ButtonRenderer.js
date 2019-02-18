function ButtonRenderer(widget) {
    this.base = WidgetRenderer;
    this.base(widget);

    this.setLabel = setLabel;

    this.onClick = onClick;

    function setLabel(aLabel) {
        this.widget.value = aLabel;
    }

    function onClick() {
        var widgetEvent = null;

        if (this.widget.actionName == "Search") {
            // Look for enclosing form
            var formNode = RendererUtils.getParentNodeBySnippetName(this.widget, "form");
            if (formNode == null) {
                formNode = this.widget;
            }

            widgetEvent = new WidgetEvent(formNode, "doAction");
            widgetEvent.set("value", "Search");
        } else {
            widgetEvent = new WidgetEvent(this.widget, 'buttonPushed');
        }

        widgetEvent.post();
    }
}
