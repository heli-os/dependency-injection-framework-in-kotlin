import annotation.Component
import reflection.ReflectionHelper
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.jvmName


/**
 * Injector, to create objects for all @Component classes.
 * autowired/inject all dependencies
 *
 * @Author Heli
 */
object Injector {

    private val diMap = mutableMapOf<KClass<*>, KClass<*>>()
    private val applicationScope = mutableMapOf<KClass<*>, Any>()

    /**
     * Start an application
     *
     * @param mainClass
     */
    fun startApplication(mainClass: KClass<*>) {
        try {
            initFramework(mainClass)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getService(clazz: KClass<T>): T {
        val beanInstance = getBeanInstance(clazz)
        return beanInstance as T
    }

    /**
     * initialize the injector framework
     */
    private fun initFramework(mainClass: KClass<*>) {
        val classes = getClasses(mainClass.java.packageName)

        classes
            .filter {
                !it.simpleName!!.endsWith("Kt") && it.hasAnnotation<Component>()
            }
            .forEach { clazz ->
                val interfaces = clazz.java.interfaces
                if (interfaces.isEmpty()) {
                    diMap[clazz] = clazz
                } else {
                    interfaces.forEach {
                        diMap[clazz] = it.kotlin
                    }
                }
                val classInstance = clazz.createInstance()
                applicationScope[clazz] = classInstance
                InjectionUtil.autowire(clazz, classInstance)
            }
    }

    /**
     * Get all the classes for the input package
     */
    fun getClasses(packageName: String): Collection<KClass<*>> {
        return ReflectionHelper.findClassesByPackageName(packageName)
    }

    fun <T : Any> getBeanInstance(interfaceClass: KClass<T>, fieldName: String = "", qualifier: String = ""): Any {
        val implementationClass = getImplementationClass(interfaceClass, fieldName, qualifier)

        if (applicationScope.containsKey(implementationClass)) {
            return applicationScope[implementationClass]!!
        }

        synchronized(applicationScope) {
            val implementService = implementationClass.createInstance()
            applicationScope[implementationClass] = implementService
            return implementService
        }
    }

    fun getImplementationClass(interfaceClass: KClass<*>, fieldName: String, qualifier: String): KClass<*> {
        val implementationClasses = diMap.entries
            .filter { entry ->
                entry.value == interfaceClass
            }.toSet()

        val errorMessage: String = if (implementationClasses.isEmpty()) {
            "no implementation found for interface ${interfaceClass.jvmName}"
        } else if (implementationClasses.size == 1) {
            return implementationClasses.first().key
        } else {
            val findBy = if (qualifier.trim().isEmpty()) fieldName else qualifier
            val implementationClass = implementationClasses
                .find { entry -> entry.key.simpleName.equals(findBy, true) }

            if (implementationClass !== null) {
                return implementationClass.key
            } else {
                "There are ${implementationClasses.size} of interface ${interfaceClass.jvmName} Expected single implementation or make use of @CustomQualifier to resolve conflict"
            }
        }
        throw RuntimeException(errorMessage)
    }
}
