package com.odhen.api.Authentication

class LoginResponse (val status: LoginStatus, val token: String) {
    constructor(status: LoginStatus) : this(status, "")
    enum class LoginStatus {
        SUCCESS,
        INVALID_CREDENTIALS,
        ERROR
    }
}