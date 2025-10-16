# Kubit Charts

[![Visit the website at kubit-lab.com/](https://img.shields.io/badge/visit-website-red.svg?logo=firefox)](https://kubit-lab.com/)
[![Maven Central Version](https://img.shields.io/maven-central/v/com.kubit-lab/charts?color=32cd32)](https://central.sonatype.com/artifact/com.kubit-lab/charts)
[![Kotlin version](https://img.shields.io/badge/Kotlin-2.2.21-yellow)]([https://kubit-lab.com/](https://kotlinlang.org/))
[![Compos version](https://img.shields.io/badge/Compose_BOM-2025.09.00-green)]([(https://developer.android.com/compose))



Kubit Charts is a library that provides a set of customizable and easy-to-use charts for Android applications.
It is built using Jetpack Compose and currently supports:

- Axis
- Line chart
- Bar chart
- Plot chart
- Pie chart
- Zoom area chart
- Chart legend
- Chart scaffold with scroll and zoom support

This library is an alpha version and is still under development. More charts and features will be added in future releases.

### Important

For more information, examples and detailed information about different type of supported charts, check [Kubit Charts doc summary](README_EXTENDED_DOC.md)

## Installation

Add these dependencies to your project

If you are using Gradle (KTS):
```
//Library
implementation("com.kubit-lab:charts:0.1.0-alpha1")

//Samples
implementation("com.kubit-lab:charts-samples:0.1.0-alpha1")
```

Inf you are using Gradle (Groovy):
```
//Library
implementation 'com.kubit-lab:charts:0.1.0-alpha1'

//Samples
implementation 'com.kubit-lab:charts-samples:0.1.0-alpha1'
```

Or, if you are using Maven:
```
<dependency>
    <groupId>com.kubit-lab</groupId>
    <artifactId>charts</artifactId>
    <version>0.1.0-alpha1</version>
</dependency>

<dependency>
    <groupId>com.kubit-lab</groupId>
    <artifactId>charts-samples</artifactId>
    <version>0.1.0-alpha1</version>
</dependency>
```

## Storybook
Check the storybook app to see the different charts on an emulator or a real device

![Storybook app](assets/storybook/storybook_scaffold.png)

## Gallery

### Chart scaffold

![ChartScaffold sample](assets/scaffold/scaffold_1.png)
![ChartScaffold sample](assets/scaffold/scaffold_2.png)

### Axes
![Multiple axes](assets/axis/axis_multiple.png)
![Horizontal axis](assets/axis/axis_horizontal.png)
![Horizontal axis](assets/axis/axis_vertical.png)
![Horizontal axis](assets/axis/axis_shade.png)

### Line Chart
![LineChart sample](assets/linechart/linechart_simple.png)
![LineChart sample](assets/linechart/linechart_shadow.png)
![LineChart sample](assets/linechart/linechart_dotted_shadow.png)
![LineChart sample](assets/linechart/linechart_multiline.png)
![LineChart sample](assets/linechart/linechart_multiline_shadow.png)

### Pie Chart

![PieChart sample](assets/piechart/piechart_basic.png)
![PieChart sample](assets/piechart/piechart_labels.png)
![PieChart sample](assets/piechart/piechart_labels_rotation.png)
![PieChart sample](assets/piechart/piechart_radius_border.png)
![PieChart sample](assets/piechart/piechart_semi_label_border.png)

### Bar Chart
![BarChart sample](assets/barchart/barchart_single_horizontal.png)
![BarChart sample](assets/barchart/barchart_stacked_horizontal.png)
![BarChart sample](assets/barchart/barchart_grouped_horizontal.png)
![BarChart sample](assets/barchart/barchart_multiple.png)
![BarChart sample](assets/barchart/barchart_linechart.png)

### Plot Chart
![PlotChart sample](assets/plotchart/plotchart_world.png)

### Zoom Area Chart
![Zoom area chart sample](assets/zoom/zoomarea_basic.png)

