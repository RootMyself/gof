# Bridge Pattern

추상화(Abstraction)와 구현(Implementation)을 분리해 독립적으로 확장 가능하게 하는 구조 패턴. 상속 대신 합성(composition)으로 둘을 연결(bridge)하여, 추상화 계층과 구현 계층이 각각 따로 늘어나도 클래스 수가 곱셈으로 폭발하지 않게 한다.

## 구성 요소

| 역할 | 클래스 | 설명 |
|---|---|---|
| Abstraction | `Draft` | 클라이언트가 다루는 추상 개념. 구현(`Display`)을 합성으로 들고 있음 |
| Refined Abstraction | `Publication` | `Draft`를 상속해 기능을 확장 (출판사, 가격 추가) |
| Implementor | `Display` | 실제 출력 방식을 정의하는 인터페이스 |
| Concrete Implementor | `SimpleDisplay`, `CaptionDisplay` | `Display`의 구체적인 구현체 |

## 구조

```
Draft (Abstraction)              Display (Implementor)
  ├─ print(display)  ------bridge------>  printTitle/printDescription/printAuthor
  │
Publication (Refined Abstraction)        SimpleDisplay, CaptionDisplay (Concrete Implementor)
```

추상화 계층(`Draft` → `Publication`)과 구현 계층(`Display` → `SimpleDisplay`/`CaptionDisplay`)이 서로 다른 축으로 독립적으로 확장된다.

## 코드

**Implementor** — 구현 계층의 인터페이스

```kotlin
interface Display {
    fun printTitle(draft: Draft)
    fun printDescription(draft: Draft)
    fun printAuthor(draft: Draft)
}
```

**Concrete Implementor** — 실제 출력 방식 구현

```kotlin
class SimpleDisplay : Display {
    override fun printTitle(draft: Draft) { println(draft.title) }
    override fun printDescription(draft: Draft) {
        draft.description.forEach { println("\t\t$it") }
    }
    override fun printAuthor(draft: Draft) { println(draft.author) }
}

class CaptionDisplay : Display {
    override fun printTitle(draft: Draft) { println("TITLE: ${draft.title}") }
    override fun printDescription(draft: Draft) {
        println("DESCRIPTION: ")
        draft.description.forEach { println("\t\t$it") }
    }
    override fun printAuthor(draft: Draft) { println("AUTHOR: ${draft.author}") }
}
```

**Abstraction** — `Display`를 합성으로 참조(bridge)해 출력을 위임

```kotlin
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
```

**Refined Abstraction** — 추상화 계층만 확장. 구현 계층(`Display`)은 그대로 재사용

```kotlin
class Publication(
    draft: Draft,
    val publisher: String,
    val cost: Int,
) : Draft(draft.title, draft.description, draft.author) {
    override fun print(display: Display) {
        super.print(display)
        printPublication()
    }

    private fun printPublication() {
        println("$publisher $cost")
    }
}
```

**Client** — 임의의 Abstraction과 임의의 Implementor를 자유롭게 조합

```kotlin
val draft = Draft(title = "...", description = listOf("..."), author = "...")

draft.print(SimpleDisplay())
draft.print(CaptionDisplay())

val publication = Publication(draft = draft, publisher = "개발출판사", cost = 50)
publication.print(SimpleDisplay())
publication.print(CaptionDisplay())
```

## 이 예제에서 Bridge가 분리하는 것

- **추상화 계층**: `Draft`(원본) ↔ `Publication`(출판사/가격 추가된 확장)
- **구현 계층**: `SimpleDisplay`(단순 출력) ↔ `CaptionDisplay`(라벨 붙여 출력)
- `Publication`은 `Display` 구현체를 몰라도 되고, `SimpleDisplay`/`CaptionDisplay`는 `Draft`가 `Publication`으로 확장돼도 수정할 필요 없음 (2×2 = 4가지 조합이 상속 없이 자유롭게 성립)

## 장점

- 추상화와 구현이 컴파일 타임이 아닌 런타임에 연결됨 (`print(display)`처럼 구현체를 갈아끼울 수 있음)
- 상속 폭발 방지: Abstraction N개 × Implementor M개를 상속으로 만들면 N×M개 클래스가 필요하지만, Bridge는 N+M개로 충분
- 구현 세부사항이 클라이언트로부터 완전히 숨겨짐 (OCP, DIP 준수)

## 주의점

- 계층을 두 개로 나누는 설계이므로, 확장 축이 하나뿐이거나 조합이 적으면 오히려 불필요한 간접 계층이 될 수 있음
- Adapter 패턴과 구조가 비슷해 보이지만 목적이 다름: Adapter는 "이미 있는 서로 다른 인터페이스를 사후에 연결"하는 것이고, Bridge는 "설계 단계에서부터 추상화와 구현을 분리"하는 것
