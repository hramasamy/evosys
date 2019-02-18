function FloatMenuHelper(renderer) {
    this.renderer = renderer;

    this.initialize = initialize;
    this.switchMenuItem = switchMenuItem;
    this.show = show;

    function initialize(menuName) {
        var menu = this.renderer.getComponent(menuName);

        for (var i = 0; i < menu.rows.length; i++) {
            for (var j = 0; j < menu.rows[i].childNodes.length; j++) {
                var menuItem = menu.rows[i].childNodes[j];
                menuItem.style.cursor = "default";
                menuItem.menu = menu;
                menuItem.onclick = new Function("this.menu.renderer." + menuItem.eventName + "(); this.menu.style.display='none';window.event.returnValue=false;");
            }
        }

        menu.onmouseover = new Function("this.renderer.menuHelper.switchMenuItem(this)");
        menu.onmouseout = new Function("this.renderer.menuHelper.switchMenuItem(this)");

        this.renderer[menuName] = menu;
    }

    function switchMenuItem(menu) {
        var selectedMenuItem = window.event.srcElement;

        for (var i = 0; i < menu.rows.length; i++) {
            for (var j = 0; j < menu.rows[i].childNodes.length; j++) {
                var menuItem = menu.rows[i].childNodes[j];
                menuItem.style.backgroundColor = (menuItem == selectedMenuItem ? "yellow" : menu.style.backgroundColor);
            }
        }
    }

    function show(menu, srcCell, noScrollContext) {
        menu.style.posLeft = window.event.offsetX
        menu.style.posTop = window.event.offsetY;

        if (!noScrollContext) {
            menu.style.posLeft += srcCell.offsetLeft - this.renderer.widget.parentNode.scrollLeft;
            menu.style.posTop += srcCell.offsetTop - this.renderer.widget.parentNode.scrollTop;
        }

        menu.srcCell = srcCell;

        menu.style.display = "inline";
        if ((typeof menu.onvisible) == "function") {
            menu.onvisible();
        }
    }
}