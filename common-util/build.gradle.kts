kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
            languageSettings.optIn("kotlin.contracts.ExperimentalContracts")
        }

        val jvmMain by getting {
            dependencies {
                val kotlinxCoroutineVersion: String by project

                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutineVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                val kryptoVersion: String by project

                implementation("com.soywiz.korlibs.krypto:krypto-js:$kryptoVersion")
            }
        }
    }
}
