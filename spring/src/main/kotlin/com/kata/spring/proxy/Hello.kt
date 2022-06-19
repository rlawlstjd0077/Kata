package com.kata.spring.proxy

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.springframework.cglib.proxy.MethodProxy
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

interface Hello {
    fun sayHello(name: String): String
    fun sayHi(name: String): String
    fun sayThankYou(name: String): String
}

class HelloTarget: Hello {
    override fun sayHello(name: String): String {
        return "Hello " + name
    }

    override fun sayHi(name: String): String {
        return "Hi " + name
    }

    override fun sayThankYou(name: String): String {
        return "Thank you " + name
    }
}

class HelloUppercase(
    val hello: Hello
): Hello {
    override fun sayHello(name: String): String {
        return hello.sayHello(name).uppercase()
    }

    override fun sayHi(name: String): String {
        return hello.sayHi(name).uppercase()
    }

    override fun sayThankYou(name: String): String {
        return hello.sayThankYou(name).uppercase()
    }
}

class UppwercaseHandler(
    val target: Hello
): InvocationHandler {
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any {
        val ret = method.invoke(target, *args)
        return (ret as String).uppercase()
    }
}

class LowercaseAdvice(): MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any {
        val ret = invocation.proceed() as String
        return ret.lowercase()
    }
}

class UppercaseAdvice(): MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any {
        val ret = invocation.proceed() as String
        return ret.uppercase()
    }
}