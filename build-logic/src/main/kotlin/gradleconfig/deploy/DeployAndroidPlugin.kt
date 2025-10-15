package gradleconfig.deploy

import java.io.File
import java.net.URI
import java.util.Locale
import java.util.Properties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create

/**
 * This plugin encapsulates all the maven publishing functionality.
 *
 * Execute this command to generate maven local:
 * ```
 * ./gradle publishToMavenLocal -PpublishVersion=0.0.0-LOCAL
 * ```
 */
class DeployAndroidPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("Applying Maven plugin...")

        with(target) {
            // Aplicar el plugin de maven-publish
            pluginManager.apply("maven-publish")
            // Aplicar el plugin de signing para firmar artefactos
            pluginManager.apply("signing")

            afterEvaluate {
                // Cargar propiedades desde local.properties
                val localProperties = Properties()
                val localPropertiesFile = File("${rootProject.projectDir}/local.properties")
                if (localPropertiesFile.exists()) {
                    localPropertiesFile.inputStream().use { localProperties.load(it) }
                }

                extensions.configure<PublishingExtension> {
                    val repositoryUrl =
                        if (project.hasProperty("repositoryUrl")) project.properties["repositoryUrl"]
                        else localProperties["repositoryUrl"] ?: DefaultRepositoryUrl
                    val repositoryUser =
                        if (project.hasProperty("repositoryUser")) project.properties["repositoryUser"]
                        else localProperties["repositoryUser"] ?: ""
                    val repositoryPass =
                        if (project.hasProperty("repositoryPass")) project.properties["repositoryPass"]
                        else localProperties["repositoryPass"] ?: ""
                    val publishVersion =
                        if (project.hasProperty("publishVersion")) project.properties["publishVersion"] else DefaultPublishVersion

                    val uploadProperties = Properties()
                    val uploadPropertiesFile = File("${projectDir}/upload.properties")
                    if (uploadPropertiesFile.exists()) {
                        uploadPropertiesFile.inputStream().use { uploadProperties.load(it) }
                    }

                    val artifactId = uploadProperties["artifact"] ?: project.name
                    val groupId = uploadProperties["group"] ?: DefaultGroupId

                    println("======================= PUBLISH PROPERTIES =======================")
                    println("artifactId: $artifactId")
                    println("groupId: $groupId")
                    println("version: $publishVersion")
                    println("url: $repositoryUrl")
                    println("================================================================")

                    repositories {
                        maven {
                            credentials {
                                username = repositoryUser.toString()
                                password = repositoryPass.toString()
                            }
                            url = URI.create(repositoryUrl.toString())
                        }
                    }

                    publications {
                        addMavenPublication(
                            Release,
                            groupId.toString(),
                            artifactId.toString(),
                            publishVersion.toString(),
                            target
                        )
                    }
                }

                // Configurar signing para firmar los artefactos
                extensions.configure<org.gradle.plugins.signing.SigningExtension> {
                    val signingKeyId = localProperties["signing.keyId"] ?: ""
                    val signingPassword = localProperties["signing.password"] ?: ""
                    val signingSecretKeyRingFile = localProperties["signing.secretKeyRingFile"] ?: ""

                    if (signingKeyId.toString().isNotEmpty() && File(signingSecretKeyRingFile.toString()).exists()) {
                        useInMemoryPgpKeys(
                            signingKeyId.toString(),
                            File(signingSecretKeyRingFile.toString()).readText(),
                            signingPassword.toString()
                        )
                        val publishingExtension = extensions.getByType(PublishingExtension::class.java)
                        sign(publishingExtension.publications)
                    }
                }
            }
        }
    }

    private fun PublicationContainer.addMavenPublication(
        environment: String,
        groupId: String,
        artifactId: String,
        version: String,
        target: Project
    ) {
        create<MavenPublication>("maven${environment.capitalize()}") {
            this.groupId = groupId
            this.artifactId = artifactId
            this.version = version

            // Usar el componente release de Android
            target.components.findByName(Release)?.let { component ->
                from(component)
            }

            // Configuración del POM para Maven Central
            pom {
                name.set(artifactId)
                description.set(ProjectDescription)
                url.set(ProjectUrl)

                licenses {
                    license {
                        name.set(LicenseName)
                        url.set(LicenseUrl)
                    }
                }

                developers {
                    developer {
                        id.set(DeveloperId)
                        name.set(DeveloperName)
                        email.set(DeveloperEmail)
                        organization.set(OrganizationName)
                        organizationUrl.set(OrganizationUrl)
                    }
                }

                scm {
                    connection.set(ScmConnection)
                    developerConnection.set(ScmDeveloperConnection)
                    url.set(ScmUrl)
                }

                issueManagement {
                    system.set(IssueSystem)
                    url.set(IssueUrl)
                }
            }
        }
    }
}

private fun String.capitalize() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
}

// Constants
private const val Release = "release"

// Repository configuration
private const val DefaultPublishVersion = "0.0.0-TEST"
private const val DefaultGroupId = "com.kubit-lab"
private const val DefaultRepositoryUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"

// POM configuration
private const val ProjectDescription =
    "A modern and comprehensive charting library for Jetpack Compose - Create beautiful, interactive and fully accessible charts in your Android applications"
private const val ProjectUrl = "https://github.com/kubit-ui/kubit-android-charts"

// License configuration
private const val LicenseName = "The Apache License, Version 2.0"
private const val LicenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"

// Developer configuration
private const val DeveloperId = "kubit-team"
private const val DeveloperName = "Kubit Development Team"
private const val DeveloperEmail = "kubit.lab.apk@gmail.com"
private const val OrganizationName = "Kubit"
private const val OrganizationUrl = "https://kubit-lab.com"

// SCM configuration
private const val ScmConnection = "scm:git:git://github.com/kubit-ui/kubit-android-charts.git"
private const val ScmDeveloperConnection = "scm:git:ssh://github.com:kubit-ui/kubit-android-charts.git"
private const val ScmUrl = "https://github.com/kubit-ui/kubit-android-charts/tree/main"

// Issue management configuration
private const val IssueSystem = "GitHub"
private const val IssueUrl = "https://github.com/kubit-ui/kubit-android-charts/issues"
