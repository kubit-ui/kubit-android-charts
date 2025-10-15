package gradleconfig.components

import com.android.build.api.dsl.AndroidResources
import com.android.build.api.dsl.BuildFeatures
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.DefaultConfig
import com.android.build.api.dsl.Installation
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalog

/**
 * Shared configuration for Android applications and libraries.
 * @param libs Version catalog to get the versions of the dependencies.
 */
fun CommonExtension<out BuildFeatures,
        out BuildType,
        out DefaultConfig,
        out ProductFlavor,
        out AndroidResources,
        out Installation>.applyCommonConfig(libs: VersionCatalog) {

    compileSdk = libs.findVersion(CatalogConstants.androidCompileSdk).get().requiredVersion.toInt()

    defaultConfig {
        minSdk = libs.findVersion(CatalogConstants.androidMinSdk).get().requiredVersion.toInt()
        lint.targetSdk = libs.findVersion(CatalogConstants.androidTargetSdk).get().requiredVersion.toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.findVersion(CatalogConstants.composeCompiler).get().requiredVersion
    }

    buildFeatures {
        compose = true
    }
}
