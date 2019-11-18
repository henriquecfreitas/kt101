package com.odhen.api.Logical.User

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service("userService")
class UserService(val userRepository: UserRepository): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw RuntimeException("User not found: $username")
        val authority = SimpleGrantedAuthority(user.role.value)
        return org.springframework.security.core.userdetails.User(user.email, user.password, setOf(authority))
    }
}