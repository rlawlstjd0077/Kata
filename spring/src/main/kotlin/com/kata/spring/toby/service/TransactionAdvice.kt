package com.kata.spring.toby.service

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.lang.RuntimeException
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


/**
 * @author Jay
 */
class TransactionAdvice(private val transactionManager: PlatformTransactionManager): MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any? {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
        try {
            val ret = invocation.proceed()
            transactionManager.commit(status)
            return ret
        } catch (e: RuntimeException) {
            transactionManager.rollback(status)
            throw e
        }
    }
}