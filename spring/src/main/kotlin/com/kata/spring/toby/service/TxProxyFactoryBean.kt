package com.kata.spring.toby.service

import org.springframework.beans.factory.FactoryBean
import org.springframework.transaction.PlatformTransactionManager
import java.lang.reflect.Proxy


/**
 * @author Jay
 */
class TxProxyFactoryBean(
    var target: Any,
    val transactionManager: PlatformTransactionManager,
    val pattern: String,
    val serviceInterface: Class<*>
): FactoryBean<Any> {
    override fun getObject(): Any? {
        val transactionAdvice = TransactionAdvice(
            transactionManager = transactionManager,
        )

        return null
    }

    override fun getObjectType(): Class<*>? {
        return serviceInterface
    }

    override fun isSingleton(): Boolean {
        return false
    }
}