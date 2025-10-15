plugins {
    id("gradleConfig.android.library.components.base")
    id("maven-publish")
    id("gradleConfig.deploy")
}

android {
    namespace = "com.kubit.charts.samples"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.uiToolingPreview)
    debugImplementation(libs.androidx.ui.tooling)

    implementation(projects.kubitCharts)
}
