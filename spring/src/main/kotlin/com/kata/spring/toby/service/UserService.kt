package com.kata.spring.toby.service

import com.kata.spring.toby.User
import org.springframework.transaction.annotation.Transactional


@Transactional
interface UserService {

    fun add(user: User)
    fun upgradeLevels()

    @Transactional(readOnly = true)
    fun get(id: String): User

    @Transactional(readOnly = true)
    fun getAll(): List<User>
}