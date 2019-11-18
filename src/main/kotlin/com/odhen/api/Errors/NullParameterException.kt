package com.odhen.api.Errors

import org.springframework.util.StringUtils

class NullParameterException(className: String, method: String, parameter: String) : RuntimeException (
        "Null value for parameter '$parameter' at '$method' call in class ${StringUtils.capitalize(className)}"
)