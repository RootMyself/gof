package solid

/**
 * SRP 위반: 계산 + 포맷팅 + 저장 + 메일 발송을 한 클래스가 전부 담당.
 * 포맷을 CSV → PDF로만 바꾸고 싶어도 이 클래스 전체를 다시 열어 테스트해야 함.
 */
class BadSalesReportService(private val sales: List<Sale>) {
    fun generateAndSend(recipient: String) {
        val total = sales.sumOf { it.amountWon }
        val csv = buildString {
            appendLine("product,amount")
            sales.forEach { appendLine("${it.product},${it.amountWon}") }
            appendLine("total,$total")
        }
        println("[Save] sales-report.csv 저장:\n$csv")
        println("[Email → $recipient] 리포트 발송 완료")
    }
}

// ---- SRP 준수: 계산 / 포맷팅 / 저장 / 발송 책임을 각각 별도 타입으로 분리 ----

class SalesReportCalculator {
    fun total(sales: List<Sale>): Long = sales.sumOf { it.amountWon }
}

interface ReportFormatter {
    fun format(sales: List<Sale>, total: Long): String
}

class CsvReportFormatter : ReportFormatter {
    override fun format(sales: List<Sale>, total: Long): String = buildString {
        appendLine("product,amount")
        sales.forEach { appendLine("${it.product},${it.amountWon}") }
        appendLine("total,$total")
    }
}

interface ReportRepository {
    fun save(content: String)
}

class InMemoryReportRepository : ReportRepository {
    private val saved = mutableListOf<String>()
    override fun save(content: String) {
        saved += content
        println("[Save] 리포트 ${saved.size}건 저장됨")
    }
}

interface ReportMailer {
    fun send(recipient: String, content: String)
}

class ConsoleReportMailer : ReportMailer {
    override fun send(recipient: String, content: String) =
        println("[Email → $recipient] 리포트 발송 완료")
}

/** 조립만 담당하는 오케스트레이터. 포맷/저장/발송 방식이 바뀌어도 이 클래스는 그대로. */
class SalesReportService(
    private val calculator: SalesReportCalculator,
    private val formatter: ReportFormatter,
    private val repository: ReportRepository,
    private val mailer: ReportMailer,
) {
    fun generateAndSend(sales: List<Sale>, recipient: String) {
        val total = calculator.total(sales)
        val content = formatter.format(sales, total)
        repository.save(content)
        mailer.send(recipient, content)
    }
}
