plugins {
    id("gradleConfig.android.application.components.base")
}

android {
    namespace = "com.kubit.charts.storybook"

    defaultConfig {
        applicationId = "com.kubit.charts.storybook.app"
        versionCode = 4
        versionName = "0.1.1"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    implementation(platform(libs.androidx.compose.composeBom))
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.appcompat)

    implementation(projects.kubitCharts.samples)
    implementation(projects.kubitCharts)

}
