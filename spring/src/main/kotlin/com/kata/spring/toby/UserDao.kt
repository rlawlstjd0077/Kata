package com.kata.spring.toby

import com.kata.spring.utils.notNull
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import javax.sql.DataSource
import kotlin.math.log


/**
 * @author Jay
 */
class UserDao(
    private val dataSource: DataSource
) {
    private val jdbcTemplate = JdbcTemplate(dataSource)

    private val userMapper = RowMapper<User> { rs, _ ->
        User(
            id = rs.getString("id"),
            name = rs.getString("name"),
            password = rs.getString("password"),
            level = Level.valueOf(rs.getString("level")),
            login = rs.getInt("login"),
            recommend = rs.getInt("recommend")
        )
    }

    fun deleteAll() {
        jdbcTemplate.update("delete from users")
    }

    fun add(user: User) {
        val sql = "insert into users(id, name, password, level, login, recommend) values(?,?,?,?,?,?)"
        jdbcTemplate.update(sql, user.id, user.name, user.password, user.level.name, user.login, user.recommend)
    }

    fun get(id: String): User {
        return jdbcTemplate.queryForObject("select * from users where id = ?", arrayOf(id), userMapper)
            .notNull { "유효하지 않은 id" }
    }

    fun getCount(): Int {
        return jdbcTemplate.queryForObject("select count(*) from users", Int::class.java).notNull()
    }

    fun getAll(): List<User> {
        return jdbcTemplate.query("select * from users order by id", userMapper)
    }
}