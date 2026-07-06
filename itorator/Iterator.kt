package itorator

/**
 * Iterator 역할: 요소를 순서대로 접근하는 인터페이스
 */
interface Iterator<T> {
    fun hasNext(): Boolean
    fun next(): T
}
