package com.assistir.juntos.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assistir.juntos.ui.theme.*

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(24.dp),
    backgroundColor: Color = GlassWhite8,
    borderColor: Color = GlassWhite25,
    elevation: Dp = 8.dp,
    glowColor: Color = CyanGlow.copy(alpha = 0.2f),
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = modifier
            .liquidGlass(
                shape = shape,
                backgroundColor = backgroundColor,
                borderColor = borderColor,
                elevation = elevation,
                glowColor = glowColor
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = GlassWhite15,
    glowColor: Color = CyanGlow.copy(alpha = 0.4f),
    content: @Composable RowScope.() -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "button_scale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .liquidGlass(
                shape = RoundedCornerShape(16.dp),
                backgroundColor = backgroundColor,
                glowColor = glowColor,
                elevation = 6.dp
            )
            .clickable(enabled = enabled) {
                isPressed = true
                onClick()
            }
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            content = content
        )
    }
}

@Composable
fun GlassIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = TextPrimary,
    glowColor: Color = CyanGlow.copy(alpha = 0.3f)
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .liquidGlass(
                shape = CircleShape,
                backgroundColor = GlassWhite15,
                glowColor = glowColor,
                elevation = 4.dp
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SpeakingAvatar(
    name: String,
    avatarUrl: String?,
    isSpeaking: Boolean,
    isMuted: Boolean,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    // Pulse animation when speaking
    val infiniteTransition = rememberInfiniteTransition(label = "speaking_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isSpeaking) 1.12f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val ringColor = if (isSpeaking) EmeraldGlow else if (isMuted) Color.Red.copy(alpha = 0.7f) else CyanGlow

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .scale(pulseScale)
                .border(
                    width = if (isSpeaking) 3.dp else 1.5.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(ringColor, Color.White.copy(alpha = 0.5f), ringColor)
                    ),
                    shape = CircleShape
                )
                .liquidGlass(
                    shape = CircleShape,
                    backgroundColor = GlassWhite15,
                    glowColor = ringColor.copy(alpha = 0.5f),
                    elevation = 6.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            // Initials or Avatar
            Text(
                text = name.take(2).uppercase(),
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = (size.value * 0.3).sp
            )

            // Animated Mouth Indicator if speaking
            if (isSpeaking) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 4.dp)
                        .width(16.dp)
                        .height(6.dp)
                        .background(EmeraldGlow, CircleShape)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = name,
            color = TextSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
