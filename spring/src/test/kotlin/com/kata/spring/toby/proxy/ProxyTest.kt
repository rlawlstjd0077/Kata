package com.kata.spring.toby.proxy

import com.kata.spring.proxy.Hello
import com.kata.spring.proxy.HelloTarget
import com.kata.spring.proxy.UppercaseAdvice
import com.kata.spring.proxy.UppwercaseHandler
import org.junit.jupiter.api.Test
import org.springframework.aop.framework.ProxyFactoryBean
import org.springframework.aop.support.DefaultPointcutAdvisor
import org.springframework.aop.support.NameMatchMethodPointcut
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.lang.reflect.Proxy


/**
 * @author Jay
 */
class ProxyTest {
    @Test
    fun `simple`() {
        // given
        val helloTarget = HelloTarget()
        expectThat(helloTarget.sayHello("J")) isEqualTo "Hello J"
        expectThat(helloTarget.sayHi("J")) isEqualTo "Hi J"
        expectThat(helloTarget.sayThankYou("J")) isEqualTo "Thank you J"
        // when
        // then
    }
    


    @Test
    fun `simple proxy test`() {
        // given
        val proxy = Proxy.newProxyInstance(
            ClassLoader.getSystemClassLoader(),
            arrayOf(Hello::class.java),
            UppwercaseHandler(HelloTarget())
        ) as Hello

        expectThat(proxy.sayHello("J")) isEqualTo "HELLO J"
        expectThat(proxy.sayHi("J")) isEqualTo "HI J"
        expectThat(proxy.sayThankYou("J")) isEqualTo "THANK YOU J"

        // when

        // then
    }

    @Test
    fun `proxy factory bean test`() {
        // given
        val proxyFactoryBean = ProxyFactoryBean()
        proxyFactoryBean.setTarget(HelloTarget())
        proxyFactoryBean.addAdvice(UppercaseAdvice())

        val proxyedHello = proxyFactoryBean.`object` as Hello


        // when
        // then
        expectThat(proxyedHello.sayHello("J")) isEqualTo "HELLO J"
        expectThat(proxyedHello.sayHi("J")) isEqualTo "HI J"
        expectThat(proxyedHello.sayThankYou("J")) isEqualTo "THANK YOU J"
    }
    
    @Test
    fun `pointcut advisor 테스트`() {
        // given
        val pfBean = ProxyFactoryBean()
        pfBean.setTarget(HelloTarget())

        val nameMatchMethodPointcut = NameMatchMethodPointcut()
        nameMatchMethodPointcut.setMappedName("sayH*")

        pfBean.addAdvisor(DefaultPointcutAdvisor(nameMatchMethodPointcut, UppercaseAdvice()))


        val proxyedHello = pfBean.`object` as Hello


        // when
        // then
        expectThat(proxyedHello.sayHello("J")) isEqualTo "HELLO J"
        expectThat(proxyedHello.sayHi("J")) isEqualTo "HI J"
        expectThat(proxyedHello.sayThankYou("J")) isEqualTo "Thank you J"
    }
}