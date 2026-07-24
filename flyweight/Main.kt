package flyweight

fun main() {
    val forest = Forest()

    repeat(6) { i ->
        val species = if (i % 2 == 0) "Oak" else "Pine"
        val color = if (i % 2 == 0) "Green" else "DarkGreen"
        forest.plantTree(x = i, y = i * 2, name = species, color = color, texture = "default")
    }

    forest.draw()

    println("total trees: ${forest.treeCount()}")
    println("total TreeType instances (shared): ${TreeFactory.count()}")
}
