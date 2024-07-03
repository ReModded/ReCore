package dev.remodded.recore.api.utils

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

/**
 * Get declared field with access to it
 * @param name: String - Field name
 * @return Field - Field object
 */
fun <T> Class<T>.getFieldAccess(name: String): Field {
    val field = getDeclaredField(name)
    field.isAccessible = true
    return field
}

/**
 * Try to get declared field with access to it
 * @param name: String - Field name
 * @return Field? - Field object or null
 */
fun <T> Class<T>.tryGetFieldAccess(name: String): Field? {
    return try {
        getFieldAccess(name)
    } catch (e: NoSuchFieldException) {
        null
    }
}

/**
 * Get declared method with access to it
 * @param name: String - Method name
 * @param parameterTypes: Array<Class<*>> - Method parameter types
 * @return Method - Method object
 */
fun <T> Class<T>.getMethodAccess(name: String, vararg parameterTypes: Class<*>): Method {
    val method = getDeclaredMethod(name, *parameterTypes)
    method.isAccessible = true
    return method
}

/**
 * Try to get declared method with access to it
 * @param name: String - Method name
 * @param parameterTypes: Array<Class<*>> - Method parameter types
 * @return Method? - Method object or null
 */
fun <T> Class<T>.tryGetMethodAccess(name: String, vararg parameterTypes: Class<*>): Method? {
    return try {
        getMethodAccess(name, *parameterTypes)
    } catch (e: NoSuchMethodException) {
        null
    }
}

/**
 * Try to get declared constructor with access to it
 * @param params: Array<Class<*>> - Parameter types of the constructor
 * @return Constructor<T> - Constructor object
 */
fun <T> Class<T>.getCtorAccess(vararg params: Class<*>): Constructor<T> {
    val ctor = getDeclaredConstructor(*params)
    ctor.isAccessible = true
    return ctor
}

/**
 * Try to get declared constructor with access to it
 * @param params: Array<Class<*>> - Parameter types of the constructor
 * @return Constructor<T>? - Constructor object or null
 */
fun <T> Class<T>.tryGetCtorAccess(vararg params: Class<*>): Constructor<T>? {
    return try {
        getCtorAccess(*params)
    } catch (e: NoSuchMethodException) {
        null
    }
}
