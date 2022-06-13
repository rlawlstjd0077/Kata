package com.kata.spring.type_token

import com.sun.jdi.Type
import java.lang.reflect.ParameterizedType


/**
 * @author Jay
 */
class SuperTypeToken {

}

class TypeSafeMap {
    val _map: MutableMap<Class<*>, Any> = mutableMapOf<Class<*>, Any>()

    fun <T> put(type: Class<T>, value: T) {
        _map[type] = value!!
    }

    fun <T> get(type: Class<T>): Any {
        return _map.getValue(type)
    }
}

class TypeReferenceSafeMap {
    val _map: MutableMap<TypeReference<*>, Any> = mutableMapOf<TypeReference<*>, Any>()

    fun <T> put(type: TypeReference<T>, value: T) {
        _map[type] = value!!
    }

    fun <T> get(tr: TypeReference<T>): T {
        return this._map.getValue(tr) as T
    }
}


open class TypeReference<T> {
    lateinit var type: java.lang.reflect.Type

    init {
        val stype = javaClass.genericSuperclass
        if (stype is ParameterizedType) {
            type = (stype as ParameterizedType).actualTypeArguments[0]
        } else {
            throw java.lang.RuntimeException()
        }
    }

    override fun equals(other: Any?): Boolean {
        return this.type == (other as TypeReference<*>).type
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}

fun main() {
    val typeSafeMap = TypeReferenceSafeMap()
    typeSafeMap.put(object : TypeReference<String>(){}, "1111")
    typeSafeMap.put(object : TypeReference<List<String>>(){}, listOf("afsa"))
    println(typeSafeMap.get(object : TypeReference<String>(){}))
    println(typeSafeMap.get(object : TypeReference<List<String>>(){}))
}
