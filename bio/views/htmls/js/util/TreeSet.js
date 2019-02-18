function TreeSet(elements) {
    this.base = AbstractSet;
    this.base(elements);
    this.myClass = TreeSet;

    this.add = add;

    function add(anElement) {
        var position = 0;

        // Find the position
        for (position = 0; position < this.elementData.length; position++) {
            var element = this.elementData[position];

            if (anElement < element) {
                break;
            } else if (BeanUtils.equals(anElement, element)) {
                return false;
            }
        }

        ArrayUtils.insertElement(this.elementData, position, anElement);
    }
}