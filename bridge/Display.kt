package bridge

interface Display {
    fun printTitle(draft: Draft)

    fun printDescription(draft: Draft)

    fun printAuthor(draft: Draft)
}
