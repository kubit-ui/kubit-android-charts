package gradleconfig.components

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import java.io.File
import java.util.Properties

/**
 * Basic configuration for the app module
 */
class ComponentsBaseApplicationAndroidPlugin : Plugin<Project> {
    override fun apply(target: Project){
        with(target) {

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager){
                //Plugins for libraries
                apply(libs.findPlugin(CatalogConstants.androidApp).get().get().pluginId)

                apply(libs.findPlugin(CatalogConstants.jetBrainsKotlinAndroid).get().get().pluginId)
                apply(libs.findPlugin(CatalogConstants.kotlinComposeCompiler).get().get().pluginId)
                apply(libs.findPlugin(CatalogConstants.detekt).get().get().pluginId)
                apply("gradleConfig.detektConfig")
            }

            //To configure the android block when plugin android.application is applied.
            extensions.configure<ApplicationExtension> {
                applyCommonConfig(libs)
                setJvmTarget(libs)

                defaultConfig {
                    targetSdk = libs.findVersion(CatalogConstants.androidTargetSdk).get().requiredVersion.toInt()
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
        }
    }
}
