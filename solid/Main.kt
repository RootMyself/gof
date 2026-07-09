package solid

fun main() {
    println("=== SRP: 판매 리포트 생성/저장/발송 ===")
    val sales = listOf(Sale("키보드", 89_000), Sale("마우스", 45_000))
    val reportService = SalesReportService(
        calculator = SalesReportCalculator(),
        formatter = CsvReportFormatter(),
        repository = InMemoryReportRepository(),
        mailer = ConsoleReportMailer(),
    )
    reportService.generateAndSend(sales, "sb92120@gmail.com")

    println("\n=== OCP: 결제 수단별 수수료 계산 ===")
    val calculators: Map<String, FeeCalculator> = mapOf(
        "CARD" to CardFeeCalculator(),
        "BANK_TRANSFER" to BankTransferFeeCalculator(),
        "CRYPTO" to CryptoFeeCalculator(),
    )
    println("카드 수수료: ${calculators.getValue("CARD").calculate(134_000)}")

    println("\n=== LSP: 읽기 전용 vs 쓰기 가능 재고 ===")
    val readOnly: InventoryReader = ReadOnlyInventoryRepository(mapOf("SKU-1" to 10))
    println("읽기 전용 재고: ${readOnly.currentStock("SKU-1")}")
    val writable: InventoryWriter = WritableInventoryRepository()
    writable.increase("SKU-1", 5)
    println("쓰기 가능 재고: ${writable.currentStock("SKU-1")}")

    println("\n=== ISP: 기능별로 분리된 사무기기 인터페이스 ===")
    val printer: Printer = SimplePrinter()
    printer.print("계약서.pdf")
    val allInOne = AllInOnePrinter()
    println(allInOne.scan("계약서.pdf"))

    println("\n=== DIP: 추상화(Notifier)에 의존하는 주문 서비스 ===")
    val orderService = OrderService(SlackNotifier())
    orderService.placeOrder("ORD-2026-0002", "#sales-alerts")
}
