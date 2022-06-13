package com.kata.spring.toby.repository

import com.kata.spring.toby.Level
import com.kata.spring.toby.User
import com.kata.spring.utils.notNull
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import javax.sql.DataSource


/**
 * @author Jay
 */
@Repository
class UserDaoJdbc(
    private val dataSource: DataSource
) : UserDao {
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

    override fun deleteAll() {
        jdbcTemplate.update("delete from users")
    }

    override fun add(user: User) {
        val sql = "insert into users(id, name, password, level, login, recommend) values(?,?,?,?,?,?)"
        jdbcTemplate.update(sql, user.id, user.name, user.password, user.level!!.name, user.login, user.recommend)
    }

    override fun get(id: String): User {
        return jdbcTemplate.queryForObject("select * from users where id = ?", arrayOf(id), userMapper)
            .notNull { "유효하지 않은 id" }
    }

    override fun getCount(): Int {
        return jdbcTemplate.queryForObject("select count(*) from users", Int::class.java).notNull()
    }

    override fun getAll(): List<User> {
        return jdbcTemplate.query("select * from users order by id", userMapper)
    }

    override fun update(user: User) {
        val sql = "update users set name = ?, password = ?, level = ?, login = ?," +
                "recommend = ? where id = ?"
        jdbcTemplate.update(
            sql, user.name, user.password, user.level!!.name, user.login, user.recommend, user.id
        )
    }
}