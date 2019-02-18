function WisePickHelper(renderer) {
    this.renderer = renderer;

    this.initialize = initialize;
    this.initializeSize = initializeSize;
    this.onMouseEnter = onMouseEnter;
    this.processGetList = processGetList;

    function initialize() {
        // Event binding
        this.renderer.inputbox.onchange = new Function("this.renderer.inputChanged()");
        this.renderer.widget.onchange = new Function("this.renderer.selectionChanged()");
        this.renderer.widget.onmouseenter = new Function("this.renderer.helper.onMouseEnter()");
    }

    function initializeSize() {
        var inputWidth = this.renderer.widget.style.posWidth - 19;
        this.renderer.widget.style.clip = "rect(auto auto auto " + inputWidth + ")";
        this.renderer.inputbox.style.posWidth = inputWidth;
        this.renderer.inputbox.style.zIndex = (this.renderer.widget.style.zIndex ? this.renderer.widget.style.zIndex + 1 : 1);
    }

    function onMouseEnter() {
        if (!this.renderer.widget.listRetrieved) {
            this.renderer.getList();
            this.renderer.widget.listRetrieved = true;
        }
    }

    function processGetList(listStr) {
        var listStr = StringUtils.trim(StringUtils.getParameter(listStr, "list", ["]]}"]), "[[");
        var optionStrings = ArrayUtils.getTokens(listStr, "][");
        ComboboxRenderer.singleton.clearOptions(this.renderer.widget);

        for (var i = 0; i < optionStrings.length; i++) {
            var optionString = optionStrings[i];
            var optionTokens = ArrayUtils.getTokens(optionString);

            var optionValue = optionTokens[0];
            var optionText = optionTokens.join(" | ");

            var option = new Option(optionText, optionValue);
            this.renderer.widget.options.add(option);

            // Set the default selection for input box
            this.renderer.widget.selectedIndex = -1;
        }
    }
}
