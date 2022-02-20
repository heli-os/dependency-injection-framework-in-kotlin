import annotation.Autowired
import annotation.Component
import annotation.Qualifier
import service.AccountService
import service.UserService

/**
 * @Author Heli
 */
@Component
class UserAccountClient {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    @Qualifier(value = ACCOUNT_SERVICE_NAME)
    private lateinit var accountService: AccountService

    fun displayUserAccount() {
        val userName = userService.getUserName()
        val accountNumber = accountService.getAccountNumber(userName)

        println("\n\tUser Name: $userName\n\tAccount Number: $accountNumber")
    }

    companion object {

        private const val ACCOUNT_SERVICE_NAME = "accountServiceImpl"
    }
}
