package com.kata.spring.toby.service

import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method


/**
 * @author Jay
 */
class TransactionHandler(
    private val target: Any,
    private val transactionManager: PlatformTransactionManager,
    private val patter: String
): InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
        if (method.name.startsWith(patter)) {
            return invokeTransaction(method, args)
        } else {
            return method.invoke(args)
        }
    }

    private fun invokeTransaction(method: Method, args: Array<out Any>?): Any {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
        try {
            val nonNullArgs = args ?: arrayOf()
            val ret = method.invoke(target, *nonNullArgs)
            transactionManager.commit(status)
            return ret
        } catch (e: InvocationTargetException) {
            transactionManager.rollback(status)
            throw e.targetException
        }
    }
}