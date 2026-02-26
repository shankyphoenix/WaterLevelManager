package link.phoenixwork.waterlevelmanager.presentation.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import link.phoenixwork.waterlevelmanager.Greeting
import link.phoenixwork.waterlevelmanager.WaterLevelViewModel
import link.phoenixwork.waterlevelmanager.data.entity.WaterLevelEntity
import link.phoenixwork.waterlevelmanager.data.remote.Sensor
import link.phoenixwork.waterlevelmanager.ui.theme.WaterLevelManagerTheme
import link.phoenixwork.waterlevelmanager.ui.theme.backgroundBlack
import link.phoenixwork.waterlevelmanager.ui.theme.black
import kotlin.div

@Composable
fun WaterLevelDisplay(modifier: Modifier, onClick: (id: String) -> Unit) {


    val vm: WaterLevelViewModel = hiltViewModel()
    val items by vm.sensorData.collectAsState()
    val status by vm.sensorStatus.collectAsState()

    LaunchedEffect (Unit) { vm.startPolling(10_000L) }

    DisposableEffect (Unit) {
        onDispose { vm.stopPolling() }
    }
    Log.d("Shanky-SensorPageData", items.toString())

    //WaterLevelDisplayUI(modifier,items)
    SketchDashboardScreen(modifier,items)

}

@Composable
fun WaterLevelDisplayUI(modifier: Modifier,items: WaterLevelEntity? = null) {

    //var  percent: Float = items?.level?.toFloat() ?: 10f
    var  percent: Float =  10f
    var borderColor: Color = Color(0xFF1F2937)
    var waterColor: Color = Color(0xFF3B82F6)
    var backgroundColor: Color = Color(0xFFE5E7EB)
    var cornerRadiusPx: Float = 24f
    var borderWidthPx: Float = 9f
    val clamped = percent.coerceIn(0f, 100f)
    val fillFraction by animateFloatAsState(
        targetValue = clamped / 100f,
        animationSpec = tween(durationMillis = 600),
        label = "waterFill"
    )

    Column (modifier=Modifier.fillMaxSize()) {
        Row(modifier = Modifier.weight(1f)
            .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically) {
            Box(modifier= modifier,
                contentAlignment = Alignment.Center) {
                Box(modifier = modifier .fillMaxWidth(0.5f)) {
                    Canvas(modifier = Modifier.fillMaxSize()) {

                        val w = size.width
                        val h = size.height

                        // Tank background
                        drawRoundRect(
                            color = backgroundColor,
                            size = Size(w, h),
                            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                        )

                        // Water height (from bottom)
                        val waterHeight = h * fillFraction
                        val topY = h - waterHeight

                        // Water fill
                        drawRoundRect(
                            color = waterColor,
                            topLeft = Offset(0f, topY),
                            size = Size(w, waterHeight),
                            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                        )

                        // Tank border
                        drawRoundRect(
                            color = Color.Green
                            ,
                            size = Size(w, h),
                            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
                            style = Stroke(width = borderWidthPx)
                        )
                    }

                    Text(
                        text = "$clamped%",
                        style = TextStyle(fontSize = 26.sp, color = Color.Black)
                    )
                }
            }
        }

        Row(modifier = Modifier.weight(1f).fillMaxHeight()) {

            Text("Second Screen")
        }

    }
}

data class BarPoint(val label: String, val value: Float) // 0..1

@Preview(showBackground = true)
@Composable
fun SketchDashboardScreenPreview() {
    WaterLevelManagerTheme {
       // SketchDashboardScreen(Modifier);
    }
}

@Composable
fun SketchDashboardScreen(
    modifier: Modifier,
    sensorData: WaterLevelEntity?,
    fillPercent: Int = 58,
    lastUpdateText: String = "5 min ago",
    activeLabel: String = "green",
    activeColor: Color = Color(0xFF2ECC71),
    bars: List<BarPoint> = listOf(
        BarPoint("08", 0.78f),
        BarPoint("07", 0.50f),
        BarPoint("06", 0.92f),
        BarPoint("05", 0.68f),
        BarPoint("04", 0.95f),
        BarPoint("03", 0.25f),
        BarPoint("03", 0.25f),
        BarPoint("03", 0.25f),
    ),
) {
    val cs = MaterialTheme.colorScheme
    val stroke = cs.tertiary
    val text = cs.primary
    val fill = cs.tertiary

    var percentageValue = sensorData?.percentage ?: 20f;



    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cs.background)

                /*.border(2.dp, stroke, RoundedCornerShape(28.dp))*/
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().weight(0.2f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    TankFillWidget(
                        percent = percentageValue.coerceIn(0f, 100f),
                        strokeColor = stroke,
                        fillColor = fill,
                        modifier = Modifier.size(width = 150.dp, height = 190.dp)
                    )

                    StatusBlock(
                        fillPercent = percentageValue.coerceIn(0f, 100f),
                        lastUpdateText = lastUpdateText,
                        activeLabel = activeLabel,
                        activeColor = activeColor,
                        textColor = text,
                        strokeColor = stroke,
                        modifier = Modifier.weight(.5f)
                    )
                }

                BarChart(
                    bars = bars,
                    strokeColor = stroke,
                    textColor = text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                        .padding(top = 12.dp)
                )
                Row(modifier = Modifier.padding(10.dp).weight(0.1f)
                ) {

                    Text(color = black, text = sensorData?.serverMsg ?: "-")
                }
            }
        }
    }
}

@Composable
private fun TankFillWidget(
    percent: Float,
    strokeColor: Color,
    fillColor: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 3.dp
) {
    Canvas(modifier = modifier) {
        val sw = strokeWidth.toPx()

        // Outer body
        drawRoundRect(
            color = backgroundBlack,
            size = size,
            cornerRadius = CornerRadius(18f, 18f),
            style = Stroke(width = sw)
        )
        // Inner padding
        val pad = 10.dp.toPx()
        val innerTop = pad + 10.dp.toPx()
        val innerLeft = pad
        val innerW = size.width - pad * 2
        val innerH = size.height - pad * 2 - 10.dp.toPx()

        // Fill height
        val fillH = innerH * (percent / 100f)
        val fillTop = innerTop + (innerH - fillH)

        // Fill area
        drawRoundRect(
            color = fillColor,
            topLeft = Offset(innerLeft, fillTop),
            size = Size(innerW, fillH),
            cornerRadius = CornerRadius(14f, 14f)
        )

        // Hatch lines
        val spacing = 10.dp.toPx()
        val hatchW = 2.dp.toPx()
        val hatchAreaRight = innerLeft + innerW
        val hatchAreaTop = fillTop
        val hatchAreaBottom = fillTop + fillH

        var x = innerLeft - innerH
       /* while (x < hatchAreaRight + innerH) {
            val start = Offset(x, hatchAreaBottom)
            val end = Offset(x + innerH, hatchAreaTop)
            drawLine(
                color = strokeColor,
                start = start,
                end = end,
                strokeWidth = hatchW,
                alpha = 0.8f
            )
            x += spacing
        }*/
    }
}

@Composable
private fun StatusBlock(
    fillPercent: Float,
    lastUpdateText: String,
    activeLabel: String,
    activeColor: Color,
    textColor: Color,
    strokeColor: Color,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Fill : ${fillPercent}%",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Last  : $lastUpdateText",
            fontSize = 18.sp,
            color = textColor
        )
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Active :",
                fontSize = 18.sp,
                color = textColor
            )
            Spacer(Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .size(14.dp)
                    .background(activeColor, CircleShape)
                    .border(1.dp, strokeColor, CircleShape)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = activeLabel,
                fontSize = 18.sp,
                color = textColor
            )
        }
    }
}

@Composable
private fun BarChart(
    bars: List<BarPoint>,
    strokeColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    val safeBars = bars.map { it.copy(value = it.value.coerceIn(0f, 1f)) }

    Canvas(modifier = modifier) {
        val leftPad = 8.dp.toPx()
        val rightPad = 8.dp.toPx()
        val bottomPad = 28.dp.toPx()
        val topPad = 8.dp.toPx()

        val chartW = size.width - leftPad - rightPad
        val chartH = size.height - topPad - bottomPad

        // Baseline
        drawLine(
            color = strokeColor,
            start = Offset(leftPad, topPad + chartH),
            end = Offset(leftPad + chartW, topPad + chartH),
            strokeWidth = 3.dp.toPx()
        )

        val n = safeBars.size.coerceAtLeast(1)
        val gap = 14.dp.toPx()
        val barW = ((chartW - gap * (n - 1)) / n).coerceAtLeast(8.dp.toPx())

        safeBars.forEachIndexed { i, bar ->
            val h = chartH * bar.value
            val x = leftPad + i * (barW + gap)
            val y = topPad + (chartH - h)

            drawRoundRect(
                color = strokeColor,
                topLeft = Offset(x, y),
                size = Size(barW, h),
                cornerRadius = CornerRadius(10f, 10f)
            ) // Fill is default

            // Bar outline
            drawRoundRect(
                color = backgroundBlack,
                topLeft = Offset(x, y),
                size = Size(barW, h),
                cornerRadius = CornerRadius(10f, 10f),
                style = Stroke(width = 3.dp.toPx())
            )

            // Label (native canvas)
            drawContext.canvas.nativeCanvas.apply {
                val paint = android.graphics.Paint().apply {
                    color = textColor.toArgb()
                    textSize = 36f
                    isAntiAlias = true
                }
                val label = bar.label
                val textW = paint.measureText(label)
                drawText(
                    label,
                    x + (barW - textW) / 2f,
                    topPad + chartH + 46f,
                    paint
                )
            }
        }
    }
}

