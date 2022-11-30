kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common-util"))
                implementation(project(":common-encoder"))
            }
        }
//        val jsMain by getting {
//            dependencies {
//                val kryptoVersion: String by project
//
//                implementation("com.soywiz.korlibs.krypto:krypto-js:$kryptoVersion")
//            }
//        }
    }
}
