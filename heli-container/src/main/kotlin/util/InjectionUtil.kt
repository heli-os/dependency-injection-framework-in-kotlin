package util

import HeliContainer
import annotation.Autowired
import annotation.Qualifier
import reflection.ReflectionHelper
import kotlin.reflect.KClass

/**
 * @Author Heli
 */
fun autowire(clazz: KClass<*>, classInstance: Any) {
    clazz.java.declaredFields
        .filter { it.isAnnotationPresent(Autowired::class.java) }
        .forEach { field ->
            val qualifier = if (field.isAnnotationPresent(Qualifier::class.java)) {
                field.getAnnotation(Qualifier::class.java).value
            } else {
                ""
            }

            val fieldInstance = HeliContainer.getBeanInstance(
                interfaceClass = field.type.kotlin,
                fieldName = field.type.name,
                qualifier = qualifier
            )

            ReflectionHelper.setField(classInstance, field, fieldInstance)
            autowire(fieldInstance.javaClass.kotlin, fieldInstance)
        }
}
