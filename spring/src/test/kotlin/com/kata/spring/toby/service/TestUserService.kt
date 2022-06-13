package com.kata.spring.toby.service

import com.kata.spring.toby.User
import com.kata.spring.toby.repository.UserDao
import org.springframework.mail.MailSender
import org.springframework.transaction.PlatformTransactionManager


/**
 * @author Jay
 */
class TestUserService(
    private val id: String,
    private val mailSender: MailSender,
    private val transactionManager: PlatformTransactionManager,
    private val userDao: UserDao
) : UserService(mailSender, transactionManager, userDao) {
    override fun upgradeLevel(user: User) {
        if (user.id == this.id) {
            throw TestServiceExecption()
        }
        super.upgradeLevel(user)
    }
}

class TestServiceExecption: RuntimeException()