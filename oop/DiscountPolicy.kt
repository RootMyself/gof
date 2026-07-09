
package oop

/**
 * 추상화 예시: "할인을 어떻게 계산하는지"는 각 구현체 내부에 숨기고,
 * 바깥에는 apply(subtotal) 하나의 계약만 노출.
 */
interface DiscountPolicy {
    val description: String // 추상 프로퍼티
    fun apply(subtotal: Money): Money
}

class TierDiscountPolicy(private val tier: CustomerTier) : DiscountPolicy {
    override val description = "${tier.name} 등급 할인 (${(tier.discountRate * 100).toInt()}%)"
    override fun apply(subtotal: Money): Money = subtotal * tier.discountRate
}

class CouponDiscountPolicy(
    private val code: String,
    private val flatAmount: Money,
    private val minOrderAmount: Money,
) : DiscountPolicy {
    override val description = "쿠폰 [$code] 정액 할인"
    override fun apply(subtotal: Money): Money =
        if (subtotal >= minOrderAmount) flatAmount else Money.ZERO
}

/**
 * 합성 예시: 여러 DiscountPolicy를 리스트로 들고 있다가 합산.
 * 실무에서 흔한 버그 포인트 — 할인을 단순 합산하면 subtotal을 초과할 수 있어 상한 처리 필요.
 */
class CombinedDiscountPolicy(private val policies: List<DiscountPolicy>) : DiscountPolicy {
    override val description = policies.joinToString(" + ") { it.description }

    override fun apply(subtotal: Money): Money {
        val total = policies.fold(Money.ZERO) { acc, policy -> acc + policy.apply(subtotal) }
        return if (total > subtotal) subtotal else total
    }
}
