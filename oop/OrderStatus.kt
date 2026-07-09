package oop

/**
 * 상태 캡슐화 예시: sealed class로 가능한 상태를 한정하고,
 * canTransitionTo에서 유효 전이만 허용. when이 sealed 하위타입을 다 못 덮으면 컴파일 에러.
 */
sealed class OrderStatus {
    object Created : OrderStatus()
    object Paid : OrderStatus()
    object Shipped : OrderStatus()
    object Delivered : OrderStatus()
    data class Cancelled(val reason: String) : OrderStatus()

    fun canTransitionTo(next: OrderStatus): Boolean = when (this) {
        is Created -> next is Paid || next is Cancelled
        is Paid -> next is Shipped || next is Cancelled
        is Shipped -> next is Delivered
        is Delivered -> false
        is Cancelled -> false
    }
}
