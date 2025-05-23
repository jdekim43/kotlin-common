import org.jreleaser.model.Active
import org.jreleaser.model.Signing

plugins {
    kotlin("multiplatform") version "2.1.21"
    id("org.jetbrains.dokka") version "2.0.0"
    id("maven-publish")
    id("org.jreleaser") version "1.18.0"
}

allprojects {
    apply {
        plugin("kotlin-multiplatform")
        plugin("org.jetbrains.dokka")
        plugin("maven-publish")
    }

    group = "kr.jadekim"
    version = "2.1.2"

    repositories {
        mavenCentral()
    }

    kotlin {
        jvmToolchain(8)

        jvm {
            testRuns["test"].executionTask.configure {
                useJUnitPlatform()
            }
        }
//        js(IR) {
//            browser()
//            nodejs()
//        }

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
//            val jsMain by getting
//            val jsTest by getting {
//                dependencies {
//                    implementation(kotlin("test-js"))
//                }
//            }
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
            maven {
                setUrl(layout.buildDirectory.dir("staging-deploy"))
            }
        }
    }
}

jreleaser {
    project {
        author("Jade Kim")
        license.set("Apache-2.0")
        links {
            vcsBrowser.set("https://github.com/jdekim43/kotlin-common")
        }
        inceptionYear.set("2021")
    }

    signing {
        active.set(Active.ALWAYS)
        armored.set(true)
        mode.set(Signing.Mode.FILE)
    }

    deploy {
        maven {
            mavenCentral {
                create("release") {
                    active.set(Active.RELEASE)
                    url.set("https://central.sonatype.com/api/v1/publisher")

                    subprojects.forEach {
                        stagingRepository(it.layout.buildDirectory.dir("staging-deploy").get().asFile.absolutePath)
                    }
                }
            }
            nexus2 {
                create("snapshot") {
                    active.set(Active.SNAPSHOT)
                    url.set("https://central.sonatype.com/repository/maven-snapshots")
                    snapshotUrl.set("https://central.sonatype.com/repository/maven-snapshots")
                    applyMavenCentralRules.set(true)
                    snapshotSupported.set(true)
                    closeRepository.set(true)
                    releaseRepository.set(true)

                    subprojects.forEach {
                        stagingRepository(it.layout.buildDirectory.dir("staging-deploy").get().asFile.absolutePath)
                    }
                }
            }
        }
    }

    release {
        github {
            repoOwner = "jdekim43"
        }
    }
}

tasks.named("publish") {
    subprojects.forEach {
        dependsOn("${it.name}:publish")
    }

    finalizedBy(":jreleaserFullRelease")
}
