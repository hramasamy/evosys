function TreeMap() {
    this.base = AbstractMap;
    this.base();

    this.put = put;

    function put(key, value) {
        var position = 0;

        // Find the position
        for (position = 0; position < this.keys.length; position++) {
            if (key < this.keys[position]) {
                break;
            } else if (key == this.keys[position]) {
                var oldValue = this.values[position];
                this.values[position] = value;
                return oldValue;
            }
        }

        ArrayUtils.insertElement(this.keys, position, key);
        ArrayUtils.insertElement(this.values, position, value);

        return null;
    }
}