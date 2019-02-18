function Map(aName, aLocator, aTitle, anInstanceName) {
    this.name = aName || "wisdomMap";
    this.locator = aLocator || aName + "_" + new Date().getTime();
    this.title = aTitle || "...";
    this.instanceName = anInstanceName || null;

    this.equals = equals;
    this.copy = copy;
    this.getDisplayTitle = getDisplayTitle;

    this.toString = toString;

    function copy(aMap) {
        this.name = aMap.name;
        this.locator = aMap.locator;
        this.title = aMap.title;
        this.instanceName = aMap.instanceName;

        return this;
    }

    function equals(aMap) {
        if (aMap) {
            return this.name == aMap.name && this.locator == aMap.locator;
        } else {
            return false;
        }
    }

    function getDisplayTitle() {
        var displayTitle = this.title;
        if (this.instanceName && this.instanceName != "null") {
            displayTitle += " - " + this.instanceName;
        }

        return displayTitle;
    }

    function toString() {
        return "name=" + this.name + "\nlocator=" + this.locator + "\ntitle=" + this.title;
    }
}