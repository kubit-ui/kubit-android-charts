# Kubit Charts

[![Visit the website at kubit-lab.com/](https://img.shields.io/badge/visit-website-red.svg?logo=firefox)](https://kubit-lab.com/)
![Maven Central Version](https://img.shields.io/maven-central/v/com.kubit-lab/charts?color=df2b51)

Kubit Charts is a library that provides a set of customizable and easy-to-use charts for Android applications. 
It is built using Jetpack Compose and currently supports:

- Axis
- Line chart
- Bar chart
- Plot chart
- Pie chart
- Zoom area chart

This library is an alpha version and is still under development. More charts and features will be added in future releases.

# AxisChart Documentation

This module provides composable components for drawing horizontal and vertical axes in charts using Jetpack Compose. 
It is designed for flexibility and customization, supporting a wide range of chart axis scenarios.

## Components

### Key concepts

**AxisData**: Contains a list of `AxisStep` objects, each representing a point on the axis with a value and label. These points are in 'math' units, or 'chart' units.
In other words, they are represented in the coordinate system of the data we want to represent in a chart, not in pixels, dps or any other graphical unit.

Inside AxisData there are two helper methods: **toCanvasCoordinatesVAxis** and **toCanvasCoordinatesHAxis**. Both methods are really similar and they are used to convert 
the 'math' units to 'canvas' units, or 'pixel' units to be able to draw them on the screen.

### `toCanvasCoordinatesVAxis` Parameters

- `height`: *(Float)* The total height of the axis in pixels. Ignored if fixedUnitSize is set.
- `topPaddingInPx`: *(Float)* Padding at the top of the axis in pixels.
- `bottomPaddingInPx`: *(Float)* Padding at the bottom of the axis in pixels.
- `decorativeHeightInPx`: *(Float)* Extra height for decorative grid lines at the axis edge, in pixels.
- `decorativeHeightPosition`: *(DecorativeHeightPosition)* Specifies where the decorative height is applied (`Top`, `Bottom`, or `Both`).
- `fixedUnitSize`: *(Float?)* If set, fixes the pixel size for each axis unit. If `null`, it is calculated automatically using height parameter.
- `zoom`: *(Float)* Zoom factor to scale the axis. Default is `1f`.

---

### `toCanvasCoordinatesHAxis` Parameters

- `width`: *(Float)* The total width of the axis in pixels. Ignored if fixedUnitSize is set.
- `startPaddingInPx`: *(Float)* Padding at the start (left) of the axis in pixels.
- `endPaddingInPx`: *(Float)* Padding at the end (right) of the axis in pixels.
- `decorativeWidthInPx`: *(Float)* Extra width for decorative grid lines at the axis edge, in pixels.
- `decorativeWidthPosition`: *(DecorativeWidthPosition)* Specifies where the decorative width is applied (`Start`, `End`, or `Both`).
- `fixedUnitSize`: *(Float?)* If set, fixes the pixel size for each axis unit. If `null`, it is calculated automatically using width parameter.
- `zoom`: *(Float)* Zoom factor to scale the axis. Default is `1f`.

### HorizontalAxisChart

Draws a horizontal axis with labels, grid lines, and decorative options.

**Parameters:**
- `data`: `AxisData` containing axis steps and labels.
- `labelHeight`: Height reserved for axis labels (`Dp`).
- `modifier`: Optional Compose `Modifier`.
- `type`: Axis position (`HorizontalAxisType.Top` or `HorizontalAxisType.Bottom`).
- `padding`: Custom axis padding (`AxisPadding`).
- `decorativeWidth`: Extra grid line length at the axis edge (`Dp`).
- `decorativeWidthPosition`: Position for decorative width (`DecorativeWidthPosition`).
- `customYOffset`: Custom Y offset for axis placement (`Dp?`).
- `labelVerticalAlignment`: Vertical alignment for labels (`AxisLabelVerticalAlignment`).
- `labelCenterAlignment`: Pivot for label rotation (`AxisLabelCenterAlignment`).
- `labelRotation`: Label rotation in degrees (`Float`).
- `labelVerticalGap`: Gap between labels and grid lines (`Dp`).
- `fixedUnitSize`: Fixed unit size for axis steps (`Dp?`).
- `horizontalScroll`: Horizontal scroll offset (`Dp`).
- `zoom`: Zoom factor (`Float`).
- `labelsBackgroundColor`: Background color for labels (`Color`).

**Basic Example:**
```kotlin
HorizontalAxisChart(
    data = axisData,
    labelHeight = 32.dp
)
```

**Advanced Example:**
```kotlin
HorizontalAxisChart(
    data = axisData,
    labelHeight = 32.dp,
    type = HorizontalAxisType.Bottom,
    padding = AxisPadding(start = 8.dp, end = 8.dp),
    decorativeWidth = 12.dp,
    decorativeWidthPosition = DecorativeWidthPosition.End,
    customYOffset = null,
    labelVerticalAlignment = AxisLabelVerticalAlignment.Center,
    labelCenterAlignment = AxisLabelCenterAlignment.Center,
    labelRotation = 45f,
    labelVerticalGap = 4.dp,
    fixedUnitSize = 40.dp,
    horizontalScroll = 20.dp,
    zoom = 1.2f,
    labelsBackgroundColor = Color.LightGray
)
```

### VerticalAxisChart

Draws a vertical axis with labels, grid lines, and decorative options.

**Parameters:**
- `data`: `AxisData` containing axis steps and labels.
- `labelWidth`: Width reserved for axis labels (`Dp`).
- `modifier`: Optional Compose `Modifier`.
- `type`: Axis position (`VerticalAxisType.Start` or `VerticalAxisType.End`).
- `padding`: Custom axis padding (`AxisPadding`).
- `decorativeHeight`: Extra grid line length at the axis edge (`Dp`).
- `decorativeHeightPosition`: Position for decorative height (`DecorativeHeightPosition`).
- `customXOffset`: Custom X offset for axis placement (`Dp?`).
- `labelHorizontalAlignment`: Horizontal alignment for labels (`AxisLabelHorizontalAlignment`).
- `labelCenterAlignment`: Pivot for label rotation (`AxisLabelCenterAlignment`).
- `labelRotation`: Label rotation in degrees (`Float`).
- `labelHorizontalGap`: Gap between labels and grid lines (`Dp`).
- `fixedUnitSize`: Fixed unit size for axis steps (`Dp?`).
- `verticalScroll`: Vertical scroll offset (`Dp`).
- `zoom`: Zoom factor (`Float`).
- `labelsBackgroundColor`: Background color for labels (`Color`).

**Basic Example:**
```kotlin
VerticalAxisChart(
    data = axisData,
    labelWidth = 48.dp
)
```

**Advanced Example:**
```kotlin
VerticalAxisChart(
    data = axisData,
    labelWidth = 48.dp,
    type = VerticalAxisType.Start,
    padding = AxisPadding(top = 8.dp, bottom = 8.dp),
    decorativeHeight = 10.dp,
    decorativeHeightPosition = DecorativeHeightPosition.Top,
    customXOffset = 16.dp,
    labelHorizontalAlignment = AxisLabelHorizontalAlignment.Center,
    labelCenterAlignment = AxisLabelCenterAlignment.Center,
    labelRotation = 90f,
    labelHorizontalGap = 6.dp,
    fixedUnitSize = 30.dp,
    verticalScroll = 10.dp,
    zoom = 0.8f,
    labelsBackgroundColor = Color.White
)
```

## State Hoisting

You can use the hoisted state functions for advanced scenarios, such as axis dependencies or parent access to processed axis data.

**Horizontal Axis State:**
```kotlin
val chartState = rememberHorizontalAxisDataState(
    data = axisData,
    width = 400f,
    startPaddingInPx = 16f,
    endPaddingInPx = 16f,
    decorativeWidthInPx = 12f,
    decorativeWidthPosition = DecorativeWidthPosition.End,
    fixedUnitSize = 40f,
    zoom = 1f
)

HorizontalAxisChart(
    chartState = chartState,
    labelHeight = 32.dp
)
```

**Vertical Axis State:**
```kotlin
val chartState = rememberVerticalAxisDataState(
    data = axisData,
    height = 300f,
    topPaddingInPx = 8f,
    bottomPaddingInPx = 8f,
    decorativeHeightInPx = 10f,
    decorativeHeightPosition = DecorativeHeightPosition.Top,
    fixedUnitSize = 30f,
    zoom = 1f
)

VerticalAxisChart(
    chartState = chartState,
    labelWidth = 48.dp
)
```

## AxisData and AxisStep

Define your axis steps and labels using `AxisData` and `AxisStep`.

**Example:**
```kotlin
val axisData = AxisData(
    axisSteps = listOf(
        AxisStep(0f, "Zero"),
        AxisStep(10f, "Ten"),
        AxisStep(20f, "Twenty"),
        AxisStep(30f, "Thirty")
    )
)
```

## Customization

- **Alignment:** Use enums like `HorizontalAxisType`, `VerticalAxisType`, `AxisLabelHorizontalAlignment`, `AxisLabelVerticalAlignment`, and `AxisLabelCenterAlignment` for axis and label positioning.
- **Decorative Lines:** Extend grid lines with `decorativeWidth`/`decorativeHeight` and their position enums.
- **Scroll & Zoom:** Control axis scaling and scrolling with `horizontalScroll`, `verticalScroll`, and `zoom`.
- **Label Styling:** Set background color, rotation, alignment, and gaps for labels.
- **Fixed Unit Size:** Lock axis step size for precise control.

## Error Handling

If you set a non-zero scroll value without a fixed unit size, an `AxisChartException.AxisScrollWithoutFixedStepException` will be thrown.

## Shade Regions

You can define colored regions on the axis using `ShadeRegion`.

**Example:**
```kotlin
val negativeRegion = ShadeRegion(
    fromX = 0f,
    toX = 50f,
    fromY = -100f,
    toY = 0f
)
```

## Defaults

- **Label Style:** See `AxisLabelStyleDefaults.default` for default text style.
- **Grid Line Style:** Use `AxisStepStyleDefaults.solid` or `AxisStepStyleDefaults.dashed`.

## Full Example

```kotlin
val axisData = AxisData(
    axisSteps = listOf(
        AxisStep(-10f, "-10"),
        AxisStep(0f, "0"),
        AxisStep(10f, "10"),
        AxisStep(20f, "20")
    )
)

HorizontalAxisChart(
    data = axisData,
    labelHeight = 32.dp,
    type = HorizontalAxisType.Top,
    padding = AxisPadding(start = 8.dp, end = 8.dp),
    decorativeWidth = 8.dp,
    decorativeWidthPosition = DecorativeWidthPosition.Start,
    labelRotation = 30f,
    labelsBackgroundColor = Color(0xFFE0E0E0)
)

VerticalAxisChart(
    data = axisData,
    labelWidth = 48.dp,
    type = VerticalAxisType.End,
    padding = AxisPadding(top = 8.dp, bottom = 8.dp),
    decorativeHeight = 8.dp,
    decorativeHeightPosition = DecorativeHeightPosition.Bottom,
    labelRotation = 0f,
    labelsBackgroundColor = Color(0xFFF5F5F5)
)
```

## Requirements

- Jetpack Compose
- Kotlin

---

For more examples and advanced usage, refer to the sample files and API documentation in the module.

