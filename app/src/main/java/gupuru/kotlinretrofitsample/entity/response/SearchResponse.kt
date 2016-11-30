package gupuru.kotlinretrofitsample.entity.response

import com.google.gson.annotations.SerializedName
import gupuru.kotlinretrofitsample.entity.Tracks

data class SearchResponse(
        @SerializedName("tracks")
        var tracks: Tracks
)