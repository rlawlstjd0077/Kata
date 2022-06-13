package com.kata.spring.toby.service

import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage


/**
 * @author Jay
 */


class MockMailSender: MailSender {
    val requests = mutableListOf<String>()

    override fun send(simpleMessage: SimpleMailMessage) {
        requests.add(simpleMessage.to[0])
    }

    override fun send(vararg simpleMessages: SimpleMailMessage?) {
    }
}