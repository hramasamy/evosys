function AbstractMap(keys, values) {
    this.keys = keys || new Array();
    this.values = values || new Array();

    this.keySet = keySet;
    this.entrySet = entrySet;
    this.isMap = true;
    this.containsKey = containsKey;
    this.get = get;
    this.put = null;
    this.size = size;
    this.isEmpty = isEmpty;
    this.remove = remove;
    this.clear = clear;
    this.toString = toString;

    function keySet() {
        var myKeys = new ArrayList();

        for (var i = 0; i < this.keys.length; i++) {
            myKeys.add(this.keys[i]);
        }

        return myKeys;
    }

    function entrySet() {
        var entries = new ArrayList();

        for (var i = 0; i < this.keys.length; i++) {
            var entry = new Object();
            entry.key = this.keys[i];
            entry.value = this.values[i];
            entries.add(entry);
        }

        return entries;
    }

    function containsKey(aKey) {
        return (this.get(aKey) != null);
    }

    function get(key) {
        for(var i=0; i<this.keys.length; i++) {
            if(key == this.keys[i]) {
                return this.values[i];
            }
        }

        return null;
    }

    function size() {
        return this.keys.length;
    }

    function isEmpty() {
        return this.size() == 0;
    }

    function remove(key) {
        var index = ArrayUtils.getElementIndex(this.keys, key);
        if(index == -1) {
            return null;
        }

        var value = this.values[index];
        this.keys = ArrayUtils.removeElementByIndex(this.keys, index);
        this.values = ArrayUtils.removeElementByIndex(this.values, index);
    }

    function clear() {
        this.keys = new Array();
        this.values = new Array();
    }

    function toString() {
        var result = "";

        for (var i = 0; i < this.size(); i++) {
            if (i > 0) {
                result += "\n";
            }
            result += this.keys[i] + "=" + this.values[i];
        }

        return result;
    }
}