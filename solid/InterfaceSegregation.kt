package solid

/**
 * ISP 위반: 하나의 뚱뚱한 인터페이스가 모든 사무기기 기능을 강제.
 * SimplePrinter는 스캔/팩스 기능이 없는데도 구현해야 해서 예외 던지는 죽은 코드가 생김.
 */
interface BadOfficeDevice {
    fun print(document: String)
    fun scan(document: String): String
    fun fax(document: String, to: String)
}

class BadSimplePrinter : BadOfficeDevice {
    override fun print(document: String) = println("[Print] $document")
    override fun scan(document: String): String = throw UnsupportedOperationException("스캔 기능 없음")
    override fun fax(document: String, to: String): Unit = throw UnsupportedOperationException("팩스 기능 없음")
}

/**
 * ISP 준수: 기능별로 인터페이스를 쪼개, 클래스는 실제 지원하는 기능만 구현.
 */
interface Printer {
    fun print(document: String)
}

interface Scanner {
    fun scan(document: String): String
}

interface FaxMachine {
    fun fax(document: String, to: String)
}

class SimplePrinter : Printer {
    override fun print(document: String) = println("[Print] $document")
}

class AllInOnePrinter : Printer, Scanner, FaxMachine {
    override fun print(document: String) = println("[Print] $document")
    override fun scan(document: String): String = "scanned:$document"
    override fun fax(document: String, to: String) = println("[Fax → $to] $document")
}
