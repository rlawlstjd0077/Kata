package com.kata.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
class Application {
}

@RestController
class MainController {
    @GetMapping("/hello")
    fun hello(): String {
        return "hello!"
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
