package reflection

import java.io.File
import java.lang.reflect.Field
import kotlin.reflect.KClass

/**
 * @Author Heli
 */
object ReflectionHelper {

    fun findClassesByPackageName(mainClass: KClass<*>): Collection<KClass<*>> {
        val packageName = mainClass.java.packageName

        val name: String = if (!packageName.startsWith('/')) {
            "/$packageName".replace('.', '/')
        } else {
            packageName.replace('.', '/')
        }

        // Get a File object for the package
        val url = mainClass.java.getResource(name)!!
        val directory = File(url.file)

        if (!directory.exists()) {
            return emptyList()
        }

        // Get the list of the files contained in the package
        return directory.walk() // FileTreeWalk is Sequence
            .filter { f -> f.isFile && !f.name.contains('$') && f.name.endsWith(".class") }
            .map {
                val fullyQualifiedClassName = packageName +
                        it.canonicalPath.removePrefix(directory.canonicalPath)
                            .dropLast(6) // remove .class
                            .drop(1) // remove first /
                            .replace('/', '.')

                // Get KClass by fullyQualifiedClassName
                Class.forName(fullyQualifiedClassName).kotlin
            }.toList()
    }

    fun setField(classInstance: Any, field: Field, fieldInstance: Any) {
        field.isAccessible = true
        field.set(classInstance, fieldInstance)
    }
}
