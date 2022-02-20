package service.impl

import annotation.Component
import service.AccountService

/**
 * @Author Heli
 */
@Component
class AccountServiceImpl : AccountService {

    override fun getAccountNumber(userName: String) = ACCOUNT_NUMBER

    companion object {
        private const val ACCOUNT_NUMBER = 110_123_456789L
    }

}
