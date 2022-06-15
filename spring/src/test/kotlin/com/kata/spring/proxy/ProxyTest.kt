package com.kata.spring.proxy

import org.junit.jupiter.api.Test
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
    fun `proxy test`() {
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
}