plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.gradle.plugin.kotlin)
    compileOnly(libs.detekt.plugin)
}

group = "gradleconfig.android.buildlogic"

gradlePlugin {
    plugins {
        //Plugins for gradle.kts files dependencies and config
        //Base configuration for Android libraries modules
        register("componentsLibraryBaseAndroid") {
            id = "gradleConfig.android.library.components.base"
            implementationClass = "gradleconfig.components.ComponentsBaseLibraryAndroidPlugin"
        }

        //Base configuration for Android applications modules
        register("componentsApplicationBaseAndroid") {
            id = "gradleConfig.android.application.components.base"
            implementationClass = "gradleconfig.components.ComponentsBaseApplicationAndroidPlugin"
        }

        //Maven central deployment config
        register("mavenCentralConfigPlugin") {
            id = "gradleConfig.mavenCentralConfig"
            implementationClass = "gradleconfig.deploy.MavenCentralConfigPlugin"
        }

        //Git hooks
        register("gitHooksPlugin") {
            id = "gradleConfig.gitHooks"
            implementationClass = "gradleconfig.scripts.GitHooksPlugin"
        }

        //Detekt Config
        register("detektConfigPlugin") {
            id = "gradleConfig.detektConfig"
            implementationClass = "gradleconfig.detekt.DetektConfigPlugin"
        }
    }
}
