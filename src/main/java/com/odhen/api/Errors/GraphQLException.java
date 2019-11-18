package com.odhen.api.Errors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public abstract class GraphQLException extends RuntimeException implements GraphQLError {
    private String errorMessage;

    GraphQLException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}