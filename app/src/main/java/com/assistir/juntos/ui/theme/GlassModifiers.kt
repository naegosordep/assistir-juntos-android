package com.assistir.juntos.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Custom Modifier for Apple Liquid Glass effect:
 * - Translucent surface background with optional gradient specular tint.
 * - Glowing gradient border with high-light specular top-edge.
 * - Animated ambient sheen for hyper-realistic liquid glass reflections.
 */
@Composable
fun Modifier.liquidGlass(
    shape: Shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
    backgroundColor: Color = GlassWhite8,
    borderColor: Color = GlassWhite25,
    borderWidth: Dp = 1.dp,
    elevation: Dp = 8.dp,
    glowColor: Color = CyanGlow.copy(alpha = 0.3f),
    enableSheen: Boolean = true
): Modifier {
    val infiniteTransition = rememberInfiniteTransition(label = "glass_sheen")
    val sheenProgress by infiniteTransition.animateFloat(
        initialValue = -0.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sheen_anim"
    )

    return this
        .shadow(
            elevation = elevation,
            shape = shape,
            ambientColor = glowColor,
            spotColor = Color.Black
        )
        .clip(shape)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    backgroundColor,
                    backgroundColor.copy(alpha = backgroundColor.alpha * 0.6f)
                )
            )
        )
        .border(
            width = borderWidth,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.45f),
                    borderColor,
                    Color.White.copy(alpha = 0.05f)
                ),
                start = Offset.Zero,
                end = Offset.Infinite
            ),
            shape = shape
        )
        .drawWithContent {
            drawContent()
            if (enableSheen) {
                val width = size.width
                val height = size.height
                val sheenX = width * sheenProgress
                drawRect(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.08f),
                            Color.Transparent
                        ),
                        start = Offset(sheenX, 0f),
                        end = Offset(sheenX + width * 0.3f, height)
                    )
                )
            }
        }
}

/**
 * Background ambient light orb effect behind glass panels.
 */
@Composable
fun Modifier.ambientBackgroundGlow(): Modifier {
    val transition = rememberInfiniteTransition(label = "ambient_glow")
    val scale1 by transition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_scale"
    )

    return this.drawWithContent {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    CyanGlow.copy(alpha = 0.15f * scale1),
                    Color.Transparent
                ),
                center = Offset(size.width * 0.2f, size.height * 0.2f),
                radius = size.width * 0.7f
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    VioletGlow.copy(alpha = 0.15f * scale1),
                    Color.Transparent
                ),
                center = Offset(size.width * 0.8f, size.height * 0.8f),
                radius = size.width * 0.7f
            )
        )
        drawContent()
    }
}
