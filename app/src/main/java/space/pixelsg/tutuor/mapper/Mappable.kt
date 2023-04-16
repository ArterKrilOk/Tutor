package space.pixelsg.tutuor.mapper

import kotlin.reflect.KClass

interface Mappable

/**
 * Automatically maps Mappable object to required type with mapper specified in Mappable class
 * Uses reflections
 */
inline fun <reified T : Mappable> Mappable.map(): T =
    SmartMapper.map(this)

/**
 * Automatically maps Mappable object to required type with specific mapper
 * Uses reflections
 */
inline fun <reified T : Mappable> Mappable.map(mapper: KClass<*>): T =
    SmartMapper.map(this, mapper)