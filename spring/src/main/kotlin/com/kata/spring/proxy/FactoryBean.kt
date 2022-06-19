package com.kata.spring.proxy

import org.springframework.beans.factory.FactoryBean


/**
 * @author Jay
 */
class Message private constructor(
    val text: String
) {

    companion object {
        fun newMessage(text: String): Message {
            return Message(text)
        }
    }
}

class MessageFactoryBean(
    val text: String
): FactoryBean<Message> {
    override fun getObject(): Message {
        return Message.newMessage(text)
    }

    override fun getObjectType(): Class<*>? {
        return Message.javaClass
    }

    override fun isSingleton(): Boolean {
        return false
    }
}