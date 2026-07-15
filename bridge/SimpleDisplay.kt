package bridge

class SimpleDisplay : Display {
    override fun printTitle(draft: Draft) {
        println(draft.title)
    }

    override fun printDescription(draft: Draft) {
        draft.description.forEach {
            println("\t\t$it")
        }
    }

    override fun printAuthor(draft: Draft) {
        println(draft.author)
    }
}
