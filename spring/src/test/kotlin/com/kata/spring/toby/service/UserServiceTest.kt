package com.kata.spring.toby.service

import com.kata.spring.toby.Level
import com.kata.spring.toby.Toby
import com.kata.spring.toby.TobyTestDatasourceConfig
import com.kata.spring.toby.User
import com.kata.spring.toby.repository.UserDao
import com.kata.spring.toby.repository.UserDaoJdbc
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import javax.annotation.PostConstruct
import javax.sql.DataSource

/**
 * TODO 특정 패키지 아래로 Component Scan 수행하도록 수정 필요함
 */
@SpringBootTest(classes = [UserService::class, UserDaoJdbc::class, TobyTestDatasourceConfig::class])
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userDao: UserDao

    private lateinit var jdbcTemplate: JdbcTemplate


    @PostConstruct
    fun setUp() {
        jdbcTemplate = JdbcTemplate(dataSource)

        try  {
            jdbcTemplate.execute("create table users (id varchar , name varchar, password varchar, level varchar, login varchar, recommend varchar )")
        } catch (e: BadSqlGrammarException) {
            /**
             * Class 레벨로 한번만 실행할 방법을 찾지 못해 우선 이렇게 처리 ..
             */
        }
        userDao.deleteAll()
    }


    @Test
    fun `add() 테스트`() {
        // given
        val userWithLevel = users[4]    //GOLD
        val userWithoutLevel = users[0].copy(level = null)

        userService.add(userWithLevel)
        userService.add(userWithoutLevel)
        // when

        val userWithLevelRead = userDao.get(userWithLevel.id)
        val userWithoutLevelRead = userDao.get(userWithoutLevel.id)

        // then
        expectThat(userWithLevelRead.level) isEqualTo userWithLevel.level
        expectThat(userWithoutLevelRead.level) isEqualTo userWithoutLevel.level
    }

    @Test
    fun `upgradeLevels() 테스트`() {

        users.forEach {
            userDao.add(it)
        }

        userService.upgradeLevels()

        val result = userDao.getAll()

        checkLevel(result[0], Level.BASIC)
        checkLevel(result[1], Level.SILVER)
        checkLevel(result[2], Level.SILVER)
        checkLevel(result[3], Level.GOLD)
        checkLevel(result[4], Level.GOLD)

        // when

        // then
    }

    private fun checkLevel(user: User, level: Level) {
        expectThat(user.level) isEqualTo level
    }

    companion object {
        val users = listOf(
            User(id = "1", name = "1 user", password = "password", level = Level.BASIC, login = 49, recommend = 0),
            User(id = "2", name = "2 user", password = "password", level = Level.BASIC, login = 50, recommend = 0),
            User(id = "3", name = "3 user", password = "password", level = Level.SILVER, login = 60, recommend = 29),
            User(id = "4", name = "3 user", password = "password", level = Level.SILVER, login = 60, recommend = 30),
            User(id = "5", name = "3 user", password = "password", level = Level.GOLD, login = 100, recommend = 100),
        )
    }
}