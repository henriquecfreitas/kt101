package com.odhen.api.Util

import com.odhen.api.Errors.NullParameterException

object ValueHandler {
    fun <T> unwarp(value: T?, name: String): T {
        if (value !== null) {
            return value
        } else {
            val caller = Thread.currentThread().stackTrace[1]
            throw NullParameterException(caller.className, caller.methodName, name)
        }
    }
}