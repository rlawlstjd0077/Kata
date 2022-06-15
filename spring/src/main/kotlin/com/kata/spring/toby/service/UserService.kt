package com.kata.spring.toby.service

import com.kata.spring.toby.User

interface UserService {
    fun add(user: User)
    fun upgradeLevels()
}