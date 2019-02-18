function MapMenu(name, menu) {
    this.name = name;
    this.menu = menu;

    this.menu.eventHandlerName = this.name + ".openMap";

    this.openMap = openMap;

    function openMap(mapName) {
        var mapTabs = HtmlUtils.findWindowObject("mapTabs");
        if (mapTabs) {
            mapTabs.openMapByName(mapName);
        }
    }
}
