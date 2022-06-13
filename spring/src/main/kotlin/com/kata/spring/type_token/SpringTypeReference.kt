package com.kata.spring.type_token

import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate


/**
 * @author Jay
 */

fun main() {
    val restTemplate = RestTemplate()
    val users =
        restTemplate.exchange("http://localhost:8080", HttpMethod.GET, null, object : ParameterizedTypeReference<List<User>>(){}).body
    println()
}
