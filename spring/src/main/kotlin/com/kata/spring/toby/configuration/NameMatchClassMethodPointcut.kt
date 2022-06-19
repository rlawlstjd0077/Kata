package com.kata.spring.toby.configuration

import org.springframework.aop.ClassFilter
import org.springframework.aop.support.NameMatchMethodPointcut
import org.springframework.util.PatternMatchUtils


/**
 * @author Jay
 */
class NameMatchClassMethodPointcut(): NameMatchMethodPointcut() {
    fun setMappedClassName(mappedClassName: String) {
        classFilter = SimpleClassFilter(mappedClassName)
    }

    class SimpleClassFilter(
        private val mappedName: String
    ): ClassFilter {
        override fun matches(clazz: Class<*>): Boolean {
            return PatternMatchUtils.simpleMatch(mappedName, clazz.simpleName)
        }
    }
}