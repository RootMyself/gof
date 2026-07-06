package itorator

fun main() {
    val array = Array(4).apply {
        append(Item("사과"))
        append(Item("바나나"))
        append(Item("포도"))
        append(Item("오렌지"))
    }

    val iterator = array.iterator()
    while (iterator.hasNext()) {
        println(iterator.next().name)
    }
}
