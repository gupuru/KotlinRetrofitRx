package gupuru.kotlinretrofitsample.entity

import com.google.gson.annotations.SerializedName

data class Items(
        var name: String,
        @SerializedName("preview_url")
        var previewUrl: String,
        var uri: String,
        @SerializedName("duration_ms")
        var durationMs: Long,
        var album: Album
)