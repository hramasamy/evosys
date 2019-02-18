function Queue() {
    this.base = ArrayList;
    this.base();

    this.enqueue = enqueue;
    this.dequeue = dequeue;

    function enqueue(anElement) {
        this.add(anElement);
    }

    function dequeue() {
        var element = null;

        if (!this.isEmpty()) {
            element = this.get(0);
            this.remove(0);
        }

        return element;
    }
}
