package com.kata.spring.toby


/**
 * @author Jay
 */
enum class Level(level: Int) {
    BASIC(1),
    SILVER(2),
    GOLD(3);

    companion object {
        fun valueOf(value: Int) {
            when(value) {
                1 -> BASIC
                2 -> SILVER
                3 -> GOLD
                else -> throw java.lang.AssertionError("Unknown value: " + value)
            }
        }
    }
}

data class User(
    val id: String,
    val name: String,
    val password: String
)