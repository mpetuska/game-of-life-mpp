import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
    kotlin("multiplatform") version "1.3.50"
    idea
}

group = "lt.petuska"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://kotlin.bintray.com/kotlin-js-wrappers")
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

val isProductionBuild = project.hasProperty("prod") || project.hasProperty("production")
kotlin {
    targets {
        jvm {
            compilations.all {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
        }
        js {
            useCommonJs()
            browser {
                webpackTask {
                    runTask {
                        devServer = KotlinWebpackConfig.DevServer(
                            inline = true,
                            lazy = false,
                            noInfo = true,
                            open = true,
                            overlay = false,
                            port = 3000,
                            proxy = mapOf("/api" to "http://0.0.0.0:8080"),
                            contentBase = listOf(
                                "${(tasks["jsProcessResources"] as Copy).destinationDir}"
                            )
                        )
                    }
                }
            }
        }
    }
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.reduxkotlin:redux-kotlin:0.2.6")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.1")
                implementation("com.soywiz.korlibs.klock:klock:1.6.0")
            }
        }
        getByName("jvmMain") {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("no.tornado:tornadofx:1.7.17")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.1")
            }
        }
        getByName("jsMain") {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains:kotlin-react:16.9.0-pre.83-kotlin-1.3.50")
                implementation("org.jetbrains:kotlin-react-dom:16.9.0-pre.83-kotlin-1.3.50")
                implementation("org.jetbrains:kotlin-styled:1.0.0-pre.83-kotlin-1.3.50")
                implementation(npm("core-js"))
                implementation(npm("styled-components"))
                implementation(npm("inline-style-prefixer"))
                implementation(npm("react"))
                implementation(npm("react-dom"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.1")
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = "5.6.1"
    }
    withType<Kotlin2JsCompile> {
        kotlinOptions {
            moduleKind = "commonjs"
            sourceMap = !isProductionBuild
            metaInfo = true
            sourceMapEmbedSources = "always"
        }
    }
    val jsBrowserWebpack by getting(KotlinWebpack::class) {
        inputs.property("isProductionBuild", isProductionBuild)
    }
    val jsProcessResources by getting(ProcessResources::class) {
        expand(project.properties)
    }
}