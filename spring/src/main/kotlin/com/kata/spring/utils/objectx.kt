package com.kata.spring.utils


fun <T: Any> T?.notNull(): T = requireNotNull(this)
inline fun <T: Any> T?.notNull(lazyMessage: () -> Any) = requireNotNull(this, lazyMessage)