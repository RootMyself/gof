package template

fun main() {
    val article =  Article(
        title = "Template Method Pattern",
        content = listOf(
            "Template Method Pattern은 알고리즘의 구조를 정의하고,",
            "일부 단계를 서브클래스에서 구현하도록 하는 디자인 패턴입니다.",
            "이를 통해 알고리즘의 구조를 변경하지 않고도 특정 단계를 재정의할 수 있습니다."
        ),
        footer = "2024-06-10"
    )

    val simple: DisplayArticleTemplate = SimpleDisplayArticle(article)
    println("[CASE 1]")
    simple.display()
    println()

    val caption: DisplayArticleTemplate = CaptionDisplayArticle(article)
    println("[CASE 2]")
    caption.display()
    println()
}