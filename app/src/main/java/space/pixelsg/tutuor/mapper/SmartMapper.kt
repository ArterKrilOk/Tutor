package space.pixelsg.tutuor.mapper

import space.pixelsg.tutuor.mapper.annotations.MapMethod
import space.pixelsg.tutuor.mapper.annotations.MapperClass
import java.lang.reflect.Method
import kotlin.reflect.KClass

object SmartMapper {
    inline fun <reified T : Mappable> map(from: Mappable): T {
        val fromClass = from::class

        if (!fromClass.annotations.any { it is MapperClass })
            throw SmartMapperMapperClassNotFound()

        val mapperAnnotation = fromClass.annotations.first {
            it is MapperClass
        } as MapperClass

        val mapperClass = mapperAnnotation.mapper

        return map(from, mapperClass)
    }

    inline fun <reified T : Mappable> map(from: Mappable, mapperKClass: KClass<*>): T {
        try {
            val mapperClass = mapperKClass.java
            if (!mapperClass.methods.any { it.isHasMethod(from::class.java, T::class.java) })
                throw SmartMapperMethodNotFound()
            val method =
                mapperClass.methods.first { it.isHasMethod(from::class.java, T::class.java) }

            val instance = mapperKClass.objectInstance ?: mapperClass.newInstance()

            return method.invoke(instance, from) as T
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw SmartMapperError()
    }

    fun Method.isHasMethod(form: Class<*>, to: Class<*>) =
        this.annotations.any { it is MapMethod } &&
                this.returnType == to &&
                this.parameterTypes.any { it == form }
}