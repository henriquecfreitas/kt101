package com.odhen.api.Errors

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.server.ServletServerHttpResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationEntryPoint(val mapper: ObjectMapper): AuthenticationEntryPoint {

    private val messageConverter: HttpMessageConverter<String> = StringHttpMessageConverter()

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        val apiError = ApiError(HttpStatus.UNAUTHORIZED, authException?.message, authException)
        val outputMessage = ServletServerHttpResponse(response!!)
        outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED)
        messageConverter.write(mapper.writeValueAsString(apiError), MediaType.APPLICATION_JSON, outputMessage)
    }

}