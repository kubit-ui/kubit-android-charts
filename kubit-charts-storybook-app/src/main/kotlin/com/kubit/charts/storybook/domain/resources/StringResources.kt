package com.kubit.charts.storybook.domain.resources

import android.content.Context
import com.kubit.charts.storybook.R

/**
 * String resources interface that can be implemented for different platforms
 * This approach makes it easier to migrate to KMM in the future
 */
interface StringResources {
    val mainTitle: String
    val componentsTitle: String
    val componentsSubtitle: String
    val componentsDescription: String
    val samplesTitle: String
    val samplesSubtitle: String
    val samplesDescription: String
    val powerfulChartLibrary: String
    val builtWithLove: String
    val jetpackCompose: String
    val viewOnGithub: String
    val contributeText: String
    val githubDescription: String
    val githubRepositoryUrl: String
}

/**
 * Android implementation of StringResources using Android resources
 * In KMM, this would be moved to androidMain source set
 */
class AndroidStringResources(private val context: Context) : StringResources {
    override val mainTitle: String
        get() = context.getString(R.string.main_title)

    override val componentsTitle: String
        get() = context.getString(R.string.components_title)

    override val componentsSubtitle: String
        get() = context.getString(R.string.components_subtitle)

    override val componentsDescription: String
        get() = context.getString(R.string.components_description)

    override val samplesTitle: String
        get() = context.getString(R.string.samples_title)

    override val samplesSubtitle: String
        get() = context.getString(R.string.samples_subtitle)

    override val samplesDescription: String
        get() = context.getString(R.string.samples_description)

    override val powerfulChartLibrary: String
        get() = context.getString(R.string.powerful_chart_library)

    override val builtWithLove: String
        get() = context.getString(R.string.built_with_love)

    override val jetpackCompose: String
        get() = context.getString(R.string.jetpack_compose)

    override val viewOnGithub: String
        get() = context.getString(R.string.view_on_github)

    override val contributeText: String
        get() = context.getString(R.string.contribute_text)

    override val githubDescription: String
        get() = context.getString(R.string.github_description)

    override val githubRepositoryUrl: String
        get() = context.getString(R.string.github_repository_url)
}

/**
 * Provider for StringResources
 * This allows easy injection and testing
 *
 *  commonMain/
 *  ├── StringResources.kt (interface)
 *  └── StringResourcesProvider.kt (common provider)
 *
 *  androidMain/
 *  └── AndroidStringResources.kt (Android)
 *
 *  iosMain/
 *  └── IOSStringResources.kt (implementación iOS)
 *
 */
object StringResourcesProvider {
    private var instance: StringResources? = null

    fun get(): StringResources = instance ?: throw IllegalStateException(
        "StringResources not initialized. Call initialize() first."
    )

    fun initialize(context: Context) {
        instance = AndroidStringResources(context.applicationContext)
    }

    fun set(stringResources: StringResources) {
        instance = stringResources
    }
}
