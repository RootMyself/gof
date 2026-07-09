package solid

/**
 * OCP 위반: 결제 수단이 늘어날 때마다 이 함수 내부를 계속 고쳐야 함.
 * when 분기가 커질수록 기존 분기를 실수로 건드릴 위험도 커짐.
 */
class BadFeeCalculator {
    fun calculate(method: String, amountWon: Long): Long = when (method) {
        "CARD" -> (amountWon * 0.025).toLong()
        "BANK_TRANSFER" -> 500
        "CRYPTO" -> (amountWon * 0.01).toLong()
        else -> error("지원하지 않는 결제 수단: $method")
    }
}

/**
 * OCP 준수: 새 결제 수단이 추가돼도 기존 클래스는 손대지 않고 클래스만 추가하면 됨
 * (확장에는 열려 있고, 변경에는 닫혀 있음).
 */
interface FeeCalculator {
    fun calculate(amountWon: Long): Long
}

class CardFeeCalculator : FeeCalculator {
    override fun calculate(amountWon: Long): Long = (amountWon * 0.025).toLong()
}

class BankTransferFeeCalculator : FeeCalculator {
    override fun calculate(amountWon: Long): Long = 500
}

class CryptoFeeCalculator : FeeCalculator {
    override fun calculate(amountWon: Long): Long = (amountWon * 0.01).toLong()
}

// 나중에 PayPalFeeCalculator 등을 추가할 때도 위 클래스들은 그대로 둔다.

