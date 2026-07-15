package bridge

open class Draft(
    val title: String,
    val description: List<String>,
    val author: String,
) {
    open fun print(display: Display) {
        display.printTitle(this)
        display.printDescription(this)
        display.printAuthor(this)
    }
}
