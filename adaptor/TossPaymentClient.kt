package adaptor

import java.util.UUID

// 외부 PG사 SDK (수정 불가)
class TossPaymentClient {
    fun requestPayment(orderNo: String, price: Int): TossResponse {
        // 실제로는 외부 API 호출
        return TossResponse(resultCode = "0000", tid = "toss_abc123")
    }
}