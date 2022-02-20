package annotation


/**
 * Service field variables should use this annotation
 *
 * @Author Heli
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FIELD)
@MustBeDocumented
annotation class Autowired
