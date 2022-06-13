package com.kata.spring.type_token

import org.springframework.core.ResolvableType


/**
 * @author Jay
 */
fun main() {
    val rs = ResolvableType.forInstance(object : TypeReference<Map<String, List<String>>>(){})
    println(rs.superType.generics[0].getNested(3).type)
}
