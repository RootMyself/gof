package solid

/**
 * DIP 위반: 고수준 모듈(OrderService)이 저수준 구현(BadSmtpEmailSender)을 직접 생성해 의존.
 * Slack 알림으로 바꾸려면 OrderService 코드 자체를 고쳐야 하고,
 * 테스트할 때도 실제 메일 발송 경로를 피할 수 없음.
 */
class BadSmtpEmailSender {
    fun sendEmail(to: String, message: String) = println("[SMTP → $to] $message")
}

class BadOrderService {
    private val emailSender = BadSmtpEmailSender()

    fun placeOrder(orderId: String, customerEmail: String) {
        println("주문 처리: $orderId")
        emailSender.sendEmail(customerEmail, "주문 $orderId 이(가) 접수되었습니다")
    }
}

/**
 * DIP 준수: OrderService는 추상화(Notifier)에만 의존하고 구현체는 생성자로 주입받음.
 * 채널 교체나 테스트용 가짜 구현으로 바꿀 때 OrderService 코드는 건드릴 필요가 없음.
 */
interface Notifier {
    fun notify(target: String, message: String)
}

class EmailNotifier : Notifier {
    override fun notify(target: String, message: String) = println("[SMTP → $target] $message")
}

class SlackNotifier : Notifier {
    override fun notify(target: String, message: String) = println("[Slack → $target] $message")
}

class OrderService(private val notifier: Notifier) {
    fun placeOrder(orderId: String, customerContact: String) {
        println("주문 처리: $orderId")
        notifier.notify(customerContact, "주문 $orderId 이(가) 접수되었습니다")
    }
}
