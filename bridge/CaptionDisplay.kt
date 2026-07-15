package bridge

class CaptionDisplay : Display {
    override fun printTitle(draft: Draft) {
        println("TITLE: ${draft.title}")
    }

    override fun printDescription(draft: Draft) {
        println("DESCRIPTION: ")
        draft.description.forEach {
            println("\t\t$it")
        }
    }

    override fun printAuthor(draft: Draft) {
        println("AUTHOR: ${draft.author}")
    }
}
