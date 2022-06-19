package com.kata.spring.toby.service

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


/**
 * @author Jay
 */
@Component
class TestUserServiceExceptionGenerator(
    private val userService: UserService
) {
    @Transactional
    fun upgradeLevelsAndThrowException() {
        userService.upgradeLevels()
        throw RuntimeException()
    }
}

//@Component
//class TestUserService(
//    @Qualifier("userServiceImpl") private val userService: UserService
//) : UserService by userService {
//    override fun upgradeLevels() {
//        userService.upgradeLevels()
//        throw TestServiceExecption()
//    }
//
//    companion object {
//        private const val TARGET_ID = 3L
//    }
//}
//
//class TestServiceExecption: RuntimeException()