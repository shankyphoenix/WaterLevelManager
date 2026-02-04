package link.phoenixwork.waterlevelmanager.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import link.phoenixwork.waterlevelmanager.data.ui.Sensor

@Composable
fun WaterLevelDisplay(name: String, sensor: Sensor?, modifier: Modifier, onClick: (id: String) -> Unit) {

    var  percent: Float = sensor?.distance ?: 0f
    var borderColor: Color = Color(0xFF1F2937)
    var waterColor: Color = Color(0xFF3B82F6)
    var backgroundColor: Color = Color(0xFFE5E7EB)
    var cornerRadiusPx: Float = 24f
    var borderWidthPx: Float = 9f
    val clamped = percent.coerceIn(0f, 100f)

    // Smooth animation when percent changes
    val fillFraction by animateFloatAsState(
        targetValue = clamped / 100f,
        animationSpec = tween(durationMillis = 600),
        label = "waterFill"
    )

    Box(modifier= modifier) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp, vertical = 15.dp)) {

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


        OutlinedButton(onClick = { onClick("2") }) {
            Text(name)
        }
    }

}


