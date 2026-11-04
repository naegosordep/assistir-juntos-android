package com.assistir.juntos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.JoinFull
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.assistir.juntos.ui.components.*
import com.assistir.juntos.ui.theme.*

@Composable
fun HomeScreen(onJoinRoom: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .ambientBackgroundGlow()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Title & Sophisticated Gradient
            Text(
                text = "Assistir Juntos",
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                brush = Brush.linearGradient(
                    colors = listOf(CyanGlow, VioletGlow, PinkSpecular)
                )
            )
            Text(
                text = "Experiência de Cinema em Grupo",
                color = TextSecondary,
                fontSize = 16.sp,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 48.dp)
            )

            // Glass Action Cards
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                glowColor = CyanGlow.copy(alpha = 0.4f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .liquidGlass(
                                shape = androidx.compose.foundation.shape.CircleShape,
                                backgroundColor = CyanGlow.copy(alpha = 0.2f),
                                glowColor = CyanGlow
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = CyanGlow)
                    }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text("Criar Nova Sala", fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Convide amigos para assistir", color = TextMuted, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                GlassButton(
                    onClick = { onJoinRoom("new-room-${System.currentTimeMillis()}") },
                    modifier = Modifier.fillMaxWidth(),
                    glowColor = CyanGlow
                ) {
                    Text("Começar Agora", fontWeight = FontWeight.Bold)
                }
            }

            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                glowColor = VioletGlow.copy(alpha = 0.4f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .liquidGlass(
                                shape = androidx.compose.foundation.shape.CircleShape,
                                backgroundColor = VioletGlow.copy(alpha = 0.2f),
                                glowColor = VioletGlow
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.JoinFull, contentDescription = null, tint = VioletGlow)
                    }
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text("Entrar com Código", fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Acesse uma sala já existente", color = TextMuted, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                GlassButton(
                    onClick = { /* Open Code Dialog */ },
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = GlassWhite8,
                    glowColor = VioletGlow
                ) {
                    Text("Digitar Código", color = TextSecondary)
                }
            }
        }

        // Bottom Decorative Element
        Text(
            text = "v1.0.0 Liquid Glass Engine",
            color = TextMuted,
            fontSize = 10.sp,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
