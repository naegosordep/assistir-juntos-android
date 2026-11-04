package com.assistir.juntos.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.assistir.juntos.model.Participant
import com.assistir.juntos.ui.components.*
import com.assistir.juntos.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomScreen(roomId: String, onLeave: () -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
            setMediaItem(mediaItem)
            prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    val participants = remember {
        mutableStateListOf(
            Participant("1", "Você (Host)", isHost = true),
            Participant("2", "Beatriz", isSpeaking = true),
            Participant("3", "Carlos", isMuted = true),
            Participant("4", "Dandara")
        )
    }

    Scaffold(
        containerColor = ObsidianBg,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Sala: ${roomId.take(8)}", fontSize = 14.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                },
                navigationIcon = {
                    GlassIconButton(Icons.Default.ArrowBack, "Voltar", onClick = onLeave)
                },
                actions = {
                    GlassIconButton(Icons.Default.Settings, "Configurações", onClick = {})
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. Video Player Section with Glass Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = 16.dp)
                    .liquidGlass(
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                        elevation = 12.dp,
                        glowColor = CyanGlow.copy(alpha = 0.4f)
                    )
            ) {
                AndroidView(
                    factory = { ctx ->
                        PlayerView(ctx).apply {
                            player = exoPlayer
                            useController = true
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // Sync indicator overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .liquidGlass(
                            shape = androidx.compose.foundation.shape.CircleShape,
                            backgroundColor = EmeraldGlow.copy(alpha = 0.2f),
                            elevation = 4.dp
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(6.dp).background(EmeraldGlow, androidx.compose.foundation.shape.CircleShape))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sincronizado", color = EmeraldGlow, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // 2. Participants Section
            Text(
                "Participantes",
                color = TextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, top = 24.dp, bottom = 12.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(participants) { participant ->
                    SpeakingAvatar(
                        name = participant.name,
                        avatarUrl = null,
                        isSpeaking = participant.isSpeaking,
                        isMuted = participant.isMuted
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 3. Chat & Controls Bar
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                backgroundColor = GlassWhite15,
                glowColor = VioletGlow.copy(alpha = 0.3f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    GlassIconButton(Icons.Default.Mic, "Mic", onClick = {}, glowColor = EmeraldGlow)
                    GlassIconButton(Icons.Default.Videocam, "Cam", onClick = {})

                    GlassButton(
                        onClick = { /* Open Chat */ },
                        backgroundColor = CyanGlow.copy(alpha = 0.2f),
                        glowColor = CyanGlow
                    ) {
                        Icon(Icons.Default.ChatBubble, contentDescription = null, size = 18.dp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Chat", fontWeight = FontWeight.Bold)
                    }

                    GlassIconButton(Icons.Default.ScreenShare, "Share", onClick = {})
                    GlassIconButton(Icons.Default.Close, "Sair", onClick = onLeave, glowColor = Color.Red)
                }
            }
        }
    }
}

private fun Icon(imageVector: androidx.compose.ui.graphics.vector.ImageVector, contentDescription: String?, size: androidx.compose.ui.unit.Dp) {
    androidx.compose.material3.Icon(imageVector, contentDescription, modifier = Modifier.size(size))
}
