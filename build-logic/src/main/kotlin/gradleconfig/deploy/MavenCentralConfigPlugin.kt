package gradleconfig.deploy

import org.gradle.api.Plugin
import org.gradle.api.Project

class MavenCentralConfigPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            println("Applying Maven Central Config plugin...")
        }
    }
}
