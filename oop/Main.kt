package oop

fun main() {
    println(CustomerTier.GOLD.benefits())

    val order = Order(id = "ORD-2026-0001")
    order.addItem(LineItem("기계식 키보드", Money.won(89_000), 1))
    order.addItem(LineItem("무선 마우스", Money.won(45_000), 2))

    order.addNotifier(EmailNotifier("sb92120@gmail.com"))
    order.addNotifier(SmsNotifier("010-1234-5678"))

    val discountPolicy = CombinedDiscountPolicy(
        listOf(
            TierDiscountPolicy(CustomerTier.GOLD),
            CouponDiscountPolicy(
                code = "WELCOME10",
                flatAmount = Money.won(10_000),
                minOrderAmount = Money.won(100_000),
            ),
        )
    )

    println("상품 합계: ${order.subtotal}")
    println("적용 할인: ${discountPolicy.description}")
    println("최종 결제 금액: ${order.total(discountPolicy)}")

    order.transitionTo(OrderStatus.Paid)
    order.transitionTo(OrderStatus.Shipped)

    // 잘못된 역행 전이 — sealed class 규칙이 막아줌
    runCatching { order.transitionTo(OrderStatus.Created) }
        .onFailure { println("전이 실패: ${it.message}") }

    order.transitionTo(OrderStatus.Delivered)
}
