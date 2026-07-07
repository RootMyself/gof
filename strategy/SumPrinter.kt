package strategy

class SumPrinter {
    fun print(strategy: SumStrategy, n: Int) {
        print("Sum of 1 ~ $n: ")
        println(strategy.sum(n))
    }
}
