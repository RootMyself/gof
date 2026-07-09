package template

class SimpleDisplayArticle(article: Article) : DisplayArticleTemplate(article) {
    override fun title() {
        println(article.title)
    }

    override fun content() {
        article.content.forEach {
            println(it)
        }
    }

    override fun footer() {
        println(article.footer)
    }
}