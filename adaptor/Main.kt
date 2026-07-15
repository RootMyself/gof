package adaptor

class OrderService(private val paymentGateway: PaymentGateway) {
    fun processOrder(orderId: String, amount: Long) {
        val result = paymentGateway.pay(orderId, amount)
        if (result.success) println("결제 성공: ${result.transactionId}")
    }
}

fun main(args: Array<String>) {
    val tossClient = TossPaymentClient()
    val paymentGateway: PaymentGateway = TossPaymentAdaptor(tossClient)
    val orderService = OrderService(paymentGateway)

    orderService.processOrder(
        orderId = "ORD-001",
        amount = 15000L
    )
}