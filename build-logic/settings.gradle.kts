@file:Suppress("UnstableApiUsage")

apply(from = "../gradleConfig/dependencies/dependency-resolution.gradle.kts")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
rootProject.name = "build-logic"
