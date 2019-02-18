function SumHelper(tableRenderer) {
    this.tableRenderer = tableRenderer;

    this.initialize = initialize;
    this.onVisible = onVisible;
    this.processTableSelected = processTableSelected;
    this.updateUI = updateUI;
    this.getMaxOrMin = getMaxOrMin;
    this.getSelectedOption = getSelectedOption;
    this.selectOption = selectOption;
    this.getOptionElement = getOptionElement;
    this.noSum = noSum;
    this.average = average;
    this.count = count;
    this.countNums = countNums;
    this.max = max;
    this.min = min;
    this.sum = sum;

    function initialize() {
        this.tableRenderer.menuHelper.initialize("autoSumMenu");
        this.tableRenderer.autoSumMenu.onvisible = new Function("this.renderer.sumHelper.onVisible()");
        this.tableRenderer.propagateZIndex(this.tableRenderer.autoSumMenu);

        this.autoSumButton = this.tableRenderer.getComponent("autoSum");

        this.autoSumCell = RendererUtils.getParentNodeByTagName(this.autoSumButton, "TD");
        this.autoSumCell.style.fontWeight = "normal";
        this.autoSumCell.renderer = this.tableRenderer;
        this.autoSumCell.oncontextmenu = new Function("this.renderer.menuHelper.show(this.renderer.autoSumMenu, this, true);return false;");
        this.autoSumCell.onclick = new Function("this.renderer.autoSumMenu.style.display = 'none';");
    }

    function onVisible() {
        var selectedOption = this.getSelectedOption() || this.lastSelectedOption;
        if (selectedOption) {
            this.selectOption(selectedOption);
        }
    }

    function processTableSelected() {
        // Fire the selection automatically
        var selectedOption = this.getSelectedOption() || this.lastSelectedOption;
        if (selectedOption) {
            this[selectedOption]();
        }
    }

    function updateUI(info) {
        // Remove previous text first
        var oldTextSpan = RendererUtils.getChildNodeByTagName(this.autoSumCell, "SPAN");
        if (oldTextSpan) {
            this.autoSumCell.removeChild(oldTextSpan);
        }

        if (info) {
            this.autoSumButton.style.display = "none";

            var infoSpan = window.document.createElement("SPAN");
            infoSpan.innerHTML = info;
            this.autoSumCell.appendChild(infoSpan);
        } else {
            this.autoSumButton.style.display = "inline";
        }
    }

    function getSelectedOption() {
        var options = ["noSum", "average", "count", "countNums", "max", "min", "sum"];
        var optionCount = options.length;

        for (var i = 0; i < optionCount; i++) {
            var option = options[i];

            var optionElement = this.getOptionElement(option);
            if (optionElement.checked) {
                return option;
            }
        }

        return null;
    }

    function selectOption(option) {
        this.getOptionElement(option).checked = true;
        this.lastSelectedOption = option;
    }

    function getOptionElement(option) {
        var optionId = this.tableRenderer.widget.id + "_sum_" + option;
        return window.document.getElementById(optionId);
    }

    function noSum() {
        this.selectOption("noSum");

        this.updateUI("");
    }

    function average() {
        this.selectOption("average");

        var numberCells = this.tableRenderer.selectHelper.getSelectedNumberCells();
        if (numberCells.length == 0) {
            this.updateUI("");
            return;
        }

        var total = this.sum(numberCells, true);
        var average = total / numberCells.length;

        this.updateUI("Average=" + MathUtils.roundFloat(average));
    }

    function count() {
        this.selectOption("count");

        this.updateUI("Count=" + this.tableRenderer.selectHelper.getSelectedCells().length);
    }

    function countNums() {
        this.selectOption("countNums");

        this.updateUI("CountSums=" + this.tableRenderer.selectHelper.getSelectedNumberCells().length);
    }

    function max() {
        this.selectOption("max");

        this.updateUI("Max=" + this.getMaxOrMin(true));
    }

    function min() {
        this.selectOption("min");

        this.updateUI("Min=" + this.getMaxOrMin(false));
    }

    function getMaxOrMin(isMax) {
        var numberCells = this.tableRenderer.selectHelper.getSelectedNumberCells();
        if (numberCells.length == 0) {
            return 0;
        }

        var result = null;
        var numberCellCount = numberCells.length;
        for (var i = 0; i < numberCellCount; i++) {
            var cell = numberCells[i];
            var cellValue = cell.leafRenderer.getCellValue(cell);

            if (result == null || (isMax && cellValue > result) || (!isMax && cellValue < result)) {
                result = cellValue;
            }
        }

        return result;
    }

    function sum(aNumberCells, noUIUpdate) {
        if (!noUIUpdate) {
            this.selectOption("sum");
        }

        var numberCells = aNumberCells || this.tableRenderer.selectHelper.getSelectedNumberCells();
        var total = 0;

        var numberCellCount = numberCells.length;
        for (var i = 0; i < numberCellCount; i++) {
            var cell = numberCells[i];
            var cellValue = cell.leafRenderer.getCellValue(cell);

            total += cellValue;
        }

        if (!noUIUpdate) {
            this.updateUI("Sum=" + MathUtils.roundFloat(total));
        }

        return total;
    }
}