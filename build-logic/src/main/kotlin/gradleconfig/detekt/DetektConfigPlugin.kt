package gradleconfig.detekt

import gradleconfig.components.CatalogConstants
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class DetektConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            println("Applying Detekt Config plugin...")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<DetektExtension> {
                autoCorrect = true

                config.setFrom(
                    "$rootDir/gradleConfig/reports/detekt-config.yml",
                )

                reports {
                    xml.required.set(true)
                    html.required.set(false)
                    txt.required.set(false)
                }

                dependencies {
                    add("detektPlugins", libs.findLibrary(CatalogConstants.detektFormatting).get())
                    add("detektPlugins", libs.findLibrary(CatalogConstants.detektComposeRules).get())
                }
            }

        }
    }
}
