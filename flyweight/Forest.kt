package flyweight

// Client: Tree(외부 상태)를 관리하고, 생성 시 Factory를 통해 Flyweight를 공유받는다
class Forest {
    private val trees = mutableListOf<Tree>()

    fun plantTree(x: Int, y: Int, name: String, color: String, texture: String) {
        val type = TreeFactory.getTreeType(name, color, texture)
        trees.add(Tree(x, y, type))
    }

    fun draw() {
        trees.forEach { it.draw() }
    }

    fun treeCount(): Int = trees.size
}
