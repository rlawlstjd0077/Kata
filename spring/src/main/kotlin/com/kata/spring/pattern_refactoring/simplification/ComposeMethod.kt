package com.kata.spring.pattern_refactoring.simplification


/**
 * 어떤 메서드의 내부 로직이 한 눈에 이해하기 어렵다면 그 로직을 의도가 잘 드러나며 동등한 수준의 작업을 하는 여러 단계로 나눈다.
 * @author Jay
 */
class AsIsMyList<T : Any>(
    private val readOnly: Boolean = false
) {
    var elements = Array<Any>(INITIAL_CAPACITY) { }

    val size: Int
        get() = elements.size


    fun add(element: T) {
        if (readOnly) {
            return
        }

        val newSize = size + 1

        if (newSize > elements.size) {
            val newElements = Array<Any>(size + INITIAL_CAPACITY) { }
            elements.forEachIndexed { index, value -> newElements[index] = value }
            elements = newElements
        }

        elements[size + 1] = element
    }

    companion object {
        private const val INITIAL_CAPACITY = 10
    }
}

class ToBeMyList<T : Any>(
    private val readOnly: Boolean = false
) {
    var elements = Array<Any>(GROTH_INCREMENT) { }

    val size: Int get() = elements.size


    fun add(element: T) {
        if (readOnly) {
            return
        }

        if (atCapacity()) {
            grow()
        }

        addElement(element)
    }

    private fun addElement(element: T) {
        elements[size + 1] = element
    }

    private fun atCapacity(): Boolean {
        val newSize = size + 1
        return newSize > elements.size
    }

    private fun grow() {
        val newElements = Array<Any>(size + GROTH_INCREMENT) { }
        elements.forEachIndexed { index, value -> newElements[index] = value }
        elements = newElements
    }

    companion object {
        private const val GROTH_INCREMENT = 10
    }
}