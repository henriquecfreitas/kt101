package com.odhen.api.GraphQL

import com.odhen.api.Authentication.TokenAuthenticationService
import com.odhen.api.Errors.UnauthenticatedException
import com.odhen.api.Errors.UnauthorizedException
import com.odhen.api.Logical.User.User
import com.odhen.api.Logical.User.User.Role
import com.odhen.api.Logical.User.UserRepository
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.GraphQLContext
import org.springframework.stereotype.Controller

@Controller
class GQLAuthentication(val userRepository: UserRepository) {

    class Authenticated(val user: User) {
        fun <T> hasRole(vararg allowedRoles: Role, function: () -> T): T {
            if (user.role in allowedRoles.asList()) {
                return function.invoke()
            } else {
                throw UnauthorizedException()
            }
        }
    }

    // SHORTCUTS
    fun <T> dev(env: DataFetchingEnvironment, function: () -> T): T {
        return authenticated(env).hasRole(Role.ADMIN, Role.DEVELOPER) {
            function.invoke()
        }
    }
    fun <T> admin(env: DataFetchingEnvironment, function: () -> T): T {
        return authenticated(env).hasRole(Role.ADMIN) {
            function.invoke()
        }
    }

    fun authenticated(env: DataFetchingEnvironment): Authenticated {
        return Authenticated(authenticate(env))
    }
    fun <T> authenticated(env: DataFetchingEnvironment, function: () -> T): T {
        authenticate(env)
        return function.invoke()
    }

    private fun authenticate(env: DataFetchingEnvironment): User {
        try {
            val context: GraphQLContext = env.getContext()
            val req = context.httpServletRequest.get()
            val authorization = req!!.getHeader(TokenAuthenticationService.HEADER_STRING)
            val token = TokenAuthenticationService.getJWT(authorization)

            val username = if (TokenAuthenticationService.validateToken(token)) {
                TokenAuthenticationService.getUsernameFromToken(token)
            } else {
                throw Exception()
            }

            return userRepository.findByEmail(username)!!
        } catch (e: Exception) {
            throw UnauthenticatedException()
        }
    }
}