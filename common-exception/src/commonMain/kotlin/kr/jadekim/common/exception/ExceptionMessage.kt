package kr.jadekim.common.exception

typealias Language = String
typealias ErrorCode = String

typealias MessageMap = MutableMap<ErrorCode, MutableMap<Language, String>>

expect fun loadPlatformMessages()

object ExceptionMessage {

    private const val LANGUAGE_ENGLISH = "en"

    var defaultLanguage: Language = LANGUAGE_ENGLISH

    var messageMap: MessageMap = mutableMapOf(
        "default" to mutableMapOf(
            "ko" to "오류가 발생했습니다.",
            "en" to "Occur error"
        )
    )

    init {
        loadPlatformMessages()
    }

    fun setMessages(locale: Language, messages: Map<ErrorCode, String>) {
        messages.forEach { (errorCode, message) ->
            val languageMap = messageMap.getOrPut(errorCode) { mutableMapOf() }
            languageMap[locale] = message
        }
    }

    fun getErrorMessage(errorCode: String, language: Language = defaultLanguage): String {
        val localeMap = messageMap.getOrPut(errorCode) {
            messageMap["default"] ?: mutableMapOf(LANGUAGE_ENGLISH to "Occur error")
        }

        return localeMap[language]
            ?: localeMap[defaultLanguage]
            ?: localeMap.values.firstOrNull()
            ?: "Occur error"
    }
}
