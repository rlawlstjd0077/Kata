package com.kata.spring.toby.service

import com.kata.spring.toby.Level
import com.kata.spring.toby.TobyTestConfig
import com.kata.spring.toby.User
import com.kata.spring.toby.repository.UserDao
import com.kata.spring.toby.repository.UserDaoJdbc
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.BadSqlGrammarException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.mail.MailSender
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.PlatformTransactionManager
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import java.lang.reflect.Proxy
import javax.annotation.PostConstruct
import javax.sql.DataSource

/**
 * TODO 특정 패키지 아래로 Component Scan 수행하도록 수정 필요함
 */
@SpringBootTest(classes = [UserServiceImpl::class, UserDaoJdbc::class, TobyTestConfig::class])
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userDao: UserDao

    private lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var transactinoManager: PlatformTransactionManager

    @Autowired
    private lateinit var mockMailSender: MailSender


    @PostConstruct
    fun setUp() {
        jdbcTemplate = JdbcTemplate(dataSource)
        transactinoManager = DataSourceTransactionManager(dataSource)

        try  {
            jdbcTemplate.execute("create table users (id varchar , name varchar, password varchar, level varchar, login varchar, recommend varchar, email varchar )")
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
        val userDao = MockUserDao(users)
        val userServiceImpl = UserServiceImpl(mockMailSender, userDao)

        userServiceImpl.upgradeLevels()

        val updated = userDao.updated
        expectThat(updated.size) isEqualTo 2
        checkUserAndLevel(updated[0], "2", Level.SILVER)
        checkUserAndLevel(updated[1], "4", Level.GOLD)

        val requests = (mockMailSender as MockMailSender).requests
        expectThat(requests.size) isEqualTo 2
        expectThat(requests[0]) isEqualTo users[1].email
        expectThat(requests[1]) isEqualTo users[3].email
    }

    private fun checkUserAndLevel(updated: User, expectedId: String, expectedLevel: Level) {
        expectThat(updated.id) isEqualTo expectedId
        expectThat(updated.level) isEqualTo expectedLevel
    }

    @Test
    fun `updateAllorNothing`() {
        // given
        val transactionHandler = TransactionHandler(
            target = TestUserService(
                id = users[3].id,
                userDao = userDao,
                mailSender = MockMailSender()
            ),
            transactionManager = transactinoManager,
            patter = "upgradeLevels"
        )

        val txUserService = Proxy.newProxyInstance(
            ClassLoader.getSystemClassLoader(),
            arrayOf(UserService::class.java),
            transactionHandler
        ) as UserService

        users.forEach {
            userDao.add(it)
        }

        try {
            txUserService.upgradeLevels()
        } catch (e: TestServiceExecption) {

        }

        checkLevel(users[1], false)
    }

    private fun checkLevel(user: User, upgraded: Boolean) {
        val userUpdate = userDao.get(user.id)

        if (upgraded) {
            expectThat(userUpdate.level) isEqualTo user.level!!.next
        } else {
            expectThat(userUpdate.level) isEqualTo user.level
        }
    }

    companion object {
        val users = listOf(
            User(id = "1", name = "1 user", password = "password", level = Level.BASIC, login = 49, recommend = 0, email = "user1@test.com"),
            User(id = "2", name = "2 user", password = "password", level = Level.BASIC, login = 50, recommend = 0, email = "user2@test.com"),
            User(id = "3", name = "3 user", password = "password", level = Level.SILVER, login = 60, recommend = 29, email = "user3@test.com"),
            User(id = "4", name = "3 user", password = "password", level = Level.SILVER, login = 60, recommend = 30, email = "user4@test.com"),
            User(id = "5", name = "3 user", password = "password", level = Level.GOLD, login = 100, recommend = 100, email = "user5@test.com"),
        )
    }
}

class MockUserDao(
    val users: List<User>,
    var updated: List<User> = mutableListOf()
): UserDao {
    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun add(user: User) {
        TODO("Not yet implemented")
    }

    override fun get(id: String): User {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<User> {
        return users
    }

    override fun update(user: User) {
        updated += user
    }

}