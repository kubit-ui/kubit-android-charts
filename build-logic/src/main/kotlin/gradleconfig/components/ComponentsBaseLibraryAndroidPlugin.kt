package gradleconfig.components

import com.android.build.api.dsl.LibraryExtension
import java.io.File
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

/**
 * Basic configuration for Android libraries.
 */
class ComponentsBaseLibraryAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                //Plugins for libraries
                apply(libs.findPlugin(CatalogConstants.androidLibrary).get().get().pluginId)

                apply(libs.findPlugin(CatalogConstants.jetBrainsKotlinAndroid).get().get().pluginId)
                apply(libs.findPlugin(CatalogConstants.kotlinComposeCompiler).get().get().pluginId)
                apply(libs.findPlugin(CatalogConstants.detekt).get().get().pluginId)
                apply(libs.findPlugin(CatalogConstants.dokka).get().get().pluginId)
                apply(libs.findPlugin(CatalogConstants.vanniktech).get().get().pluginId)
                apply("gradleConfig.detektConfig")
                apply("gradleConfig.mavenCentralConfig")
            }

            //To configure the android block when plugin android.library is applied.
            extensions.configure<LibraryExtension> {
                applyCommonConfig(libs)
                setJvmTarget(libs)

                lint {
                    xmlReport = true
                    htmlReport = true
                    abortOnError = true
                    lintConfig = File("${project.rootDir}/gradleConfig/reports/lint.xml")
                }

                buildTypes {
                    release {
                        //Proguard required if we want to minify (shrink).
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }
    }
}
