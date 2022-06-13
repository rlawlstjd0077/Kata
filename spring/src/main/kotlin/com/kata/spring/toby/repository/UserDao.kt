package com.kata.spring.toby.repository

import com.kata.spring.toby.User

interface UserDao {
    fun deleteAll()
    fun add(user: User)
    fun get(id: String): User
    fun getCount(): Int
    fun getAll(): List<User>
    fun update(user: User)
}