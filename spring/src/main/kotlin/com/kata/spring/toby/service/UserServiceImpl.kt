package com.kata.spring.toby.service

import com.kata.spring.toby.Level
import com.kata.spring.toby.User
import com.kata.spring.toby.repository.UserDao
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition


/**
 * @author Jay
 */
@Service()
open class UserServiceImpl(
    private val mailSender: MailSender,
    private val userDao: UserDao
) : UserService {

    override fun add(user: User) {
        if (user.level == null) user.level = Level.BASIC
        userDao.add(user)
    }

    override fun upgradeLevels() {
        upgradeLevelsInternal()
    }

    private fun upgradeLevelsInternal() {
        val users = userDao.getAll()

        users.forEach {
            if (canUpgradeLevel(it)) {
                upgradeLevel(it)
            }
        }
    }

    protected fun upgradeLevel(user: User) {
        user.upgrade()
        userDao.update(user)
        sendUpgradeEmail(user)
    }

    private fun sendUpgradeEmail(user: User) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(user.email)
        mailMessage.from = "admin@gmail.com"
        mailMessage.subject = "Upgrade 안내"
        mailMessage.setText("사용자님의 등급이 ${user.level!!.name} 로 업그레이드 되었습니다.")

        mailSender.send(mailMessage)
    }

    private fun canUpgradeLevel(user: User): Boolean {
        return when (user.level) {
            Level.BASIC -> user.login >= MIN_LOGCOUNT_FOR_SILVER
            Level.SILVER -> user.recommend >= MIN_RECOMMEND_FOR_GOLD
            Level.GOLD -> false
            null -> throw java.lang.IllegalArgumentException("Unknown Level: ${user.level}")
        }
    }

    companion object {
        const val MIN_LOGCOUNT_FOR_SILVER = 50
        const val MIN_RECOMMEND_FOR_GOLD = 30
    }
}

