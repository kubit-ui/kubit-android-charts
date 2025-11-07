package com.kubit.charts.samples.components.scaffold.weather

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kubit.charts.components.axis.AxisLabelStyleDefaults
import com.kubit.charts.components.axis.HorizontalAxisChart
import com.kubit.charts.components.axis.HorizontalAxisType
import com.kubit.charts.components.axis.VerticalAxisChart
import com.kubit.charts.components.axis.model.AxisBuilder
import com.kubit.charts.components.axis.model.AxisPadding
import com.kubit.charts.components.axis.model.AxisStepStyle
import com.kubit.charts.components.axis.model.DecorativeHeightPosition
import com.kubit.charts.components.chart.linechart.LineChart
import com.kubit.charts.components.chart.linechart.model.IntersectionPainter
import com.kubit.charts.components.chart.linechart.model.LineStyle
import com.kubit.charts.components.chart.linechart.model.LineType
import com.kubit.charts.components.chart.linechart.model.Point
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPoint
import com.kubit.charts.components.chart.linechart.model.SelectionHighlightPopUp
import com.kubit.charts.components.chart.linechart.model.ShadowUnderLine
import com.kubit.charts.components.chart.linechart.model.lineBuilder
import com.kubit.charts.components.scaffold.ChartScaffold
import com.kubit.charts.samples.R
import com.kubit.charts.samples.components.utils.ChartsSampleColors
import kotlinx.collections.immutable.toImmutableList

@Preview(heightDp = 400, widthDp = 800)
@Composable
@Suppress("LongMethod", "MagicNumber")
fun WeatherSample() {

    val painterSizePx = with(LocalDensity.current) { WeatherIconSizeDp.dp.toPx() }
    val offsetPx = with(LocalDensity.current) { WeatherIconOffsetDp.dp.toPx() }
    val weatherIcons = WeatherIcons()

    BoxWithConstraints {
        val height = constraints.maxHeight

        val yAxisStepSize =
            (with(LocalDensity.current) { height.toDp() } - AxisLabelHeightDp.dp - AxisDecorativeSizeDp.dp * 2) / (VerticalSteps.axisSteps.maxOf { it.axisValue } - VerticalSteps.axisSteps.minOf { it.axisValue })
        val xAxisStepSize = 30.dp

        ChartScaffold(
            modifier = Modifier
                .padding()
                .background(ChartsSampleColors.white),
            isPinchZoomEnabled = false,
            xAxisData = HorizontalSteps,
            yAxisData = VerticalSteps,
            xUnitSize = xAxisStepSize,
            yUnitSize = yAxisStepSize,
            axisPadding = AxisPadding(
                start = (AxisPaddingStartDp + 20).dp,
                bottom = AxisPaddingBottomDp.dp
            ),
            horizontalAxis = { scroll, zoom, padding ->
                HorizontalAxisChart(
                    data = HorizontalSteps,
                    type = HorizontalAxisType.Bottom,
                    labelHeight = AxisLabelHeightDp.dp,
                    padding = padding,
                    decorativeWidth = AxisDecorativeSizeDp.dp,
                    labelsBackgroundColor = ChartsSampleColors.white,
                    zoom = zoom,
                    horizontalScroll = scroll,
                    fixedUnitSize = xAxisStepSize
                )
            },
            verticalAxis = { _, zoom, padding ->
                VerticalAxisChart(
                    data = VerticalSteps,
                    labelWidth = AxisLabelWidthDp.dp,
                    padding = padding,
                    decorativeHeight = AxisDecorativeSizeDp.dp,
                    decorativeHeightPosition = DecorativeHeightPosition.Both,
                    labelsBackgroundColor = ChartsSampleColors.white,
                    zoom = zoom,
                )
            },
            content = { contentData ->
                with(contentData) {
                    LineChart(
                        modifier = Modifier.fillMaxHeight(),
                        lines = listOf(
                            lineBuilder {
                                addPoints(
                                    weatherPoints(
                                        painterSizePx = painterSizePx,
                                        offsetPxY = offsetPx,
                                        offsetPxX = 0f,
                                        icons = weatherIcons
                                    )
                                )
                                setSelectionHighlightPoint(SelectionHighlightPoint())
                                setSelectionHighlightPopUp(SelectionHighlightPopUp())
                                setLineStyle(
                                    LineStyle(
                                        lineType = LineType.SmoothCurve(),
                                        color = ChartsSampleColors.colorColdBlue,
                                        width = LineWidth,
                                    )
                                )
                                setShadowUnderLine(
                                    ShadowUnderLine(
                                        brush = Gradient
                                    )
                                )
                            }
                        ).toImmutableList(),
                        backgroundColor = Color.Transparent,
                        xAxisData = HorizontalSteps,
                        yAxisData = VerticalSteps,
                        xAxisStepSize = xAxisStepSize,
                        yAxisStepSize = yAxisStepSize,
                        horizontalScroll = contentData.horizontalScroll,
                        onPointSelect = { point, offset ->
                            Log.d("LineChart", "Point selected: (${point.x},${point.y}) at $offset")
                        },
                    )
                }
            }
        )
    }
}

// Clase para agrupar los iconos meteorológicos
private class WeatherIcons {
    val nightCloudy: Painter
        @Composable
        get() = painterResource(id = R.drawable.moon_night_weather_cloud_cloudy)
    val cloudy: Painter
        @Composable
        get() = painterResource(id = R.drawable.cloud_weather_forecast_rain_cloudy)
    val dayCloudy: Painter
        @Composable
        get() = painterResource(id = R.drawable.cloudy_sunny_forecast_sun_cloud_weather)
    val sunny: Painter
        @Composable
        get() = painterResource(id = R.drawable.sunny_sun_weather_climate_forecast)
    val sunnyRain: Painter
        @Composable
        get() = painterResource(id = R.drawable.forecast_sun_cloud_weather_raining)
    val rain: Painter
        @Composable
        get() = painterResource(id = R.drawable.forecast_rain_cloud_weather_raining)
}

@Suppress("LongMethod", "MagicNumber")
@Composable
private fun weatherPoints(
    painterSizePx: Float,
    offsetPxY: Float,
    offsetPxX: Float,
    icons: WeatherIcons
): List<Point> = listOf(
    Point(
        x = 0f,
        y = 19f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.cloudy,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 2f,
        y = 20f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.cloudy,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 4f,
        y = 22f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.cloudy,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 6f,
        y = 23f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.nightCloudy,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 8f,
        y = 24f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.sunny,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 10f,
        y = 23f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.dayCloudy,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 12f,
        y = 22.5f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.dayCloudy,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 14f,
        y = 22f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.sunnyRain,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 16f,
        y = 22.5f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.rain,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 18f,
        y = 22f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.rain,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 20f,
        y = 21f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.nightCloudy,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
    Point(
        x = 22f,
        y = 18f,
        intersectionNode = IntersectionPainter(
            size = Size(painterSizePx, painterSizePx),
            painter = icons.nightCloudy,
            offset = Offset(y = offsetPxY, x = offsetPxX)
        )
    ),
)

private val VerticalSteps = AxisBuilder()
    .setDefaultStepStyle(null)
    .setDefaultLabelStyle(AxisLabelStyleDefaults.default.copy(color = ChartsSampleColors.gray))
    .addNode(16f, "16 º")
    .addNode(18f, "18 º")
    .addNode(20f, "20 º")
    .addNode(22f, "22 º")
    .addNode(24f, "24 º")
    .addNode(26f, "26 º")
    .addNode(28f, "28 º")
    .build()

private val HorizontalSteps = AxisBuilder()
    .setDefaultStepStyle(
        AxisStepStyle.solid(
            strokeColor = ChartsSampleColors.lightGray.copy(alpha = (0.5f)),
            strokeWidth = 0.5.dp,
        )
    )
    .setDefaultLabelStyle(AxisLabelStyleDefaults.default.copy(color = ChartsSampleColors.gray))
    .addNode(0f, "0 H")
    .addNode(2f)
    .addNode(4f, "4 H")
    .addNode(6f)
    .addNode(8f, "8 H")
    .addNode(10f)
    .addNode(12f, "12 H")
    .addNode(14f)
    .addNode(16f, "16 H")
    .addNode(18f)
    .addNode(20f, "20 H")
    .addNode(22f)
    .build()

private const val WeatherIconSizeDp = 24
private const val WeatherIconOffsetDp = 24
private const val AxisLabelHeightDp = 20
private const val AxisLabelWidthDp = 20
private const val AxisDecorativeSizeDp = 6
private const val AxisPaddingStartDp = 20
private const val AxisPaddingBottomDp = 20
private const val LineWidth = 4f
private const val LineShadowAlpha = 0.1f
private val Gradient = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to ChartsSampleColors.colorColdBlue.copy(alpha = LineShadowAlpha),
        1.0f to ChartsSampleColors.colorColdBlue.copy(alpha = 0f)
    )
)
