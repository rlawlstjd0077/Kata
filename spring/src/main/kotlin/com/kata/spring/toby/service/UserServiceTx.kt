package com.kata.spring.toby.service

import com.kata.spring.toby.User
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition


/**
 * @author Jay
 */
@Service(value = "userService")
class UserServiceTx(
    private val userService: UserServiceImpl,
    private val transactionManager: PlatformTransactionManager,
): UserService {
    override fun add(user: User) {
        userService.add(user)
    }

    override fun upgradeLevels() {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())

        try {
            userService.upgradeLevels()
            transactionManager.commit(status)
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }
}