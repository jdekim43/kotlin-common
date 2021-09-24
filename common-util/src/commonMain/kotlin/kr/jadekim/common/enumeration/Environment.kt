package kr.jadekim.common.enumeration

import kotlin.jvm.JvmStatic

interface IEnvironment {
    val name: String
}

enum class Environment : IEnvironment {
    LOCAL,
    DEVELOPMENT,
    QA,
    STAGE,
    PRODUCTION;

    companion object {
        @JvmStatic
        fun from(name: String?): Environment? = when (name?.lowercase()) {
            "local" -> LOCAL
            "dev", "development" -> DEVELOPMENT
            "qa" -> QA
            "stg", "stage", "staging" -> STAGE
            "prd", "prod", "production", "real", "live" -> PRODUCTION
            else -> null
        }
    }
}
