package com.kata.flux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@SpringBootApplication
open class Application {
}

@RestController
class MainController {
    @GetMapping("/hello")
    fun hello(): Mono<String> {
        println("log1")
        val m = Mono.fromSupplier { generateHello() }.log()
        m.subscribe()
        //val m = Mono.just(generateHello()).log()
        println("log2")
        return m
    }

    fun generateHello(): String {
        println("generated")
        return "Hello"
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
