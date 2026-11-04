package com.assistir.juntos.network

import com.assistir.juntos.model.PlaybackState
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

class RoomWebSocketClient(private val client: HttpClient) {

    private var session: DefaultClientWebSocketSession? = null
    private val _playbackEvents = MutableSharedFlow<PlaybackState>()
    val playbackEvents = _playbackEvents.asSharedFlow()

    suspend fun connect(roomId: String, userId: String) {
        try {
            client.webSocket(method = HttpMethod.Get, host = "your-backend.com", path = "/room/$roomId?user=$userId") {
                session = this
                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        // Deserialize and emit playback state
                        // val state = Json.decodeFromString<PlaybackState>(text)
                        // _playbackEvents.emit(state)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun sendPlaybackState(state: PlaybackState) {
        session?.send(Frame.Text(Json.encodeToString(PlaybackState.serializer(), state)))
    }

    fun disconnect() {
        session = null
    }
}
