// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinComposeCompiler) apply false
    alias(libs.plugins.vanniktech.maven.publish) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)

    id("gradleConfig.gitHooks")
    id("gradleConfig.detektConfig")
}
