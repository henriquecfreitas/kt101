package com.odhen.api.GraphQL

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.odhen.api.Authentication.AuthenticationController
import com.odhen.api.Logical.User.User
import com.odhen.api.Logical.User.UserController
import com.odhen.api.Logical.User.UserRepository
import com.odhen.api.Security.ApiPasswordEncoder
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class MutationRouter(
        val gqlAuthentication: GQLAuthentication,
        val authenticationController: AuthenticationController,
        val userController: UserController,
        val userRepository: UserRepository
): GraphQLMutationResolver {

    // DEV
    fun testInsert(qtd: Int): String {
        for (i in 0 until qtd) {
            val user = User(null, "testinsert$i@email.com", ApiPasswordEncoder.encode("$i"), User.Role.USER)
            userRepository.save(user)
        }
        return ""
    }

    // AUTH
    fun register(username: String, password:String) = authenticationController.register(username, password)
    fun login(username: String, password:String) = authenticationController.doLogin(username, password)

    // USER
    fun updateUserRole(id: Int, role: User.Role, env: DataFetchingEnvironment) = gqlAuthentication.admin(env) {
        userController.updateUserRole(id, role)
    }
    fun resignAdmin(env: DataFetchingEnvironment): User {
        val authenticated = gqlAuthentication.authenticated(env)
        return authenticated.hasRole(User.Role.ADMIN) {
            userController.designateDeveloper(authenticated.user)
        }
    }
    fun resignDeveloper(env: DataFetchingEnvironment): User {
        val authenticated = gqlAuthentication.authenticated(env)
        return authenticated.hasRole(User.Role.ADMIN, User.Role.DEVELOPER) {
            userController.relegate(authenticated.user)
        }
    }
    fun dropAccount(env: DataFetchingEnvironment): RequestStatus {
        val authenticated = gqlAuthentication.authenticated(env)
        return try {
            userController.drop(authenticated.user)
            RequestStatus.SUCCESS
        } catch (e: Exception) { RequestStatus.ERROR }
    }

}