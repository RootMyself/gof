package adaptor

data class PaymentResult(
    val success: Boolean,
    val transactionId: String,
)