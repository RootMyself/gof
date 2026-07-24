package flyweight

// Flyweight: 공유되는 내부 상태(intrinsic state)만 가짐
class TreeType(
    val name: String,
    val color: String,
    val texture: String,
) {
    fun draw(canvasX: Int, canvasY: Int) {
        println("[$name/$color/$texture] draw at ($canvasX, $canvasY)")
    }
}
