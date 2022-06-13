package com.kata.spring.toby.repository

import com.kata.spring.toby.Level
import com.kata.spring.toby.User
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.core.JdbcTemplate
import javax.annotation.PostConstruct
import javax.sql.DataSource

interface UserDao {
    fun deleteAll()
    fun add(user: User)
    fun get(id: String): User
    fun getCount(): Int
    fun getAll(): List<User>
    fun update(user: User)
}
