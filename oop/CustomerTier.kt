package oop

/**
 * 다형성 예시: enum 상수마다 다른 동작(benefits)을 오버라이드.
 * if/when으로 등급별 분기하는 대신 각 상수가 자기 행동을 스스로 안다.
 */
enum class CustomerTier(val discountRate: Double) {
    BRONZE(0.0) {
        override fun benefits() = "기본 혜택 없음"
    },
    SILVER(0.03) {
        override fun benefits() = "3% 상시 할인"
    },
    GOLD(0.07) {
        override fun benefits() = "7% 상시 할인 + 무료 배송"
    },
    VIP(0.15) {
        override fun benefits() = "15% 상시 할인 + 전담 상담원"
    };

    abstract fun benefits(): String
}
