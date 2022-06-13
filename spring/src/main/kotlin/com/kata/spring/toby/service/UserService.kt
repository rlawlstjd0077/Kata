package com.kata.spring.toby.service

import com.kata.spring.toby.Level
import com.kata.spring.toby.User
import com.kata.spring.toby.repository.UserDao
import org.springframework.stereotype.Service


/**
 * @author Jay
 */
@Service
class UserService(
    private val userDao: UserDao
) {

    fun add(user: User) {
        if (user.level == null) user.level = Level.BASIC
        userDao.add(user)
    }

    fun upgradeLevels() {
        val users = userDao.getAll()

        users.forEach {

            var changed = false

            if (canUpgradeLevel(it)) {
                upgradeLevel(it)
            }

            if (it.level == Level.BASIC && it.login >= 50) {
                it.level = Level.SILVER
                changed = true
            } else if (it.level == Level.SILVER && it.recommend >= 30) {
               it.level = Level.GOLD
                changed = true
            } else if (it.level == Level.GOLD) {
                changed = false
            } else {
                changed = false
            }

            if (changed) userDao.update(it)
        }
    }

    private fun upgradeLevel(user: User) {
        user.upgrade()
        userDao.update(user)
    }

    private fun canUpgradeLevel(user: User): Boolean {
        return when(user.level) {
            Level.BASIC -> user.login >= 50
            Level.SILVER -> user.recommend >= 30
            Level.GOLD -> false
            null -> throw java.lang.IllegalArgumentException("Unknown Level: ${user.level}")
        }
    }

    private fun User.upgrade() {
        val nextLevel = level!!.next

        if (nextLevel == null) {
            throw IllegalArgumentException("$level 은 업그레이드 불가함")
        } else {
            level = nextLevel
        }
    }
}

