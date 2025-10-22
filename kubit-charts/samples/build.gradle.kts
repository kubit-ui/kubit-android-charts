plugins {
    id("gradleConfig.android.library.components.base")
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


mavenPublishing {
    publishToMavenCentral(true)
    signAllPublications()

    coordinates(
        groupId = "com.kubit-lab",
        artifactId = "charts-samples",
        version = project.findProperty("version") as? String
            ?: error("No se ha definido el parámetro 'version'. Usa -Pversion=...")
    )

    pom {
        name.set("Kubit Android Charts Samples")
        description.set("A modern and comprehensive charting library for Jetpack Compose - Create beautiful, interactive and fully accessible charts in your Android applications")
        inceptionYear.set("2025")
        url.set("https://github.com/kubit-ui/kubit-android-charts")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        developers {
            developer {
                id.set("kubit-team")
                name.set("Kubit Development Team")
                email.set("kubit.lab.apk@gmail.com")
            }
        }

        organization {
            name.set("Kubit")
            url.set("https://kubit-lab.com")
        }

        scm {
            url.set("https://github.com/kubit-ui/kubit-android-charts/tree/main")
            connection.set("scm:git:git://github.com/kubit-ui/kubit-android-charts.git")
            developerConnection.set("scm:git:ssh://github.com:kubit-ui/kubit-android-charts.git")
        }

        issueManagement {
            system.set("GitHub")
            url.set("https://github.com/kubit-ui/kubit-android-charts/issues")
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
