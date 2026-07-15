package adaptor

import java.util.UUID

// Adaptor: Toss 응답 -> 우리 시스템 형태로 변환
class TossPaymentAdaptor(
    private val tossClient: TossPaymentClient
) : PaymentGateway {

    override fun pay(orderId: String, amount: Long): PaymentResult {
        val response = tossClient.requestPayment(orderId, amount.toInt())
        return PaymentResult(
            success = response.resultCode == "0000",
            transactionId = response.tid
        )
    }
}