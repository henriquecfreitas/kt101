package com.odhen.api.Errors

import graphql.ErrorType

class UnauthenticatedException(): GraphQLException(
        "Unauthenticated access. Authentication token is invalid, expired or missing in request headers."
) {
    override fun getErrorType(): ErrorType {
        return ErrorType.ValidationError
    }
}