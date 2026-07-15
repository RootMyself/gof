package adaptor

// 우리 시스템 Target 인터페이스
interface PaymentGateway {
    fun pay(orderId: String, amount: Long): PaymentResult
}