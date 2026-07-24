package flyweight

// Context: 외부 상태(extrinsic state)와 Flyweight 참조를 함께 가짐
class Tree(
    private val x: Int,
    private val y: Int,
    private val type: TreeType,
) {
    fun draw() {
        type.draw(x, y)
    }
}
