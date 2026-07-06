# Iterator 패턴

## 개념

컬렉션의 **내부 구조를 노출하지 않고 요소에 순차 접근**하게 하는 행동(Behavioral) 패턴. 핵심은 순회 책임을 컬렉션에서 분리해 별도 객체(Iterator)에 맡기는 것.

## 구조

| 역할 | 설명 | 구현 파일 |
|---|---|---|
| **Iterator** | 순회 인터페이스 (`hasNext`, `next`) | `Iterator.kt` |
| **ConcreteIterator** | 순회 로직, 현재 위치 관리 | `ArrayIterator.kt` |
| **Aggregate** | Iterator를 생성하는 집합체 인터페이스 | `Aggregator.kt` |
| **ConcreteAggregate** | 데이터 보유, Iterator 반환 | `Array.kt` |

## 왜 쓰는가

- **캡슐화**: 내부 구조(배열, 리스트, 트리)를 몰라도 순회 가능
- **책임 분리**: 컬렉션은 저장, Iterator는 순회만
- **교체 용이**: 컬렉션 구현이 바뀌어도 순회 코드 불변
- **다중 순회**: 여러 Iterator가 각자 위치로 독립 순회

## 실무 사용

직접 구현보다 **언어/라이브러리 내장 Iterator 사용이 대부분**.

### for-in 루프 자체가 Iterator 패턴

```kotlin
for (item in list) { ... }   // 컴파일러가 iterator() → hasNext()/next()로 변환
```

`Iterable` 구현하면 커스텀 클래스도 `for` 루프 사용 가능:

```kotlin
class DateRange(val start: LocalDate, val end: LocalDate) : Iterable<LocalDate> {
    override fun iterator() = object : Iterator<LocalDate> {
        var current = start
        override fun hasNext() = current <= end
        override fun next() = current.also { current = current.plusDays(1) }
    }
}
```

### 대표 사례

- **지연 평가**: Kotlin `Sequence`, Java `Stream` — 대용량 데이터를 중간 컬렉션 없이 처리 (`File.useLines`)
- **DB 커서**: JDBC `ResultSet.next()`, JPA `ScrollableResults`, Spring Batch `ItemReader` — 수백만 행을 메모리에 안 올리고 순차 처리
- **페이징 SDK**: AWS S3 paginator 등 — 페이지 넘김을 Iterator 뒤로 감춰 전체 순회처럼 사용
- **무한/스트리밍**: `generateSequence`, Kafka `poll()` 루프, 코루틴 `Flow`

### 직접 구현하는 경우

- 커스텀 자료구조(트리, 그래프)에 `Iterable` 구현
- 순회 전략 제공 (전위/중위/후위 등)
- 페이징 API, 파일 청크 읽기를 Iterator로 래핑

## 주의점

- 순회 중 원본 수정 시 `ConcurrentModificationException` — 수정은 `MutableIterator.remove()` 사용
- 대부분 일회용 — 한 번 소비하면 재사용 불가
- 스냅샷 아님 — 원본 참조하므로 일관성 필요하면 복사본 사용
