kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common-util"))
            }
        }
    }
}
