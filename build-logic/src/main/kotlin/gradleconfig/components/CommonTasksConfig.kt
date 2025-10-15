package gradleconfig.components

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * This is required for tasks like Lint, to avoid conflicts between Java versions.
 */
fun Project.setJvmTarget(libs: VersionCatalog) {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = libs.findVersion(CatalogConstants.jvmTarget).get().requiredVersion
        }
    }

}

