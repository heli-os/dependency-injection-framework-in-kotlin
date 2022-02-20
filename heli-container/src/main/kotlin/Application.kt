/**
 * @Author Heli
 */
class Application

fun main() {
    Injector.startApplication(Application::class)
    Injector.getService(UserAccountClient::class).displayUserAccount()
}
