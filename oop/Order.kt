package oop

/**
 * 캡슐화 + 합성 예시.
 * - status는 public getter / private setter로 노출해 외부가 임의로 상태를 바꾸지 못하게 막음.
 * - 상속 대신 DiscountPolicy(파라미터)와 OrderNotifier 목록(필드)을 합성해 기능을 조립.
 */
class Order(
    val id: String,
    private val notifiers: MutableList<OrderNotifier> = mutableListOf(),
) {
    private val items = mutableListOf<LineItem>()

    var status: OrderStatus = OrderStatus.Created
        private set

    val subtotal: Money
        get() = items.fold(Money.ZERO) { acc, item -> acc + item.subtotal }

    fun addItem(item: LineItem) {
        check(status == OrderStatus.Created) { "이미 진행된 주문에는 상품을 추가할 수 없음: $status" }
        items.add(item)
    }

    fun addNotifier(notifier: OrderNotifier) {
        notifiers.add(notifier)
    }

    fun total(discountPolicy: DiscountPolicy): Money = subtotal - discountPolicy.apply(subtotal)

    fun transitionTo(next: OrderStatus) {
        require(status.canTransitionTo(next)) {
            "잘못된 상태 전이: ${status::class.simpleName} → ${next::class.simpleName}"
        }
        status = next
        notifiers.forEach { it.notify(this, next) }
    }
}
