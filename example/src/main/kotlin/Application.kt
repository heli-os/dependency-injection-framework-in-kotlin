/**
 * @Author Heli
 */
class Application

fun main() {
    HeliContainer.startApplication(Application::class)
    HeliContainer.getService(UserAccountClient::class).displayUserAccount()
}
