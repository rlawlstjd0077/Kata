package com.kata.spring.toby

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


/**
 * 테스트용 Datasource 설정 Configuration
 * 추후 테스트 시에 공통화 전략 수립 필요
 *
 * @author Jay
 */
@Configuration
@EnableTransactionManagement
open class TobyTestDatasourceConfig {

    @Bean
    @Profile("test")
    fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.h2.Driver")
        dataSource.url = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
        dataSource.username = "sa"
        dataSource.password = "sa"
        return dataSource
    }
}
