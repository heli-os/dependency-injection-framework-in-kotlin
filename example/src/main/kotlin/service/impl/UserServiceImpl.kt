package service.impl

import annotation.Component
import service.UserService

/**
 * @Author Heli
 */
@Component
class UserServiceImpl : UserService {
    
    override fun getUserName() = USER_NAME

    companion object {
        private const val USER_NAME = "heli-os"
    }
}
