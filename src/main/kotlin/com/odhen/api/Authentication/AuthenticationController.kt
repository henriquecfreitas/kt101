package com.odhen.api.Authentication

import com.odhen.api.Security.ApiPasswordEncoder
import com.odhen.api.Logical.User.User
import com.odhen.api.Logical.User.UserRepository
import com.odhen.api.Logical.User.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Controller

@Controller
class AuthenticationController(
        val userService: UserService,
        val userRepository: UserRepository,
        val authenticationManager: AuthenticationManager
) {

    fun doLogin(username: String, password:String): LoginResponse {
        return try {
            val userAuthToken = UsernamePasswordAuthenticationToken(username, password)
            authenticationManager.authenticate(userAuthToken)
            val userDetails = userService.loadUserByUsername(username)
            LoginResponse(
                    LoginResponse.LoginStatus.SUCCESS,
                    TokenAuthenticationService.generateToken(userDetails)
            )
        } catch (e: BadCredentialsException) {
            LoginResponse(LoginResponse.LoginStatus.INVALID_CREDENTIALS)
        } catch (e: Exception) {
            LoginResponse(LoginResponse.LoginStatus.ERROR)
        }
    }

    fun register(username: String, password:String): LoginResponse {
        return try {
            val user = User(null, username, ApiPasswordEncoder.encode(password), User.Role.USER)
            userRepository.save(user)

            val userDetails = org.springframework.security.core.userdetails.User(
                    user.email,
                    user.password,
                    setOf(SimpleGrantedAuthority(user.role.value))
            )

            LoginResponse(
                LoginResponse.LoginStatus.SUCCESS,
                TokenAuthenticationService.generateToken(userDetails)
            )
        } catch (e: Exception) {
            LoginResponse(LoginResponse.LoginStatus.ERROR)
        }
    }
}