package com.odhen.api.Errors

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.odhen.api.Util.LowerCaseClassNameResolver
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import java.time.LocalDateTime
import javax.validation.ConstraintViolation

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver::class)
class ApiError(val status: HttpStatus?, val message: String?, val ex: Throwable?) {

    internal interface ApiSubError {}
    internal class ApiValidationError
        (val obj: String, val message: String, val field: String?, val rejectedValue: Any?): ApiSubError {}

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private val timestamp: LocalDateTime = LocalDateTime.now()
    private val debugMessage: String? = ex?.getLocalizedMessage()
    private val subErrors: MutableList<ApiSubError> = mutableListOf()

    private fun addSubError(subError: ApiSubError) {
        subErrors.add(subError);
    }


    private fun addValidationError(obj: String, message: String?, field: String?, rejectedValue: Any?) {
        addSubError(ApiValidationError(obj, message.orEmpty(), field, rejectedValue))
    }

    private fun addValidationError(obj: String, message: String?) {
        addValidationError(obj, message, null, null)
    }
    private fun addValidationError(e: FieldError) {
        addValidationError(e.objectName, e.defaultMessage, e.field, e.rejectedValue)
    }
    private fun addValidationError(e: ObjectError) {
        addValidationError(e.objectName, e.defaultMessage)
    }

    fun addValidationErrors(fieldErrors: List<FieldError>) {
        fieldErrors.forEach {
            addValidationError(it)
        }
    }
    fun addValidationError(globalErrors: List<ObjectError>) {
        globalErrors.forEach {
            addValidationError(it)
        }
    }

    /**
     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
     *
     * @param cv the ConstraintViolation
     */
    private fun addValidationError(cv: ConstraintViolation<Any>) {
        addValidationError(
                cv.rootBeanClass.getSimpleName(),
                cv.message,
                (cv.propertyPath as PathImpl).leafNode.asString(),
                cv.invalidValue
        )
    }
    fun addValidationErrors(constraintViolations: Set<ConstraintViolation<Any>>) {
        constraintViolations.forEach{
            addValidationError(it)
        }
    }
}