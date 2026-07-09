package oop

/**
 * 캡슐화 예시: 원시 타입(Long/Double) 대신 값 객체로 감싸 불변식(음수 금액 불가)을 강제.
 * 외부에서 amount를 직접 조작할 수 없고, 연산도 이 클래스가 제공하는 메서드로만 가능.
 */
@JvmInline
value class Money(val amount: Long) : Comparable<Money> {

    init {
        require(amount >= 0) { "금액은 음수 불가: $amount" }
    }

    operator fun plus(other: Money): Money = Money(amount + other.amount)

    operator fun minus(other: Money): Money {
        val result = amount - other.amount
        require(result >= 0) { "차감 결과가 음수: $amount - ${other.amount}" }
        return Money(result)
    }

    operator fun times(factor: Int): Money = Money(amount * factor)

    operator fun times(rate: Double): Money = Money((amount * rate).toLong())

    override fun compareTo(other: Money): Int = amount.compareTo(other.amount)

    override fun toString(): String = "₩${"%,d".format(amount)}"

    companion object {
        val ZERO = Money(0)
        fun won(amount: Long): Money = Money(amount)
    }
}
