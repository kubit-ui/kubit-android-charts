package com.kubit.charts.samples.components.scaffold.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kubit.charts.samples.R
import com.kubit.charts.samples.components.utils.ChartsSampleColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WeatherAppLayoutSample() {
    val currentDate = SimpleDateFormat(DatePattern, Locale.forLanguageTag(LocaleTag)).format(Date())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ChartsSampleColors.SkyBlueColor,
                        ChartsSampleColors.LightBlueColor
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(DefaultPadding.dp)
        ) {
            // Header with location and date
            WeatherHeader(
                location = DefaultLocationName,
                date = currentDate
            )

            Spacer(modifier = Modifier.height(SmallSpacing.dp))

            // Current temperature and weather condition
            CurrentWeatherInfo(
                temperature = DefaultTemperature,
                weatherCondition = DefaultWeatherCondition,
                highTemp = DefaultHighTemperature,
                lowTemp = DefaultLowTemperature
            )

            Spacer(modifier = Modifier.height(MediumSpacing.dp))

            // Chart card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ChartCardHeight.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = CardElevation.dp),
                shape = RoundedCornerShape(CardCornerRadius.dp),
                colors = CardDefaults.cardColors(containerColor = ChartsSampleColors.white)
            ) {
                Column(
                    modifier = Modifier.padding(CardInnerPadding.dp)
                ) {
                    Text(
                        text = ForecastTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = ChartsSampleColors.darkGray,
                        modifier = Modifier.padding(
                            horizontal = TitleHorizontalPadding.dp,
                            vertical = TitleVerticalPadding.dp
                        )
                    )
                    WeatherSample()
                }
            }
        }
    }
}

@Composable
private fun WeatherHeader(
    location: String,
    date: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sunny_sun_weather_climate_forecast),
                    contentDescription = LocationIconDescription,
                    modifier = Modifier.size(LocationIconSize.dp),
                    tint = ChartsSampleColors.darkGray
                )
                Spacer(modifier = Modifier.width(ExtraSmallSpacing.dp))
                Text(
                    text = location,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = ChartsSampleColors.darkGray
                )
            }
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium,
                color = ChartsSampleColors.darkGray.copy(alpha = DateTextAlpha)
            )
        }
    }
}

@Composable
private fun CurrentWeatherInfo(
    temperature: String,
    weatherCondition: String,
    highTemp: String,
    lowTemp: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = temperature,
                fontSize = TemperatureFontSize.sp,
                fontWeight = FontWeight.Light,
                color = ChartsSampleColors.white
            )
            Text(
                text = weatherCondition,
                style = MaterialTheme.typography.bodyLarge,
                color = ChartsSampleColors.white.copy(alpha = WeatherConditionAlpha)
            )
            Row {
                Text(
                    text = "${HighTempPrefix}$highTemp",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ChartsSampleColors.white.copy(alpha = TemperatureRangeAlpha)
                )
                Spacer(modifier = Modifier.width(SmallSpacing.dp))
                Text(
                    text = "${LowTempPrefix}$lowTemp",
                    style = MaterialTheme.typography.bodyMedium,
                    color = ChartsSampleColors.white.copy(alpha = TemperatureRangeAlpha)
                )
            }
        }

        // Large weather icon
        Image(
            painter = painterResource(id = R.drawable.sunny_sun_weather_climate_forecast),
            contentDescription = WeatherIconDescription,
            modifier = Modifier.size(WeatherIconSize.dp),
        )
    }
}

@Preview(heightDp = PreviewHeight, widthDp = PreviewWidth)
@Composable
fun WeatherAppLayoutPreview() {
    WeatherAppLayoutSample()
}

// String Constants
private const val DatePattern = "EEEE, dd MMM"
private const val LocaleTag = "en"
private const val DefaultLocationName = "Madrid"
private const val DefaultTemperature = "24°"
private const val DefaultWeatherCondition = "Mostly Cloudy"
private const val DefaultHighTemperature = "26°"
private const val DefaultLowTemperature = "18°"
private const val ForecastTitle = "Hourly Forecast"
private const val LocationIconDescription = "Location"
private const val WeatherIconDescription = "Current weather condition"
private const val HighTempPrefix = "High: "
private const val LowTempPrefix = "Low: "

// Size Constants
private const val DefaultPadding = 16
private const val SmallSpacing = 16
private const val MediumSpacing = 24
private const val ExtraSmallSpacing = 4
private const val ChartCardHeight = 300
private const val CardElevation = 8
private const val CardCornerRadius = 16
private const val CardInnerPadding = 8
private const val TitleHorizontalPadding = 8
private const val TitleVerticalPadding = 4
private const val LocationIconSize = 16
private const val WeatherIconSize = 100
private const val TemperatureFontSize = 72
private const val PreviewHeight = 500
private const val PreviewWidth = 400

// Alpha Constants
private const val DateTextAlpha = 0.7f
private const val WeatherConditionAlpha = 0.9f
private const val TemperatureRangeAlpha = 0.8f
