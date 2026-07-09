package template

abstract class DisplayArticleTemplate(
   protected val article: Article
) {
    fun display() {
        title()
        content()
        footer()
    }

    abstract fun title()
    abstract fun content()
    abstract fun footer()
}