package oop

/**
 * 주문에 담기는 한 줄 항목. 수량 불변식(1 이상)을 생성자에서 강제.
 */
data class LineItem(val product: String, val unitPrice: Money, val quantity: Int) {
    init {
        require(quantity > 0) { "수량은 1 이상이어야 함: $quantity" }
    }

    val subtotal: Money get() = unitPrice * quantity
}
