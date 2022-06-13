package com.kata.spring.datasource

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


/**
 * @author Jay
 */
@SpringBootApplication
class DataSourceTestApplication {
}

fun main(args: Array<String>) {
    runApplication<DataSourceTestApplication>(*args)
}
