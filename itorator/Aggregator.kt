package itorator

/**
 * Aggregator 역할: Iterator를 생성(+create)하는 집합체 인터페이스
 */
interface Aggregator<T> {
    fun iterator(): Iterator<T>
}
