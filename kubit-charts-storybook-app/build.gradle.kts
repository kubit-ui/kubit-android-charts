plugins {
    id("gradleConfig.android.application.components.base")
}

android {
    namespace = "com.kubit.charts.storybook"

    defaultConfig {
        applicationId = "com.kubit.charts.storybook.app"
        versionCode = 1
        versionName = "1.0.0"
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

    implementation(projects.kubitCharts.samples)
}
