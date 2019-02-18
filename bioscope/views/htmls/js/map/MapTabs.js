function MapTabs(aMapClass) {
    this.currentMapName = null;
    this.closeWindowGIF = null;
    this.mapEventURI = null;
    this.blankPageURI = null;
    this.maps = new HashSet();

    this.initialize = initialize;
    this.createMap = createMap;
    this.removeMap = removeMap;
    this.removeAllMaps = removeAllMaps;

    this.update = update;
    this.updateCurrentMap = updateCurrentMap;
    this.getMapTabsHolder = getMapTabsHolder;
    this.repaint = repaint;
    this.openMapByName = openMapByName;
    this.openMapByLocator = openMapByLocator;
    this.openMap = openMap;
    this.getOpenMapUrl = getOpenMapUrl;
    this.setCurrentMap = setCurrentMap;
    this.findMap = findMap;
    this.closeMap = closeMap;

    this.initialize();

    function initialize() {
        // This is important to conquer the problem of "cant execute from freed scripts"
        this.mapsPool = new Queue();
        for (var i = 0; i < 50; i++) {
            this.mapsPool.enqueue(new Map());
        }
    }

    function createMap(mapName) {
        var newMap = new Map(mapName);

        var map = this.mapsPool.dequeue();
        map.copy(newMap);

        this.maps.add(map);
        return map;
    }

    function removeMap(aMap) {
        this.mapsPool.enqueue(this.maps.remove(aMap));
    }

    function removeAllMaps() {
        this.mapsPool.addAll(this.maps);
        this.maps.clear();
    }

    function update(aMaps) {
        if (!aMaps) {
            this.maps.clear();
            return;
        }

        // Remove those unwanted
        var mapsArray = this.maps.toArray();
        for (var i = 0; i < mapsArray.length; i++) {
            var map = mapsArray[i];
            if (!this.findMap(map.locator, aMaps)) {
                this.removeMap(map);
            }
        }

        // Set those missing
        var aMapsArray = aMaps.toArray();
        for (var i = 0; i < aMapsArray.length; i++) {
            var map = aMapsArray[i];
            if (!this.findMap(map.locator, this.maps)) {
                newMap = this.createMap(map.mapName);
                newMap.copy(map);
            }
        }

        // Now repaint
        this.repaint();
    }

    function updateCurrentMap(aMap) {
        if (!this.currentMap) {
            this.currentMap = this.createMap(aMap.name);
            this.currentMap.copy(aMap);
        }

        this.currentMap.copy(aMap);
        this.repaint();
    }

    function getMapTabsHolder() {
        return window.document.getElementById("tabs");
    }

    function repaint() {
        if (this.maps.isEmpty()) {
            this.currentMap = null;
            var contentWnd = HtmlUtils.findWindow("content");
            contentWnd.location.href = this.blankPageURI;
        }

        if (!this.maps.isEmpty()) {
            if (!this.currentMap || !this.maps.contains(this.currentMap)) {
                this.openMapByLocator(this.maps.toArray()[0].locator);
                return;
            }
        }

        var result = "<table style=\"position:absolute;top:0\"><tr>";

        var mapsArray = this.maps.toArray();
        for (var i = 0; i < mapsArray.length; i++) {
            var map = mapsArray[i];

            var className = this.currentMap.equals(map) ? "label" : "bodytext";

            result += "<td class='" + className + "' style='border:1 outset;cursor:default' title='" + map.name + "' valign='top'>";

            result += "<img src=\"" + this.closeWindowGIF + "\" onclick=\"mapTabs.closeMap('" + map.locator + "')\""
                    + " align='top'"
                    + " style='cursor:hand'"
                    + ">&nbsp;&nbsp;";

            if (this.currentMap.equals(map)) {
                result += map.getDisplayTitle();
            } else {
                result += "<a href=\"javascript:mapTabs.openMapByLocator('" + map.locator + "')\">"
                          + map.getDisplayTitle()
                          + "</a>";
            }

            result += "&nbsp;&nbsp;</td>";
        }

        result += "</tr></table>";

        this.getMapTabsHolder().innerHTML = result;
    }

    function openMapByName(mapName) {
        var map = this.createMap(mapName);
        this.openMap(map, true);
    }

    function openMapByLocator(mapLocator) {
        var map = this.findMap(mapLocator);
        if (map == null) {
            map = this.createMap(mapLocator);
        }

        this.openMap(map, false);
    }

    function openMap(map, isByMapName) {
        this.setCurrentMap(map);

        var queryParam = (isByMapName ? "mapName=" + map.name : "mapLocator=" + map.locator);
        var contentWnd = HtmlUtils.findWindow("content");
        contentWnd.location.href = this.getOpenMapUrl(queryParam);
    }

    function getOpenMapUrl(queryParam) {
        return HtmlUtils.encodeWithTimestamp(this.mapEventURI + "?userAction=openMap&" + queryParam);
    }

    function setCurrentMap(aMap) {
        this.currentMap = aMap;
        this.repaint();
    }

    function findMap(mapLocator, aMaps) {
        var maps = aMaps || this.maps;

        var mapsArray = maps.toArray();
        for (var i = 0; i < mapsArray.length; i++) {
            var map = mapsArray[i];
            if (map.locator == mapLocator) {
                return map;
            }
        }
        return null;
    }

    function closeMap(mapLocator) {
        var parameters = new HashMap();
        parameters.put("userAction", "closeMap");
        parameters.put("mapLocator", mapLocator);

        var commandTransporter = HtmlUtils.findWindowObject("commandTransporter");
        if (commandTransporter) {
            commandTransporter.sendMessage(parameters);
        }
    }
}