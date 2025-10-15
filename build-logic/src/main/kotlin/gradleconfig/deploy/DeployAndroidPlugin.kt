package gradleconfig.deploy

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import java.io.File
import java.net.URI
import java.util.Locale
import java.util.Properties

/**
 * This plugin encapsulates all the maven publishing functionality.
 */
class DeployAndroidPlugin : Plugin<Project> {

    private val defaultNexusUrl = "https://nexus.ods.ok-cloud.net/repository/modelbank-android/"

    override fun apply(target: Project) {
        println("Applying Maven plugin...")

        with(target) {
            // Aplicar el plugin de maven-publish
            pluginManager.apply("maven-publish")

            afterEvaluate {
                extensions.configure<PublishingExtension> {
                    val nexusUrl =
                        if (project.hasProperty("nexusUrl")) project.properties["nexusUrl"] else defaultNexusUrl
                    val nexusUser =
                        if (project.hasProperty("nexusUser")) project.properties["nexusUser"] else ""
                    val nexusPass =
                        if (project.hasProperty("nexusPass")) project.properties["nexusPass"] else ""
                    val nexusVersion =
                        if (project.hasProperty("nexusVersion")) project.properties["nexusVersion"] else "1.0.0"

                    val nexusUploadProperties = Properties()
                    val uploadPropertiesFile = File("${projectDir}/upload.properties")
                    if (uploadPropertiesFile.exists()) {
                        uploadPropertiesFile.inputStream().use { nexusUploadProperties.load(it) }
                    }

                    val nexusArtifactId = nexusUploadProperties["nexus.artifact"] ?: project.name
                    val nexusGroupId = nexusUploadProperties["nexus.groupId"] ?: "com.kubit"
                    val buildDirectory = layout.buildDirectory.get()

                    println("======================= NEXUS PROPERTIES =======================")
                    println("artifactId: $nexusArtifactId")
                    println("groupId: $nexusGroupId")
                    println("version: $nexusVersion")
                    println("url: $nexusUrl")
                    println("================================================================")

                    repositories {
                        maven {
                            credentials {
                                username = nexusUser.toString()
                                password = nexusPass.toString()
                            }
                            url = URI.create(nexusUrl.toString())
                        }
                    }

                    publications {
                        addMavenPublication(
                            Release,
                            nexusGroupId.toString(),
                            nexusArtifactId.toString(),
                            nexusVersion.toString(),
                            buildDirectory.asFile,
                            target
                        )
                    }
                }
            }
        }
    }

    private fun PublicationContainer.addMavenPublication(
        environment: String,
        nexusGroupId: String,
        nexusArtifactId: String,
        nexusVersion: String,
        buildDirectory: File,
        target: Project)
    {
        create<MavenPublication>("maven${environment.capitalize()}") {
            groupId = nexusGroupId
            artifactId = nexusArtifactId
            version = nexusVersion

            // Usar el componente release de Android
             target.components.findByName(Release)?.let { component ->
                from(component)
            }
        }
    }
}

private fun String.capitalize() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
}

private const val Release = "release"
