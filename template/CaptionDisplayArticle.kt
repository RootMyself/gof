package template

class CaptionDisplayArticle(article: Article) : DisplayArticleTemplate(article) {
    override fun title() {
        println("TITLE: ${article.title}")
    }

    override fun content() {
        println("CONTENT:")
        article.content.forEach {
            println("\t$it")
        }
    }

    override fun footer() {
        println("FOOTER: ${article.footer}")
    }
}