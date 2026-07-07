package strategy

class GaussSumStrategy : SumStrategy {
    override fun sum(n: Int): Int = (n + 1) * (n / 2)
}
