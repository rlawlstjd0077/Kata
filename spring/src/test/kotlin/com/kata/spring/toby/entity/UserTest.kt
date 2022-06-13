package com.kata.spring.toby.entity

import com.kata.spring.toby.Level
import com.kata.spring.toby.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class UserTest {


    @Test
    fun `upgradeLevel() 테스트`() {
        // given
        val user = User("test", " test", "test", null, 0, 0, "test@mail.com")
        val levels = Level.values()

        // when
        // then
        levels.forEach {
            if (it.next != null) {
                user.level = it
                user.upgrade()
                expectThat(user.level) isEqualTo it.next
            }
        }

    }

    @Test
    fun `update 불가능한 상황에서 upgrade 시에 예외 발생`() {
        // given
        val user = User("test", " test", "test", null, 0, 0, "test@mail.com")
        val levels = Level.values()

        // when
        levels.forEach {
            if (it.next == null) {
                user.level = it
                assertThrows<IllegalArgumentException> {
                    user.upgrade()
                }
            }
        }

        // then
    }
}