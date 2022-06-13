package com.kata.spring.type_token

import com.kata.spring.datasource.DataSourceTestApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


/**
 * @author Jay
 */
@SpringBootApplication
class Application {
}

@RestController
class MyController {

    @GetMapping("/")
    fun users(): List<User> {
        return listOf(User("asfsa", Child("fsaf")), User("asfsa1", Child("fsaf")), User("asfsa2", Child("fsaf")))
    }
}

data class User(
    val name: String,
    val child: Child
)

data class Child(
    val name: String
)

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
