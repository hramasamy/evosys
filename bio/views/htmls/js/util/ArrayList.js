function ArrayList(elements) {
    this.base = AbstractCollection;
    this.base(elements);

    this.set = set;
    this.get = get;
    this.remove = remove;

    function set(anIndex, anElement) {
        this.elementData[anIndex] = anElement;
    }

    function remove(anIndex) {
        var removedElement = this.get(anIndex);
        this.elementData = ArrayUtils.removeElementByIndex(this.elementData, anIndex);
        return removedElement;
    }

    function get(anIndex) {
        return this.elementData[anIndex];
    }
}
