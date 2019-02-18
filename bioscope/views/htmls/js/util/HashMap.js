HashMap.prototype.parseMap = parseHashMap;

function parseHashMap(mapString, delimiter) {
    var result = new HashMap();

    var object = BeanUtils.parseObject(mapString, delimiter);
    for (var i = 0; i < object.keys.length; i++) {
        var key = object.keys[i];

        result.put(key, object[key]);
    }

    return result;
}

function HashMap(keys, values) {
    this.base = AbstractMap;
    this.base(keys, values);

    this.put = put;

    function put(key, value) {
        var index = ArrayUtils.getElementIndex(this.keys, key);
        if(index != -1) {
            var oldValue = this.values[index];
            this.values[index] = value;
            return oldValue;
        }

        this.keys[this.keys.length] = key;
        this.values[this.values.length] = value;
        return null;
    }
}