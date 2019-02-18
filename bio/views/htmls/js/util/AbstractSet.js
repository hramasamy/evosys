AbstractSet.prototype = new AbstractCollection();

function AbstractSet(elements) {
    this.base = AbstractCollection;
    this.base(elements);
    this.myClass = AbstractSet;

    this.add = add;
    this.clone = clone;

    function add(anElement) {
        return this.contains(anElement) ? false : AbstractSet.prototype.add(anElement, this);
    }

    function clone() {
        var aSet = new this.myClass();
        aSet.addAll(this);
        return aSet;
    }
}