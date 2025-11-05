package com.kubit.charts.storybook.ui.storybookcomponents

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.kubit.charts.storybook.theme.KubitTheme
import com.kubit.charts.storybook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StorybookScreen(
    title: String,
    modifier: Modifier = Modifier,
    onNavigateBack: (() -> Unit)? = null,
    showTitle: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (showTitle) {
                TitleTopAppBar(
                    title = title,
                    onNavigateBack = onNavigateBack,
                    onGithubClick = {
                        val intent = Intent(Intent.ACTION_VIEW, GithubUrl.toUri())
                        context.startActivity(intent)
                    },
                    scrollBehavior = scrollBehavior
                )
            } else {
                MinimalTopAppBar(
                    onNavigateBack = onNavigateBack,
                    onGithubClick = {
                        val intent = Intent(Intent.ACTION_VIEW, GithubUrl.toUri())
                        context.startActivity(intent)
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        },
        containerColor = KubitTheme.material.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            KubitTheme.material.colorScheme.background,
                            KubitTheme.material.colorScheme.surface.copy(alpha = 0.3f)
                        )
                    )
                )
        ) {
            content.invoke(paddingValues)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TitleTopAppBar(
    title: String,
    onNavigateBack: (() -> Unit)?,
    onGithubClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = KubitTheme.material.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 32.sp
                ),
                color = KubitTheme.material.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            onNavigateBack?.let {
                CleanIconButton(
                    onClick = it,
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = KubitTheme.material.colorScheme.onSurface
                        )
                    }
                )
            }
        },
        actions = {
            CleanIconButton(
                onClick = onGithubClick,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.github),
                        contentDescription = "GitHub Repository",
                        tint = KubitTheme.material.colorScheme.onSurface
                    )
                }
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = KubitTheme.material.colorScheme.background,
            scrolledContainerColor = KubitTheme.material.colorScheme.surface
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MinimalTopAppBar(
    onNavigateBack: (() -> Unit)?,
    onGithubClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = { }, // Sin título para que el HeroSection sea el protagonista
        navigationIcon = {
            onNavigateBack?.let {
                CleanIconButton(
                    onClick = it,
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = KubitTheme.material.colorScheme.onSurface
                        )
                    }
                )
            }
        },
        actions = {
            CleanIconButton(
                onClick = onGithubClick,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.github),
                        contentDescription = "GitHub Repository",
                        tint = KubitTheme.material.colorScheme.onSurface
                    )
                }
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = KubitTheme.material.colorScheme.background.copy(alpha = 1f),
            scrolledContainerColor = KubitTheme.material.colorScheme.surface.copy(alpha = 1f)
        )
    )
}

@Composable
private fun CleanIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(40.dp),
        shape = CircleShape,
        color = KubitTheme.material.colorScheme.surface.copy(alpha = 0.1f),
        contentColor = KubitTheme.material.colorScheme.onSurface
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            icon()
        }
    }
}

private const val GithubUrl = "https://github.com/kubit-ui/kubit-android-charts"
