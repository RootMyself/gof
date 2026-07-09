package solid

/**
 * LSP 위반: ReadOnlyInventoryRepository가 InventoryRepository를 구현하지만
 * increase/decrease 호출 시 예외를 던져 부모 계약을 깨뜨림.
 * 배치 잡이 List<InventoryRepository>를 다형적으로 순회하며 호출하면 런타임에 터짐.
 */
interface InventoryRepository {
    fun currentStock(sku: String): Int
    fun increase(sku: String, amount: Int)
    fun decrease(sku: String, amount: Int)
}

class BadReadOnlyInventoryRepository(private val stock: Map<String, Int>) : InventoryRepository {
    override fun currentStock(sku: String): Int = stock[sku] ?: 0
    override fun increase(sku: String, amount: Int): Unit =
        throw UnsupportedOperationException("읽기 전용 저장소는 재고를 늘릴 수 없음")
    override fun decrease(sku: String, amount: Int): Unit =
        throw UnsupportedOperationException("읽기 전용 저장소는 재고를 줄일 수 없음")
}

/**
 * LSP 준수: "읽기"와 "쓰기" 계약을 아예 분리. 읽기 전용 구현체는 읽기 인터페이스만 구현하면 되므로
 * 지키지 못할 계약(증감)을 떠맡을 필요가 없음 — ISP와도 맞닿는 지점.
 */
interface InventoryReader {
    fun currentStock(sku: String): Int
}

interface InventoryWriter : InventoryReader {
    fun increase(sku: String, amount: Int)
    fun decrease(sku: String, amount: Int)
}

class ReadOnlyInventoryRepository(private val stock: Map<String, Int>) : InventoryReader {
    override fun currentStock(sku: String): Int = stock[sku] ?: 0
}

class WritableInventoryRepository(
    private val stock: MutableMap<String, Int> = mutableMapOf(),
) : InventoryWriter {
    override fun currentStock(sku: String): Int = stock[sku] ?: 0

    override fun increase(sku: String, amount: Int) {
        stock[sku] = currentStock(sku) + amount
    }

    override fun decrease(sku: String, amount: Int) {
        val remaining = currentStock(sku) - amount
        check(remaining >= 0) { "재고 부족: $sku (남은 재고 ${currentStock(sku)}, 요청 $amount)" }
        stock[sku] = remaining
    }
}
