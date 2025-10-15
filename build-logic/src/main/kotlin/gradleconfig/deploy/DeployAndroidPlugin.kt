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
 * Gradle plugin that encapsulates all Maven publishing functionality for Android projects.
 *
 * This plugin automates the process of publishing Android libraries to Maven repositories by:
 * - Loading configuration from local.properties and upload.properties files
 * - Setting up Maven repository credentials and URLs
 * - Creating Maven publications with proper POM metadata
 * - Configuring Android release components for publication
 *
 * ## Configuration Files
 *
 * ### local.properties (project root)
 * Contains sensitive repository credentials:
 * ```properties
 * repositoryUrl=https://your-maven-repo.com/repository/
 * repositoryUser=your-username
 * repositoryPass=your-password
 * ```
 *
 * ### upload.properties (module level)
 * Contains artifact configuration:
 * ```properties
 * group=com.your.company
 * artifact=your-artifact-name
 * ```
 *
 * ## Usage Examples
 *
 * ### Publishing to Maven Local
 * ```bash
 * ./gradlew publishToMavenLocal -PpublishVersion=1.0.0-LOCAL
 * ```
 *
 * ### Publishing to Remote Repository
 * ```bash
 * ./gradlew publish -PpublishVersion=1.0.0
 * ```
 *
 * ### Publishing with Custom Repository
 * ```bash
 * ./gradlew publish -PpublishVersion=1.0.0 -PrepositoryUrl=https://custom-repo.com/
 * ```
 *
 * ## Project Properties
 *
 * The following Gradle project properties can be used to override default values:
 * - `publishVersion`: Version string for the published artifact (default: "0.0.0-TEST")
 * - `repositoryUrl`: Maven repository URL (overrides local.properties)
 * - `repositoryUser`: Repository username (overrides local.properties)
 * - `repositoryPass`: Repository password (overrides local.properties)
 *
 * ## POM Configuration
 *
 * The plugin automatically configures the Maven POM with:
 * - Project description and URL
 * - Apache 2.0 license information
 * - Developer and organization details
 * - SCM (Source Control Management) information
 * - Issue tracking information
 *
 * @since 1.0.0
 * @author Kubit Development Team
 */
class DeployAndroidPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        println("Applying Maven plugin...")

        with(target) {
            pluginManager.apply("maven-publish")
            afterEvaluate {
                configurePublishing()
            }
        }
    }

    /**
     * Configures Maven publishing configuration for the project.
     * Loads local and upload properties, configures repository and publications.
     */
    private fun Project.configurePublishing() {
        println("Configuring publishing for project: $name")

        val localProperties = loadLocalProperties()
        val publishConfig = createPublishConfiguration(localProperties)
        val uploadProperties = loadUploadProperties()

        printPublishingConfiguration(publishConfig, uploadProperties)

        extensions.configure<PublishingExtension> {
            repositories {
                maven {
                    credentials {
                        username = publishConfig.repositoryUser
                        password = publishConfig.repositoryPass
                    }
                    url = URI.create(publishConfig.repositoryUrl)
                }
            }

            publications {
                addMavenPublication(
                    Release,
                    uploadProperties.groupId,
                    uploadProperties.artifactId,
                    publishConfig.publishVersion,
                    this@configurePublishing
                )
            }
        }
    }

    /**
     * Loads local properties from the local.properties file in the root project.
     * @return Properties with loaded local configurations
     */
    private fun Project.loadLocalProperties(): Properties {
        println("Loading local properties...")
        val localProperties = Properties()
        val localPropertiesFile = File("${rootProject.projectDir}/local.properties")

        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { localProperties.load(it) }
            println("Local properties loaded successfully")
        } else {
            println("Warning: local.properties file not found")
        }

        return localProperties
    }

    /**
     * Creates publish configuration by combining project properties and local properties.
     * @param localProperties Properties loaded from local.properties
     * @return PublishConfiguration with all necessary configuration for publishing
     */
    private fun Project.createPublishConfiguration(localProperties: Properties): PublishConfiguration {
        return PublishConfiguration(
            repositoryUrl = getPropertyOrDefault("repositoryUrl", localProperties["repositoryUrl"]) ?: "",
            repositoryUser = getPropertyOrDefault("repositoryUser", localProperties["repositoryUser"]) ?: "",
            repositoryPass = getPropertyOrDefault("repositoryPass", localProperties["repositoryPass"]) ?: "",
            publishVersion = getPropertyOrDefault("publishVersion", null) ?: DefaultPublishVersion
        )
    }

    /**
     * Gets the value of a project property or uses a default value.
     * @param propertyName Name of the property to search for
     * @param defaultValue Default value if the property doesn't exist
     * @return Property value as String or null
     */
    private fun Project.getPropertyOrDefault(propertyName: String, defaultValue: Any?): String? {
        return if (hasProperty(propertyName)) {
            properties[propertyName]?.toString()
        } else {
            defaultValue?.toString()
        }
    }

    /**
     * Loads upload properties from the upload.properties file of the project.
     * @return UploadConfiguration with artifactId and groupId configuration
     */
    private fun Project.loadUploadProperties(): UploadConfiguration {
        println("Loading upload properties...")
        val uploadProperties = Properties()
        val uploadPropertiesFile = File("${projectDir}/upload.properties")

        if (uploadPropertiesFile.exists()) {
            uploadPropertiesFile.inputStream().use { uploadProperties.load(it) }
            println("Upload properties loaded successfully")
        } else {
            println("Warning: upload.properties file not found, using defaults")
        }

        return UploadConfiguration(
            artifactId = uploadProperties["artifact"]?.toString() ?: name,
            groupId = uploadProperties["group"]?.toString() ?: DefaultGroupId
        )
    }

    /**
     * Prints publishing configuration to console for debugging purposes.
     * @param publishConfig Publishing configuration with URL, user and version
     * @param uploadConfig Upload configuration with artifactId and groupId
     */
    private fun printPublishingConfiguration(
        publishConfig: PublishConfiguration,
        uploadConfig: UploadConfiguration
    ) {
        println("")
        println("")
        println("======================= PUBLISH PROPERTIES =======================")
        println("artifactId: ${uploadConfig.artifactId}")
        println("groupId: ${uploadConfig.groupId}")
        println("version: ${publishConfig.publishVersion}")
        println("url: ${publishConfig.repositoryUrl}")
        println("================================================================")
        println("")
        println("")
    }

    /**
     * Adds a Maven publication to the publications container with the specified configuration.
     * @param environment Publishing environment (e.g.: "release")
     * @param groupId Maven group ID
     * @param artifactId Maven artifact ID
     * @param version Artifact version
     * @param target Target project from which to get the Android component
     */
    private fun PublicationContainer.addMavenPublication(
        environment: String,
        groupId: String,
        artifactId: String,
        version: String,
        target: Project
    ) {
        println("Creating Maven publication: maven${environment.capitalize()}")

        create<MavenPublication>("maven${environment.capitalize()}") {
            this.groupId = groupId
            this.artifactId = artifactId
            this.version = version

            // Use Android release component
            target.components.findByName(Release)?.let { component ->
                from(component)
                println("Using Android release component for publication")
            } ?: run {
                println("Warning: Android release component not found")
            }

            configurePom(artifactId)
        }
    }

    /**
     * Configures the POM (Project Object Model) of the Maven publication with all project information.
     * @param artifactId Artifact ID that will be used as the POM name
     */
    private fun MavenPublication.configurePom(artifactId: String) {
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

    // Data classes for better data organization
    private data class PublishConfiguration(
        val repositoryUrl: String,
        val repositoryUser: String,
        val repositoryPass: String,
        val publishVersion: String
    )

    private data class UploadConfiguration(
        val artifactId: String,
        val groupId: String
    )
}

/**
 * Extension function to capitalize the first letter of a String.
 * Uses Locale.ROOT to avoid localization issues.
 * @return String with the first letter capitalized
 */
private fun String.capitalize() = this.replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
}

// Constants
private const val Release = "release"

// Repository configuration
private const val DefaultPublishVersion = "0.0.0-TEST"
private const val DefaultGroupId = "com.kubit-lab"

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
