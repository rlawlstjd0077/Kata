package com.kata.spring.aop

import org.aopalliance.intercept.MethodInterceptor
import org.aopalliance.intercept.MethodInvocation
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.aop.ClassFilter
import org.springframework.aop.Pointcut
import org.springframework.aop.framework.ProxyFactoryBean
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut


/**
 * @author Jay
 */
class AdvisorTest {

    @Test
    fun testClassNamePointcutAdvisor() {
        // given
        val classMethodPointcut = object : NameMatchMethodPointcut() {
            override fun getClassFilter(): ClassFilter {
                return ClassFilter {
                    it.simpleName.startsWith("HelloT")
                }
            }
        }

        classMethodPointcut.setMappedName("sayH*")

        checkAdviced(HelloTaret(), classMethodPointcut, true)

        class HelloWorld: HelloTaret()

        checkAdviced(HelloWorld(), classMethodPointcut, false)

        // when

        // then
    }

    private fun checkAdviced(target: Any, pointcut: Pointcut, adviced: Boolean) {
        val proxyFactoryBean = ProxyFactoryBean()
        proxyFactoryBean.setTarget(target)
        proxyFactoryBean.addAdvisor(DefaultPointcutAdvisor(pointcut,  UppercaseAdvice()))
        val proxiedHello = proxyFactoryBean.`object` as Hello

        if (adviced) {
            Assertions.assertThat(proxiedHello.sayHi("Jay")).isEqualTo("HI JAY")
            Assertions.assertThat(proxiedHello.sayThankYou("Jay")).isEqualTo("Thank you Jay")
        } else {
            Assertions.assertThat(proxiedHello.sayHi("Jay")).isEqualTo("Hi Jay")
            Assertions.assertThat(proxiedHello.sayThankYou("Jay")).isEqualTo("Thank you Jay")
        }
    }
}


class UppercaseAdvice: MethodInterceptor {
    override fun invoke(invocation: MethodInvocation): Any {
        val ret = invocation.proceed() as String
        return ret.uppercase()
    }
}

interface Hello {
    fun sayHi(name: String): String
    fun sayThankYou(name: String): String
}

open class HelloTaret: Hello {
    override fun sayHi(name: String): String {
        return "Hi $name"
    }

    override fun sayThankYou(name: String): String {
        return "Thank you $name"
    }
}
