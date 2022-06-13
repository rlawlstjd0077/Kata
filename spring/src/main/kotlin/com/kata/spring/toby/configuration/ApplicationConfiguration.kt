package com.kata.spring.toby.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.JavaMailSenderImpl


/**
 * @author Jay
 */
@Configuration
class ApplicationConfiguration {

    @Bean
    fun mailSender() = JavaMailSenderImpl()
}