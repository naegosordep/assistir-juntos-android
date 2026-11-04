package com.assistir.juntos.model

import kotlinx.serialization.Serializable

@Serializable
data class Participant(
    val id: String,
    val name: String,
    val isSpeaking: Boolean = false,
    val isMuted: Boolean = false,
    val isCamerOn: Boolean = false,
    val isHost: Boolean = false
)

@Serializable
data class ChatMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val text: String,
    val timestamp: Long,
    val stickerId: String? = null
)

@Serializable
data class PlaybackState(
    val isPlaying: Boolean,
    val currentPosition: Long,
    val videoUrl: String? = null,
    val lastUpdated: Long
)
