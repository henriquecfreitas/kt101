package com.odhen.api.Errors

import graphql.ErrorType

class UnauthorizedException(): GraphQLException(
        "Unauthorized access. User don't have permission to call this query/mutation."
) {
    override fun getErrorType(): ErrorType {
        return ErrorType.ValidationError
    }
}