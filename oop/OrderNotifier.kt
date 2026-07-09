package oop

/**
 * 상속 예시: 알림 발송 흐름(notify)은 부모가 고정하고,
 * 메시지 형식/발송 채널만 자식이 구체화(템플릿 메서드). 인터페이스가 아니라
 * 추상 클래스를 쓴 이유 — 공통 로직(notify)을 실제로 공유해야 하기 때문.
 */
abstract class OrderNotifier(protected val target: String) {

    fun notify(order: Order, status: OrderStatus) {
        send(buildMessage(order, status))
    }

    protected abstract fun buildMessage(order: Order, status: OrderStatus): String
    protected abstract fun send(message: String)
}

class EmailNotifier(target: String) : OrderNotifier(target) {
    override fun buildMessage(order: Order, status: OrderStatus) =
        "[Email → $target] 주문 #${order.id} 상태 변경: ${status::class.simpleName}"

    override fun send(message: String) = println(message)
}

class SmsNotifier(target: String) : OrderNotifier(target) {
    override fun buildMessage(order: Order, status: OrderStatus) =
        "[SMS → $target] 주문 #${order.id} → ${status::class.simpleName}"

    override fun send(message: String) = println(message)
}
