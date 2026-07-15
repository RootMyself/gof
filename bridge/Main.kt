package bridge

fun main() {
    val draft =
        Draft(
            title = "쉽게 배우는 디자인 패턴",
            description =
                listOf(
                    "디자인 패턴에 대한 내용입니다.",
                    "쉽게 배울 수 있습니다.",
                    "어댑터, 브릿지 패턴 등을 배웁니다.",
                ),
            author = "김승범",
        )

    val simple = SimpleDisplay()
    draft.print(simple)

    val caption = CaptionDisplay()
    draft.print(caption)

    val publication =
        Publication(
            draft = draft,
            publisher = "개발출판사",
            cost = 50,
        )
    publication.print(simple)
    publication.print(caption)
}
