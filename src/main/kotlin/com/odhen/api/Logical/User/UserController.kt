package com.odhen.api.Logical.User

import com.odhen.api.Errors.NotFoundException
import org.springframework.stereotype.Controller

@Controller
class UserController(val userRepository: UserRepository) {

    fun updateUserRole(id: Int, role: User.Role): User {
        val user = userRepository.findById(id.toLong()).orElseThrow { NotFoundException("User", "id", id) }
        user.role = role
        return userRepository.save(user)
    }

    fun updateUserRole(user: User, role: User.Role): User {
        user.role = role
        return userRepository.save(user)
    }

    fun designateDeveloper(user: User): User {
        user.role = User.Role.DEVELOPER
        return userRepository.save(user)
    }
    fun relegate(user: User): User {
        user.role = User.Role.USER
        return userRepository.save(user)
    }
    fun drop(user: User) {
        userRepository.delete(user)
    }

}