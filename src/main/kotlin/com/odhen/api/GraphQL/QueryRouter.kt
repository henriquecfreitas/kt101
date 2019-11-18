package com.odhen.api.GraphQL

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.odhen.api.Authentication.AuthenticationController
import com.odhen.api.Logical.User.UserRepository
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class QueryRouter(
        val gqlAuthentication: GQLAuthentication,
        val authenticationController: AuthenticationController,
        val userRepository: UserRepository
): GraphQLQueryResolver {

    // USER
    fun getUsers(env: DataFetchingEnvironment) = gqlAuthentication.dev(env) {
        userRepository.findAll()
    }

    fun getUser(id: Int, env: DataFetchingEnvironment) = gqlAuthentication.authenticated(env) {
        userRepository.findById(id.toLong())
    }

    fun getUserByEmail(email: String, env: DataFetchingEnvironment) = gqlAuthentication.dev(env) {
        userRepository.findByEmail(email)
    }

}