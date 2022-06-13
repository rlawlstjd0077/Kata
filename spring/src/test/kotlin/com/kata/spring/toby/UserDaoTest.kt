package com.kata.spring.toby

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import strikt.api.expectThat
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import javax.annotation.PostConstruct
import javax.sql.DataSource


@SpringBootTest(classes = [TobyTestDatasourceConfig::class])
@ActiveProfiles("test")
class UserDaoTest {
    @Autowired
    private lateinit var dataSource: DataSource

    private lateinit var userDao: UserDao

    private lateinit var jdbcTemplate: JdbcTemplate

    @PostConstruct
    fun setUp() {
        userDao = UserDao(dataSource)
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
    fun `getAll() 메서드는 저장된 모든 user 목록을 반환한다`() {
        // given
        val user1 = User(id = "1", name = "1 user", password = "password", level = Level.BASIC, login = 1, recommend = 1)
        val user2 = User(id = "2", name = "2 user", password = "password", level = Level.BASIC, login = 1, recommend = 1)
        val user3 = User(id = "3", name = "3 user", password = "password", level = Level.BASIC, login = 1, recommend = 1)

        // when
        // then
        userDao.add(user1)
        val users1 = userDao.getAll()
        expectThat(users1) hasSize 1
        expectThat(users1[0]) isEqualTo user1

        userDao.add(user2)
        val users2 = userDao.getAll()
        expectThat(users2) hasSize 2
        expectThat(users2[0]) isEqualTo user1
        expectThat(users2[1]) isEqualTo user2

        userDao.add(user3)
        val users3 = userDao.getAll()
        expectThat(users3) hasSize 3
        expectThat(users3[0]) isEqualTo user1
        expectThat(users3[1]) isEqualTo user2
        expectThat(users3[2]) isEqualTo user3
    }

    @Test
    fun `getAll() 메서드는 저장된 user가 없는 경우 빈 목록을 반환한다`() {
        // given
        val list = userDao.getAll()
        expectThat(list) hasSize 0
    }
}