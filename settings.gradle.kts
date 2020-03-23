pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.library",
                "com.android.application" -> useModule("com.android.tools.build:gradle:3.5.2")
                "org.jetbrains.kotlin.multiplatform",
                "kotlin-android-extensions" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.71")
            }
        }
    }

    repositories {
        gradlePluginPortal()
        google()
        jcenter()
    }
}