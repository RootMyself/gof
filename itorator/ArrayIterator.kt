package itorator

/**
 * ConcreteIterator 역할: Array를 참조하며 순회 위치를 관리
 */
class ArrayIterator(private val array: Array) : Iterator<Item> {
    private var index = 0

    override fun hasNext(): Boolean = index < array.length

    override fun next(): Item {
        if (!hasNext()) throw NoSuchElementException()
        return array.getItemAt(index++)
    }
}
