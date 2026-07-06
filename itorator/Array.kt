package itorator

/**
 * ConcreteAggregator 역할: Item을 보유(composition)하고 ArrayIterator를 생성
 */
class Array(private val capacity: Int) : Aggregator<Item> {
    private val items = arrayOfNulls<Item>(capacity)
    private var last = 0

    fun getItemAt(index: Int): Item =
        items[index] ?: throw IndexOutOfBoundsException("index=$index")

    fun append(item: Item) {
        require(last < capacity) { "Array is full (capacity=$capacity)" }
        items[last++] = item
    }

    val length: Int
        get() = last

    override fun iterator(): Iterator<Item> = ArrayIterator(this)
}
