package com.kata.spring.datasource.repository

import com.kata.spring.datasource.entity.TestEntity
import org.springframework.data.repository.CrudRepository


/**
 * @author Jay
 */
interface TestRepository: CrudRepository<TestEntity, Long> {
}
