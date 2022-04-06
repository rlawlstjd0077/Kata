package com.kata.spring.datasource

import com.kata.spring.datasource.configuration.H2TestProfileJPAConfig
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import javax.sql.DataSource


/**
 * @author Jay
 */
@SpringBootTest(classes = [
    DataSourceTestApplication::class,
    H2TestProfileJPAConfig::class
])
@ActiveProfiles("test")
class H2DatabaseTest {
    @Autowired
    private lateinit var datasource: DataSource

    @Test
    fun `test`() {
        // given
        println("fasfsa")

        // when

        // then
    }
}
