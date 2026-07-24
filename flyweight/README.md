# Flyweight Pattern

동일하거나 유사한 객체를 대량으로 생성해야 할 때, 공유 가능한 상태(intrinsic state)를 캐싱해 재사용함으로써 메모리 사용량을 줄이는 구조 패턴. 객체마다 달라지는 상태(extrinsic state)는 외부에서 매번 전달받아 분리한다.

## 구성 요소

| 역할 | 클래스 | 설명 |
|---|---|---|
| Flyweight | `TreeType` | 여러 객체가 공유하는 내부 상태(`name`, `color`, `texture`)만 가짐 |
| Flyweight Factory | `TreeFactory` | 같은 조합의 `TreeType`을 캐싱해 재사용. 없으면 새로 생성 |
| Context (Unshared) | `Tree` | 개별 객체마다 달라지는 외부 상태(`x`, `y`)와 `TreeType` 참조를 함께 가짐 |
| Client | `Forest` | `Tree`를 생성/관리하며, 생성 시 Factory를 통해 `TreeType`을 공유받음 |

## 구조

```
Forest (Client)
  └─ List<Tree>
         Tree (Context)                TreeFactory
           ├─ x, y (extrinsic)           └─ cache: Map<key, TreeType>
           └─ type ------참조-------->  TreeType (Flyweight)
                                          ├─ name, color, texture (intrinsic)
                                          └─ draw(x, y)  ← 외부 상태는 매개변수로 받음
```

`Tree` 여러 개가 같은 `TreeType` 인스턴스 하나를 공유한다.

## 코드

**Flyweight** — 공유되는 내부 상태만 가짐. 외부 상태는 메서드 파라미터로 받음

```kotlin
class TreeType(
    val name: String,
    val color: String,
    val texture: String,
) {
    fun draw(canvasX: Int, canvasY: Int) {
        println("[$name/$color/$texture] draw at ($canvasX, $canvasY)")
    }
}
```

**Flyweight Factory** — 동일 조합이면 캐시에서 꺼내 재사용, 없으면 새로 생성

```kotlin
object TreeFactory {
    private val cache = mutableMapOf<String, TreeType>()

    fun getTreeType(name: String, color: String, texture: String): TreeType {
        val key = "$name-$color-$texture"
        return cache.getOrPut(key) {
            println("creating new TreeType: $key")
            TreeType(name, color, texture)
        }
    }

    fun count(): Int = cache.size
}
```

**Context** — 외부 상태(`x`, `y`)와 Flyweight 참조를 함께 가짐

```kotlin
class Tree(
    private val x: Int,
    private val y: Int,
    private val type: TreeType,
) {
    fun draw() {
        type.draw(x, y)
    }
}
```

**Client** — `Tree` 생성 시 Factory를 통해 `TreeType`을 공유받음

```kotlin
class Forest {
    private val trees = mutableListOf<Tree>()

    fun plantTree(x: Int, y: Int, name: String, color: String, texture: String) {
        val type = TreeFactory.getTreeType(name, color, texture)
        trees.add(Tree(x, y, type))
    }

    fun draw() {
        trees.forEach { it.draw() }
    }
}
```

## 이 예제에서 공유되는 것 / 분리되는 것

- **공유(intrinsic)**: `name`, `color`, `texture` → 같은 수종이면 `TreeType` 인스턴스 하나만 생성됨
- **분리(extrinsic)**: `x`, `y` → 나무마다 다르므로 `Tree`가 각자 들고 있다가 `draw()` 호출 시 전달
- 나무를 몇 개를 심든(`Main.kt`에서는 6그루) `TreeType` 인스턴스는 실제 종류 수(2개)만큼만 생성됨 (`TreeFactory.count()`로 확인 가능)

## 장점

- 대량의 유사 객체를 생성할 때 메모리 사용량을 크게 줄일 수 있음
- 내부 상태와 외부 상태를 명확히 분리해 설계가 깔끔해짐

## 주의점

- 외부 상태를 매번 메서드 인자로 전달해야 하므로 코드가 복잡해지고, 클라이언트(Context)가 외부 상태 관리 책임을 떠안음
- 공유 객체는 불변(immutable)으로 유지해야 함 — 하나의 `TreeType`을 여러 `Tree`가 참조하므로 상태를 변경하면 다른 객체에도 영향을 줌
- 객체 생성 비용이 낮거나 개수가 적으면 캐싱/조회 오버헤드가 오히려 손해일 수 있음
