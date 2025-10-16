package gradleconfig.components

/**
 * Auxiliary class o avoid hardcoding strings in the plugins and to centralize every TOML
 * change here.
 */
object CatalogConstants {

    //Basic config
    //Plugins
    const val androidLibrary = "androidLibrary"
    const val androidApp = "androidApplication"
    const val jetBrainsKotlinAndroid = "kotlin"
    const val detekt = "detekt"
    const val vanniktech = "vanniktech-maven-publish"

    //Versions
    const val androidCompileSdk = "androidCompileSdk"
    const val androidMinSdk = "androidMinSdk"
    const val androidTargetSdk = "androidTargetSdk"
    const val jvmTarget = "jvmTarget"
    const val composeCompiler = "composeCompiler"
    const val kotlinComposeCompiler = "kotlinComposeCompiler"
    const val dokka = "dokka"

    //Detekt plugins
    const val detektComposeRules = "composeRules-detekt"
    const val detektFormatting = "detekt-formatting"
}
