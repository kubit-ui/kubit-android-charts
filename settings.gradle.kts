apply(from = "gradleConfig/dependencies/dependency-resolution.gradle.kts")

pluginManagement {
    includeBuild("build-logic")
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Kubit-Android-Charts"

include(":kubit-charts")
include(":kubit-charts-storybook-app")
include(":kubit-charts:samples")
