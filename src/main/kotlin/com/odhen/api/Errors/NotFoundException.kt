package com.odhen.api.Errors

import graphql.ErrorType

class NotFoundException(entityName: String, parameter: String, value: Any) : GraphQLException (
        "No data found for '$entityName' where '$parameter = $value'."
) {
    override fun getErrorType() = ErrorType.DataFetchingException
}