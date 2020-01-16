import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsSetupTask
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
  kotlin("multiplatform")
  idea
}

group = "lt.petuska"
version = "1.0-SNAPSHOT"

repositories {
  mavenLocal()
  jcenter()
  mavenCentral()
  maven { url = uri("https://kotlin.bintray.com/kotlin-js-wrappers") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlinx.html") }
}

idea {
  module {
    isDownloadJavadoc = true
    isDownloadSources = true
  }
}

object Versions {
  const val coroutines = "1.3.3-native-mt"
  const val kvdom = "0.0.2-SNAPSHOT"
  const val tornadofx = "1.7.19"
  const val redux = "0.3.1"
}

val os = org.gradle.internal.os.OperatingSystem.current()!!
kotlin {
  /* Targets configuration omitted.
  *  To find out how to configure the targets, please follow the link:
  *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
  targets {
    metadata {
      sourceSets.create("browserMain")
    }
    jvm {
      compilations.all {
        kotlinOptions {
          jvmTarget = "1.8"
        }
      }
    }
    js {
      browser {
        runTask {
          devServer = devServer?.copy(
            inline = false,
            lazy = false,
            noInfo = true,
            open = false,
            overlay = true,
            port = 3000
          )
        }
      }
    }
    js("react") {
      browser {
        runTask {
          devServer = devServer?.copy(
            inline = false,
            lazy = false,
            noInfo = true,
            open = false,
            overlay = true,
            port = 3000
          )
        }
      }
    }
    wasm32 {
      binaries {
        executable {
          entryPoint = "lt.petuska.gol.main"
        }
      }
    }
    when {
      os.isLinux -> linuxX64("desktop")
      os.isWindows -> mingwX64("desktop")
      else -> null
    }?.apply {
      compilations["main"].apply {
        cinterops {
          create("gtk3") {
            execAndCapture("pkg-config --cflags gtk+-3.0")?.let {
              compilerOpts(*it)
              println("Compiler Opts: $compilerOpts")
            }
          }
        }
      }
      binaries {
        executable {
          entryPoint = "lt.petuska.gol.main"
          execAndCapture("pkg-config --libs gtk+-3.0")?.let {
            linkerOpts(*it)
            println("Linker Opts: $linkerOpts")
          }
        }
      }
    }
    
    sourceSets {
      val commonMain by getting {
        dependencies {
          implementation(kotlin("stdlib-common"))
          implementation("org.reduxkotlin:redux-kotlin:${Versions.redux}")
        }
      }
      val browserMain by getting {
        dependsOn(commonMain)
        dependencies {
          implementation("lt.petuska:kvdom:${Versions.kvdom}")
        }
      }
      val jsMain by getting {
        dependsOn(browserMain)
        dependencies {
          implementation("org.reduxkotlin:redux-kotlin-js:${Versions.redux}")
        }
      }
      val wasm32Main by getting {
        dependsOn(browserMain)
        dependencies {
          implementation("org.reduxkotlin:redux-kotlin-wasm:${Versions.redux}")
        }
      }
      val reactMain by getting {
        dependencies {
          implementation("org.jetbrains:kotlin-react:16.9.0-pre.89-kotlin-1.3.60")
          implementation("org.jetbrains:kotlin-react-dom:16.9.0-pre.89-kotlin-1.3.60")
          implementation("org.jetbrains:kotlin-styled:1.0.0-pre.89-kotlin-1.3.60")
          implementation(npm("core-js"))
          implementation(npm("react"))
          implementation(npm("react-dom"))
          implementation(npm("react-dom"))
          implementation(npm("styled-components"))
          implementation(npm("inline-style-prefixer"))
        }
      }
      val jvmMain by getting {
        dependencies {
          implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
          implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:${Versions.coroutines}")
          implementation("no.tornado:tornadofx:${Versions.tornadofx}")
        }
      }
      val desktopMain by getting {
        dependsOn(commonMain)
        dependencies {
          implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutines}")
        }
      }
    }
  }
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = "1.8"
    }
  }
  val wrapper by getting(Wrapper::class) {
    gradleVersion = "6.0.1"
  }
}

afterEvaluate {
  tasks {
    val compileKotlinJvm by getting(KotlinCompile::class)
    val jvmProcessResources by getting(Copy::class)
    val jvmRun by creating(JavaExec::class) {
      dependsOn(compileKotlinJvm)
      group = "run"
      main = "lt.petuska.gol.IndexKt"
      classpath = configurations["jvmRuntimeClasspath"] + compileKotlinJvm.outputs.files +
          jvmProcessResources.outputs.files
      workingDir = buildDir
    }
    val jsBrowserRun by getting(KotlinWebpack::class) {
      group = "run"
      doFirst {
        println("Starting webpack-devServer")
        println("Avialable on: http://localhost:3000")
      }
    }
    val reactBrowserRun by getting(KotlinWebpack::class) {
      group = "run"
      doFirst {
        println("Starting webpack-devServer")
        println("Avialable on: http://localhost:3000")
      }
    }
    val kotlinNodeJsSetup by getting(NodeJsSetupTask::class)
    val wasm32ProcessResources by getting(Copy::class)
    val linkReleaseExecutableWasm32 by getting(org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink::class)
    val wasmBundle by creating(Copy::class) {
      group = "build"
      dependsOn(wasm32ProcessResources, linkReleaseExecutableWasm32)
      from(linkReleaseExecutableWasm32.destinationDir)
      from(wasm32ProcessResources.destinationDir)
      destinationDir = file("$buildDir/bundle/wasm")
      inputs.files(wasm32ProcessResources.outputs, linkReleaseExecutableWasm32.outputs)
    }
    val wasmBrowserRun by creating(Exec::class) {
      dependsOn(wasmBundle, kotlinNodeJsSetup)
      group = "run"
      println(kotlinNodeJsSetup.destination)
      executable = "http-server"
      workingDir = wasmBundle.destinationDir
      args("${wasmBundle.destinationDir}", "-c-1")
    }
    val assemble by getting {
      dependsOn(wasmBundle)
    }
  }
}

fun execAndCapture(cmd: String): Array<String>? = ByteArrayOutputStream().use { stream ->
  exec {
    commandLine(*cmd.split(" ").toTypedArray())
    standardOutput = stream
  }.takeIf { it.exitValue == 0 }?.let {
    stream.toString().trim().split(" ").map(String::trim).toTypedArray()
  }
}
