package bridge

class Publication(
    draft: Draft,
    val publisher: String, // 출판사 이름 추가
    val cost: Int, // 가격 추가
) : Draft(draft.title, draft.description, draft.author) {
    override fun print(display: Display) {
        super.print(display)
        printPublication()
    }

    // 추가 정보 출력
    private fun printPublication() {
        println($$"#$$publisher $$$cost")
    }
}
