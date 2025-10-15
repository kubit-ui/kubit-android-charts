package gradleconfig.scripts

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import java.io.File

/**
 * This plugin encapsulates git hooks configuration
 */

class GitHooksPlugin : Plugin<Project> {

    private val sampleAppModuleName = "kubit-charts-storybook-app"

    override fun apply(target: Project) {
        println("Applying git hooks...")

        with(target){
            tasks.create("installPreCommitGitHook", Copy::class.java) {
                from(File("$rootDir/gradleConfig/scripts/pre-commit"))
                into(File("$rootDir/.git/hooks/"))
                fileMode = 0x0744
            }

            tasks.create("installPostCommitGitHook", Copy::class.java) {
                from(File("$rootDir/gradleConfig/scripts/post-commit"))
                into(File("$rootDir/.git/hooks/"))
                fileMode = 0x0744
            }

            afterEvaluate {
                tasks.getByPath(":${sampleAppModuleName}:preBuild")
                    .dependsOn(
                        ":installPreCommitGitHook",
                        ":installPostCommitGitHook"
                    )
            }
        }
    }
}