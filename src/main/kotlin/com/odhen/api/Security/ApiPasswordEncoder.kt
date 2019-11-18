package com.odhen.api.Security

import org.springframework.security.crypto.password.PasswordEncoder

object ApiPasswordEncoder: PasswordEncoder {
    override fun encode(rawPassword: CharSequence?): String {
        return rawPassword.toString()
    }

    override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
        return rawPassword.toString() == encodedPassword
    }
}