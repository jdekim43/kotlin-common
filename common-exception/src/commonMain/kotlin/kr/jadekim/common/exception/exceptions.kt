package kr.jadekim.common.exception

open class FriendlyException(
    val code: String,
    val data: Any? = null,
    cause: Throwable? = null,
    message: String? = cause?.message,
    val level: ExceptionLevel = ExceptionLevel.ERROR,
) : RuntimeException("$code : $message", cause) {

    open fun getFriendlyMessage(
        language: Language? = null,
    ) = ExceptionMessage.getErrorMessage(code, language ?: ExceptionMessage.defaultLanguage)
}

class UnknownException(
    cause: Throwable,
    message: String? = null,
) : FriendlyException(
    code = "COM-1",
    cause = cause,
    message = message ?: cause.message ?: "Unknown Exception",
)

class ProgrammingException(
    message: String,
    cause: Throwable? = null,
) : FriendlyException(
    code = "COM-2",
    message = message,
    cause = cause,
)

class AssertException(
    message: String,
) : FriendlyException(
    code = "COM-3",
    message = message,
)
