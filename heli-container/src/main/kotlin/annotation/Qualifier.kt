package annotation

import java.lang.annotation.Inherited

/**
 * Service field variables should use this annotation
 *
 * This annotation can be used to avoid conflict if
 * there are multiple implementations of the same interface
 *
 * @Author Heli
 */
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE_PARAMETER,
    AnnotationTarget.CLASS,
    AnnotationTarget.ANNOTATION_CLASS
)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@MustBeDocumented
annotation class Qualifier(val value: String = "")
