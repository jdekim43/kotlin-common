plugins {
    kotlin("multiplatform") version "1.5.30"
    id("org.jetbrains.dokka") version "1.5.30"
    id("maven-publish")
    id("signing")
}

group = "kr.jadekim"
version = "2.0.1"

allprojects {
    apply {
        plugin("kotlin-multiplatform")
        plugin("org.jetbrains.dokka")
        plugin("maven-publish")
        plugin("signing")
    }

    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }

    kotlin {
        jvm {
            compilations.all {
                kotlinOptions.jvmTarget = "1.8"
            }
            testRuns["test"].executionTask.configure {
                useJUnitPlatform()
            }
        }
        js(LEGACY) {
            browser()
            nodejs()
        }

        @Suppress("UNUSED_VARIABLE")
        sourceSets {
            val commonMain by getting
            val commonTest by getting {
                dependencies {
                    implementation(kotlin("test-common"))
                    implementation(kotlin("test-annotations-common"))
                }
            }
            val jvmMain by getting
            val jvmTest by getting {
                dependencies {
                    val junitVersion: String by project

                    implementation(kotlin("test-junit5"))

                    runtimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
                    compileOnly("org.junit.jupiter:junit-jupiter-api:$junitVersion")
                    compileOnly("org.junit.jupiter:junit-jupiter-params:$junitVersion")
                }
            }
            val jsMain by getting
            val jsTest by getting {
                dependencies {
                    implementation(kotlin("test-js"))
                }
            }
        }
    }

    val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)
    val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
        dependsOn(dokkaHtml)
        archiveClassifier.set("javadoc")
        from(dokkaHtml.outputDirectory)
    }

    publishing {
        publications.withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set(project.name)
                description.set("Kotlin Commons")
                url.set("https://github.com/jdekim43/kotlin-common")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("jdekim43")
                        name.set("Jade Kim")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/jdekim43/kotlin-common.git")
                    developerConnection.set("scm:git:git://github.com/jdekim43/kotlin-common.git")
                    url.set("https://github.com/jdekim43/kotlin-common")
                }
            }
        }

        repositories {
            val ossrhUsername: String by project
            val ossrhPassword: String by project

            if (version.toString().endsWith("-SNAPSHOT", true)) {
                maven {
                    name = "mavenCentralSnapshot"
                    setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                    credentials {
                        username = ossrhUsername
                        password = ossrhPassword
                    }
                }
            } else {
                maven {
                    name = "mavenCentral"
                    setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    credentials {
                        username = ossrhUsername
                        password = ossrhPassword
                    }
                }
            }
        }
    }

    signing {
        sign(publishing.publications)
    }
}

tasks.named("publish") {
    subprojects.forEach {
        dependsOn("${it.name}:publish")
    }
}
