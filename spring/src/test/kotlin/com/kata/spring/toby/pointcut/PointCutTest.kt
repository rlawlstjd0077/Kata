package com.kata.spring.toby.pointcut

import org.junit.jupiter.api.Test
import org.springframework.aop.aspectj.AspectJExpressionPointcut
import strikt.api.expectThat
import strikt.assertions.isEqualTo


/**
 * @author Jay
 */
class Target() {
    fun hello() {}
    fun hello(a: String) {}
    fun minus(a: Int, b: Int): Int { return 0 }
    fun plus(a: Int, b: Int): Int { return 0 }
    fun method() { }
}

class Bean() {
    fun method() {}
}

class PointCutTest {
    @Test
    fun `method Signature Pointcut 테스트`() {
        // given
        val pointcut = AspectJExpressionPointcut()
        pointcut.expression = "execution(public int com.kata.spring.toby.pointcut.Target.minus(int,int))"
        // when
        // then

        expectThat(pointcut.classFilter.matches(Target::class.java) &&
                pointcut.methodMatcher.matches(Target::class.java.getMethod("minus", Int::class.java, Int::class.java), null)) isEqualTo true

        expectThat(pointcut.classFilter.matches(Target::class.java) &&
                pointcut.methodMatcher.matches(Target::class.java.getMethod("plus", Int::class.java, Int::class.java), null)) isEqualTo false

        expectThat(pointcut.classFilter.matches(Target::class.java) &&
                pointcut.methodMatcher.matches(Target::class.java.getMethod("hello"), null)) isEqualTo false
    }

    @Test
    fun `전체 point cut 테스트`() {
        // given
        targetClassPointcutMatches("execution(* *(..))", true, true, true, true, true, true)
        targetClassPointcutMatches("execution(* hello(..))", true, true, false, false, false, false)

        // when

        // then
    }

    private fun targetClassPointcutMatches(expression: String, vararg expected: Boolean) {
        pointcutMatches(expression, expected[0], Target::class.java, "hello")
        pointcutMatches(expression, expected[1], Target::class.java, "hello", String::class.java)
        pointcutMatches(expression, expected[2], Target::class.java, "minus", Int::class.java, Int::class.java)
        pointcutMatches(expression, expected[3], Target::class.java, "plus", Int::class.java, Int::class.java)
        pointcutMatches(expression, expected[4], Target::class.java, "method")
        pointcutMatches(expression, expected[5], Bean::class.java, "method")
    }

    private fun pointcutMatches(expression: String, expected: Boolean, clazz: Class<*>, methodName: String, vararg args: Class<*>) {
        val pointcut = AspectJExpressionPointcut()
        pointcut.expression = expression

        expectThat(pointcut.classFilter.matches(clazz) && pointcut.methodMatcher.matches(clazz.getMethod(methodName, *args), null)) isEqualTo expected
    }
}