package strategy

fun main() {
    val printer = SumPrinter()
    printer.print(SimpleSumStrategy(), 100)
    printer.print(GaussSumStrategy(), 100)
}
