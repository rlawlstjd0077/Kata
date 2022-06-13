package com.kata.flux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.stream.Stream


/**
 * @author Jay
 */
@SpringBootApplication
open class FluxApplication {
}

@RestController
class MyController {
    /*
    @GetMapping("/hello")
    fun hello(): Mono<String> {
        println("log1")
        val m = Mono.fromSupplier { generateHello() }.log()
        m.subscribe()
        //val m = Mono.just(generateHello()).log()
        println("log2")
        return m
    }
    */

    @GetMapping("/event/{id}")
    fun hello(
        @PathVariable id: Long
    ): Mono<Event> {
        return Mono.just(Event(2, "www$id"))
    }

    @GetMapping("/events", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun events(): Flux<Event> {
        val es = Flux.generate<Event, Long>(
            { 1L },
            { id, sink ->
                sink.next(Event(id, "value$id"))
                id + 1
            }
        )
        val interval = Flux.interval(Duration.ofSeconds(1))
        return Flux.zip(es, interval).map {
            it.t1
        }
    }
}

data class Event(
    val id: Long,
    val name: String
)

fun main(args: Array<String>) {
    runApplication<FluxApplication>(*args)
}
