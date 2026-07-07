package strategy

class SimpleSumStrategy : SumStrategy {
    override fun sum(n: Int): Int {
        var sum = 0
        for (i in 1..n) {
            sum += i
        }
        return sum
    }
}
