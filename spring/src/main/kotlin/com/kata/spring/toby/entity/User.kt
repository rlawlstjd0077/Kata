package com.kata.spring.toby


/**
 * @author Jay
 */
enum class Level(val level: Int, val _next: Int?) {
    BASIC(1, 2),
    SILVER(2, 3),
    GOLD(3, null);

    val next: Level?
        get() = _next?.let { Companion.valueOf(it) }

    companion object {
        fun valueOf(value: Int): Level {
            return when(value) {
                1 -> BASIC
                2 -> SILVER
                3 -> GOLD
                else -> throw java.lang.AssertionError("Unknown value: $value")
            }
        }
    }
}

data class User(
    val id: String,
    val name: String,
    val password: String,
    var level: Level?,
    var login: Int,
    val recommend: Int,
    val email: String
) {

    fun upgrade() {
        val nextLevel = level!!.next

        if (nextLevel == null) {
            throw IllegalArgumentException("$level 은 업그레이드 불가함")
        } else {
            level = nextLevel
        }
    }
}