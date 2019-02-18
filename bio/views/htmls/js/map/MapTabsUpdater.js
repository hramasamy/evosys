function MapTabsUpdater(aSetClass) {
    this.setClass = aSetClass || HashSet;
    this.maps = new this.setClass();
    this.startTime = new Date().getTime();

    this.addMap = addMap;
    this.update = update;

    function addMap(map) {
        this.maps.add(map);
    }

    function update() {
        var mapTabs = HtmlUtils.findWindowObject("mapTabs");
        if (mapTabs && mapTabs.getMapTabsHolder()) {
            mapTabs.update(this.maps);
        } else if (new Date().getTime() - this.startTime < 300000) {
            window.setTimeout("mapTabsUpdater.update()", 200);
        }
    }
}
